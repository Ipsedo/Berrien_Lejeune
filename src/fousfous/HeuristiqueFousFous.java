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
				return plateau.getNbBlanc() - plateau.getNbNoir() * 3;
			} else {
				return plateau.getNbNoir() - plateau.getNbBlanc() * 3;
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
				//return (16 - plateau.getNbNoir() - (16 - plateau.getNbBlanc())) * 10 + (plateau.getNbMenaces(PlateauFousFous.JNOIR) - plateau.getNbMenaces(PlateauFousFous.JBLANC));
				return (plateau.getNbBlanc() - plateau.getNbNoir()) * 25 + (plateau.getNbMenaces(PlateauFousFous.JBLANC) - plateau.getNbMenaces(PlateauFousFous.JNOIR));
			} else {
				//return 16 - plateau.getNbBlanc();// + plateau.getNbMenaces(PlateauFousFous.JNOIR);
				//return (16 - plateau.getNbBlanc() - (16 - plateau.getNbNoir())) * 10 + (plateau.getNbMenaces(PlateauFousFous.JBLANC) - plateau.getNbMenaces(PlateauFousFous.JNOIR));
				return (plateau.getNbNoir() - plateau.getNbBlanc()) * 25 + (plateau.getNbMenaces(PlateauFousFous.JNOIR) - plateau.getNbMenaces(PlateauFousFous.JBLANC));
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
				return (plateau.getNbBlanc() * 32 + plateau.getNbDefense(PlateauFousFous.JBLANC)) - (plateau.getNbNoir() * 32 + plateau.getNbDefense(PlateauFousFous.JNOIR)) * 2;// + (plateau.getNbMenaces(PlateauFousFous.JBLANC));
			} else {
				return (plateau.getNbNoir() * 32 + plateau.getNbDefense(PlateauFousFous.JNOIR)) - (plateau.getNbBlanc() * 32 + plateau.getNbDefense(PlateauFousFous.JBLANC)) * 2;// + (plateau.getNbMenaces(PlateauFousFous.JNOIR));
			}
		}
		
	};

}
