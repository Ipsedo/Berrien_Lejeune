package fousfous;

/**
 * 
 * @author samuel, alice
 *
 */
public class Cell {
	
	private int height;
	private int width;
	
	/**
	 * 
	 * @param height The cell height
	 * @param width The cell width
	 */
	public Cell(int height, int width){
		this.height = height;
		this.width = width;
	}
	
	/**
	 * 
	 * @return The cell height
	 */
	public int getHeight(){
		return this.height;
	}
	
	/**
	 * 
	 * @return The cell width
	 */
	public int getWidth(){
		return this.width;
	}
	
	public String toString(){
		char i = (char) (this.height + 'A');
		char j = (char) (this.width + '1');
		
		return String.valueOf(i) + String.valueOf(j);
	}
	
	public static void main(String[] args){
		Cell test = new Cell(1, 2);
		System.out.println(test);
	}
}
