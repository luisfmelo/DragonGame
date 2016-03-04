package logic;

import java.util.Random;

public class Game {
	public Hero hero = new Hero(); 			//pos (1,1)
	private Dragon dragon = new Dragon(); 	//pos (1,3)
	private Sword sword = new Sword(); 		//pos (1,8)
	private Exit exit = new Exit(); 		//pos (9,5)
	private boolean GameRunning = false;
	private int level = 1;
	
	public Game(Maze maze, int lvl, int len){

		level = lvl;	

		try{
			maze.buildMaze(len, exit);
			sword.setRandomPos(maze);
			while ( true)
			{
				dragon.setRandomPos(maze);
				hero.setRandomPos(maze);
				if ( !near(hero.pos, dragon.getLetter()) )
					break;
			}

		}catch (NumberFormatException e){
			System.out.println("Invalid Argument! Creating default 10x10 maze...");
			maze.setDefaultMaze();
		}
		
		if ( level == 1)
		{
			dragon.setLetter('d');
			dragon.setPos(maze, dragon.pos);			
		}
	}

	//hero and Dragon near each other 
	public boolean near(Point p, char element)
	{
		Point next_point = new Point(0,0);
			
		//near Dragon
		if(element == 'H' || element == 'A'){
			next_point.setX( dragon.pos.getX() );
			next_point.setY( dragon.pos.getY() );
		}
		//near Hero
		else if (element == 'D' || element == 'd')
		{
			next_point.setX( hero.pos.getX() );
			next_point.setY( hero.pos.getY() );
		}
		//algum deles morreu -> iria dar excecao
		else if (element == ' ' || element == 'F')
			return false;
		else
		{
			System.out.println("Parametro Incorreto");
			throw new IllegalArgumentException();
		}
		
		if(		(p.getX() == next_point.getX() && (p.getY() == next_point.getY() + 1 || p.getY() == next_point.getY() - 1)) ||
				(p.getY() == next_point.getY() && (p.getX() == next_point.getX() + 1 || p.getX() == next_point.getX() - 1)) )
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
			dragon.setPos(maze, dragon.pos);	
		}
		else if (sleep > p && dragon.isSleepy())//wake up sunshine
		{
			dragon.setSleepy(false);
			dragon.setLetter('D');
			dragon.setPos(maze, dragon.pos);
		}	
	}

	public boolean checkPos (Maze maze, String c, Element el) throws IllegalArgumentException {
		Point newPos = new Point(0, 0);
		
		// Get new Coordenates
		switch(c.toUpperCase().charAt(0)){
			case 'W': 	newPos.setX( el.pos.getX() );
						newPos.setY( el.pos.getY() - 1 );
						break;
			case 'A': 	newPos.setX( el.pos.getX() - 1 );
						newPos.setY( el.pos.getY() );
					  	break;
	  		case 'S':	newPos.setX( el.pos.getX() );
	  					newPos.setY( el.pos.getY() + 1 );
	  					break;
			case 'D': 	newPos.setX( el.pos.getX() + 1 );
						newPos.setY( el.pos.getY() );
					  	break;
			default: 	throw new IllegalArgumentException();		
		}
		
	
		// DEBUG
		//*********************************************************
		System.out.println(el.getLetter() + "->input:" + c.toUpperCase().charAt(0));
		System.out.println("ANTIGAS POSICOES: (" + el.pos.getX() + ";" + el.pos.getY() + ")");
		System.out.println("NOVAS POSICOES: (" + newPos.getX() + ";" + newPos.getY() + ")");
		
		if ( newPos.getX()> -1 )
			System.out.println("ESTAVA: \'" + maze.charAt(newPos) + "\'");
		//*********************************************************
		
	//BOTH
		//check if it is a wall (y,x)
		if( maze.charAt(newPos) == 'X' )
		{
			newPos.setX( el.pos.getX() );
			newPos.setY( el.pos.getY() );
			return false;
		}
		// encounter - hero kills dragon
		else if ( near(newPos, el.getLetter()) && ( hero.isArmed() || maze.charAt(newPos) == 'E' ))
		{
			//if hero goes to sword and at the same time encounters Dragon: hero picks sword and kills Dragon
			if ( maze.charAt(newPos) == 'E' )
			{
				hero.setArmed(true);
				sword.setVisible(false);
				hero.setLetter('A');
			}
			dragon.setDead(true);
			dragon.setLetter(' ');
			dragon.setPos(maze, dragon.pos);
		}
		// encounter - dragon kills hero -> GAME OVER
		else if ( near(newPos, el.getLetter()) && !hero.isArmed() && (dragon.getLetter() != 'd') )
		{
			hero.setDead(true);
			hero.setLetter(' ');
			hero.setPos(maze, hero.pos);
			endGame("lose");
			return true;
		}
	//HERO
		if (el.getLetter() == 'H' || el.getLetter() == 'A')
		{
			//check if he picked sword
			if ( maze.charAt(newPos) == 'E' )
			{
				hero.setArmed(true);
				sword.setVisible(false);
				hero.setLetter('A');
			}
			//avoids colision of coords when dragon is sleeping
			else if ( maze.charAt(newPos) == 'd' )
			{
				newPos.setX( hero.pos.getX() );
				newPos.setY( hero.pos.getY() );
				return false;
			}
			//check if dragon alive and hero wants to get out
			else if( !dragon.isDead() && maze.charAt(newPos) == 'S' )
			{
				newPos.setX( hero.pos.getX() );
				newPos.setY( hero.pos.getY() );
				System.out.println("Dragon is still alive!");
			}
			//check if dragon is dead and hero wants to get out -> WIN
			else if( dragon.isDead() &&  maze.charAt(newPos) == 'S' )
			{
				hero.setPos(maze, exit.pos);
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
				sword.setPos(maze, sword.pos);

				el.setPos(maze, newPos);	

				sword.setPos(maze, sword.pos);
			}
			//check if dragon and sword are in the same position
			else if ( maze.charAt(newPos) == 'E' )
			{
				el.setLetter('F');
			}
			//dragon and exit in same position - don't update
			else if( maze.charAt(newPos) == 'S' )
				pcMove(maze);			
		}
				
		el.setPos(maze, newPos);	
		return true;
	}
	
}