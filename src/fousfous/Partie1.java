package fousfous;

public interface Partie1 {

	/**
	 * initialise un plateau à partir d'un fichier texte
	 * @param fileName
	 */
	public void setFromFile(String fileName);
	
	/**
	 * sauve la configuration d'un plateau dans un fichier
	 * @param fileName
	 */
	public void saveToFile(String fileName);
	
	/**
	 * indique si le coup <move> est valise pour le joueur <player> sur le plateau courant
	 * @param move le coup a jouer sous la forme 'A1-B2'
	 * @param player 
	 * @return
	 */
	public boolean estValide(String move, String player);
	
	/**
	 * calcule les coups possibles pour le joueur <player> sur le plateau courant
	 * @param player
	 * @return
	 */
	public String[] mouvementsPossibles(String player);
	
	/**
	 * modifie le plateau en jouant le coup move
	 * @param move
	 * @param player
	 */
	public void play(String move, String player);
	
	/**
	 * vrai lorsque le plateau correspond a une fin de partie
	 * @return
	 */
	public boolean finDePartie();
}
