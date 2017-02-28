import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


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

	public boolean estValide(String move, String player) {
		// TODO Auto-generated method stub
		return false;
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

}
