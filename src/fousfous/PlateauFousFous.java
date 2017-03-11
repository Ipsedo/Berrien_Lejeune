package fousfous;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;


import utils.Partie1;
import fousfous.Cell;

/**
 * 
 * @author samuel, alice
 *
 */
public class PlateauFousFous implements Partie1 {
	
	private final int BLANC = 1;
	private final int NOIR = -1;
	private final int VIDE = 0;
	
	private final int LIMIT = 8;
	
	private final String JBLANC = "Blanc";
	private final String JNOIR = "Noir";
	
	private int[][] plateau;
	
	/**
	 * 
	 */
	public PlateauFousFous(){
		this.plateau = new int[][]{
				{VIDE, BLANC, VIDE, BLANC, VIDE, BLANC, VIDE, BLANC},
				{NOIR, VIDE, NOIR, VIDE, NOIR, VIDE, NOIR, VIDE},
				{VIDE, BLANC, VIDE, BLANC, VIDE, BLANC, VIDE, BLANC},
				{NOIR, VIDE, NOIR, VIDE, NOIR, VIDE, NOIR, VIDE},
				{VIDE, BLANC, VIDE, BLANC, VIDE, BLANC, VIDE, BLANC},
				{NOIR, VIDE, NOIR, VIDE, NOIR, VIDE, NOIR, VIDE},
				{VIDE, BLANC, VIDE, BLANC, VIDE, BLANC, VIDE, BLANC},
				{NOIR, VIDE, NOIR, VIDE, NOIR, VIDE, NOIR, VIDE}
		};
	}

