package logic;

import java.util.Random;

public class Game {
	public Hero hero = new Hero(); 			//pos (1,1)
	private Dragon dragon = new Dragon(); 	//pos (1,3)
	private Sword sword = new Sword(); 		//pos (1,8)
	private Exit exit = new Exit(); 		//pos (9,5)
	private boolean GameRunning = false;
	private int level = 1;
	
	public Game(Maze maze, int lvl){
		try{
			maze.setRandomMaze(hero, dragon, sword, exit);
			//dragon.setRandomPos(maze);
			sword.setRandomPos(maze);
			while ( true)
			{
				dragon.setRandomPos(maze);
				hero.setRandomPos(maze);
				if ( !near(hero.getPosY(), hero.getPosX(), dragon.getLetter()) )
					break;
			}

		}catch (IllegalArgumentException e) {
			maze.setDefaultMaze();
		}
		
		this.level = lvl;		
		if ( level == 1)
		{
			dragon.setLetter('d');
			dragon.setPos(maze, dragon.getPosX(), dragon.getPosY());			
		}
	}

	//hero and Dragon near each other 
	public boolean near(int y, int x, char element)
	{
		int next_y, next_x;
			
		//near Dragon
		if(element == 'H' || element == 'A'){
			next_y = dragon.getPosY();
			next_x = dragon.getPosX();
		}
		//near Hero
		else if (element == 'D' || element == 'd')
		{
			next_y = hero.getPosY();
			next_x = hero.getPosX();
		}
		//algum deles morreu -> iria dar exce�ao
		else if (element == ' ' || element == 'F')
			return false;
		else
		{
			System.out.println("Parametro Incorreto");
			throw new IllegalArgumentException();
		}
		
		if(	(x == next_x && (y == next_y + 1 || y == next_y - 1)) ||
			(y == next_y && (x == next_x + 1 || x == next_x - 1)) )
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
			System.out.println("\n\nWIN\n\n");
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
		if ( !GameRunning || dragon.isDead() || level == 1) //if level 1(pc move don't exist) or game ended... return
			return;
		else if (level == 2)
			sleepyDragon(maze, 50); //probabilidade de o Dragao estar a dormir. Podemos fazer: Rand.nextInt(2); 
		
		Random Rand = new Random();
		int move = Rand.nextInt(4);//5);    // tirei o 5 para ele andar sempre pelo menos uma vez
		
		boolean c;
		if (level == 2 && dragon.isSleepy())
			return; //if dragon is taking a nap... 
		
			/*******************DEBUG*******************************/
					System.out.println("----------->" + move);
			/*******************DEBUG*******************************/
					
					
		switch (move){
			// Cima
			case 0:
				//c = checkDragonPos(maze, "W");
				c = checkPos(maze, "W", dragon);
				if ( !c )
					pcMove(maze);
				break;
			//Baixo
			case 1:
				//c = checkDragonPos(maze, "S");
				c = checkPos(maze, "S", dragon);
				if ( !c )
					pcMove(maze);
				break;
			//Direita
			case 2:
				//c = checkDragonPos(maze, "D");
				c = checkPos(maze, "D", dragon);
				if ( !c )
					pcMove(maze);
				break;
			//Esquerda
			case 3:
				//c = checkDragonPos(maze, "A");
				c = checkPos(maze, "A", dragon);
				if ( !c )
					pcMove(maze);
				break;
			default: pcMove(maze);
			//case 4: don't move	
		}
	};
	
	private void sleepyDragon(Maze maze, int p) {
		Random Rand = new Random();
		int sleep = Rand.nextInt(100);
		
		if (sleep >= 100 || sleep < 0)
			sleepyDragon(maze, p);
		
		if (sleep <= p) //go back to sleep
		{
			dragon.setSleepy(true);
			dragon.setLetter('d');
			dragon.setPos(maze, dragon.getPosX(), dragon.getPosY());	
		}
		else if (sleep > p && dragon.isSleepy())//wake up sunshine
		{
			dragon.setSleepy(false);
			dragon.setLetter('D');
			dragon.setPos(maze, dragon.getPosX(), dragon.getPosY());
		}	
	}

