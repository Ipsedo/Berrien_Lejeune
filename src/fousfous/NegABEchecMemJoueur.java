package fousfous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class NegABEchecMemJoueur implements IJoueur {
	
	protected int profMax = 10;
	
	private HashMap<Integer, InfosPlateau> transpoTable = new HashMap<Integer, InfosPlateau>();
	
	private int mColor;
	private String joueurMax;
	private String joueurMin;
	protected PlateauFousFous mPartie = new PlateauFousFous();
	
	private Heuristique h = HeuristiqueFousFous.ffH1;

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
		
		/** pas du tout sûr -> à verifier (mais ça marche et ça va supersupersupzerrrrr viteeee !!!!! */
		System.out.println("NegABEchecMem, profondeur max : " + this.profMax);
		
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
		
		alpha = -this.negABEchecMem(this.profMax - 1, tmpP, -beta, -alpha, -1);
		
		for(String c : coupsPossibles){
			tmpP = this.mPartie.copy();
			tmpP.play(c, this.joueurMax);
			int newVal = -this.negABEchecMem(this.profMax - 1, tmpP, -beta, -alpha, -1);
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
	
	private int negABEchecMem(int prof, PlateauFousFous partie, int alpha, int beta, int parité){
		int max = 0;
		
		int alphaInit = alpha;
		
		InfosPlateau entreeT = this.transpoTable.get(partie.getPlateauHashCode());
		
		if(entreeT != null && entreeT.getProf() >= prof){ // >= prof ou <= prof vu que notre prof est decrementale
			switch(entreeT.getFlag()){
				// verifier que le switch va bien direct dans la bonne case et passe pas par les autres -> deja eu un bug comme ça
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
		String meilleurCoup = "";
		String joueur = parité > 0 ? this.joueurMax : this.joueurMin;
		if(prof == 0 || partie.finDePartie()){
			max = parité * h.computeHeuristique(joueur, partie);
		} else {
			max = Integer.MIN_VALUE + 1;
			if(entreeT != null){
				meilleurCoup = entreeT.getMeilleurCoup();
			}
			//forall coupPossible et pas pigé :  s = succ(n, c) + meilleurCoup en 1er
			
			/** Faire le meilleur coup en 1er **/
			for(String c : partie.mouvementsPossibles(joueur)){
	    		PlateauFousFous tmp = partie.copy();
	    		tmp.play(c, joueur);
	    		max = Math.max(max, -this.negABEchecMem(prof - 1, tmp, -beta, -alpha, -parité));
	    		if(max > alpha){
	    			alpha = max;
	    			meilleurCoup = c;
	    		}
	    		if(alpha >= beta){
	    			break;
	    		}
			}
			
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
		this.transpoTable.put(partie.getPlateauHashCode(), entreeT); //besoin de remplacer l'ancienne valeur de entreeT si celle-ci pas null ? (ou bien elle est écrasée automatiquement ?)
		
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
