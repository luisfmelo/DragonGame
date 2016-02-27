package cli;

import java.util.Scanner;

import logic.Game;
import logic.Maze;

public class Interaction {

	public static void main(String[] args) {
		System.out.println("Dragon Game!");
		Maze maze = new Maze();
		
	//Game Config
		//Dragao: parado/mov aleatoria/mov aleatoria + dormir
		System.out.println("Nivel de exigência pretendido:");
		System.out.println("1. Dragao a dormin");
		System.out.println("2. Dragao com movimento aleatorio intercalado com dormir");
		System.out.println("3. Dragao com movimento aleatorio sempre acordado");
		Scanner sc = new Scanner(System.in);
		int level = sc.nextInt();
		
		maze.setLen(10);
		maze.newMaze();
		
	//Create Game
		Game myGame = new Game(maze, level);
		
	//Run Game
		//myGame.run();
		String key;
		myGame.setGameRunning(true);

		while( myGame.isGameRunning() )
		{
		//1. receive command
			sc = new Scanner(System.in);
			key = sc.next();
			
		//2. Check
			try {
				myGame.checkHeroPos(maze, key);
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid command! Try again.");
				continue;
			}
			
		//3. pc faz o seu move -> DO NOT DO NOW
			myGame.pcMove(maze);
			
		//4. print maze
			maze.print();
		}		
		
		sc.close();

	}

}
