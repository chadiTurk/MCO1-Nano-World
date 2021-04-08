package mco1;

import java.util.*;

public class World {
	
	private ArrayList<ArrayList<Entity>> entities;
	public Alien alien; //CHANGE THIS TO PRIVATE
	private Nano nano;
	private Gold gold;
	private Pit pit;
	private int gridSize; // has to be from 8 to 64
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
		
		System.out.println(entities.get(0).size());
		alien.generateFront(gridSize);
		nano.generateInitialPos(gridSize);
		gold.generateInitialPos(gridSize);
		pit.generateInitialPos(gridSize);
		
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
					entities.get(i).set(i,pit);
					pit.setInBoard(true);
				}
				
			}
			
			
		}
		
		
	}
	
	
	public void drawBoard() {
		
		System.out.println("value of xpos of alien :" + alien.getXPos());
		
		for(int i=0;i<gridSize;i++) {
			System.out.print("");
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
	
	public void moveAlienDown() {
		for(int i = 0; i<gridSize-1;i++) {
			for(int j=0;j<gridSize;j++) {
				if(entities.get(i).get(j) instanceof Alien && alienMoved == false) {
					System.out.println("Value of i:" + i + " j: " + j);
					System.out.println("executed");
					Alien tempAlien = (Alien) entities.get(i).get(j);
					entities.get(i).remove(j);
					entities.get(i+1).add(j, tempAlien);
					entities.get(i).add(new Space());
					alienMoved = true;
				}
			}
		}
		
		alienMoved = false;
	}
	
}
