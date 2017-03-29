package fousfous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class NegABMemJoueur implements IJoueur {

	protected int profMax = 6;
	
	private int mColor;
	private String joueurMax;
	private String joueurMin;
	protected PlateauFousFous mPartie = new PlateauFousFous();
	
	protected Heuristique h = HeuristiqueFousFous.ffH1;
	
	private HashMap<Integer, InfosPlateau> transpoTable = new HashMap<Integer, InfosPlateau>();

	public void initJoueur(int mycolour) {
		// TODO Auto-generated method stub
		this.mColor = mycolour;
		if(this.mColor == -1){
			this.joueurMax = PlateauFousFous.JBLANC;
			this.joueurMin = PlateauFousFous.JNOIR;
		} else {
			this.joueurMin = PlateauFousFous.JBLANC;
			this.joueurMax = PlateauFousFous.JNOIR;
		}
	}

	public int getNumJoueur() {
		// TODO Auto-generated method stub
		return this.mColor;
	}

	private int negAB(int prof, PlateauFousFous partie, int alpha, int beta, int parité){		
		int alphaInit = alpha;
		
		String meilleurCoup = "";
		String joueur = parité > 0 ? this.joueurMax : this.joueurMin;
		ArrayList<String> coupsPossibleList = new ArrayList<String>(Arrays.asList(partie.mouvementsPossibles(joueur)));
		
		InfosPlateau entreeT = this.transpoTable.get(partie.hashCode());
		if(entreeT != null && !coupsPossibleList.contains(this.transpoTable.get(partie.hashCode()).getMeilleurCoup())){
			entreeT = null;
		}
		
		if(entreeT != null && entreeT.getProf() >= prof){
			//System.out.println("Avant switch, entreeT, val : " + entreeT.getVal() + ", prof : " + entreeT.getProf() + ", meilleurCoup : " + entreeT.getMeilleurCoup() + " flag : " + entreeT.getFlag());
			switch(entreeT.getFlag()){
				case BINF:
					alpha = Math.max(alpha, entreeT.getVal());
					break;
				case BSUP:
					beta = Math.min(beta, entreeT.getVal());
					break;
				case EXACTVAL:
					return entreeT.getVal();			
			}
			if(alpha >= beta){
				return entreeT.getVal();
			}
		}
		
		int res = alpha;
		
		if(prof <= 0 || partie.finDePartie()){
			return parité * this.h.computeHeuristique(this.joueurMax, partie);
		}else{
			if(entreeT != null){
				meilleurCoup = entreeT.getMeilleurCoup();
				int i = coupsPossibleList.indexOf(meilleurCoup);
				String tmpCoup = coupsPossibleList.get(0);
				coupsPossibleList.add(i, tmpCoup);
				coupsPossibleList.add(0, meilleurCoup);
			}
			
			String[] coupsPossible = coupsPossibleList.toArray(new String[coupsPossibleList.size()]);
			
			for(String c : coupsPossible){
	    		PlateauFousFous tmp = partie.copy();
	    		tmp.play(c, joueur);
	    		int tmpA = -negAB(prof - 1, tmp, -beta, -alpha, -parité);
	    		if(tmpA > alpha){
	    			alpha = tmpA;
	    			res = alpha;
	    			meilleurCoup = c;
	    		}
	    		if(alpha >= beta){
	    			res = beta;
	    			break;
	    			//return beta;
	    		}
			}
		}
		if(entreeT == null){
			entreeT = new InfosPlateau(); // vu que si le get depuis la hashMap ne donne rien faut bien creer une instance
		}
		entreeT.setVal(res);
		entreeT.setMeilleurCoup(meilleurCoup);
		if(res <= alphaInit){
			entreeT.setFlag(InfosPlateau.Flag.BSUP);
		} else if(res >= beta){
			entreeT.setFlag(InfosPlateau.Flag.BINF);
		} else {
			entreeT.setFlag(InfosPlateau.Flag.EXACTVAL);
		}
		entreeT.setProf(prof);
		this.transpoTable.put(partie.hashCode(), entreeT);
		return res;
	}

	public String choixMouvement() {
		// TODO Auto-generated method stub
		System.out.println("NegAB, profondeur max : " + this.profMax);
		
		ArrayList<String> coupsPossibles = new ArrayList<String>(Arrays.asList(this.mPartie.mouvementsPossibles(this.joueurMax)));
		
		if(coupsPossibles.isEmpty()){
			return "xxxxx";
		}
		
		int alpha = Integer.MIN_VALUE + 1;
		int beta = Integer.MAX_VALUE - 1;
		
	    PlateauFousFous tmpP = this.mPartie.copy();
	    
		String meilleurCoup = coupsPossibles.get(0);
		coupsPossibles.remove(0);
		tmpP.play(meilleurCoup, this.joueurMax);
		
		alpha = -this.negAB(this.profMax - 1, tmpP, -beta, -alpha, -1);
		
		for(String c : coupsPossibles){
			tmpP = this.mPartie.copy();
			tmpP.play(c, this.joueurMax);
			int newVal = -this.negAB(this.profMax - 1, tmpP, -beta, -alpha, -1);
			if(newVal > alpha){
				meilleurCoup = c;
				alpha = newVal;
			}
		}
		this.mPartie.play(meilleurCoup, this.joueurMax);
		System.out.println("A joué : " + meilleurCoup);
		System.out.println(this.mPartie);
		return meilleurCoup;
	}

	public void declareLeVainqueur(int colour) {
		// TODO Auto-generated method stub
		if(colour == this.mColor){
			System.out.println("Hasta la vista, baby");
		}

	}

	public void mouvementEnnemi(String coup) {
		// TODO Auto-generated method stub
		this.mPartie.play(coup, this.joueurMin);

	}

	public String binoName() {
		// TODO Auto-generated method stub
		return "NegABMem";
	}

}
