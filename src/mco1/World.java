package mco1;

import java.util.*;

public class World {
	
	private ArrayList<ArrayList<Entity>> entities;
	private Alien alien; 
	private Nano nano;
	private Gold gold;
	private Pit pit;
	private int gridSize; 
	private boolean alienMoved;
	private int agentOption;
	private boolean smartRotate;
	private boolean smartMove;
	private boolean smartShoot;
	private int iNano;
	private int jNano;
	private boolean scanUp;
	private boolean scanRight;
	private boolean scanDown;
	private boolean scanLeft;
	private ArrayList<VisitedLocation> visitedLocations;
	private ArrayList<PitLocations> pitLocations;
	private boolean alienCanMove;
	private boolean moveInsteadRotate;
	private boolean alienScannedFront;
	private int rotateSuccessions;
	private int numberOfPits;
	private int xPit;
	private int yPit;
	private ArrayList<Pit> pits;
	
	public World(int gridSize,int agentOption) {
		this.entities = new ArrayList<ArrayList<Entity>>();
		this.gridSize = gridSize;
		this.alien = new Alien();
		this.nano = new Nano();
		this.gold = new Gold();
		this.alienMoved = false;
		this.agentOption = agentOption;
		this.smartRotate = false;
		this.smartMove = false;
		this.smartShoot = false;
		this.scanUp = false;
		this.scanRight = false;
		this.scanDown = false;
		this.scanLeft = false;
		this.visitedLocations = new ArrayList<>();
		this.pitLocations = new ArrayList<>();
		this.alienCanMove = false;
		this.moveInsteadRotate = false;
		this.alienScannedFront = false;
		this.rotateSuccessions = 0;
		this.pits = new ArrayList<>();
		this.numberOfPits = 0;
		this.pit = new Pit();

	}
	
	
	public void initializeWorldUser() {
		
		int xNano = 0;
		int yNano = 0;
		int xGold = 0;
		int yGold = 0;
		
		Scanner scanner = new Scanner(System.in);
		
		alien.generateFront(gridSize);
		
		for(int i=0;i<gridSize;i++) {
			this.entities.add(new ArrayList<Entity>());
		}
		
		for(int i =0;i<gridSize;i++) {
			for(int j=0;j<gridSize;j++) {
				entities.get(i).add(new Space());
			}
		}
		
		
		System.out.println("Enter number of pits:");
		this.numberOfPits = scanner.nextInt();
		
		for(int n = 0;n<numberOfPits;n++) {
			
			System.out.println("Pit " + (n+1) + ":");
			
			System.out.print("Enter x coordinate: ");
			this.xPit = scanner.nextInt();
			
			System.out.print("Enter y coordinate: ");
			this.yPit = scanner.nextInt();
			
			Pit pit = new Pit();
			
			pit.setX(xPit);
			pit.setY(yPit);
			
			this.pits.add(pit);
		}
		
		System.out.println("Enter location for nano: ");
		
		System.out.print("Enter x coordinate:");
		xNano = scanner.nextInt();
		System.out.print("Enter y coordinate: ");
		yNano = scanner.nextInt();
		
		nano.setXPos(xNano);
		nano.setYPos(yNano);
		
		System.out.println("Enter location for gold: ");
		
		System.out.print("Enter x coordinate:");
		xGold = scanner.nextInt();
		System.out.print("Enter y coordinate: ");
		yGold = scanner.nextInt();
		
		gold.setXPos(xGold);
		gold.setYPos(yGold);
		
		
		
		for(int i = 0;i<gridSize;i++) {
			for(int j = 0;j<gridSize;j++) {
				
				for(int n = 0;n<this.pits.size();n++) {
					if(i == pits.get(n).getX() && j == pits.get(n).getY()) {
					 entities.get(i).set(j,pits.get(n));
					}
				}
			}
		}
		
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
				
			  }
			}
		
		
		
		
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
		
	
		
		for(int i = 0;i<this.numberOfPits;i++) {
			this.pits.add(new Pit());
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
		
		if(this.nano.isAlive() == false) {
			System.out.println("Nano has been shot!");
		}
		
		System.out.println("Total moves: " + this.alien.getNumberOfMovements());
		System.out.println("Total rotate: " + this.alien.getNumberOfRotations());
		System.out.println("Total scan: " + this.alien.getNumberOfScans());
		System.out.println("Latest scan result:" + this.alien.scan());
		
		
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
	
	public void smartMove() {
		
		
		if(this.alien.getFront() == "v") {
			smartMoveAlienDown();
		}
		else if(this.alien.getFront() == "^") {
			smartMoveAlienUp();
		}else if(this.alien.getFront() == "<") {
			smartMoveAlienLeft();
		}else if(this.alien.getFront() == ">") {
			smartMoveAlienRight();
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

					}	
					else if(entities.get(i+1).get(j) instanceof Pit || entities.get(i+1).get(j) instanceof Nano ) {
						this.alien.setIsAlive(false);
						entities.get(i).remove(j);
						entities.get(i).add(j,new Space());
						
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
					}
					else if(entities.get(i-1).get(j) instanceof Pit || entities.get(i-1).get(j) instanceof Nano) {
						this.alien.setIsAlive(false);
						entities.get(i).remove(j);
						entities.get(i).add(j,new Space());
						
					}
					else {
						Alien tempAlien = (Alien) entities.get(i).get(j);
						entities.get(i).remove(j);
						entities.get(i).add(j, new Space());
						entities.get(i-1).set(j, tempAlien);
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
					}
					else if(entities.get(i).get(j-1) instanceof Pit || entities.get(i).get(j-1) instanceof Nano ) {
						this.alien.setIsAlive(false);
						entities.get(i).remove(j);
						entities.get(i).add(j,new Space());
						
					}
					else {
						Alien tempAlien = (Alien) entities.get(i).get(j);
						entities.get(i).remove(j);
						entities.get(i).add(j-1, tempAlien);
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
		for(int i = 0; i<gridSize;i++) {
			for(int j=0;j<gridSize-1;j++) {
				if(entities.get(i).get(j) instanceof Alien && alienMoved == false && alien.getFront() == ">") {
					if(entities.get(i).get(j+1) instanceof Gold){
						Alien tempAlien = (Alien) entities.get(i).get(j);
						entities.get(i).set(j+1,tempAlien);
						entities.get(i).set(j,new Space());
						alienMoved = true;
						alien.setHasGold(true);
					}
					else if(entities.get(i).get(j+1) instanceof Pit || entities.get(i).get(j+1) instanceof Nano) {
						this.alien.setIsAlive(false);
						entities.get(i).remove(j);
						entities.get(i).add(j,new Space());

					}
					else {
						Alien tempAlien = (Alien) entities.get(i).get(j);
						entities.get(i).remove(j);
						entities.get(i).add(j+1,tempAlien);
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
	
	
	public void smartMoveAlienDown() {
	
		for(int i = 0;i<gridSize-1;i++) {
			for(int j=0;j<gridSize;j++) {
				if(entities.get(i).get(j) instanceof Alien && alienMoved == false && alien.getFront() == "v") {
					
					this.alienCanMove = true;
					
					
					if((checkPit(i+1,j) || checkVisit(i+1,j))){
						this.moveInsteadRotate = true;
						this.alienScannedFront = false;
						

						removeStuck(i,j);
						removeSurroundPit(i, j);
						rotateAlien();
											
					}		
					
					else {
						if(entities.get(i+1).get(j) instanceof Gold && this.alienScannedFront == true){
							entities.get(i+1).remove(j);
							Alien tempAlien = (Alien) entities.get(i).get(j);
							entities.get(i).remove(j);
							entities.get(i+1).add(j,tempAlien);
							entities.get(i).add(j,new Space());
							alienMoved = true;
							alien.setHasGold(true);
							this.alien.incrementMovement();
							

						}	
						else if((entities.get(i+1).get(j) instanceof Pit || entities.get(i+1).get(j) instanceof Nano ) && this.alienScannedFront == true) {

							rotateAlien();
							
						}
						else if (this.alienScannedFront == true) {
								Alien tempAlien = (Alien) entities.get(i).get(j);
								entities.get(i).remove(j);
								entities.get(i+1).set(j, tempAlien);
								entities.get(i).add(j, new Space());
								
								alienMoved = true;
								this.alien.incrementMovement();
								visitedLocations.add(new VisitedLocation(i,j));
								this.moveInsteadRotate = false;
								this.alienScannedFront = false;
								
							}
						else {
							randomMove();
						  }
						}
					
						
				
				}
			}
		}
		
		if(alienIsAlive() && !(alienHasGold())) {
			System.out.println(System.lineSeparator().repeat(150));
		}
		
		if(this.alienCanMove == false) {
			rotateAlien();
		}
		
		
		this.alienCanMove = false;
		
		alienMoved = false;
	}
	
	public void smartMoveAlienUp() {
		for(int i = 1; i<gridSize;i++) {
			for(int j=0;j<gridSize;j++) {
				if(entities.get(i).get(j) instanceof Alien && alienMoved == false && alien.getFront() == "^") {
					
					this.alienCanMove = true;
					
					
					
					if((checkPit(i-1,j) || checkVisit(i-1,j))){
						this.moveInsteadRotate = true;
						this.alienScannedFront = false;
						removeStuck(i,j);
						removeSurroundPit(i, j);
						rotateAlien();
						
											
					}

					else {
						if(entities.get(i-1).get(j) instanceof Gold && this.alienScannedFront == true){
							entities.get(i-1).remove(j);
							Alien tempAlien = (Alien) entities.get(i).get(j);
							entities.get(i).remove(j);
							entities.get(i-1).add(j,tempAlien);
							entities.get(i).add(j,new Space());
							alienMoved = true;
							alien.setHasGold(true);
							this.alien.incrementMovement();
							
						}
						else if((entities.get(i-1).get(j) instanceof Pit || entities.get(i-1).get(j) instanceof Nano) && this.alienScannedFront == true){
							rotateAlien();
						}
						else if(this.alienScannedFront == true){
							Alien tempAlien = (Alien) entities.get(i).get(j);
							entities.get(i).remove(j);
							entities.get(i).add(j, new Space());
							entities.get(i-1).set(j, tempAlien);
							alienMoved = true;
							this.alien.incrementMovement();
							visitedLocations.add(new VisitedLocation(i,j));
							this.moveInsteadRotate = false;
							this.alienScannedFront = false;
							
						}
						else {
							randomMove();
						}
					}
					
					
				}
			}
		}
		if(alienIsAlive() && !(alienHasGold())) {
			System.out.println(System.lineSeparator().repeat(150));
		}
		
		if(this.alienCanMove == false) {
			rotateAlien();
		}
		
		
		this.alienCanMove = false;
		
		alienMoved = false;
	}
	
	public void smartMoveAlienLeft() {
		for(int i = 0; i<gridSize;i++) {
			for(int j=1;j<gridSize;j++) {
				if(entities.get(i).get(j) instanceof Alien && alienMoved == false && alien.getFront() == "<") {
					
					this.alienCanMove = true;
					
					
					if((checkPit(i,j-1) || checkVisit(i,j-1))){
						this.moveInsteadRotate = true;
						
						this.alienScannedFront = false;
					
						removeStuck(i,j);
						removeSurroundPit(i, j);
					
						rotateAlien();
					}

					else {
						if(entities.get(i).get(j-1) instanceof Gold && this.alienScannedFront == true){
							Alien tempAlien = (Alien) entities.get(i).get(j);
							entities.get(i).remove(j);
							entities.get(i).set(j-1,tempAlien);
						
							entities.get(i).add(j,new Space());
							alienMoved = true;
							alien.setHasGold(true);
							this.alien.incrementMovement();
						}
						else if((entities.get(i).get(j-1) instanceof Pit || entities.get(i).get(j-1) instanceof Nano) && this.alienScannedFront == true) {
						
							rotateAlien();
						}
						else if(this.alienScannedFront == true) {
							Alien tempAlien = (Alien) entities.get(i).get(j);
							entities.get(i).remove(j);
							entities.get(i).add(j-1, tempAlien);
							alienMoved = true;
							this.alien.incrementMovement();
							visitedLocations.add(new VisitedLocation(i,j));
							this.moveInsteadRotate = false;
							this.alienScannedFront = false;
						
						}else {
							randomMove();
						}
						
					}
					
				
					
				}
			}
		}
		
		if(alienIsAlive() && !(alienHasGold())) {
			System.out.println(System.lineSeparator().repeat(150));
		}
		
		if(this.alienCanMove == false) {
			rotateAlien();
		}
		
		
		this.alienCanMove = false;
		
		alienMoved = false;
	}
	
	public void smartMoveAlienRight() {
		for(int i = 0; i<gridSize;i++) {
			for(int j=0;j<gridSize-1;j++) {
				if(entities.get(i).get(j) instanceof Alien && alienMoved == false && alien.getFront() == ">") {
					
					this.alienCanMove = true;
					
					
					if((checkPit(i,j+1) || checkVisit(i, j+1))) {
						this.moveInsteadRotate = true;
						this.alienScannedFront = false;
						
						removeStuck(i,j);
						removeSurroundPit(i, j);
						rotateAlien();
					}

					else {
						if(entities.get(i).get(j+1) instanceof Gold && 	this.alienScannedFront == true){
							Alien tempAlien = (Alien) entities.get(i).get(j);
							entities.get(i).set(j+1,tempAlien);
							entities.get(i).set(j,new Space());
							alienMoved = true;
							alien.setHasGold(true);
							this.alien.incrementMovement();
						}
						else if((entities.get(i).get(j+1) instanceof Pit || entities.get(i).get(j+1) instanceof Nano)  && this.alienScannedFront == true){
							rotateAlien();
							
						}
						else if (this.alienScannedFront == true){
							Alien tempAlien = (Alien) entities.get(i).get(j);
							entities.get(i).remove(j);
							entities.get(i).add(j+1,tempAlien);
							alienMoved = true;
							this.alien.incrementMovement();
							visitedLocations.add(new VisitedLocation(i,j));
							this.moveInsteadRotate = false;
							this.alienScannedFront = false;
							
						} else {
							randomMove();
						}
					}
					
				}
			}
		}
		
		if(alienIsAlive() && !(alienHasGold())){
			System.out.println(System.lineSeparator().repeat(150));
		}
		
		if(this.alienCanMove == false) {
			rotateAlien();
		}
		
		
		this.alienCanMove = false;
		alienMoved = false;
	}
	
	public boolean alienIsAlive() {
		return this.alien.getIsAlive();
	}
	
	
	public void scanFrontOfAlien() {
		
		if(agentOption == 1) {
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
		}
		
		else if(agentOption == 2) {
			for(int i =0;i<gridSize;i++) {
				for(int j=0;j<gridSize;j++) {
					if(entities.get(i).get(j) instanceof Alien) {
						this.alien.incrementScan();
						if(alien.getFront() == "^") {
							if(i!=0){
								this.alien.setScan(entities.get(i-1).get(j).getSymbol());
								scanUp = true;
								smartScan(i,j);
								this.alienScannedFront = true;
							}
							else
								this.alien.setScan("*");						
						}
						else if(alien.getFront() == ">") {
							if(j!= gridSize - 1) {
								scanRight = true;
								this.alien.setScan(entities.get(i).get(j+1).getSymbol());
								smartScan(i,j);
								this.alienScannedFront = true;
							}
							else
								this.alien.setScan("*");
						}
						else if(alien.getFront() == "v") {
							if(i!= gridSize - 1) {
								scanDown = true;
								this.alien.setScan(entities.get(i+1).get(j).getSymbol());
								smartScan(i,j);
								this.alienScannedFront = true;
							}
							else
								this.alien.setScan("*");
						}
						else if(alien.getFront() == "<") {
							if(j!=0) {
								scanLeft = true;
								this.alien.setScan(entities.get(i).get(j-1).getSymbol());
								smartScan(i,j);
								this.alienScannedFront = true;
							}
							else 
								this.alien.setScan("*");
						}
					}
				}
			}
		}
		
		
		System.out.println(System.lineSeparator().repeat(150));
	}
	
	
	public void smartScan(int i , int j) {
		
		if(alien.scan() == "breeze") {
			this.smartRotate = true;
			this.pitLocations.add(new PitLocations(i,j));
		}
		
		else if(alien.scan() == "smell") {
			iNano = i;
			jNano = j;
			this.smartShoot = true;
		}
		
		else if(alien.scan() == "glitter") {
			this.smartMove = true;
		}
		
	
		
	}
	
	public void rotateAlien() {
		this.alien.rotate();
		this.alien.incrementRotation();
		if(alienIsAlive()) {
			System.out.println(System.lineSeparator().repeat(150));
		}
	}
	
	public void shootNano() {
		if(alien.getFront() == "^"){
			entities.get(iNano-1).set(jNano,new Space());
		}
		else if(alien.getFront() == ">") {
			entities.get(iNano).set(jNano+1, new Space());
		}
		else if(alien.getFront() == "v") {
			entities.get(iNano+1).set(jNano, new Space());
		}
		else if (alien.getFront() == "<") {
			entities.get(iNano).set(jNano-1, new Space());
		}
		
		this.nano.setAlive(false);
		
		this.smartShoot = false;
	}
	
	public boolean alienHasGold() {
		return this.alien.HasGold();
	}
	
	public boolean checkPit(int i, int j) {
		for(int n = 0;n<this.pitLocations.size();n++) {
			if((pitLocations.get(n).getI() == i) && (pitLocations.get(n).getJ() == j)){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean checkVisit(int i, int j) {
		for(int n =0;n<this.visitedLocations.size();n++) {
			if((visitedLocations.get(n).getI() == i) && (visitedLocations.get(n).getJ() == j)){
				return true;
			}
		}
		
		return false;
	}

	public ArrayList<ArrayList<Entity>> getEntities() {
		return entities;
	}

	public Alien getAlien() {
		return alien;
	}

	public Nano getNano() {
		return nano;
	}

	public Gold getGold() {
		return gold;
	}

	
	public int getGridSize() {
		return gridSize;
	}

	public boolean isAlienMoved() {
		return alienMoved;
	}

	public int getAgentOption() {
		return agentOption;
	}

	public boolean isSmartRotate() {
		return smartRotate;
	}

	public boolean isSmartMove() {
		return smartMove;
	}

	public boolean isSmartShoot() {
		return smartShoot;
	}

	public int getiNano() {
		return iNano;
	}

	public int getjNano() {
		return jNano;
	}

	public boolean isScanUp() {
		return scanUp;
	}

	public boolean isScanRight() {
		return scanRight;
	}

	public boolean isScanDown() {
		return scanDown;
	}

	public boolean isScanLeft() {
		return scanLeft;
	}

	public ArrayList<VisitedLocation> getVisitedLocations() {
		return visitedLocations;
	}

	public ArrayList<PitLocations> getPitLocations() {
		return pitLocations;
	}

	public boolean isAlienCanMove() {
		return alienCanMove;
	}
	
	public boolean getMoveInsteadRotate() {
		return this.moveInsteadRotate;
	}
	
	public void printVisited() {
		for(int n = 0 ;n<this.visitedLocations.size();n++){
			System.out.println("Visited i : " + this.visitedLocations.get(n).getI() + " Visited j: " + this.visitedLocations.get(n).getJ());
		}
	}
	
	public void printPits() {
		for(int n = 0 ;n<this.pitLocations.size();n++){
			System.out.println("Visited i : " + this.pitLocations.get(n).getI() + " Visited j: " + this.pitLocations.get(n).getJ());
		}
	}
	
	public void removeStuck(int i, int j) {
		int temp = this.visitedLocations.size();
		boolean stuckUp = false;
		boolean stuckDown = false;
		boolean stuckRight = false;
		boolean stuckLeft = false;
		
		
		
		for(int n = 0;n<temp;n++) {
		
			
			if(i == 0 & j == 0) {
				if(this.visitedLocations.get(n).getI() == i + 1 && this.visitedLocations.get(n).getJ() == j) {
					stuckDown = true;
				}
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j+1){
					stuckRight = true;
				}
			}
			else if (i == gridSize - 1 && j == 0) {
				if(this.visitedLocations.get(n).getI() == i - 1 && this.visitedLocations.get(n).getJ() == j) {
					stuckUp = true;
				}	
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j+1){
					stuckRight = true;
				}
			}
			else if (i == 0 && j == gridSize - 1 ) {
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j-1) {
					stuckLeft = true;
				}	
				if(this.visitedLocations.get(n).getI() == i + 1 && this.visitedLocations.get(n).getJ() == j) {
					stuckDown = true;
				}
			}
			
			else if (i == gridSize -1 && j == gridSize - 1 ) {
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j-1) {
					stuckLeft = true;
				}	
				if(this.visitedLocations.get(n).getI() == i - 1 && this.visitedLocations.get(n).getJ() == j) {
					stuckUp = true;
				}	
			}
			
			else if(i == 0) {
				if(this.visitedLocations.get(n).getI() == i + 1 && this.visitedLocations.get(n).getJ() == j) {
					stuckDown = true;
				}
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j-1) {
					stuckLeft = true;
				}
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j+1){
					stuckRight = true;
				}
			}
			
			else if (i == gridSize - 1) {
				if(this.visitedLocations.get(n).getI() == i - 1 && this.visitedLocations.get(n).getJ() == j) {
					stuckUp = true;
				}		
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j-1){
					stuckLeft = true;
				}
				
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j+1) {
					stuckRight = true;
				}
			}
			else if(j == 0) {
				if(this.visitedLocations.get(n).getI() == i + 1 && this.visitedLocations.get(n).getJ() == j) {
					stuckDown = true;
				}
						
				if(this.visitedLocations.get(n).getI() == i-1 && this.visitedLocations.get(n).getJ() == j) {
					stuckUp = true;
				}
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j+1){
					stuckRight = true;
				}
			}
			else if(j == gridSize -1) {
				if(this.visitedLocations.get(n).getI() == i + 1 && this.visitedLocations.get(n).getJ() == j){
					stuckUp = true;
				}
				if(this.visitedLocations.get(n).getI() == i-1 && this.visitedLocations.get(n).getJ() == j) {
					stuckDown = true;
				}
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j-1) {
					stuckLeft = true;
				}
			}
			else 
				
				if(this.visitedLocations.get(n).getI() == i -1 && this.visitedLocations.get(n).getJ() == j){
					stuckUp = true;
				}
				if(this.visitedLocations.get(n).getI() == i+1 && this.visitedLocations.get(n).getJ() == j) {
					stuckDown = true;
				}
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j-1) {
					stuckLeft = true;
				}
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j+1){
					stuckRight = true;
				}
			
		}
		
		int tempSize = this.visitedLocations.size();
		
		if(i == 0 && j == 0) {
			if(stuckDown == true && stuckRight == true) {
				
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i + 1 && this.visitedLocations.get(n).getJ() == j) {
						visitedLocations.remove(n);
					}
				}
				
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j+1){
						visitedLocations.remove(n);
					}
				}
			}
		}
		
		else if (i == gridSize - 1  && j == 0 ) {
			if(stuckUp == true && stuckRight == true) {
				
				
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i - 1 && this.visitedLocations.get(n).getJ() == j) {
						visitedLocations.remove(n);
					}
				}
				
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j+1){
						visitedLocations.remove(n);
					}
				}
				
			}
		}
		
		else if (i == 0 && j == gridSize - 1) {
			if(stuckLeft == true && stuckDown == true) {
				
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j-1){
						visitedLocations.remove(n);
					}
				}
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i + 1 && this.visitedLocations.get(n).getJ() == j) {
						visitedLocations.remove(n);
					}
				}
				
			}
		}
		
		else if ( i == gridSize - 1 && j == gridSize - 1) {
			if(stuckLeft == true && stuckUp == true) {
				
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j-1){
						visitedLocations.remove(n);
					}
				}
				
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i - 1 && this.visitedLocations.get(n).getJ() == j) {
						visitedLocations.remove(n);
					}
				}
				
			}
		}
		
		else if(i == 0) {
			if(stuckDown == true && stuckLeft == true && stuckRight == true) {
				
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i + 1 && this.visitedLocations.get(n).getJ() == j) {
						visitedLocations.remove(n);
					}
				}
				
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j-1){
						visitedLocations.remove(n);
					}
				}
				
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j+1){
						visitedLocations.remove(n);
					}
				}
			}
		}
		else if(i == gridSize - 1) {
			if(stuckUp == true && stuckLeft == true && stuckRight == true) {
				
				
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i - 1 && this.visitedLocations.get(n).getJ() == j) {
						visitedLocations.remove(n);
					}
				}
				
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j-1){
						visitedLocations.remove(n);
					}
				}
				
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j+1){
						visitedLocations.remove(n);
					}
				}
				
			}
		}
		else if(j == 0) {
			if(stuckDown == true && stuckUp == true && stuckRight == true) {
				
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i + 1 && this.visitedLocations.get(n).getJ() == j) {
						visitedLocations.remove(n);
						
					}
				}
				
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i -1  && this.visitedLocations.get(n).getJ() == j){
						visitedLocations.remove(n);
					}
				}
				
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j+1){
						visitedLocations.remove(n);
					}
				}
				
			}
		}
		else if (j == gridSize - 1) {
			if(stuckDown == true && stuckUp == true && stuckLeft == true) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i + 1 && this.visitedLocations.get(n).getJ() == j) {
						visitedLocations.remove(n);
					}
				}
				
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i -1  && this.visitedLocations.get(n).getJ() == j){
						visitedLocations.remove(n);
					}
				}
				
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j-1){
						visitedLocations.remove(n);
					}
				}
			}
		}
		
			if(stuckDown == true && stuckUp == true && stuckLeft == true && stuckRight == true) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i + 1 && this.visitedLocations.get(n).getJ() == j) {
						visitedLocations.remove(n);
					}
				}
				
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i - 1  && this.visitedLocations.get(n).getJ() == j){
						visitedLocations.remove(n);
					}
				}
				
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j-1){
						visitedLocations.remove(n);
					}
				}
				
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j + 1){
						visitedLocations.remove(n);
					}
				}
				
				
			}
		
		
	}
	
	
	public void removeSurroundPit(int i, int j) {
		
		boolean stuckUp = false;
		boolean stuckDown = false;
		boolean stuckLeft = false;
		boolean stuckRight = false;
		boolean pitUp = false;
		boolean pitDown = false;
		boolean pitLeft = false;
		boolean pitRight = false;
		
		
		int temp = this.visitedLocations.size();
		
		for(int n = 0; n<temp;n++) {
			
			if(i == 0 && j == 0) {
				if(this.visitedLocations.get(n).getI() == i + 1 && this.visitedLocations.get(n).getJ() == j) {
					stuckDown = true;
				}
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j+1){
					stuckRight = true;
				}
				
			    if(stuckDown == true) {
			    	if(this.entities.get(i).get(j+1) instanceof Pit) {
			    		pitRight = true;
			    	}
			    }
			    
			    if(stuckRight == true) {
			    	if(this.entities.get(i+1).get(j) instanceof Pit) {
			    		pitDown = true;
			    	}
			    }
			    
			    
			}
			
			else if (i == gridSize - 1 && j == 0) {
				if(this.visitedLocations.get(n).getI() == i-1 && this.visitedLocations.get(n).getJ() == j){
					stuckUp = true;
				}
				
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j+1){
					stuckRight = true;
				}
				
				
				if(stuckUp == true) {
					if(this.entities.get(i).get(j+1) instanceof Pit) {
			    		pitRight = true;
			    	}
				}
				if(stuckRight == true) {
					if(this.entities.get(i-1).get(j) instanceof Pit) {
				    	pitUp = true;
				    }
			
				}
				
			
			
			}
		 
			else if (i == 0  && j == gridSize - 1 ) {
				if(this.visitedLocations.get(n).getI() == i + 1 && this.visitedLocations.get(n).getJ() == j){
					stuckDown = true;
				}
				
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j - 1){
					stuckLeft = true;
				}
				
				
				if(stuckDown == true) {
					if(this.entities.get(i).get(j-1) instanceof Pit) {
			    		pitLeft = true;
			    	}
				}
				if(stuckLeft == true) {
					if(this.entities.get(i+1).get(j) instanceof Pit) {
				    	pitDown = true;
				    }
			
				}
				
			
			
			}
			
			else if (i == gridSize - 1  && j == gridSize - 1 ) {
				if(this.visitedLocations.get(n).getI() == i - 1 && this.visitedLocations.get(n).getJ() == j){
					stuckUp = true;
				}
				
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j - 1){
					stuckLeft = true;
				}
				
				
				if(stuckUp == true) {
					if(this.entities.get(i).get(j-1) instanceof Pit) {
			    		pitLeft = true;
			    	}
				}
				if(stuckLeft == true) {
					if(this.entities.get(i-1).get(j) instanceof Pit) {
				    	pitUp = true;
				    }
			
				}
			
			
			
			}
		  
			else if (j == 0) {
				if(this.visitedLocations.get(n).getI() == i - 1 && this.visitedLocations.get(n).getJ() == j){
					stuckUp = true;
				}
				
				if(this.visitedLocations.get(n).getI() == i + 1 && this.visitedLocations.get(n).getJ() == j){
					stuckDown = true;
				}
				
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j+1){
					stuckRight = true;
				}
				
				if(stuckDown == true) {
					if(this.entities.get(i).get(j+1) instanceof Pit) {
			    		pitRight = true;
			    	}
				}
				
				if(stuckRight == true) {
					if(this.entities.get(i-1).get(j) instanceof Pit) {
				    	pitUp = true;
				    }
					
					if(this.entities.get(i+1).get(j) instanceof Pit) {
						pitDown = true;
					}
				}
				
				if(stuckUp == true) {
					if(this.entities.get(i).get(j+1) instanceof Pit) {
			    		pitRight = true;
			    	}
				}
				
			}
			
			else if (i == 0) {
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j-1){
					stuckLeft = true;
				}
				
				if(this.visitedLocations.get(n).getI() == i + 1 && this.visitedLocations.get(n).getJ() == j){
					stuckDown = true;
				}
				
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j+1){
					stuckRight = true;
				}
				
				if(stuckDown == true) {
					if(this.entities.get(i).get(j+1) instanceof Pit) {
			    		pitRight = true;
			    	}
					
					if(this.entities.get(i).get(j-1) instanceof Pit) {
			    		pitLeft = true;
			    	}
				}
				
				if(stuckRight == true) {
					if(this.entities.get(i+1).get(j) instanceof Pit) {
						pitDown = true;
					}
				}
				
				if(stuckLeft == true) {
					if(this.entities.get(i+1).get(j) instanceof Pit) {
						pitDown = true;
					}
				}
			}
			
			else if (j == gridSize - 1) {
				if(this.visitedLocations.get(n).getI() == i - 1 && this.visitedLocations.get(n).getJ() == j){
					stuckUp = true;
				}
				
				if(this.visitedLocations.get(n).getI() == i + 1 && this.visitedLocations.get(n).getJ() == j){
					stuckDown = true;
				}
				
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j-1){
					stuckLeft = true;
				}
				
				if(stuckDown == true) {
					if(this.entities.get(i).get(j-1) instanceof Pit) {
			    		pitLeft = true;
			    	}
				}
				
				if(stuckLeft == true) {
					if(this.entities.get(i-1).get(j) instanceof Pit) {
				    	pitUp = true;
				    }
					
					if(this.entities.get(i+1).get(j) instanceof Pit) {
						pitDown = true;
					}
				}
				
				if(stuckUp == true) {
					if(this.entities.get(i).get(j-1) instanceof Pit) {
			    		pitLeft = true;
			    	}
				}
			}
			
			else if (i == gridSize - 1) {
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j-1){
					stuckLeft = true;
				}
				
				if(this.visitedLocations.get(n).getI() == i - 1 && this.visitedLocations.get(n).getJ() == j){
					stuckUp = true;
				}
				
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j+1){
					stuckRight = true;
				}
				
				if(stuckUp == true) {
					if(this.entities.get(i).get(j+1) instanceof Pit) {
			    		pitRight = true;
			    	}
					
					if(this.entities.get(i).get(j-1) instanceof Pit) {
			    		pitLeft = true;
			    	}
				}
				
				if(stuckRight == true) {
					if(this.entities.get(i-1).get(j) instanceof Pit) {
						pitUp = true;
					}
				}
				
				if(stuckLeft == true) {
					if(this.entities.get(i-1).get(j) instanceof Pit) {
						pitUp = true;
					}
				}
			}
			
			else {
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j-1){
					stuckLeft = true;
				}
				
				if(this.visitedLocations.get(n).getI() == i - 1 && this.visitedLocations.get(n).getJ() == j){
					stuckUp = true;
				}
				
				if(this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j+1){
					stuckRight = true;
				}
				
				if(this.visitedLocations.get(n).getI() == i + 1 && this.visitedLocations.get(n).getJ() == j){
					stuckDown = true;
				}
				
				if(stuckLeft == true) {
					if(this.entities.get(i-1).get(j) instanceof Pit) {
						pitUp = true;
					}
					if(this.entities.get(i+1).get(j) instanceof Pit) {
						pitDown = true;
					}
					
					if(this.entities.get(i).get(j+1) instanceof Pit) {
			    		pitRight = true;
			    	}
					
				}
				
				if(stuckRight == true) {
					if(this.entities.get(i-1).get(j) instanceof Pit) {
						pitUp = true;
					}
					if(this.entities.get(i+1).get(j) instanceof Pit) {
						pitDown = true;
					}
					
					if(this.entities.get(i).get(j-1) instanceof Pit) {
			    		pitLeft = true;
			    	}
					
				}
				
				if(stuckUp == true) {
					if(this.entities.get(i).get(j-1) instanceof Pit) {
			    		pitLeft = true;
			    	}
					if(this.entities.get(i+1).get(j) instanceof Pit) {
						pitDown = true;
					}
					
					if(this.entities.get(i).get(j+1) instanceof Pit) {
			    		pitRight = true;
			    	}
					
				}
				
				if(stuckDown == true) {
					if(this.entities.get(i).get(j-1) instanceof Pit) {
			    		pitLeft = true;
			    	}
					if(this.entities.get(i-1).get(j) instanceof Pit) {
						pitUp = true;
					}
					
					if(this.entities.get(i).get(j+1) instanceof Pit) {
			    		pitRight = true;
			    	}
					
				}
				
			}
			
		}
		
		int tempSize = this.visitedLocations.size();
		
		if(i == 0 && j == 0) {
			if(stuckRight == true && pitDown == true) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j + 1){
						visitedLocations.remove(n);
					}
				}
			}
			
			if(stuckDown == true && pitRight == true) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i +1 && this.visitedLocations.get(n).getJ() == j){
						visitedLocations.remove(n);
					}
				}
			}
		}
		
		else if (i == gridSize - 1 && j == 0) {
			if(stuckUp == true && pitRight == true) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i-1 && this.visitedLocations.get(n).getJ() == j){
						visitedLocations.remove(n);
					}
				}
			}
			
			if(stuckRight == true && pitUp == true) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j+1){
						visitedLocations.remove(n);
					}
				}
			}
		}
		
		else if (i == 0  && j == gridSize - 1 ) {
			if(stuckLeft == true && pitDown == true) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j-1){
						visitedLocations.remove(n);
					}
				}
			}
			
			if(stuckDown == true && pitLeft == true) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i+1 && this.visitedLocations.get(n).getJ() == j){
						visitedLocations.remove(n);
					}
				}
			}
		}
		else if (i == gridSize - 1  && j == gridSize - 1 ) {
			if(stuckLeft == true && pitUp == true) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j-1){
						visitedLocations.remove(n);
					}
				}
			}
			
			if(stuckUp == true && pitLeft == true) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i-1 && this.visitedLocations.get(n).getJ() == j){
						visitedLocations.remove(n);
					}
				}
			}
		}
		else if(j == 0 ) {
			//up down right
			if(stuckUp == true && (pitRight == true ||pitDown == true)) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i-1 && this.visitedLocations.get(n).getJ() == j){
						visitedLocations.remove(n);
					}
				}
			}
			
			if(stuckDown == true && (pitRight == true ||pitUp == true)) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i+1 && this.visitedLocations.get(n).getJ() == j){
						visitedLocations.remove(n);
					}
				}
			}
			
			if(stuckRight == true && (pitUp == true ||pitDown == true)) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j+1){
						visitedLocations.remove(n);
					}
				}
			}
		}
		else if (i == 0) {
			//left down right
			if(stuckLeft == true && (pitRight == true ||pitDown == true)) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j-1){
						visitedLocations.remove(n);
					}
				}
			}
			
			if(stuckDown == true && (pitRight == true ||pitLeft == true)) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i+1 && this.visitedLocations.get(n).getJ() == j){
						visitedLocations.remove(n);
					}
				}
			}
			
			if(stuckRight == true && (pitLeft == true ||pitDown == true)) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j+1){
						visitedLocations.remove(n);
					}
				}
			}
			
			
		}
		else if (j == gridSize -1 ) {
			//up left down
			if(stuckUp == true && (pitLeft == true ||pitDown == true)) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i-1 && this.visitedLocations.get(n).getJ() == j){
						visitedLocations.remove(n);
					}
				}
			}
			if(stuckLeft == true && (pitUp == true ||pitDown == true)) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j-1){
						visitedLocations.remove(n);
					}
				}
			}
			
			if(stuckDown == true && (pitLeft == true ||pitUp == true)) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i+1 && this.visitedLocations.get(n).getJ() == j){
						visitedLocations.remove(n);
					}
				}
			}
		}
		else if (i == gridSize -1 ) {
			//up left right
			if(stuckUp == true && (pitLeft == true ||pitRight == true)) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i-1 && this.visitedLocations.get(n).getJ() == j){
						visitedLocations.remove(n);
					}
				}
			}
			
			if(stuckLeft == true && (pitUp == true ||pitRight == true)) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j-1){
						visitedLocations.remove(n);
					}
				}
			}
			
			if(stuckRight == true && (pitUp == true ||pitLeft == true)) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j+1){
						visitedLocations.remove(n);
					}
				}
			}
		}
		else {
			if(stuckUp == true && (pitDown == true || pitRight == true || pitLeft == true)) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i-1 && this.visitedLocations.get(n).getJ() == j){
						visitedLocations.remove(n);
					}
				}
			}
			
			if(stuckDown == true && (pitUp == true || pitRight == true || pitLeft == true)) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i+1 && this.visitedLocations.get(n).getJ() == j){
						visitedLocations.remove(n);
					}
				}
			}
			
			if(stuckLeft == true && (pitUp == true || pitRight == true || pitDown == true)) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j-1){
						visitedLocations.remove(n);
					}
				}
			}
			
			if(stuckRight == true && (pitUp == true || pitLeft == true || pitDown == true)) {
				for(int n = 0;n<tempSize;n++) {
					if(n < this.visitedLocations.size() && this.visitedLocations.get(n).getI() == i && this.visitedLocations.get(n).getJ() == j+1){
						visitedLocations.remove(n);
					}
				}
			}
		}
		
	  }
	
	public void randomMove() {
		
		if(this.rotateSuccessions == 3) {
			scanFrontOfAlien();
			this.rotateSuccessions = 0;
		}
		else {
			int randomNum;
			Random random = new Random();
			
			randomNum = random.nextInt(3);
			
			if (randomNum == 0 || randomNum == 2) {
				scanFrontOfAlien();
			}
			else if(randomNum == 1) {
				rotateAlien();
				this.rotateSuccessions++;
			}
		}
		
	}
}

