package mco1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Launcher  extends TimerTask{
	
	private static int gridSize;
	private static Timer timer = new Timer();
	private static World world;
	private static int speed;
	private static int gameOption;
	private static int agentOption;
	private static boolean scanExecuted = false;
	private static boolean rotateExecute = false;
	private static boolean moveExecuted = false;
	private static int i = 0;
	private static int j = 0 ;
	static Random random = new Random();
	public static void main(String[] args) {
		
		Launcher launcher = new Launcher();
		
		Scanner scanner = new Scanner(System.in);
		
		do {
			System.out.println("Enter grid size (n):");
			gridSize = scanner.nextInt();
			if(gridSize<8 || gridSize >50) {
				System.out.println("Invalid grid size, enter a value between 8 and 50.");
			}
			
		}while(gridSize < 8 || gridSize >50);
		
		
		
		do {
			System.out.println("[1] Level R (Random)");
			System.out.println("[2] Level S (Smart)");
			System.out.print("User choice: ");
			agentOption = scanner.nextInt();
		}while(agentOption < 1 || agentOption > 2);
		
		do {
			System.out.println("[1] Step by step");
			System.out.println("[2] Continuous" );
			System.out.print("User choice: ");
			gameOption = scanner.nextInt();
		}while(gameOption < 1 || gameOption > 2);
		
//		do {
//			System.out.println("[1] Choose location for the entities");
//			System.out.println("[2] Generate random locations");
//			System.out.println("User choice: ");
//			userOption = scanner.nextInt();
//		}while()
		
		if(gameOption == 2) {
			do {
				System.out.println("Enter speed from 1 - 20 (1 being the fastest) : ");
				speed = scanner.nextInt();
			}while(speed < 1 || speed > 20);
		}
		
		
		world = new World(gridSize,agentOption);
		world.initializeWorld();
		world.drawBoard();
		
		if(world.alienIsAlive() && agentOption == 1){
			System.out.println("value of gameOption = " + gameOption);
			
			if(gameOption == 1) {
			     stepByStepRandom();
				}
			
			
			else {
				System.out.println("executed");
				timer.schedule(new Launcher(),0, speed * 100);
			}
		}
		else if(world.alienIsAlive() && agentOption == 2) {
			
			timer.schedule(new Launcher(),0, speed * 100);
			
		}
		
		scanner.close(); 
	}	
	
	private static void stepByStepRandom() {
		
		Scanner newScanner = new Scanner(System.in);
		
		while(world.alienIsAlive() && !(world.alienHasGold())) {
			int randomNumber;
			
			randomNumber = random.nextInt(3);
			
			if(randomNumber == 0) {
				world.moveAlien();
			}
			else if (randomNumber == 1){
				world.rotateAlien();
			}
			else if(randomNumber == 2) {
				world.scanFrontOfAlien();
			}
			
			world.drawBoard();
			
			newScanner.nextLine();
		}
		
	}

	@Override
	public void run() {
		
		if(agentOption == 1) {
			int randomNumber;
			
			if(world.alienIsAlive() && !(world.alienHasGold())){
				 randomNumber = random.nextInt(3);
			
				if(randomNumber == 0) {
					world.moveAlien();
				}
				else if (randomNumber == 1){
					world.rotateAlien();
				}
				else if(randomNumber == 2) {
					world.scanFrontOfAlien();
				}
				
				world.drawBoard();
			}
			else {
				timer.cancel();
			}
		}
		else if (agentOption == 2) {
				if(world.alienIsAlive() && !(world.alienHasGold())) {
			

				if(world.isSmartShoot() == true) {
					world.shootNano();
				}
				else if(world.isSmartMove() == true) {
					world.moveAlien();
				}
				else {
					   if((!world.isScanUp() || !world.isScanDown() || !world.isScanLeft() || !world.isScanRight()) && i == j) {
					    	world.scanFrontOfAlien();
					    	i++;
					    }
			
					   else if(i!= j && j!=4){
						   world.rotateAlien();
						   j++;
					   }
					   
					   else {
						   world.smartMove();
						   if(world.getMoveInsteadRotate() == false) {
							   i = 0;
							   j = 0;
						   }
						  
					   }
						   
					   }
				
				world.drawBoard();
				}
					
				else {
					timer.cancel();
				}
			   
				
				
			}
				
		}
		

	}
	
	

