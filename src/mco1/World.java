package mco1;

import java.util.*;

public class World {
	
	private ArrayList<ArrayList<Entity>> entities;
	private Alien alien;
	private Nano nano;
	private Gold gold;
	private Pit pit;
	private int gridSize; // has to be from 8 to 64

	
	public World(int gridSize) {
		this.entities = new ArrayList<ArrayList<Entity>>();
		this.gridSize = gridSize;
		this.alien = new Alien();
		this.nano = new Nano();
		this.gold = new Gold();
		this.pit = new Pit();
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
		alien.generateInitialPos(gridSize);
		alien.initialFront(gridSize);
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
		
		for(int i=0;i<gridSize;i++) {
			System.out.print("       ");
			for(int j=0;j<gridSize;j++) {
				if(entities.get(i).get(j) instanceof Alien)
					System.out.print(entities.get(i).get(j));
				
				else
					System.out.print(entities.get(i).get(j) + " ");
					if(j == gridSize -1)
						System.out.println();
					
			}

		}
	}
	
}
