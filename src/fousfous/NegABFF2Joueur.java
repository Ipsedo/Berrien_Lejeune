package fousfous;

public class NegABFF2Joueur extends NegABJoueur {

	public void init(int myColour){
		super.initJoueur(myColour);
		super.h = HeuristiqueFousFous.ffH2;
	}
}
