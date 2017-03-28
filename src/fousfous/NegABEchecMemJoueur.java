package fousfous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class NegABEchecMemJoueur implements IJoueur {
	
	protected int profMax = 6;
	
	private HashMap<PlateauFousFous, InfosPlateau> transpoTable = new HashMap<PlateauFousFous, InfosPlateau>();
	
	private int mColor;
	private String joueurMax;
	private String joueurMin;
	protected PlateauFousFous mPartie = new PlateauFousFous();
	
	protected Heuristique h = HeuristiqueFousFous.ffH1prime;

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

	public String choixMouvement() {
		// TODO Auto-generated method stub
		
		/** pas du tout sûr piur cette method -> à verifier */
		
		System.out.println("NegABEchecMem, profondeur max : " + this.profMax);
		
		ArrayList<String> coupsPossibles = new ArrayList<String>(Arrays.asList(this.mPartie.mouvementsPossibles(this.joueurMax)));
		
		if(coupsPossibles.isEmpty()){
			return "xxxxx";
		}
		
		/*int alpha = Integer.MIN_VALUE + 1;
		int beta = Integer.MAX_VALUE - 1;*/
		int alpha = -50;
		int beta = 50;
		
	    PlateauFousFous tmpP = this.mPartie.copy();
	    
		String meilleurCoup = coupsPossibles.get(0);
		coupsPossibles.remove(0);
		tmpP.play(meilleurCoup, this.joueurMax);
		
		int max = Integer.MIN_VALUE + 1;
		
		max = Math.max(max, -this.negABEchecMem(this.profMax - 1, tmpP, -beta, -alpha, -1));
		
		for(String c : coupsPossibles){
			tmpP = this.mPartie.copy();
			tmpP.play(c, this.joueurMax);
			max = Math.max(max, -this.negABEchecMem(this.profMax - 1, tmpP, -beta, -alpha, -1));
			if(max > alpha){
				meilleurCoup = c;
				alpha = max;
			}
			/*if(alpha >= beta){
				break;
			}*/
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
	
	private int negABEchecMem(int prof, PlateauFousFous partie, int alpha, int beta, int parité){
		int max = 0;
		
		int alphaInit = alpha;
		
		String meilleurCoup = "";
		String joueur = parité > 0 ? this.joueurMax : this.joueurMin;
		ArrayList<String> coupsPossibleList = new ArrayList<String>(Arrays.asList(partie.mouvementsPossibles(joueur)));
		
		InfosPlateau entreeT = null;
		if(this.transpoTable.containsKey(partie) && coupsPossibleList.contains(this.transpoTable.get(partie).getMeilleurCoup())){
			entreeT = this.transpoTable.get(partie);
		}
		
		if(entreeT != null && entreeT.getProf() >= prof){
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
		
		if(prof == 0 || partie.finDePartie()){
			return parité * h.computeHeuristique(this.joueurMax, partie); // return ou max <- heuristique ?
		} else {
			max = Integer.MIN_VALUE + 1;
			if(entreeT != null){
				meilleurCoup = entreeT.getMeilleurCoup();
				int i = coupsPossibleList.indexOf(meilleurCoup);
				String tmpCoup = coupsPossibleList.get(0);
				coupsPossibleList.add(i, tmpCoup);
				coupsPossibleList.add(0, meilleurCoup);
			}
			String[] coupsPossible = coupsPossibleList.toArray(new String[coupsPossibleList.size()]);
			//forall coupPossible et pas pigé :  s = succ(n, c) + meilleurCoup en 1er
			
			/** Faire le meilleur coup en 1er **/
			for(String c : coupsPossible){
	    		PlateauFousFous tmp = partie.copy();
	    		tmp.play(c, joueur);
	    		max = Math.max(max, -this.negABEchecMem(prof - 1, tmp, -beta, -alpha, -parité));
	    		if(max > alpha){
	    			alpha = max;
	    			meilleurCoup = c;
	    		}
	    		if(alpha >= beta){
	    			meilleurCoup = c;
	    			break;
	    		}
			}
			//System.out.println("meilleur coup avant " + meilleurCoup);
			meilleurCoup = meilleurCoup.isEmpty() ? coupsPossibleList.get(0) : meilleurCoup; // pour eviter de creer des infosPlateau avec meilleur coup null
			//System.out.println("meilleur coup après " + meilleurCoup);
		}
		
		if(entreeT == null){
			entreeT = new InfosPlateau(); // vu que si le get depuis la hashMap ne donne rien faut bien creer une instance
		}
		entreeT.setVal(max);
		entreeT.setMeilleurCoup(meilleurCoup);
		if(max <= alphaInit){
			entreeT.setFlag(InfosPlateau.Flag.BSUP);
		} else if(max >= beta){
			entreeT.setFlag(InfosPlateau.Flag.BINF);
		} else {
			entreeT.setFlag(InfosPlateau.Flag.EXACTVAL);
		}
		entreeT.setProf(prof);
		this.transpoTable.put(partie, entreeT); //besoin de remplacer l'ancienne valeur de entreeT si celle-ci pas null ? (ou bien elle est écrasée automatiquement ?)
		
		return max;
	}

	public void mouvementEnnemi(String coup) {
		// TODO Auto-generated method stub
		this.mPartie.play(coup, this.joueurMin);
	}

	public String binoName() {
		// TODO Auto-generated method stub
		return "NegABEchecMem";
	}

}
