package mco1;

import java.util.*;

public class World {
	
	private ArrayList<ArrayList<Entity>> entities;
	private Alien alien; //CHANGE THIS TO PRIVATE
	private Nano nano;
	private Gold gold;
	private Pit pit;
	private int gridSize; 
	private boolean alienMoved;
	
	public World(int gridSize) {
		this.entities = new ArrayList<ArrayList<Entity>>();
		this.gridSize = gridSize;
		this.alien = new Alien();
		this.nano = new Nano();
		this.gold = new Gold();
		this.pit = new Pit();
		this.alienMoved = false;
	}
	
	public void initializeWorld() {
		
		for(int i=0;i<gridSize;i++) {
				this.entities.add(new ArrayList<Entity>());
		}
		
		for(int i =0;i<gridSize;i++) {
			for(int j=0;j<gridSize;j++) {
				entities.get(i).add(new Space());
			}
		}
			
		initializeEntities();
		
	
		for(int i=0;i<gridSize;i++) {
			for(int j=0;j<gridSize;j++) {
			
				
				if((i == alien.getXPos() && j == alien.getYPos())){
					entities.get(i).set(j,alien);
					alien.setInBoard(true);
				}
				
				else if((i == nano.getXPos() && j == nano.getYPos())){
					
					entities.get(i).set(j,nano);
					nano.setInBoard(true);
				}
				else if((i == gold.getXPos() && j == gold.getYPos())){
					entities.get(i).set(j,gold);
					gold.setInBoard(true);
				}
				else if((i == pit.getXPos() && j == pit.getYPos())) {
					entities.get(i).set(j,pit);
					pit.setInBoard(true);
				}
				
			}
			
			
		}
		
		
	}
	
	public void initializeEntities() {
		alien.generateFront(gridSize);
		
		do {
			nano.generateInitialPos(gridSize);
			gold.generateInitialPos(gridSize);
			pit.generateInitialPos(gridSize);
		}while((nano.xPos == gold.xPos && nano.yPos == gold.yPos) || 
				(nano.xPos == pit.xPos && nano.yPos == pit.yPos) ||
				(gold.xPos == pit.xPos && gold.yPos == pit.yPos) ||
				(gold.xPos == 0 && gold.yPos == 0) || (nano.xPos == 0 && nano.yPos == 0) ||
				(pit.xPos == 0 || pit.yPos == 0) 
				);
		
	
		
	}
	
	public void drawBoard() {	
		
		if(!(alienIsAlive()) || alienHasGold()) {
			System.out.println(System.lineSeparator().repeat(150));
		}
		
		for(int i=0;i<entities.size();i++) {
			System.out.print("      ");
			for(int j=0;j<entities.size();j++) {
				if(entities.get(i).get(j) instanceof Alien) {
					System.out.print(entities.get(i).get(j));
				}
					
				
				else
					System.out.print(entities.get(i).get(j) + " ");
					if(j == gridSize -1)
						System.out.println();
					
			}

		}
		
		if(!(alienIsAlive())) {
			System.out.println("Game over! The alien has died!");
		}
		else if(alienHasGold()) {
			System.out.println("Search successful!");
		}
		
		System.out.println("Total moves: " + this.alien.getNumberOfMovements());
		System.out.println("Total rotate: " + this.alien.getNumberOfRotations());
		System.out.println("Total scan: " + this.alien.getNumberOfScans());
		System.out.println("Latest scan result: " + alien.scan());

		
	}
	
	public void moveAlien() {
		if(this.alien.getFront() == "v") {
			moveAlienDown();
		}
		else if(this.alien.getFront() == "^") {
			moveAlienUp();
		}else if(this.alien.getFront() == "<") {
			moveAlienLeft();
		}else if(this.alien.getFront() == ">") {
			moveAlienRight();
		}
	}
	
