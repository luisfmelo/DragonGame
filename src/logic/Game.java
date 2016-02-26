package logic;

import java.util.Scanner;

public class Game {
	private Maze maze = new Maze();
	private Hero hero = new Hero(); 		//pos (1,1)
	private Dragon dragon = new Dragon(); 	//pos (1,3)
	private Sword sword = new Sword(); 		//pos (1,8)
	private Exit exit = new Exit(); 		//pos (9,5)
	private boolean GameRunning = false;
	private Scanner sc;

	
	public Game(){
		maze.setLen(10);
		maze.newMaze();
		
		dragon.setPos(maze, 1, 3);
		sword.setPos(maze, 1, 8);
		exit.setPos(maze, 9, 5);
		hero.setPos(maze, 1, 1);
		maze.print();
	}

	public void run() {
		String key;
		boolean check = false;
		GameRunning = true;

		//Running: acho que e melhor meter isto na class Game(faz parte da logica do jogo
		while(GameRunning)
		{
			//1. receive command
			key = receiveCommand();
			
			//2. Check
			check = checkPos(key);
				//2.1 comando 		ok: anda
					
				//2.2 comando   not ok: faz algo
			
			//3. pc faz o seu move -> DO NOT DO NOW
			
			//4. print maze
			maze.print();
		}
		sc.close();
		
	}
	
	private String receiveCommand(){
		sc = new Scanner(System.in);
		return sc.next();
	};

	//TODO
	private boolean checkPos(String c) {
		int newPosX = -1, newPosY = -1;
		
		switch(c.toUpperCase().charAt(0)){
			case 'S': 	newPosY = hero.getPosY() + 1; 
					  	newPosX = hero.getPosX();
					  	break;
			case 'A': 	newPosX = hero.getPosX() - 1;
					  	newPosY = hero.getPosY();
					  	break;
			case 'W': 	newPosY = hero.getPosY() - 1; 
					  	newPosX = hero.getPosX();
					  	break;
			case 'D': 	newPosX = hero.getPosX() + 1;
					  	newPosY = hero.getPosY();
					  	break;
			default: newPosY = -1; newPosX = -1;
		}

		
		// DEBUG
		//*********************************************************
		System.out.println("input:" + c.toUpperCase().charAt(0));
		System.out.println("ANTIGAS POSICOES: (" + hero.getPosX() + ";" + hero.getPosY() + ")");
		System.out.println("NOVAS POSICOES: (" + newPosX + ";" + newPosY + ")");
		
		if ( newPosX > -1 )
			System.out.println("ESTAVA: \'" + maze.maze[newPosY][newPosX] + "\'");
		//*********************************************************
		
		
		//check if it is a wall
		if( maze.maze[newPosY][newPosX] == 'X' )
		{
			newPosX = hero.getPosX();
			newPosY = hero.getPosY();
		}
		//check if he picked sword
		else if ( maze.maze[newPosY][newPosX] == 'E' )
		{
			hero.setArmed(true);
			sword.setVisible(false);
			hero.setLetter('A');
		}
		//hero kills dragon
		else if ( nearDragon(newPosY, newPosX) && hero.isArmed())
		{
			dragon.setDead(true);
			dragon.setLetter(' ');
			dragon.setPos(maze, dragon.getPosX(), dragon.getPosY());
		}
		//dragon kills hero -> GAME OVER
		else if ( nearDragon(newPosY, newPosX) && !hero.isArmed())
		{
			hero.setDead(true);
			hero.setLetter(' ');
			hero.setPos(maze, hero.getPosX(), hero.getPosY());
			endGame("lose");
		}
		hero.setPos(maze, newPosX, newPosY);	
		//System.out.println("NOVA POSICAO: " + newPosX + "|" + newPosY + "..");
		
		return true;
	}
	
	//hero and Dragon near each other 
	private boolean nearDragon(int h_y, int h_x)
	{
		int d_y = dragon.getPosY(), d_x = dragon.getPosX();
		boolean adjPos = false;
		
		if(	h_y >= d_y - 1 && h_y <= d_y + 1 &&
			h_x >= d_x - 1 && h_x <= d_x + 1 )
			return true;

		return false;
	};
	
	private void endGame(String state){
		if (state.equals("lose"))
		{
			System.out.println("\n\nGAME OVER\n\n");
			//handle game over
		}
		else if (state.equals("win"))
		{
			System.out.println("\n\nCONGRATULATION\n\n");
			//handle Victory
		}
		
		GameRunning = false;
	};
	
	//TODO
	private boolean pcMove(){
		return true;
	};
}
