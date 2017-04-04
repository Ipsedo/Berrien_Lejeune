package fousfous.joueur;

import fousfous.HeuristiqueFousFous;
import fousfous.PlateauFousFous;

public class NegABMemPhaseJoueur extends NegABMemJoueur {
	
	private PlateauFousFous.PhaseJeu oldPhase;
	
	public void initJoueur(int mycolour) {
		// TODO Auto-generated method stub
		super.initJoueur(mycolour);
		this.profMax = 6;
		this.h = HeuristiqueFousFous.ffH1;
		this.oldPhase = super.mPartie.getGamePhase();
	}
	
	public String choixMouvement() {
		if(this.oldPhase != super.mPartie.getGamePhase()){
			switch(super.mPartie.getGamePhase()){
			case DEBUT:
				this.profMax = 6;
				break;
			case PREMILIEU:
				this.profMax = 8;
				super.transpoTable.clear();
				break;
			case POSTMILIEU:
				this.profMax = 8;
				break;
			case FIN:
				this.profMax = 10;
				super.transpoTable.clear();
				break;
			}
			System.out.println("changé");
			this.oldPhase = super.mPartie.getGamePhase();
		}
		return super.choixMouvement();
	}
	
	public String binoName(){
		return "NegABMemPhase";
	}

}
