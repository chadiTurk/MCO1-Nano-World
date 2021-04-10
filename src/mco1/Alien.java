package mco1;
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

public class Alien extends Entity {
	
	private String front;
	private Random random;
	private ArrayList<Integer> randomFront;
	private String entityInFront;
	private boolean hasGold;
	private boolean isAlive;
	private int numberOfRotations;
	private int numberOfMovements;
	private int numberOfScans;
	
	public Alien(){
		this.symbol = "A";
		this.random = new Random();
		this.xPos = 0;
		this.yPos = 0;
		this.randomFront = new ArrayList<>();
		this.hasGold = false;
		this.isAlive = true;
		this.entityInFront = "*";
		this.numberOfRotations = 0;
		this.numberOfMovements = 0;
		this.numberOfScans = 0;
	}
	
	@Override
	public String toString() {
		return this.symbol + this.front;
	}
	
	public void generateFront(int gridSize) {
		
		if(xPos == 0 && yPos == 0) {
			int position = ThreadLocalRandom.current().nextInt(2,4);
			updateFront(position);
			
		
		}
		
//		else if(xPos == gridSize - 1 && yPos == 0) {
//			int position = ThreadLocalRandom.current().nextInt(1,3);
//			updateFront(position);
//			
//		
//		}
//		else if(xPos == 0 && yPos == gridSize -1) {
//			int position = ThreadLocalRandom.current().nextInt(3,5);
//			updateFront(position);
//			
//		
//		}
//		
//		else if(xPos == gridSize - 1 && yPos == gridSize - 1) {
//			int position = random.nextBoolean() ? 1:4;
//			updateFront(position);
//			
//		
//		}
//		
//		else if(xPos == 0 && (yPos >0 || yPos <gridSize -1 )) {
//			int position = ThreadLocalRandom.current().nextInt(2,5);
//			updateFront(position);
//
//		}
//		
//		else if(yPos == 0 && (xPos>0 || xPos < gridSize -1)) {
//			int position = ThreadLocalRandom.current().nextInt(1,4);
//			updateFront(position);
//			
//		
//		}
//		
//		else if(xPos == gridSize - 1 && (yPos >0 || yPos < gridSize - 1)) {
//			randomFront.add(1);
//			randomFront.add(2);
//			randomFront.add(4);
//			Collections.shuffle(randomFront);
//			
//			updateFront(randomFront.get(0));
//			
//			randomFront.clear();
//			
//		
//		}
//		
//		else if(yPos == gridSize - 1 && (xPos >0 || xPos < gridSize - 1)){
//			randomFront.add(1);
//			randomFront.add(3);
//			randomFront.add(4);
//			Collections.shuffle(randomFront);
//			
//			updateFront(randomFront.get(0));
//			randomFront.clear();
//			
//		
//		}
//		else {
//			int position = ThreadLocalRandom.current().nextInt(1,5);
//			updateFront(position);
//			
//			
//		}
		
	}
	
	public void updateFront(int position) {
		
		switch(position) {
		case 1:
			this.front = "^";
			break;
		case 2:
			this.front = ">";
			break;
		case 3:
			this.front = "v";
			break;
		case 4:
			this.front = "<";
			break;
		}
	}
	
	
	
	public void rotate() { //change direction of symbol but make sure that it points to a valid location e.g. edge of board
		
		
		if(this.front == "^") {
			this.front = ">";
		}
		else if(this.front == ">") {
			this.front = "v";
		}	
		else if(this.front == "v") {
			this.front = "<";
		}
		else if(this.front == "<") {
			this.front = "^";
		}
	}
	
	
	public void shoot() { 
		
	}

	public String getFront() {
		return front;
	}

	public void setFront(String front) {
		this.front = front;
	}
	
	public void setHasGold(boolean status) {
		this.hasGold = status;
	}
	
	public void setIsAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public boolean getIsAlive() {
		return this.isAlive;
	}

	public String scan() {
		if(entityInFront == "P") {
			return "breeze";
		}
		else if(entityInFront == "N") {
			return "smell";
		}
		else if(entityInFront == "G") {
			return "glitter";
		}
		else return "null";
	}

	public void setScan(String entityInFront) {
		this.entityInFront = entityInFront;
	}

	public Random getRandom() {
		return random;
	}

	public ArrayList<Integer> getRandomFront() {
		return randomFront;
	}

	public String getEntityInFront() {
		return entityInFront;
	}

	public boolean HasGold() {
		return hasGold;
	}

	public int getNumberOfRotations() {
		return numberOfRotations;
	}

	public int getNumberOfMovements() {
		return numberOfMovements;
	}

	public int getNumberOfScans() {
		return numberOfScans;
	}
	
	public void incrementRotation() {
		this.numberOfRotations++;
	}
	
	public void incrementMovement() {
		this.numberOfMovements++;
	}
	
	public void incrementScan() {
		this.numberOfScans++;
	}
}
