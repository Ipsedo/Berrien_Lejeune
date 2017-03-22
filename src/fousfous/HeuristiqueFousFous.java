package fousfous;

public class HeuristiqueFousFous {
	
	public static Heuristique ffH1 = new Heuristique(){

		public int computeHeuristique(String joueur, Partie1 partie) {
			PlateauFousFous plateau = (PlateauFousFous) partie;
			if(joueur == PlateauFousFous.JBLANC){
				return plateau.getNbBlanc() - plateau.getNbNoir();
			} else {
				return plateau.getNbNoir() - plateau.getNbBlanc();
			}
		}
		
	};

}
