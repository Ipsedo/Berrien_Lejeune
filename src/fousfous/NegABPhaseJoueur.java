package fousfous;

public class NegABPhaseJoueur extends NegABJoueur {

	public String choixMouvement() {
		// TODO Auto-generated method stub
		switch(super.mPartie.getGamePhase()){
			case DEBUT:
				super.h = HeuristiqueFousFous.ffH1prime;
				this.profMax = 6;
				break;
			case MILIEU:
				super.h = HeuristiqueFousFous.ffH1prime;
				this.profMax = 7;
				break;
			case FIN:
				super.h = HeuristiqueFousFous.ffH1prime;
				this.profMax = 10;
				break;
		}
		
		return super.choixMouvement();
	}

	public String binoName(){
		return "NegABPhase";
	}
}
