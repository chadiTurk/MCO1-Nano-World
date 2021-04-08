package mco1;
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

public class Alien extends Entity {
	private String front;
	private Random random;
	private ArrayList<Integer> randomFront;
	
	public Alien(){
		this.symbol = "A";
		this.random = new Random();
		this.randomFront = new ArrayList<>();
	}
	
	@Override
	public String toString() {
		return this.symbol + this.front;
	}
	
	public void initialFront(int gridSize) {
		
		if(xPos == 0 && yPos == 0) {
			int position = ThreadLocalRandom.current().nextInt(2,4);
			generateFront(position);
			
		
		}
		
		else if(xPos == gridSize - 1 && yPos == 0) {
			int position = ThreadLocalRandom.current().nextInt(1,3);
			generateFront(position);
			
		
		}
		else if(xPos == 0 && yPos == gridSize -1) {
			int position = ThreadLocalRandom.current().nextInt(3,5);
			generateFront(position);
			
		
		}
		
		else if(xPos == gridSize - 1 && yPos == gridSize - 1) {
			int position = random.nextBoolean() ? 1:4;
			generateFront(position);
			
		
		}
		
		else if(xPos == 0 && (yPos >0 || yPos <gridSize -1 )) {
			int position = ThreadLocalRandom.current().nextInt(2,5);
			generateFront(position);

		}
		
		else if(yPos == 0 && (xPos>0 || xPos < gridSize -1)) {
			int position = ThreadLocalRandom.current().nextInt(1,4);
			generateFront(position);
			
		
		}
		
		else if(xPos == gridSize - 1 && (yPos >0 || yPos < gridSize - 1)) {
			randomFront.add(1);
			randomFront.add(2);
			randomFront.add(4);
			Collections.shuffle(randomFront);
			
			generateFront(randomFront.get(0));
			
			randomFront.clear();
			
		
		}
		
		else if(yPos == gridSize - 1 && (xPos >0 || xPos < gridSize - 1)){
			randomFront.add(1);
			randomFront.add(3);
			randomFront.add(4);
			Collections.shuffle(randomFront);
			
			generateFront(randomFront.get(0));
			randomFront.clear();
			
		
		}
		else {
			int position = ThreadLocalRandom.current().nextInt(1,5);
			generateFront(position);
			
			
		}
		
	}
	
	public void generateFront(int position) {
		
		System.out.println("The value of position is :" + position);
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
		
	}
	
	public void scan() {
		
	}
	
	public void shoot() { //shoot nano
		
	}
}
