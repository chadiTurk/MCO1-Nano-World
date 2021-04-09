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
			
				if((i == alien.getXPos() && j == alien.getYPos()) && alien.isInBoard() == false){
					entities.get(i).set(j,alien);
					alien.setInBoard(true);
				}
				
				else if((i == nano.getXPos() && j == nano.getYPos()) && nano.isInBoard() == false){
					entities.get(i).set(j,nano);
					nano.setInBoard(true);
				}
				else if((i == gold.getXPos() && j == gold.getYPos()) && gold.isInBoard() == false){
					entities.get(i).set(j,gold);
					gold.setInBoard(true);
				}
				else if((i == pit.getXPos() && j == gold.getYPos()) && pit.isInBoard() == false) {
					entities.get(i).set(j,pit);
					pit.setInBoard(true);
				}
				
			}
			
			
		}
		
		
	}
	
	public void initializeEntities() {
		alien.generateFront(gridSize);
		nano.generateInitialPos(gridSize);
		gold.generateInitialPos(gridSize);
		pit.generateInitialPos(gridSize);
		
	}
	
	public void drawBoard() {	
		
		for(int i=0;i<gridSize;i++) {
			System.out.print("      ");
			for(int j=0;j<gridSize;j++) {
				if(entities.get(i).get(j) instanceof Alien) {
					System.out.print(entities.get(i).get(j));
				}
					
				
				else
					System.out.print(entities.get(i).get(j) + " ");
					if(j == gridSize -1)
						System.out.println();
					
			}

		}
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
		for(int i = 0; i<gridSize-1;i++) {
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
						System.out.println("Search successful");
					}
					else if(entities.get(i+1).get(j) instanceof Pit || entities.get(i+1).get(j) instanceof Nano ) {
						this.alien.setIsAlive(false);
						entities.get(i).remove(j);
						entities.get(i).add(j,new Space());
						if(entities.get(i+1).get(j) instanceof Pit) {
							System.out.println("Game over! The alien has fallen into a pit!");
						}
						else if(entities.get(i+1).get(j) instanceof Nano) {
							System.out.println("Game over! Nano has killed the alien!");
						}
						
					}
					else {
						System.out.println("executed");
						Alien tempAlien = (Alien) entities.get(i).get(j);
						entities.get(i).remove(j);
						entities.get(i+1).add(j, tempAlien);
						entities.get(i).add(j,new Space());
						alienMoved = true;
					}
				
				}
			}
		}
		
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
						System.out.println("Search successful");
					}
					else if(entities.get(i-1).get(j) instanceof Pit || entities.get(i-1).get(j) instanceof Nano) {
						this.alien.setIsAlive(false);
						entities.get(i).remove(j);
						entities.get(i).add(j,new Space());
						
						if(entities.get(i-1).get(j) instanceof Pit) {
							System.out.println("Game over! The alien has fallen into a pit!");
						}
						else if(entities.get(i-1).get(j) instanceof Nano) {
							System.out.println("Game over! Nano has killed the alien!");
						}
						
					}
					else {
						System.out.println("executed");
						Alien tempAlien = (Alien) entities.get(i).get(j);
						entities.get(i).remove(j);
						entities.get(i-1).add(j, tempAlien);
						entities.get(i).add(j,new Space());
						alienMoved = true;
					}
					
				}
			}
		}
		
		alienMoved = false;
	}
	
	public void moveAlienLeft() {
		for(int i = 0; i<gridSize;i++) {
			for(int j=1;j<gridSize;j++) {
				if(entities.get(i).get(j) instanceof Alien && alienMoved == false && alien.getFront() == "<") {
					if(entities.get(i).get(j-1) instanceof Gold){
						entities.get(i).remove(j-1);
						entities.get(i).remove(j);
						entities.get(i).add(j,new Space());
						alienMoved = true;
						alien.setHasGold(true);
						System.out.println("Search successful");
					}
					else if(entities.get(i).get(j-1) instanceof Pit || entities.get(i).get(j-1) instanceof Nano ) {
						this.alien.setIsAlive(false);
						entities.get(i).remove(j);
						entities.get(i).add(j,new Space());
						if(entities.get(i).get(j-1) instanceof Pit) {
							System.out.println("Game over! The alien has fallen into a pit!");
						}
						else if(entities.get(i).get(j-1) instanceof Nano) {
							System.out.println("Game over! Nano has killed the alien!");
						}
						
					}
					else {
						System.out.println("executed");
						Alien tempAlien = (Alien) entities.get(i).get(j);
						entities.get(i).remove(j);
						entities.get(i).add(j-1, tempAlien);
						entities.get(i).add(j,new Space());
						alienMoved = true;
					}
					
				}
			}
		}
		
		alienMoved = false;
	}
	
	public void moveAlienRight() {
		for(int i = 0; i<gridSize-1;i++) {
			for(int j=0;j<gridSize-1;j++) {
				if(entities.get(i).get(j) instanceof Alien && alienMoved == false && alien.getFront() == ">") {
					if(entities.get(i).get(j+1) instanceof Gold){
						entities.get(i).remove(j+1);
						Alien tempAlien = (Alien) entities.get(i).get(j);
						entities.get(i).remove(j);
						entities.get(i).add(j+1,tempAlien);
						entities.get(i).add(j,new Space());
						alienMoved = true;
						alien.setHasGold(true);
						System.out.println("Search successful");
					}
					else if(entities.get(i).get(j+1) instanceof Pit || entities.get(i).get(j+1) instanceof Nano) {
						this.alien.setIsAlive(false);
						entities.get(i).remove(j);
						entities.get(i).add(j,new Space());
						if(entities.get(i).get(j+1) instanceof Pit) {
							System.out.println("Game over! The alien has fallen into a pit!");
						}
						else if(entities.get(i).get(j+1) instanceof Nano) {
							System.out.println("Game over! Nano has killed the alien!");
						}
						
					}
					else {
						System.out.println("executed");
						Alien tempAlien = (Alien) entities.get(i).get(j);
						entities.get(i).remove(j);
						entities.get(i).add(j+1, tempAlien);
						entities.get(i).add(j,new Space());
						alienMoved = true;
					}
					
				}
			}
		}
		
		alienMoved = false;
	}
	
	public boolean alienIsAlive() {
		return this.alien.getIsAlive();
	}
}

