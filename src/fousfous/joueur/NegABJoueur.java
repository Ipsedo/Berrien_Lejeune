package fousfous.joueur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import fousfous.HeuristiqueFousFous;
import fousfous.PlateauFousFous;

public class NegABJoueur extends Joueur {

	public void initJoueur(int mycolour) {
		// TODO Auto-generated method stub
		super.initJoueur(mycolour);
		super.h = HeuristiqueFousFous.ffH1;
		super.profMax = 6;
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
		System.out.println(this.sdf.format(new Date()) + ", " + this.binoName() + ", profondeur max : " + this.profMax);
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
		long tmpTime = (System.currentTimeMillis() - t1);
		this.totalTime += tmpTime;
		System.out.println("A joué : " + meilleurCoup + " en " + tmpTime + " / " + this.totalTime);
		System.out.println(this.mPartie);
		return meilleurCoup;
	}

	public String binoName(){
		return "NegAB";
	}
}
