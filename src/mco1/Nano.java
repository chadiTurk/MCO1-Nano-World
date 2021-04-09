package mco1;

public class Nano extends Entity {
	
	private boolean isAlive;
	
	public Nano() {
		this.symbol = "N";
		this.isAlive = true;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
}
