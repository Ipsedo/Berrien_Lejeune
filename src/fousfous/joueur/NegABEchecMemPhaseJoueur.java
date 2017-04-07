package fousfous.joueur;

public class NegABEchecMemPhaseJoueur extends NegABEchecMemJoueur {
	
	public String choixMouvement() {
		// TODO Auto-generated method stub
		switch(super.mPartie.getGamePhase()){
			case DEBUT:
				this.profMax = 5;
				break;
			case PREMILIEU:
				this.profMax = 6;
				break;
			case POSTMILIEU:
				this.profMax = 7;
				break;
			case FIN:
				this.profMax = 10;
				break;
		}
		
		return super.choixMouvement();
	}

	public String binoName(){
		return "NegABEchecMemPhase";
	}
}
