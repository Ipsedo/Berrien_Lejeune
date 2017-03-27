package fousfous;

public class NegABPhaseJoueur extends NegABJoueur {

	public String choixMouvement() {
		// TODO Auto-generated method stub
		switch(super.mPartie.getGamePhase()){
			case DEBUT:
				super.h = HeuristiqueFousFous.ffH3;
				this.profMax = 4;
				break;
			case MILIEU:
				super.h = HeuristiqueFousFous.ffH4;
				this.profMax = 4;
				break;
			case FIN:
				super.h = HeuristiqueFousFous.ffH4;
				this.profMax = 4;
				break;
		}
		
		return super.choixMouvement();
	}

	public String binoName(){
		return "NegABPhase";
	}
}
