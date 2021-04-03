package mco1;
import java.util.Scanner;
public class Launcher {
	
	private static int gridSize;
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		do {
			System.out.println("Enter grid size (n):");
			gridSize = scanner.nextInt();
			if(gridSize<8 || gridSize >64) {
				System.out.println("Invalid grid size, enter a value between 8 and 64.");
			}
			
		}while(gridSize < 8 || gridSize >64);
		
		
		World world = new World(gridSize);
		world.initializeWorld();
		world.drawBoard();
		scanner.close(); // put at end of the function
	}
}
