package fousfous;

public class InfosPlateau {
	
	/** besoin de savoir qui est le joueur courant ? **/
	private String joueurCourant;

	private int prof;
	
	public enum Flag {EXACTVAL, BINF, BSUP};
	private Flag mFlag;
	
	private int val;
	
	private String meilleurCoup;
	
	public InfosPlateau(){
		
	}
	
	public InfosPlateau(String joueurCourant, int prof, Flag flag, int val, String meilleurCoup){
		this.joueurCourant = joueurCourant;
		this.prof = prof;
		this.mFlag = flag;
		this.val = val;
		this.meilleurCoup = meilleurCoup;
	}
	
	public Flag getFlag(){
		return this.mFlag;
	}
	
	public int getVal(){
		return this.val;
	}
	
	public String getMeilleurCoup(){
		return this.meilleurCoup;
	}
	
	public int getProf(){
		return this.prof;
	}
	
	public void setFlag(Flag nFlag){
		this.mFlag = nFlag;
	}
	
	public void setVal(int val){
		this.val = val;
	}
	
	public void setProf(int prof){
		this.prof = prof;
	}
	
	public void setMeilleurCoup(String meilleurCoup){
		this.meilleurCoup = meilleurCoup;
	}
}
