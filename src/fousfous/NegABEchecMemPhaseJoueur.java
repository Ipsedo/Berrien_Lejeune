package fousfous;

public class NegABEchecMemPhaseJoueur extends NegABEchecMemJoueur {
	
	public String choixMouvement() {
		// TODO Auto-generated method stub
		switch(super.mPartie.getGamePhase()){
			case DEBUT:
				this.profMax = 10;
				break;
			case MILIEU:
				this.profMax = 20;
				break;
			case FIN:
				this.profMax = 30;
				break;
		}
		
		return super.choixMouvement();
	}

	public String binoName(){
		return "NegABEchecMemPhase";
	}
}
