package fousfous;

public class NegABPhaseJoueur extends NegABJoueur {

	public String choixMouvement() {
		// TODO Auto-generated method stub
		switch(super.mPartie.getGamePhase()){
			case DEBUT:
				this.profMax = 5;
				break;
			case MILIEU:
				this.profMax = 7;
				break;
			case FIN:
				this.profMax = 9;
				break;
		}
		
		return super.choixMouvement();
	}

	public String binoName(){
		return "NegABPhase";
	}
}
