
public class Square {

	private int num;
	private boolean preset;
	private int[] bans;

	public Square() {
		this.num = 0;
		this.preset = false;
		this.bans = new int[10];
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public boolean isPreset() {
		return preset;
	}

	public int[] getBans() {
		return bans;
	}

	public void setBans(int[] bans) {
		this.bans = bans;
	}

}
