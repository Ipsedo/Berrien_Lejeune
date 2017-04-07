package fousfous.joueur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import fousfous.HeuristiqueFousFous;
import fousfous.InfosPlateau;
import fousfous.PlateauFousFous;

public class NegABMemJoueur extends Joueur {
	
	protected HashMap<Integer, InfosPlateau> transpoTable = new HashMap<Integer, InfosPlateau>();

	public void initJoueur(int mycolour) {
		// TODO Auto-generated method stub
		super.initJoueur(mycolour);
		this.profMax = 6;
		this.h = HeuristiqueFousFous.ffH1;
	}

	/**
	 * @param prof
	 * @param partie
	 * @param alpha
	 * @param beta
	 * @param parité
	 * @return
	 */
	private int negAB(int prof, PlateauFousFous partie, int alpha, int beta, int parité){				
		if(prof <= 0 || partie.finDePartie()){
			return parité * this.h.computeHeuristique(this.joueurMax, partie);
		}else{
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
					break;
				}
			}
			if(entreeT != null && !contains){
				entreeT = null;
			}
			
			if(entreeT != null && entreeT.getProf() >= prof){
				return entreeT.getVal();
			}
			
			int res = alpha;
			if(entreeT != null){
				String tmpC = coupsPossibles[0];
				coupsPossibles[0] = meilleurCoup;
				coupsPossibles[indMeilCoup] = tmpC;
			}
			
			for(String c : coupsPossibles){
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
	    		}
			}
			
			if(entreeT == null){
				entreeT = new InfosPlateau();
			}
			entreeT.setVal(res);
			entreeT.setMeilleurCoup(meilleurCoup);
			entreeT.setProf(prof);
			this.transpoTable.put(partie.hashCode(), entreeT);
			return res;
		}
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
		this.transpoTable.clear();
		return meilleurCoup;
	}


	public String binoName() {
		// TODO Auto-generated method stub
		return "NegABMem";
	}

}
