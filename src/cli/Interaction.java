package cli;

import java.util.Scanner;

import logic.Game;
import logic.Maze;

public class Interaction {	

	public static void main(String[] args) {
		System.out.println("Dragon Game!");
		Scanner sc = new Scanner(System.in);
		int level, len;

		int round = 1;
		
	//Game Config
		// Get Size
		/*System.out.print("Tamanho do labirinto (impar): ");
		sc = new Scanner(System.in);
		len = sc.nextInt();*/
		len = 30;

		Maze maze = new Maze(len);

		// Dragao: parado/mov aleatoria/mov aleatoria + dormir
		/*System.out.println("Nivel de exigencia pretendido:");
		System.out.println("1. Dragao a dormir");
		System.out.println("2. Dragao com movimento aleatorio intercalado com dormir");
		System.out.println("3. Dragao com movimento aleatorio sempre acordado");
		sc = new Scanner(System.in);

		level = sc.nextInt();
		if( level > 3 || level < 1)
		{
			System.out.println("Erro na selecao do nivel. A terminar Jogo");
			System.exit(2);
		}*/
		level = 3;
		
		
	//Create Game
		Game myGame = new Game(maze, level);

		
		//maze.print();
		
		/*
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
		*/
		sc.close();

	}

}
