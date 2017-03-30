package fousfous;

public class NegABPhaseJoueur extends NegABJoueur {
	
	public void initJoueur(int mycolour){
		super.initJoueur(mycolour);
		super.h = HeuristiqueFousFous.ffH4;
	}

	public String choixMouvement() {
		// TODO Auto-generated method stub
		switch(super.mPartie.getGamePhase()){
			case DEBUT:
				this.profMax = 6;
				break;
			case PREMILIEU:
				this.profMax = 7;
				break;
			case POSTMILIEU:
				this.profMax = 9;
				break;
			case FIN:
				this.profMax = 15;
				break;
		}
		
		return super.choixMouvement();
	}

	public String binoName(){
		return "NegABPhase";
	}
}
