package logic;

import java.util.Random;

public class Game {
	public Hero hero = new Hero(); 		//pos (1,1)
	private Dragon dragon = new Dragon(); 	//pos (1,3)
	private Sword sword = new Sword(); 		//pos (1,8)
	private Exit exit = new Exit(); 		//pos (9,5)
	private boolean GameRunning = false;
	private int level;

	public Game(Maze maze, int level){
		this.level = level;

		dragon.setPos(maze, 4, 2);
		sword.setPos(maze, 1, 4);
		exit.setPos(maze, 9, 5);
		hero.setPos(maze, 1, 1);
		//maze.print();
	}

	//hero and Dragon near each other 
	private boolean near(int y, int x, char element)
	{
		int next_y, next_x;
		
		//near Dragon
		if(element == 'H' || element == 'A'){
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
		if ( !GameRunning || level == 1) //if level 1(pc move don't exist) or game ended... return
			return;
		else if (level == 2)
			sleepyDragon(50); //probabilidade de o Dragao estar a dormir. Podemos fazer: Rand.nextInt(2); 
		
		Random Rand = new Random();
		int move = Rand.nextInt(4);//5);    // tirei o 5 para ele andar sempre pelo menos uma vez
		
		boolean c;
		if (level == 2 && dragon.isSleepy())
			return;; //if dragon is taking a nap... 
		
		System.out.println("----------->" + move);
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
	
	private void sleepyDragon(int p) {
		Random Rand = new Random();
		int sleep = Rand.nextInt(100);
		
		if (sleep >= 100 || sleep < 0)
			sleepyDragon(p);
		
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
		

		/*if (c.toUpperCase().charAt(0) == 'S')
		{
			newPosY = el.getPosY() + 1; 
		  	newPosX = el.getPosX();
		}
		else if (c.toUpperCase().charAt(0) == 'A')
		{
			newPosX = el.getPosX() - 1;
		  	newPosY = el.getPosY();
		}
		else if (c.toUpperCase().charAt(0) == 'W')
		{
			newPosY = el.getPosY() - 1; 
		  	newPosX = el.getPosX();
		}
		else if (c.toUpperCase().charAt(0) == 'W')
		{
			newPosX = el.getPosX() + 1;
			newPosY = el.getPosY();
		}
		else
			System.out.println("**CHAPEU**" + el.getLetter() + "--" + c.toUpperCase().charAt(0) + "**");
		*/
		
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
		else if ( el.getLetter() == 'D')
		{
			boolean dragon_sword=false;

			//check if dragon and sword are in the same position
			if ( maze.maze[newPosY][newPosX] == 'E' )
			{
				sword.setLetter(' ');
				el.setLetter('F');
				dragon_sword = true;
			}
			//dragon and exit in same position - don't update
			else if( maze.maze[newPosY][newPosX] == 'S' )
			{
				pcMove(maze);
			}
			
			// Drgaon runs away from sword
			if( dragon_sword )
			{
				el.setLetter('D');
				sword.setLetter('E');	
				sword.setPos(maze, sword.getPosX(), sword.getPosY());
				dragon_sword = false;
			}
		}
				
		el.setPos(maze, newPosX, newPosY);	
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
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
*/
}