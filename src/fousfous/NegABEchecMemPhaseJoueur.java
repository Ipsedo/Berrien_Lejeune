package fousfous;

public class NegABEchecMemPhaseJoueur extends NegABEchecMemJoueur {
	
	public String choixMouvement() {
		// TODO Auto-generated method stub
		switch(super.mPartie.getGamePhase()){
			case DEBUT:
				super.h = HeuristiqueFousFous.ffH3;
				this.profMax = 5;
				break;
			case MILIEU:
				super.h = HeuristiqueFousFous.ffH4;
				this.profMax = 6;
				break;
			case FIN:
				super.h = HeuristiqueFousFous.ffH1prime;
				this.profMax = 7;
				break;
		}
		
		return super.choixMouvement();
	}

	public String binoName(){
		return "NegABEchecMemPhase";
	}
}
