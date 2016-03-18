package cli;

import java.util.Scanner;
import logic.Game;
import logic.Maze;
import logic.Point;

public class Interaction {	

	public static void main(String[] args) {
		System.out.println("Dragon Game!");
		Scanner sc = new Scanner(System.in);
		int level, len, n_dragons;

		int round = 1;
		n_dragons=4;
	//Game Config
		// Get Size
		System.out.print("Tamanho do labirinto (impar): ");
		sc = new Scanner(System.in);
		len = sc.nextInt();

		// Dragao: parado/mov aleatoria/mov aleatoria + dormir
		System.out.println("Nivel de exigencia pretendido:");
		System.out.println("1. Dragao a dormir");
		System.out.println("2. Dragao com movimento aleatorio intercalado com dormir");
		System.out.println("3. Dragao com movimento aleatorio sempre acordado");
		sc = new Scanner(System.in);

		level = sc.nextInt();
		if( level > 3 || level < 1)
		{
			System.out.println("Erro na selecao do nivel. A terminar Jogo");
			System.exit(2);
		}
		
	//Create Game
		Game myGame = new Game(level, Integer.toString(len), Integer.toString(n_dragons));

		
		//maze.print();
		
		
	//Run Game
		String key;
		boolean c;
		myGame.setGameRunning(true);

		while( myGame.isGameRunning() )
		{
		//0. Round Status Maze
			System.out.println("\nRound: " + round + "\n");
			myGame.maze.print();

		//1. receive command
			sc = new Scanner(System.in);
			key = sc.next();
			
		//2. Check
			try {
				if ( !myGame.checkPos(key.charAt(0), myGame.hero) )
					continue;
			} catch (IllegalArgumentException e) {
				continue;
			}
			
		//3. pc faz o seu move
			for(int i=0; i<myGame.dragons.size();i++)
				myGame.pcMove(myGame.dragons.get(i));

			round++;
		}		
		
		sc.close();
	}
}