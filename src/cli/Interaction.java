package cli;

import java.util.Scanner;

import logic.Game;

public class Interaction {

	public static void main(String[] args) {
		System.out.println("Dragon Game!");
		//Game Config
			//Dragao: parado/mov aleatoria/mov aleatoria + dormir
		System.out.println("Nivel de exigência pretendido:");
		System.out.println("1. Dragao a dormin");
		System.out.println("2. Dragao com movimento aleatorio intercalado com dormir");
		System.out.println("3. Dragao com movimento aleatorio sempre acordado");
		Scanner sc = new Scanner(System.in);
		int level = sc.nextInt();
		
		//Create Game
		Game myGame = new Game(level);
		
		//Run Game
		myGame.run();
		
		
		sc.close();

	}

}
