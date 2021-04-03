package mco1;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Entity {
	
	protected int xPos;
	protected int yPos;
	protected int currentPos;
	protected String stringPos;
	protected boolean inBoard;
	protected String symbol;
	
	
	public Entity() {	
		this.inBoard = false;
	}
	
	public void generateInitialPos(int gridSize){
		xPos = ThreadLocalRandom.current().nextInt(0, gridSize);
		
		yPos = ThreadLocalRandom.current().nextInt(0, gridSize);
	
	
	}
	


	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}


	public int getCurrentPos() {
		return currentPos;
	}

	public boolean isInBoard() {
		return inBoard;
	}

	public void setInBoard(boolean inBoard) {
		this.inBoard = inBoard;
	}
	
	public String toString() {
		return this.symbol;
	}
	
	
}
