package mco1;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Launcher  extends TimerTask{
	
	private static int gridSize;
	private static Timer timer = new Timer();
	private static World world;
	private static int numberOfSeconds;
	
	Random random = new Random();
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
		
		
		world = new World(gridSize);
		System.out.println("Enter number of seconds between each move:");
		numberOfSeconds = scanner.nextInt() * 1000 ;
		
		world.initializeWorld();
		world.drawBoard();
		
		if(world.alienIsAlive()) {
			timer.schedule(new Launcher(),0,numberOfSeconds);
		}
		
		
		
		
		
		
		scanner.close(); 
	}
	@Override
	public void run() {
//		int randomNumber;
//		
//		if(world.alienIsAlive()) {
//			 randomNumber = random.nextInt(2);
//			
//			if(randomNumber == 1) {
//				world.moveAlien();
//			}
//			else {
//				world.rotateAlien();
//			}
//			
//			world.drawBoard();
//		}
//		else {
//			timer.cancel();
//		}
//		
		
		world.moveAlienDown();
		world.drawBoard();
	}
	
	
}
