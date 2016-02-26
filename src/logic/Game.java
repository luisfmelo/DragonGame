package logic;

import java.util.Scanner;

public class Game {
	private Hero hero = new Hero(); 		//pos (1,1)
	private Dragon dragon = new Dragon(); 	//pos (1,3)
	private Sword sword = new Sword(); 		//pos (1,8)
	private Exit exit = new Exit(); 		//pos (9,5)
	private boolean GameRunning = false;
	private int level;

	
	public Game(Maze maze, int level){
		this.level = level;

		dragon.setPos(maze, 1, 3);
		sword.setPos(maze, 1, 8);
		exit.setPos(maze, 9, 5);
		hero.setPos(maze, 1, 1);
		maze.print();
	}

	
	//TODO
	public void checkPos(Maze maze, String c) throws IllegalArgumentException {
		int newPosX, newPosY;
		
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
			default: 	throw new IllegalArgumentException();
						
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
		else if ( nearDragon(newPosY, newPosX) && !hero.isArmed() && (dragon.getLetter() != 'd') )
		{
			hero.setDead(true);
			hero.setLetter(' ');
			hero.setPos(maze, hero.getPosX(), hero.getPosY());
			endGame("lose");
		}
		//check if dragon alive and hero wants to get out
		else if( !dragon.isDead() && maze.maze[newPosY][newPosX] == 'S' )
		{
			newPosX = hero.getPosX();
			newPosY = hero.getPosY();
			System.out.println("Dragon is still alive!");
		}
		//check if dragon is dead and hero wants to get out -> WIN
		else if( dragon.isDead() && maze.maze[newPosY][newPosX] == 'S' )
		{
			hero.setPos(maze, exit.getPosX(), exit.getPosY());
			endGame("win");
		}
				
		hero.setPos(maze, newPosX, newPosY);	
	}
	
	//hero and Dragon near each other 
	private boolean nearDragon(int h_y, int h_x)
	{
		int d_y = dragon.getPosY(), d_x = dragon.getPosX();
		
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
		
		setGameRunning(false);
	};
	
	//TODO
	private boolean pcMove(){
		return true;
	}

	public void config() {
		// TODO Auto-generated method stub
		
	}


	public boolean isGameRunning() {
		return GameRunning;
	}


	public void setGameRunning(boolean gameRunning) {
		GameRunning = gameRunning;
	};
}