	public boolean checkPos (Maze maze, String c, Element el) throws IllegalArgumentException {
		int newPosX = -1, newPosY = -1;
		
		// Get new Coordenates
		switch(c.toUpperCase().charAt(0)){
			case 'S': 	newPosY = el.getPosY() + 1; 
					  	newPosX = el.getPosX();
					  	break;
			case 'A': 	newPosX = el.getPosX() - 1;
					  	newPosY = el.getPosY();
					  	break;
			case 'W': 	newPosY = el.getPosY() - 1; 
					  	newPosX = el.getPosX();
					  	break;
			case 'D': 	newPosX = el.getPosX() + 1;
					  	newPosY = el.getPosY();
					  	break;
			default: 	System.out.println("**" + el.getLetter() + "--" + c.toUpperCase().charAt(0) + "**");
						throw new IllegalArgumentException();		
		}
		
	
		// DEBUG
		//*********************************************************
		System.out.println(el.getLetter() + "->input:" + c.toUpperCase().charAt(0));
		System.out.println("ANTIGAS POSICOES: (" + el.getPosX() + ";" + el.getPosY() + ")");
		System.out.println("NOVAS POSICOES: (" + newPosX + ";" + newPosY + ")");
		
		if ( newPosX > -1 )
			System.out.println("ESTAVA: \'" + maze.maze[newPosY][newPosX] + "\'");
		//*********************************************************
		
	//BOTH
		//check if it is a wall
		if( maze.maze[newPosY][newPosX] == 'X' )
		{
			newPosX = el.getPosX();
			newPosY = el.getPosY();
			return false;
		}
		// encounter - hero kills dragon
		else if ( near(newPosY, newPosX, el.getLetter()) && ( hero.isArmed() || maze.maze[newPosY][newPosX] == 'E' ))
		{
			//if hero goes to sword and at the same time encounters Dragon: hero picks sword and kills Dragon
			if (maze.maze[newPosY][newPosX] == 'E' )
			{
				hero.setArmed(true);
				sword.setVisible(false);
				hero.setLetter('A');
			}
			dragon.setDead(true);
			dragon.setLetter(' ');
			dragon.setPos(maze, dragon.getPosX(), dragon.getPosY());
		}
		// encounter - dragon kills hero -> GAME OVER
		else if ( near(newPosY, newPosX, el.getLetter()) && !hero.isArmed() && (dragon.getLetter() != 'd') )
		{
			hero.setDead(true);
			hero.setLetter(' ');
			hero.setPos(maze, hero.getPosX(), hero.getPosY());
			endGame("lose");
			return true;
		}
	//HERO
		if (el.getLetter() == 'H' || el.getLetter() == 'A')
		{
			//check if he picked sword
			if ( maze.maze[newPosY][newPosX] == 'E' )
			{
				hero.setArmed(true);
				sword.setVisible(false);
				hero.setLetter('A');
			}
			//avoids colision of coords when dragon is sleeping
			else if ( maze.maze[newPosY][newPosX] == 'd' )
			{
				newPosX = hero.getPosX();
				newPosY = hero.getPosY();
				return false;
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
				return true;
			}
		}
	//DRAGON
		else if ( el.getLetter() == 'D' || el.getLetter() == 'F' )
		{
			// Dragon runs away from sword
			if( el.getLetter() == 'F' )
			{
				el.setLetter('D');
				sword.setPos(maze, sword.getPosX(), sword.getPosY());

				el.setPos(maze, newPosX, newPosY);	

				sword.setPos(maze, sword.getPosX(), sword.getPosY());
			}
			//check if dragon and sword are in the same position
			else if ( maze.maze[newPosY][newPosX] == 'E' )
			{
				el.setLetter('F');
			}
			//dragon and exit in same position - don't update
			else if( maze.maze[newPosY][newPosX] == 'S' )
				pcMove(maze);			
		}
				
		el.setPos(maze, newPosX, newPosY);	
		return true;
	}
	
}