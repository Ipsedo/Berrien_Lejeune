package fousfous;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/**
 * 
 * @author samuel, alice
 *
 */
public class PlateauFousFous implements Partie1 {
	
	private final int BLANC = 1;
	private final int NOIR = -1;
	private final int VIDE = 0;
	
	public static final int LIMIT = 8;
	
	public static final String JBLANC = "Blanc";
	public static final String JNOIR = "Noir";
	
	private int[][] plateau;
	
	public enum PhaseJeu {
		DEBUT, MILIEU, FIN;
	}
	
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
	
	private PlateauFousFous(int[][] plateau){
		this.plateau = new int[PlateauFousFous.LIMIT][PlateauFousFous.LIMIT];
		for(int i = 0; i < PlateauFousFous.LIMIT; i++){
			for(int j = 0; j < PlateauFousFous.LIMIT; j++){
				this.plateau[i][j] = plateau[i][j];
			}
		}
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		// On regarde la diagonale en bas à droite
		int i = cell.getHeight() + 1;
		int j = cell.getWidth() + 1;
		while(i < LIMIT && j < LIMIT){
			// Si pion adversaire, on peut prendre
			if(this.plateau[i][j] == (player == JBLANC ? NOIR : BLANC)){
				return true;
			} else if(this.plateau[i][j] == (player == JBLANC ? BLANC : NOIR)){
				break;
			}
			// sinon on regarde plus loin dans la diagonale
			i++;
			j++;
		}
		
		// Diagonale haut/droite
		i = cell.getHeight() - 1;
		j = cell.getWidth() + 1;
		while(i >= 0 && j < LIMIT){
			if(this.plateau[i][j] == (player == JBLANC ? NOIR : BLANC)){
				return true;
			} else if(this.plateau[i][j] == (player == JBLANC ? BLANC : NOIR)){
				break;
			}
			i--;
			j++;
		}
		
		// Diagonale bas/gauche
		i = cell.getHeight() + 1;
		j = cell.getWidth() - 1;
		while(i < LIMIT && j >= 0){
			if(this.plateau[i][j] == (player == JBLANC ? NOIR : BLANC)){
				return true;
			} else if(this.plateau[i][j] == (player == JBLANC ? BLANC : NOIR)){
				break;
			}
			i++;
			j--;
		}
		
		// Diagonale haut/gauche
		i = cell.getHeight() - 1;
		j = cell.getWidth() - 1;
		while(i >= 0 && j >= 0){
			if(this.plateau[i][j] == (player == JBLANC ? NOIR : BLANC)){
				return true;
			} else if(this.plateau[i][j] == (player == JBLANC ? BLANC : NOIR)){
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
		
		//On regarde si la trajectoire est bien une diagonale
		if(Math.abs(i1 - i2) != Math.abs(j1 - j2)){
			return false;
		}
		
		// Tests si aucun pion sur la trajectoire du move
		
		// test si trajectoire va en direction haut/droite
		if(i1 > i2 && j1 < j2){
			while(i1-1 > i2 && j1+1 < j2){
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
			while(i1+1 < i2 && j1-1 > j2){
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
	
	/**
	 * 
	 * @param cell The current cell
	 * @return true if the current cell is out of bounds
	 */
	public boolean outOfBounds(Cell cell){
		return !(cell.getHeight() >= 0 && cell.getHeight() < LIMIT && cell.getWidth() >= 0 && cell.getWidth() < LIMIT); 
	}

	public boolean estValide(String move, String player) {
		Cell cFst = this.parseCell(move.split("-")[0]);
		Cell cSnd = this.parseCell(move.split("-")[1]);
		
		if(this.outOfBounds(cFst) || this.outOfBounds(cSnd)){
			return false;
		}
		
		if(this.plateau[cFst.getHeight()][cFst.getWidth()] != (player == JBLANC ? BLANC : NOIR)){
			return false;
		}
		
		if(this.doitPrendre(cFst, player) && this.plateau[cSnd.getHeight()][cSnd.getWidth()] != (player == JBLANC ? NOIR : BLANC)){
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
		ArrayList<String> tmpRes = new ArrayList<String>();
		
		for(int i = 0; i < LIMIT ; i++){
			for(int j = 0; j < LIMIT; j++){
				Cell fstCell = new Cell(i, j);
	
				//diagonale en bas à droite
				for(int k = i + 1, l = j + 1; k < LIMIT && l < LIMIT; k++, l++){
					Cell sndCell = new Cell(k, l);
					if(this.estValide(fstCell + "-" + sndCell, player)){
						tmpRes.add(fstCell + "-" + sndCell);
					}
				}
				
				//diagonale en bas à gauche
				for(int k = i + 1, l = j - 1; k < LIMIT && l >= 0; k++, l--){
					Cell sndCell = new Cell(k, l);
					if(this.estValide(fstCell + "-" + sndCell, player)){
						tmpRes.add(fstCell + "-" + sndCell);
					}
				}
				
				//diagonale en haut à droite
				for(int k = i - 1, l = j + 1; k >= 0 && l < LIMIT; k--, l++){
					Cell sndCell = new Cell(k, l);
					if(this.estValide(fstCell + "-" + sndCell, player)){
						tmpRes.add(fstCell + "-" + sndCell);
					}
				}
				
				//diagonale en haut à gauche
				for(int k = i - 1, l = j - 1; k >= 0 && l >= 0; k--, l--){
					Cell sndCell = new Cell(k, l);
					if(this.estValide(fstCell + "-" + sndCell, player)){
						tmpRes.add(fstCell + "-" + sndCell);
					}
				}
				
			}
		}
		
		String[] res = new String[tmpRes.size()];
		res = tmpRes.toArray(res);
		return res;
	}

	public void play(String move, String player) {
		if(!this.estValide(move, player)){
			throw new RuntimeException("Coup invalide : " + move + ", pour joueur : " + player);
		}
		
		Cell cFst = this.parseCell(move.split("-")[0]);
		Cell cSnd = this.parseCell(move.split("-")[1]);
		
		this.plateau[cSnd.getHeight()][cSnd.getWidth()] = this.plateau[cFst.getHeight()][cFst.getWidth()];
		this.plateau[cFst.getHeight()][cFst.getWidth()] = VIDE;
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
	
	public String toString(){
		String res = "% ABCDEFGH %\n";
		for(int i = 0; i < LIMIT; i ++){
			res += (i+1) + " ";
			for(int j = 0; j < LIMIT; j++){
				if(this.plateau[i][j] == VIDE){
					res += "-";
				} else if(this.plateau[i][j] == BLANC){
					res += "b";
				} else if(this.plateau[i][j] == NOIR){
					res += "n";
				}
			}
			res += " " + (i+1) + "\n";
		}
		res += "% ABCDEFGH %\n";
		return res;
	}
	
	public int getNbBlanc(){
		int sum = 0;
		for(int[] line : this.plateau){
			for(int cell : line){
				sum += (cell == BLANC ? 1 : 0);
			}
		}
		return sum;
	}
	
	public int getNbNoir(){
		int sum = 0;
		for(int[] line : this.plateau){
			for(int cell : line){
				sum += (cell == NOIR ? 1 : 0);
			}
		}
		return sum;
	}
	
	public int getNbMenaces(String joueur){
		int res = 0;
		int mColor = joueur == PlateauFousFous.JBLANC ? 1 : -1;
		int oColor = -mColor;
		for(int i = 0; i < LIMIT; i++){
			for(int j = 0; j < LIMIT; j++){
				if(this.plateau[i][j] == mColor){
					for(int k = i + 1, l = j + 1; k < LIMIT && l < LIMIT; k++, l++){
						if(this.plateau[k][l] == oColor){
							res++;
							break;
						} else if(this.plateau[k][l] == mColor){
							break;
						}
					}
					for(int k = i - 1, l = j + 1; k >= 0 && l < LIMIT; k--, l++){
						if(this.plateau[k][l] == oColor){
							res++;
							break;
						} else if(this.plateau[k][l] == mColor){
							break;
						}
					}
					for(int k = i + 1, l = j - 1; k < LIMIT && l >= 0; k++, l--){
						if(this.plateau[k][l] == oColor){
							res++;
							break;
						} else if(this.plateau[k][l] == mColor){
							break;
						}
					}
					for(int k = i - 1, l = j - 1; k >= 0 && l >= 0; k--, l--){
						if(this.plateau[k][l] == oColor){
							res++;
							break;
						} else if(this.plateau[k][l] == mColor){
							break;
						}
					}
				}
			}
		}
		return res;
	}
	
	public PlateauFousFous copy(){
		return new PlateauFousFous(this.plateau);
	}
	
	public PhaseJeu getGamePhase(){
		if(this.getNbBlanc() > 11 && this.getNbNoir() > 11){
			return PhaseJeu.DEBUT;
		}else if(this.getNbBlanc() > 8 || this.getNbNoir() > 8){
			return PhaseJeu.MILIEU;
		}else if((this.getNbBlanc() <= 8 && this.getNbBlanc() >= 5) || (this.getNbNoir() <= 8 && this.getNbNoir() >= 5)){
			return PhaseJeu.MILIEU;
		}else{
			return PhaseJeu.FIN;
		}
	}
	
	public int getPlateauHashCode(){
		int res = 0;
		//TODO trouver vraie fonction de hash ..
		for(int i = 0; i < LIMIT; i++){
			for(int j = 0; j < LIMIT; j++){
				res ^= (this.plateau[i][j] + 2) * (i + 1 + LIMIT) * (j + 1);
			}
		}
		return res;
	}
	
	
	/**
	 * 
	 * @param args Arguments
	 */
	public static void main(String[] args){
		PlateauFousFous p = new PlateauFousFous();
		p.setFromFile("plateau.txt");
		System.out.println(p.getNbBlanc());
		
		String[] joueurs = new String[]{PlateauFousFous.JBLANC, PlateauFousFous.JNOIR};
		int tour = 0;
		
		Scanner reader = new Scanner(System.in);
		while(!p.finDePartie()){
			System.out.println(p);
			System.out.println("C'est à " + joueurs[tour] + " de jouer !");
			for(String mv : p.mouvementsPossibles(joueurs[tour])){
				System.out.print("[" + mv + "], ");
			}
			System.out.println("");
			String move = reader.next();
			while(!p.estValide(move, joueurs[tour])){
				System.out.println("Entrez un coup valide !");
				move = reader.next();
			}
			p.play(move, joueurs[tour]);
			tour = 1 - tour;
		}
		reader.close();
	}
	
}
