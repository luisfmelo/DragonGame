package cli;

import java.util.Scanner;
import logic.Game;

public class Interaction {	

	/**
	 * Main Class for <b>C</b>ommand <b>L</b>ine <b>I</b>nterface 
	 * @author Luis
	 * @author Teresa
	 */
	public static void main(String[] args) {
		System.out.println("Dragon Game!");
		Scanner sc = new Scanner(System.in);
		int level, len, n_dragons;

		int round = 1;
		n_dragons=0;
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
		
	//Run Game
		String key;
		myGame.setGameRunning(true);

		while( myGame.isGameRunning() )
		{
		// 0. Round Status Maze
			System.out.println("\nRound: " + round + "\n");
			myGame.getMaze().print();

		// 1. receive command
			sc = new Scanner(System.in);
			key = sc.next();
			
		// 2. Check
			try {
				if ( !myGame.checkPos(key.charAt(0), myGame.getHero() ) )
					continue;
			} catch (IllegalArgumentException e) {
				continue;
			}
			
		// 3. pc faz o seu move
			for(int i=0; i<myGame.getDragons().size();i++)
				myGame.pcMove(myGame.getDragons().get(i));
			
		// 4. update game status
			if ( myGame.defeatOrLose() )
				myGame.setGameRunning(false);
			round++;
		}		
		
		sc.close();
	}
}