package fousfous;

public abstract class Joueur implements IJoueur {

	protected int profMax;
	private int mColor;
	protected String joueurMax;
	protected String joueurMin;
	protected PlateauFousFous mPartie = new PlateauFousFous();
	
	protected Heuristique h;
	
	public void initJoueur(int mycolour) {
		// TODO Auto-generated method stub
		this.mColor = mycolour;
		if(this.mColor == -1){
			this.joueurMax = PlateauFousFous.JBLANC;
			this.joueurMin = PlateauFousFous.JNOIR;
		} else {
			this.joueurMin = PlateauFousFous.JBLANC;
			this.joueurMax = PlateauFousFous.JNOIR;
		}
		this.h = HeuristiqueFousFous.ffH1;
		this.profMax = 4;
	}

	public int getNumJoueur() {
		// TODO Auto-generated method stub
		return this.mColor;
	}

	public abstract String choixMouvement();

	public void declareLeVainqueur(int colour) {
		// TODO Auto-generated method stub
		if(colour == this.mColor){
			System.out.println("Hasta la vista, baby");
		}
	}

	public void mouvementEnnemi(String coup) {
		// TODO Auto-generated method stub
		this.mPartie.play(coup, this.joueurMin);
	}

	public String binoName() {
		// TODO Auto-generated method stub
		return "JoueurBase";
	}

}
