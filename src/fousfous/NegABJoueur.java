package fousfous;

import java.util.ArrayList;
import java.util.Arrays;

public class NegABJoueur implements IJoueur {
	
	protected int profMax = 4;
	
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
	
	private int negAB(int pronf, PlateauFousFous partie, int alpha, int beta, int parité){
		String joueur = parité > 0 ? this.joueurMax : this.joueurMin;
		if(pronf <= 0 || partie.finDePartie()){
			return parité * this.h.computeHeuristique(this.joueurMax, partie);
		}else{
			for(String c : partie.mouvementsPossibles(joueur)){
	    		PlateauFousFous tmp = partie.copy();
	    		tmp.play(c, joueur);
	    		alpha = Math.max(alpha, -negAB(pronf - 1, tmp, -beta, -alpha, -parité));
	    		if(alpha >= beta){
	    			return beta;
	    		}
			}
		}
		return alpha;
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
		return "NegAB";
	}
}