	public void moveAlienDown() {
	
		for(int i = 0;i<gridSize-1;i++) {
			for(int j=0;j<gridSize;j++) {
				if(entities.get(i).get(j) instanceof Alien && alienMoved == false && alien.getFront() == "v") {
					if(entities.get(i+1).get(j) instanceof Gold){
						entities.get(i+1).remove(j);
						Alien tempAlien = (Alien) entities.get(i).get(j);
						entities.get(i).remove(j);
						entities.get(i+1).add(j,tempAlien);
						entities.get(i).add(j,new Space());
						alienMoved = true;
						alien.setHasGold(true);
//						System.out.println("Search successful");
					}	
					else if(entities.get(i+1).get(j) instanceof Pit || entities.get(i+1).get(j) instanceof Nano ) {
						this.alien.setIsAlive(false);
						entities.get(i).remove(j);
						entities.get(i).add(j,new Space());
//						if(entities.get(i+1).get(j) instanceof Pit) {
//							System.out.println("Game over! The alien has fallen into a pit!");
//						}
//						else if(entities.get(i+1).get(j) instanceof Nano) {
//							System.out.println("Game over! Nano has killed the alien!");
//						}
						
					}
					else {
							
			
							Alien tempAlien = (Alien) entities.get(i).get(j);
							entities.get(i).remove(j);
							entities.get(i+1).set(j, tempAlien);
							entities.get(i).add(j, new Space());
							
							alienMoved = true;
						}
						
				
				}
			}
		}
		
		if(alienIsAlive() && !(alienHasGold())) {
			System.out.println(System.lineSeparator().repeat(150));
		}
		this.alien.incrementMovement();
		alienMoved = false;
	}
	
	public void moveAlienUp() {
		for(int i = 1; i<gridSize;i++) {
			for(int j=0;j<gridSize;j++) {
				if(entities.get(i).get(j) instanceof Alien && alienMoved == false && alien.getFront() == "^") {
					if(entities.get(i-1).get(j) instanceof Gold){
						entities.get(i-1).remove(j);
						Alien tempAlien = (Alien) entities.get(i).get(j);
						entities.get(i).remove(j);
						entities.get(i-1).add(j,tempAlien);
						entities.get(i).add(j,new Space());
						alienMoved = true;
						alien.setHasGold(true);
//						System.out.println("Search successful");
					}
					else if(entities.get(i-1).get(j) instanceof Pit || entities.get(i-1).get(j) instanceof Nano) {
						this.alien.setIsAlive(false);
						entities.get(i).remove(j);
						entities.get(i).add(j,new Space());
						
//						if(entities.get(i-1).get(j) instanceof Pit) {
//							System.out.println("Game over! The alien has fallen into a pit!");
//						}
//						else if(entities.get(i-1).get(j) instanceof Nano) {
//							System.out.println("Game over! Nano has killed the alien!");
//						}
						
					}
					else {
						Alien tempAlien = (Alien) entities.get(i).get(j);
						entities.get(i).remove(j);
						entities.get(i).add(j, new Space());
						entities.get(i-1).set(j, tempAlien);
//						entities.get(i).add(j,new Space());
						alienMoved = true;
					
					}
					
				}
			}
		}
		if(alienIsAlive() && !(alienHasGold())) {
			System.out.println(System.lineSeparator().repeat(150));
		}
		
		this.alien.incrementMovement();
		alienMoved = false;
	}
	
