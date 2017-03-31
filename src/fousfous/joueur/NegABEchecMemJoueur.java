package fousfous.joueur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import fousfous.HeuristiqueFousFous;
import fousfous.InfosPlateau;
import fousfous.PlateauFousFous;
import fousfous.InfosPlateau.Flag;

public class NegABEchecMemJoueur extends Joueur {
	
	private HashMap<Integer, InfosPlateau> transpoTable = new HashMap<Integer, InfosPlateau>();
	

	public void initJoueur(int mycolour) {
		super.initJoueur(mycolour);
		this.profMax = 6;
		this.h = HeuristiqueFousFous.ffH4;
	}

	public String choixMouvement() {
		// TODO Auto-generated method stub
		
		/** pas du tout sûr piur cette method -> à verifier */
		
		System.out.println(this.binoName() + ", profondeur max : " + this.profMax);
		
		ArrayList<String> coupsPossibles = new ArrayList<String>(Arrays.asList(this.mPartie.mouvementsPossibles(this.joueurMax)));
		
		if(coupsPossibles.isEmpty()){
			return "xxxxx";
		}
		
		/*int alpha = Integer.MIN_VALUE + 1;
		int beta = Integer.MAX_VALUE - 1;*/
		int alpha = 1;
		int beta = 2;
		
	    PlateauFousFous tmpP = this.mPartie.copy();
	    
		String meilleurCoup = coupsPossibles.get(0);
		coupsPossibles.remove(0);
		tmpP.play(meilleurCoup, this.joueurMax);
		
		int max = Integer.MIN_VALUE + 1;
		
		max = Math.max(max, -this.negABEchecMem(this.profMax - 1, tmpP, -beta, -alpha, -1));
		
		for(String c : coupsPossibles){
			tmpP = this.mPartie.copy();
			tmpP.play(c, this.joueurMax);
			int tmpMax = -this.negABEchecMem(this.profMax - 1, tmpP, -beta, -alpha, -1);
			System.out.println("tmpMax : " + tmpMax + ", max : " + max);
			if(tmpMax > max){
				meilleurCoup = c;
				max = tmpMax;
			}
			/*max = Math.max(max, -this.negABEchecMem(this.profMax - 1, tmpP, -beta, -alpha, -1));
			if(max > alpha){
				meilleurCoup = c;
				alpha = max;
			}
			if(alpha >= beta){
				break;
			}*/
		}
		this.mPartie.play(meilleurCoup, this.joueurMax);
		System.out.println("A joué : " + meilleurCoup);
		System.out.println(this.mPartie);
		//this.transpoTable.clear();
		return meilleurCoup;
		
	}
	
	private int negABEchecMem(int prof, PlateauFousFous partie, int alpha, int beta, int parité){
		int max = 0;
		
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
			for(String c : coupsPossible){
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
			meilleurCoup = meilleurCoup.isEmpty() ? coupsPossibleList.get(0) : meilleurCoup; // pour eviter de creer des infosPlateau avec meilleur coup null
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
		this.transpoTable.put(partie.hashCode(), entreeT); //besoin de remplacer l'ancienne valeur de entreeT si celle-ci pas null ? (ou bien elle est écrasée automatiquement ?)
		
		return max;
	}

	public String binoName() {
		return "NegABEchecMem";
	}

}
