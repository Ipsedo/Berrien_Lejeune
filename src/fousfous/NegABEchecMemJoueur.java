package fousfous;

import java.util.HashMap;

public class NegABEchecMemJoueur implements IJoueur {
	
	private HashMap<Integer, InfosPlateau> transpoTable;
	
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
		return null;
	}

	public void declareLeVainqueur(int colour) {
		// TODO Auto-generated method stub

	}
	
	public int negABEchecMem(int prof, PlateauFousFous partie, int alpha, int beta, int parité){
		int max = 0;
		
		int alphaInit = alpha;
		
		InfosPlateau entreeT = this.transpoTable.get(partie.getPlateau());
		
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
		this.transpoTable.put(partie.getPlateau().hashCode(), entreeT);
		
		return max;
	}

	public void mouvementEnnemi(String coup) {
		// TODO Auto-generated method stub

	}

	public String binoName() {
		// TODO Auto-generated method stub
		return "NegABEchecMem";
	}

}
