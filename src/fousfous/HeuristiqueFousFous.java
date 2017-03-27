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
	
	public static Heuristique ffH1bis = new Heuristique(){

		public int computeHeuristique(String joueur, Partie1 partie) {
			PlateauFousFous plateau = (PlateauFousFous) partie;
			if(joueur == PlateauFousFous.JBLANC){
				return plateau.getNbBlanc() - plateau.getNbNoir() * 2;
			} else {
				return plateau.getNbNoir() - plateau.getNbBlanc() * 2;
			}
		}
		
	};
	
	public static Heuristique ffH1prime = new Heuristique(){

		public int computeHeuristique(String joueur, Partie1 partie) {
			PlateauFousFous plateau = (PlateauFousFous) partie;
			if(joueur == PlateauFousFous.JBLANC){
				return (int) (Math.pow(plateau.getNbBlanc() - plateau.getNbNoir(), 3d));
			} else {
				return (int) (Math.pow(plateau.getNbNoir() - plateau.getNbBlanc(), 3d));
			}
		}
		
	};
	
	public static Heuristique ffH2 = new Heuristique(){

		public int computeHeuristique(String joueur, Partie1 partie) {
			PlateauFousFous plateau = (PlateauFousFous) partie;
			if(joueur == PlateauFousFous.JBLANC){
				//return 16 - plateau.getNbNoir();// + plateau.getNbMenaces(PlateauFousFous.JBLANC);
				return (16 - plateau.getNbNoir() - (16 - plateau.getNbBlanc())) * 10 + (plateau.getNbMenaces(PlateauFousFous.JBLANC) - plateau.getNbMenaces(PlateauFousFous.JNOIR));
				//return (plateau.getNbBlanc() - plateau.getNbNoir()) + (plateau.getNbMenaces(PlateauFousFous.JBLANC) - plateau.getNbMenaces(PlateauFousFous.JNOIR));
			} else {
				//return 16 - plateau.getNbBlanc();// + plateau.getNbMenaces(PlateauFousFous.JNOIR);
				return (16 - plateau.getNbBlanc() - (16 - plateau.getNbNoir())) * 10 + (plateau.getNbMenaces(PlateauFousFous.JNOIR) - plateau.getNbMenaces(PlateauFousFous.JBLANC));
				//return (plateau.getNbNoir() - plateau.getNbBlanc()) + (plateau.getNbMenaces(PlateauFousFous.JNOIR) - plateau.getNbMenaces(PlateauFousFous.JBLANC));
			}
		}
		
	};
	
	public static Heuristique ffH3 = new Heuristique(){

		public int computeHeuristique(String joueur, Partie1 partie) {
			PlateauFousFous plateau = (PlateauFousFous) partie;
			if(joueur == PlateauFousFous.JBLANC){
				return 16 - plateau.getNbNoir();
			} else {
				return 16 - plateau.getNbBlanc();
			}
		}
		
	};
	
	public static Heuristique ffH4 = new Heuristique(){

		public int computeHeuristique(String joueur, Partie1 partie) {
			PlateauFousFous plateau = (PlateauFousFous) partie;
			if(joueur == PlateauFousFous.JBLANC){
				return (plateau.getNbBlanc() - plateau.getNbNoir() * 2) * 2 + plateau.getNbDefense(PlateauFousFous.JBLANC) - plateau.getNbDefense(PlateauFousFous.JNOIR);
			} else {
				return (plateau.getNbNoir() - plateau.getNbBlanc() * 2) * 2 + plateau.getNbDefense(PlateauFousFous.JNOIR) - plateau.getNbDefense(PlateauFousFous.JBLANC);
			}
		}
		
	};

}
