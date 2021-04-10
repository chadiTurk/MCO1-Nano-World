package mco1;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Launcher  extends TimerTask{
	
	private static int gridSize;
	private static Timer timer = new Timer();
	private static World world;
	private static int speed;
	
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
		
		do {
			System.out.println("Enter speed from 1 - 20 (1 being the fastest) : ");
			speed = scanner.nextInt();
		}while(speed < 1 || speed > 20);
		
		
		world.initializeWorld();
		world.drawBoard();
		
		if(world.alienIsAlive()) {
			timer.schedule(new Launcher(),0, speed * 100);
		}
		
		
		
		
		
		
		scanner.close(); 
	}
	@Override
	public void run() {
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
	
	
}
