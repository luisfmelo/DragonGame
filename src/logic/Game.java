package logic;

import java.util.Random;

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
		sword.setPos(maze, 1, 4);
		exit.setPos(maze, 9, 5);
		hero.setPos(maze, 1, 1);
		maze.print();
	}

	//hero and Dragon near each other 
	private boolean near(int y, int x, char element)
	{
		int next_y, next_x;
		
		//near Dragon
		if(element == 'H'){
			next_y = dragon.getPosY();
			next_x = dragon.getPosX();
		}
		//near Hero
		else if (element == 'D')
		{
			next_y = hero.getPosY();
			next_x = hero.getPosX();
		}
		else
		{
			System.out.println("Parametro Incorreto");
			throw new IllegalArgumentException();
		}
		
		if(	y >= next_y - 1 && y <= next_y + 1 &&
			x >= next_x - 1 && x <= next_x + 1 )
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
	
	public boolean isGameRunning() {
		return GameRunning;
	}

	public void setGameRunning(boolean gameRunning) {
		GameRunning = gameRunning;
	};

	public void pcMove(Maze maze){
		if ( level == 1)
			return;
		else if (level == 2)
			sleepyDragon(50); //probabilidade de o Dragao estar a dormir
		
		Random Rand = new Random();
		int move = Rand.nextInt(4);//5);    // tirei o 5 para ele andar sempre pelo menos uma vez
		boolean c;
		if (level == 2 && dragon.isSleepy())
			move = 4; //if dragon is taking a nap... don't do anything in switch...case
		
		System.out.println("----------->" + move);
		switch (move){
			// Cima
			case 0:
				c = checkDragonPos(maze, "W");
				if ( !c )
					pcMove(maze);
				break;
			//Baixo
			case 1:
				c = checkDragonPos(maze, "S");
				if ( !c )
					pcMove(maze);
				break;
			//Direita
			case 2:
				c = checkDragonPos(maze, "D");
				if ( !c )
					pcMove(maze);
				break;
			//Esquerda
			case 3:
				c = checkDragonPos(maze, "A");
				if ( !c )
					pcMove(maze);
				break;
			//case 4: don't move	
		}
	};
	
	private void sleepyDragon(int p) {
		Random Rand = new Random();
		int sleep = Rand.nextInt(100);
		
		if (sleep <= p) //go back to sleep
		{
			dragon.setSleepy(true);
			dragon.setLetter('d');
		}
		else if (sleep > p && dragon.isSleepy())//wake up sunshine
		{
			dragon.setSleepy(false);
			dragon.setLetter('D');
		}
	}

	public void checkHeroPos(Maze maze, String c) throws IllegalArgumentException {
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
		else if ( near(newPosY, newPosX, 'H') && hero.isArmed())
		{
			dragon.setDead(true);
			dragon.setLetter(' ');
			dragon.setPos(maze, dragon.getPosX(), dragon.getPosY());
		}
		//dragon kills hero -> GAME OVER
		else if ( near(newPosY, newPosX, 'H') && !hero.isArmed() && (dragon.getLetter() != 'd') )
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
		
	private boolean checkDragonPos(Maze maze, String c) {
		int newPosX = -1, newPosY = -1;
	
		switch(c.toUpperCase().charAt(0)){
			case 'S': 	newPosY = dragon.getPosY() + 1; 
					  	newPosX = dragon.getPosX();
					  	break;
			case 'A': 	newPosX = dragon.getPosX() - 1;
					  	newPosY = dragon.getPosY();
					  	break;
			case 'W': 	newPosY = dragon.getPosY() - 1; 
					  	newPosX = dragon.getPosX();
					  	break;
			case 'D': 	newPosX = dragon.getPosX() + 1;
					  	newPosY = dragon.getPosY();
					  	break;
			default: throw new IllegalArgumentException();
		}
	
		boolean dragon_sword=false;
		
		//check if it is a wall
		if( maze.maze[newPosY][newPosX] == 'X' )
		{
			newPosX = dragon.getPosX();
			newPosY = dragon.getPosY();
			return false;
		}
		//check if he picked sword
		else if ( maze.maze[newPosY][newPosX] == 'E' )
		{
			sword.setLetter(' ');
			dragon.setLetter('F');
			dragon_sword=true;
		}
		//hero kills dragon
		else if ( near(newPosY, newPosX, 'D') && hero.isArmed())
		{
			dragon.setDead(true);
			dragon.setLetter(' ');
			dragon.setPos(maze, dragon.getPosX(), dragon.getPosY());
		}
		//dragon kills hero -> GAME OVER
		else if ( near(newPosY, newPosX, 'D') && !hero.isArmed())
		{
			hero.setDead(true);
			hero.setLetter(' ');
			hero.setPos(maze, dragon.getPosX(), dragon.getPosY());
			endGame("lose");
		}
		
		if(!dragon_sword )
		{
			if( !dragon.isDead())
				dragon.setLetter('D');
			sword.setLetter('E');	
			sword.setPos(maze, sword.getPosX(), sword.getPosY());
		}
			
		dragon.setPos(maze, newPosX, newPosY);
	
		return true;
	}

}