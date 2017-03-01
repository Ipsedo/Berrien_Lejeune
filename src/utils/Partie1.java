package utils;

public interface Partie1 {

	/**
	 * 
	 * @param fileName
	 */
	public void setFromFile(String fileName);
	
	/**
	 * 
	 * @param fileName
	 */
	public void saveToFile(String fileName);
	
	/**
	 * 
	 * @param move
	 * @param player
	 * @return
	 */
	public boolean estValide(String move, String player);
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public String[] mouvementsPossibles(String player);
	
	/**
	 * 
	 * @param move
	 * @param player
	 */
	public void play(String move, String player);
	
	/**
	 * 
	 * @return
	 */
	public boolean finDePartie();
}
