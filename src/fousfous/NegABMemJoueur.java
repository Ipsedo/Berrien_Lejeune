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
		String meilleurCoup = "";
		String joueur = parité > 0 ? this.joueurMax : this.joueurMin;
		String[] coupsPossibles = partie.mouvementsPossibles(joueur);
		
		InfosPlateau entreeT = this.transpoTable.get(partie.hashCode());
		
		boolean contains = false;
		int indMeilCoup = -1;
		for(int i = 0; entreeT != null && i < coupsPossibles.length; i++){
			if(coupsPossibles[i].equals(entreeT.getMeilleurCoup())){
				contains = true;
				indMeilCoup = i;
				meilleurCoup = coupsPossibles[i];
			}
		}
		if(entreeT != null && !contains){
			entreeT = null;
		}
		
		if(entreeT != null && entreeT.getProf() >= prof){
			alpha = Math.max(entreeT.getVal(), alpha);
			if(alpha >= beta){
				return entreeT.getVal();
			}
		}
		
		int res = alpha;
		
		if(prof <= 0 || partie.finDePartie()){
			return parité * this.h.computeHeuristique(this.joueurMax, partie);
		}else{
			if(entreeT != null){
				String tmpC = coupsPossibles[0];
				coupsPossibles[0] = meilleurCoup;
				coupsPossibles[indMeilCoup] = tmpC;
			}			
			for(int i = 0; i < coupsPossibles.length; i++){
	    		PlateauFousFous tmp = partie.copy();
	    		tmp.play(coupsPossibles[i], joueur);
	    		int tmpA = -negAB(prof - 1, tmp, -beta, -alpha, -parité);
	    		if(tmpA > alpha){
	    			alpha = tmpA;
	    			res = alpha;
	    			meilleurCoup = coupsPossibles[i];
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
		/*if(res <= alphaInit){
			entreeT.setFlag(InfosPlateau.Flag.BSUP);
		} else if(res >= beta){
			entreeT.setFlag(InfosPlateau.Flag.BINF);
		} else {
			entreeT.setFlag(InfosPlateau.Flag.EXACTVAL);
		}*/
		entreeT.setProf(prof);
		this.transpoTable.put(partie.hashCode(), entreeT);
		return res;
	}

	public String choixMouvement() {
		// TODO Auto-generated method stub
		System.out.println("NegABMem, profondeur max : " + this.profMax);
		long t1 = System.currentTimeMillis();
		
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
		System.out.println("A joué : " + meilleurCoup + " en " + (System.currentTimeMillis() - t1));
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
