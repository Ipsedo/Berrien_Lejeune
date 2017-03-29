package fousfous;

public class NegABFF2Joueur extends NegABJoueur {

	public void initJoueur(int myColour){
		super.initJoueur(myColour);
		super.h = HeuristiqueFousFous.ffH2;
	}
}
