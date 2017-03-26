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
	
	public static Heuristique ffH2 = new Heuristique(){

		public int computeHeuristique(String joueur, Partie1 partie) {
			PlateauFousFous plateau = (PlateauFousFous) partie;
			if(joueur == PlateauFousFous.JBLANC){
				return (16 - plateau.getNbNoir() - (16 - plateau.getNbBlanc())) + (plateau.getNbMenaces(PlateauFousFous.JBLANC) - plateau.getNbMenaces(PlateauFousFous.JNOIR));
				//return (plateau.getNbBlanc() - plateau.getNbNoir()) + (plateau.getNbMenaces(PlateauFousFous.JBLANC) - plateau.getNbMenaces(PlateauFousFous.JNOIR));
			} else {
				return (16 - plateau.getNbBlanc() - (16 - plateau.getNbNoir())) + (plateau.getNbMenaces(PlateauFousFous.JNOIR) - plateau.getNbMenaces(PlateauFousFous.JBLANC));
				//return (plateau.getNbNoir() - plateau.getNbBlanc()) + (plateau.getNbMenaces(PlateauFousFous.JNOIR) - plateau.getNbMenaces(PlateauFousFous.JBLANC));
			}
		}
		
	};

}
