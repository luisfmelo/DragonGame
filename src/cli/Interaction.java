package cli;

import java.util.Scanner;

import logic.Game;
import logic.Maze;

public class Interaction {

	public static void main(String[] args) {
		System.out.println("Dragon Game!");
		Maze maze = new Maze();
		int round = 1;
		
	//Game Config
		//Dragao: parado/mov aleatoria/mov aleatoria + dormir
		System.out.println("Nivel de exig�ncia pretendido:");
		System.out.println("1. Dragao a dormir");
		System.out.println("2. Dragao com movimento aleatorio intercalado com dormir");
		System.out.println("3. Dragao com movimento aleatorio sempre acordado");
		Scanner sc = new Scanner(System.in);
		int level = sc.nextInt();
		
		if( level > 3 || level < 1)
		{
			System.out.println("Erro na sele��o do nivel. A terminar Jogo");
			System.exit(2);
		}
		
		maze.setLen(10);
		maze.newMaze();
		
	//Create Game
		Game myGame = new Game(maze, level);
		
	//Run Game
		String key;
		boolean c;
		myGame.setGameRunning(true);

		while( myGame.isGameRunning() )
		{
			System.out.println("\nRound: " + round + "\n");
		//0. Print Maze
			maze.print();
			
		//1. receive command
			sc = new Scanner(System.in);
			key = sc.next();
			
		//2. Check
			try {
				//myGame.checkHeroPos(maze, key);
				c = myGame.checkPos(maze, key, myGame.hero);
				if ( !c )
					continue;
			} catch (IllegalArgumentException e) {
				continue;
			}
			
		//3. pc faz o seu move
			myGame.pcMove(maze);

			round++;
		}		
		
		sc.close();

	}

}
