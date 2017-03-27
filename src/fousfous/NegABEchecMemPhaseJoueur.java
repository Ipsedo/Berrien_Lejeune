package fousfous;

public class NegABEchecMemPhaseJoueur extends NegABEchecMemJoueur {
	
	public String choixMouvement() {
		// TODO Auto-generated method stub
		switch(super.mPartie.getGamePhase()){
			case DEBUT:
				super.h = HeuristiqueFousFous.ffH1;
				this.profMax = 7;
				break;
			case MILIEU:
				super.h = HeuristiqueFousFous.ffH2;
				this.profMax = 10;
				break;
			case FIN:
				super.h = HeuristiqueFousFous.ffH3;
				this.profMax = 14;
				break;
		}
		
		return super.choixMouvement();
	}

	public String binoName(){
		return "NegABEchecMemPhase";
	}
}
