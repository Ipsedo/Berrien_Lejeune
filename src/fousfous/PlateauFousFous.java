package fousfous;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import utils.Partie1;

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
	
	public static final String JBLANC = "Blanc";
	public static final String JNOIR = "Noir";
	
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
	
	public boolean outOfBounds(Cell cell){
		return !(cell.getHeight() >= 0 && cell.getHeight() < LIMIT && cell.getWidth() >= 0 && cell.getWidth() < LIMIT); 
	}

	public boolean estValide(String move, String player) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		ArrayList<String> tmpRes = new ArrayList<String>();
		
		for(int i = 0; i < LIMIT ; i++){
			for(int j = 0; j < LIMIT; j++){
				Cell fstCell = new Cell(i, j);
	
				//diagonale en bas à droite
				for(int k = i + 1, l = j + 1; k < LIMIT && l < LIMIT; k++, l++){
					Cell sndCell = new Cell(k, l);
					if(this.estValide(fstCell.toString() + "-" + sndCell.toString(), player)){
						tmpRes.add(fstCell.toString() + "-" + sndCell.toString());
					}
				}
				
				//diagonale en bas à gauche
				for(int k = i + 1, l = j - 1; k < LIMIT && l >= 0; k++, l--){
					Cell sndCell = new Cell(k, l);
					if(this.estValide(fstCell.toString() + "-" + sndCell.toString(), player)){
						tmpRes.add(fstCell.toString() + "-" + sndCell.toString());
					}
				}
				
				//diagonale en haut à droite
				for(int k = i - 1, l = j + 1; k >= 0 && l < LIMIT; k--, l++){
					Cell sndCell = new Cell(k, l);
					if(this.estValide(fstCell.toString() + "-" + sndCell.toString(), player)){
						tmpRes.add(fstCell.toString() + "-" + sndCell.toString());
					}
				}
				
				//diagonale en haut à gauche
				for(int k = i - 1, l = j - 1; k >= 0 && l >= 0; k--, l--){
					Cell sndCell = new Cell(k, l);
					if(this.estValide(fstCell.toString() + "-" + sndCell.toString(), player)){
						tmpRes.add(fstCell.toString() + "-" + sndCell.toString());
					}
				}
				
			}
		}
		
		System.out.println(tmpRes.size());
		
		String[] res = new String[tmpRes.size()];
		res = tmpRes.toArray(res);
		return res;
	}

	public void play(String move, String player) {
		// TODO Auto-generated method stub
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
	
	
	/**
	 * 
	 * @param args Arguments
	 */
	public static void main(String[] args){
		//TODO Faire les tests (save/load un plateau, verfier les methodes servantes à estValide() )
		PlateauFousFous p = new PlateauFousFous();
		p.setFromFile("plateau.txt");
		
		String[] joueurs = new String[]{PlateauFousFous.JBLANC, PlateauFousFous.JNOIR};
		int tour = 0;
		
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		while(!p.finDePartie()){
			System.out.println(p);
			System.out.println("C'est à " + joueurs[tour] + " de joueur !");
			for(String mv : p.mouvementsPossibles(joueurs[tour])){
				System.out.print(mv + ", ");
			}
			System.out.println("");
			String move = reader.next();
			while(!p.estValide(move, joueurs[tour])){
				move = reader.next();
			}
			p.play(move, joueurs[tour]);
			tour = 1 - tour;
		}
		reader.close();
	}
	
}