	public void setFromFile(String fileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line = "";
			while ((line = br.readLine()) != null){
				if(line.matches("[1-8](.*)")){
					int i = Character.getNumericValue(line.charAt(0)) - 1;
					int j = 0;
					for(int k = 0; k < line.length(); k++){
						if(line.charAt(k) == '-'){
							this.plateau[i][j] = VIDE;
							j++;
						} else if(line.charAt(k) == 'b'){
							this.plateau[i][j] = BLANC;
							j++;
						} else if(line.charAt(k) == 'n'){
							this.plateau[i][j] = NOIR;
							j++;
						}
					}
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**
		for(int i=0; i<8; i++){
			for(int j=0; j<8;j++){
				System.out.println(this.plateau[i][j]);
			}
		}**/

	}

	public void saveToFile(String fileName) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			for(int i = 0; i < LIMIT; i++){
				bw.write(Integer.toString(i + 1));
				for(int j = 0; j < LIMIT; j++){
					if(this.plateau[i][j] == VIDE){
						bw.write("-");
					} else if(this.plateau[i][j] == BLANC){
						bw.write("b");
					} else if(this.plateau[i][j] == NOIR){
						bw.write("n");
					}
				}
				bw.write(Integer.toString(i + 1) + "\n");
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * @param cell The cell in String format like "C3" to convert
	 * @return A Cell instance containing height and width
	 */
	public Cell parseCell(String cell){
		int width = cell.charAt(0) - 'A';
		int height = (int) cell.charAt(1) - '1';		
		return new Cell(height, width);
	}
	
	/**
	 * 
	 * @param cell The current Cell
	 * @param player The current player
	 * @return True if the cell's pion must take another pion
	 */
	public boolean doitPrendre(Cell cell, String player){
		// On regarde la diagonale en haut à droite
		int i = cell.getHeight() + 1;
		int j = cell.getWidth() + 1;
		while(i < LIMIT && j < LIMIT){
			// Si pion adversaire, on peut prendre
			if(this.plateau[i][j] == (player == this.JBLANC ? NOIR : BLANC)){
				return true;
			} else if(this.plateau[i][j] == (player == this.JBLANC ? BLANC : NOIR)){
				break;
			}
			// sinon on regarde plus loin dans la diagonale
			i++;
			j++;
		}
		
		// Diagonale bas/droite
		i = cell.getHeight() - 1;
		j = cell.getWidth() + 1;
		while(i >= 0 && j < LIMIT){
			if(this.plateau[i][j] == (player == this.JBLANC ? NOIR : BLANC)){
				return true;
			} else if(this.plateau[i][j] == (player == this.JBLANC ? BLANC : NOIR)){
				break;
			}
			i--;
			j++;
		}
		
		// Diagonale haut/gauche
		i = cell.getHeight() + 1;
		j = cell.getWidth() - 1;
		while(i < LIMIT && j >= 0){
			if(this.plateau[i][j] == (player == this.JBLANC ? NOIR : BLANC)){
				return true;
			} else if(this.plateau[i][j] == (player == this.JBLANC ? BLANC : NOIR)){
				break;
			}
			i++;
			j--;
		}
		
		// Diagonale bas/gauche
		i = cell.getHeight() - 1;
		j = cell.getWidth() - 1;
		while(i >= 0 && j >= 0){
			if(this.plateau[i][j] == (player == this.JBLANC ? NOIR : BLANC)){
				return true;
			} else if(this.plateau[i][j] == (player == this.JBLANC ? BLANC : NOIR)){
				break;
			}
			i--;
			j--;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param fst The first Cell
	 * @param snd The second Cell
	 * @return True if the trajectory is correct
	 */
	public boolean trajectoireOK(Cell fst, Cell snd){
		
		int i1 = fst.getHeight();
		int j1 = fst.getWidth();
		int i2 = snd.getHeight();
		int j2 = snd.getWidth();
		
		if(Math.abs(i1 - i2) != Math.abs(j1 - j2)){
			return false;
		}
		
		// Tests si aucun pion sur la trajectoire du move
		
		// test si trajectoire va en direction haut/droite
		if(i1 > i2 && j1 < j2){
			while(i1-1 < i2 && j1+1 < j2){
				if(this.plateau[i1-1][j1+1] != VIDE){
					return false;
				} else {
					i1--;
					j1++;
				}
			}
			return true;
		}
		
		// test trajectoire direction bas/droite
		if(i1 < i2 && j1 < j2){
			while(i1+1 < i2 && j1+1 < j2){
				if(this.plateau[i1+1][j1+1] != VIDE){
					return false;
				} else {
					i1++;
					j1++;
				}
			}
			return true;
		}
		
		// test trajectoire direction haut/gauche
		if(i1 > i2 && j1 > j2){
			while(i1-1 > i2 && j1-1 > j2){
				if(this.plateau[i1-1][j1-1] != VIDE){
					return false;
				} else {
					i1--;
					j1--;
				}
			}
			return true;
		}
		
		// test trajectoire direction bas/gauche
		if(i1 < i2 && j1 > j2){
			while(i1+1 < i2 && j1-1 < j2){
				if(this.plateau[i1+1][j1-1] != VIDE){
					return false;
				} else {
					i1++;
					j1--;
				}
			}
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param cell The current cell
	 * @param snd The current player
	 * @return True if player's pawn must threaten an another pawn
	 */
	public boolean menaceOk(Cell sndCell, String player) {
		if(this.plateau[sndCell.getHeight()][sndCell.getWidth()] != VIDE){
			return false;
		}
		
		//diagonale en bas à droite
		for(int i = sndCell.getHeight() + 1, j = sndCell.getWidth() + 1; i < LIMIT && j < LIMIT; i++, j++){
			if(this.plateau[i][j] == (player == JBLANC ? BLANC : NOIR)){
				break;
			}
			if(this.plateau[i][j] == (player == JBLANC ? NOIR : BLANC)){
				return true;
			}
		}
		
		//diagonale en bas à gauche
		for(int i = sndCell.getHeight() + 1, j = sndCell.getWidth() - 1; i < LIMIT && j >= 0; i++, j--){
			if(this.plateau[i][j] == (player == JBLANC ? BLANC : NOIR)){
				break;
			}
			if(this.plateau[i][j] == (player == JBLANC ? NOIR : BLANC)){
				return true;
			}
		}
		
		//diagonale en haut à droite
		for(int i = sndCell.getHeight() - 1, j = sndCell.getWidth() + 1; i >= 0 && j < LIMIT; i--, j++){
			if(this.plateau[i][j] == (player == JBLANC ? BLANC : NOIR)){
				break;
			}
			if(this.plateau[i][j] == (player == JBLANC ? NOIR : BLANC)){
				return true;
			}
		}
		
		//diagonale en haut à gauche
		for(int i = sndCell.getHeight() - 1, j = sndCell.getWidth() - 1; i >= 0 && j >= 0; i--, j--){
			if(this.plateau[i][j] == (player == JBLANC ? BLANC : NOIR)){
				break;
			}
			if(this.plateau[i][j] == (player == JBLANC ? NOIR : BLANC)){
				return true;
			}
		}
		
		return false;
	}

	public boolean estValide(String move, String player) {
		// TODO Auto-generated method stub
		Cell cFst = this.parseCell(move.split("-")[0]);
		Cell cSnd = this.parseCell(move.split("-")[1]);
		
		if(this.plateau[cFst.getHeight()][cFst.getWidth()] != (player == this.JBLANC ? this.BLANC : this.NOIR)){
			return false;
		}
		
		if(this.doitPrendre(cFst, player) && this.plateau[cSnd.getHeight()][cSnd.getWidth()] != (player == this.JBLANC ? NOIR : BLANC)){
			return false;
		}
		
		if(!this.trajectoireOK(cFst, cSnd)){
			return false;
		}
		
		if(!this.doitPrendre(cFst, player) && !this.menaceOk(cSnd, player)){
			return false;
		}
		
		return true;
	}

	public String[] mouvementsPossibles(String player) {
		// TODO Auto-generated method stub
		return null;
	}

	public void play(String move, String player) {
		// TODO Auto-generated method stub

	}

	public boolean finDePartie() {
		int nbBlanc = 0;
		int nbNoir = 0;
		for(int[] line : this.plateau){
			for(int cell : line){
				if(cell == BLANC){
					nbBlanc++;
				} else if(cell == NOIR){
					nbNoir++;
				}
			}
		}
		return nbBlanc == 0 || nbNoir == 0;
	}
	
	
	/**
	 * 
	 * @param args Arguments
	 */
	public static void main(String[] args){
		//TODO Faire les tests (save/load un plateau, verfier les methodes servantes à estValide() )
	PlateauFousFous p = new PlateauFousFous();
	p.setFromFile("plateau.txt");
	
	
	
	}
	
}
