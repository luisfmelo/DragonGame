package logic;

import java.util.Scanner;

public class Game {
	private Maze maze = new Maze();
	private Hero hero = new Hero(); 		//pos (1,1)
	private Dragon dragon = new Dragon(); 	//pos (1,3)
	private Sword sword = new Sword(); 		//pos (1,8)
	private Exit exit = new Exit(); 		//pos (9,5)

	
	public Game(){
		this.maze.setLen(10);
		this.maze.newMaze();
		this.hero.setPosX(1);
		this.hero.setPosY(1);
		this.dragon.setPosX(1);
		this.dragon.setPosY(1);
		this.exit.setPosX(1);
		this.exit.setPosY(1);
		this.sword.setPosX(1);
		this.sword.setPosY(1);
	}

	public void run() {
		String key;

		//Running: acho que e melhor meter isto na class Game(faz parte da logica do jogo
		while(true)
		{
			maze.print();
			Scanner sc = new Scanner(System.in);
			key = sc.next();
			//criar metodo: move? 
			//mas ja tenho o setPosX e o setPosY
			//preciso de uma funçao que check se a posiçao e correta
			if ((key == "W" || key == "w") && checkPos('H', hero.getPosX(), hero.getPosY() - 1))
			{
				hero.setPosY(hero.getPosY() - 1);
			}
			
		}
		
	}

	//TODO
	private boolean checkPos(char c, int posX, int posY) {
		if ( this.maze.getMaze()[posX][posY] == 'X' )
			return false;
		
		return true;
	}
}
