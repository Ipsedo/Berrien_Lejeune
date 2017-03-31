package fousfous.joueur;

import fousfous.HeuristiqueFousFous;

public class NegABFF2Joueur extends NegABJoueur {

	public void initJoueur(int myColour){
		super.initJoueur(myColour);
		super.profMax = 6;
		super.h = HeuristiqueFousFous.ffH1;
	}
}
