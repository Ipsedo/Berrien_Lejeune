package fousfous;

public class HeuristiqueFousFous {
	
	public int getH(String joueur, PlateauFousFous plateau){
		if(joueur == PlateauFousFous.JBLANC){
			return plateau.getNbBlanc() - plateau.getNbNoir();
		} else {
			return plateau.getNbNoir() - plateau.getNbBlanc();
		}
	}

}