	public void moveAlienLeft() {
		for(int i = 0; i<gridSize;i++) {
			for(int j=1;j<gridSize;j++) {
				if(entities.get(i).get(j) instanceof Alien && alienMoved == false && alien.getFront() == "<") {
					if(entities.get(i).get(j-1) instanceof Gold){
						Alien tempAlien = (Alien) entities.get(i).get(j);
						entities.get(i).remove(j);
						entities.get(i).set(j-1,tempAlien);
						
						entities.get(i).add(j,new Space());
						alienMoved = true;
						alien.setHasGold(true);
//						System.out.println("Search successful");
					}
					else if(entities.get(i).get(j-1) instanceof Pit || entities.get(i).get(j-1) instanceof Nano ) {
						this.alien.setIsAlive(false);
						entities.get(i).remove(j);
						entities.get(i).add(j,new Space());
//						if(entities.get(i).get(j-1) instanceof Pit) {
//							System.out.println("Game over! The alien has fallen into a pit!");
//						}
//						else if(entities.get(i).get(j-1) instanceof Nano) {
//							System.out.println("Game over! Nano has killed the alien!");
//						}
						
					}
					else {
						Alien tempAlien = (Alien) entities.get(i).get(j);
						entities.get(i).remove(j);
						entities.get(i).add(j-1, tempAlien);
//						entities.get(i).add(j,new Space());
						alienMoved = true;
					}
					
				}
			}
		}
		
		if(alienIsAlive() && !(alienHasGold())) {
			System.out.println(System.lineSeparator().repeat(150));
		}
		
		
		this.alien.incrementMovement();
		alienMoved = false;
	}
	
	public void moveAlienRight() {
		for(int i = 0; i<gridSize-1;i++) {
			for(int j=0;j<gridSize-1;j++) {
				if(entities.get(i).get(j) instanceof Alien && alienMoved == false && alien.getFront() == ">") {
					if(entities.get(i).get(j+1) instanceof Gold){
						Alien tempAlien = (Alien) entities.get(i).get(j);
						entities.get(i).set(j+1,tempAlien);
						entities.get(i).set(j,new Space());
						alienMoved = true;
						alien.setHasGold(true);
//						System.out.println("Search successful");
					}
					else if(entities.get(i).get(j+1) instanceof Pit || entities.get(i).get(j+1) instanceof Nano) {
						this.alien.setIsAlive(false);
						entities.get(i).remove(j);
						entities.get(i).add(j,new Space());
//						if(entities.get(i).get(j+1) instanceof Pit) {
//							System.out.println("Game over! The alien has fallen into a pit!");
//						}
//						else if(entities.get(i).get(j+1) instanceof Nano) {
//							System.out.println("Game over! Nano has killed the alien!");
//						}
						
					}
					else {
						Alien tempAlien = (Alien) entities.get(i).get(j);
						entities.get(i).remove(j);
						entities.get(i).add(j+1,tempAlien);
//						entities.get(i).add(j,new Space());
						alienMoved = true;
						
					}
					
				}
			}
		}
		
		if(alienIsAlive() && !(alienHasGold())){
			System.out.println(System.lineSeparator().repeat(150));
		}
		
		this.alien.incrementMovement();
		alienMoved = false;
	}
	

	
	public boolean alienIsAlive() {
		return this.alien.getIsAlive();
	}
	
	
	public void scanFrontOfAlien() {
				
		for(int i =0;i<gridSize;i++) {
			for(int j=0;j<gridSize;j++) {
				if(entities.get(i).get(j) instanceof Alien) {
					this.alien.incrementScan();
					if(alien.getFront() == "^") {
						if(i!=0){
							this.alien.setScan(entities.get(i-1).get(j).getSymbol());
						}
						else
							this.alien.setScan("*");						
					}
					else if(alien.getFront() == ">") {
						if(j!= gridSize - 1) {
							this.alien.setScan(entities.get(i).get(j+1).getSymbol());
						}
						else
							this.alien.setScan("*");
					}
					else if(alien.getFront() == "v") {
						if(i!= gridSize - 1) {
							this.alien.setScan(entities.get(i+1).get(j).getSymbol());
						}
						else
							this.alien.setScan("*");
					}
					else if(alien.getFront() == "<") {
						if(j!=0) {
							this.alien.setScan(entities.get(i).get(j-1).getSymbol());
						}
						else 
							this.alien.setScan("*");
					}
				}
			}
		}
		
				System.out.println(System.lineSeparator().repeat(150));
	}
	
	public void rotateAlien() {
		this.alien.rotate();
		this.alien.incrementRotation();
		if(alienIsAlive()) {
			System.out.println(System.lineSeparator().repeat(150));
		}
	}
	

	
	public boolean alienHasGold() {
		return this.alien.HasGold();
	}
}

