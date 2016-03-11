package logic;

import java.util.ArrayList;
import java.util.Random;

public class Game {
	public Hero hero = new Hero(); 			//pos (1,1)
	public ArrayList <Dragon> dragons = new ArrayList<Dragon> ();  	//pos (1,3)
	private Sword sword = new Sword(); 		//pos (1,8)
	private Exit exit = new Exit(); 		//pos (9,5)
	private boolean GameRunning = false;
	private int level = 1;
	public Maze maze = new Maze();
	private boolean Victory = false;
	private boolean Defeat = false;
	
	//Game com Maze enviado pelo utilizador -> TESTE
	public Game(Maze m,int lvl, Hero h, Dragon d, Sword s){
		level = lvl;
		maze = m;
		this.dragons.add(d);
		this.hero = h;
		this.sword = s;
		this.GameRunning = true;
	}
	
	//Game criado com maze aleatorio com tamanho especificado
	public Game(int lvl, int len, int n_Dragons){

		level = lvl;	
		for (int i=0; i<n_Dragons;i++)
			this.dragons.add(new Dragon());		

		
		try{
			maze.buildMaze(len, exit);
			sword.setRandomPos(maze);
			while ( true)
			{
				for(int i=0;i<n_Dragons;i++)
					dragons.get(i).setRandomPos(maze);
				
				hero.setRandomPos(maze);
				for(int i=0;i<n_Dragons;i++){
				if ( 	!hero.pos.adjacentTo(dragons.get(i).pos) && 
						maze.getElementPos(sword.getLetter()) != null &&
						maze.getElementPos(hero.getLetter()) != null &&
						maze.getElementPos(dragons.get(i).getLetter()) != null )
							break;
				}
			}

		}catch (NumberFormatException e){
			System.out.println("Invalid Argument! Creating default 10x10 maze...");
			maze.setDefaultMaze();
			for(int i=n_Dragons-1;i>0;i--)
				dragons.remove(i);

			dragons.get(0).setPos( maze, new Point(3,1) );
			sword.setPos( maze, new Point(8,1) );
			exit.setPos( maze, new Point(5,9) );
			hero.setPos( maze, new Point(1,1) );
		}
		
		if ( level == 1)
		{
			for(int i=0;i<n_Dragons;i++){
					dragons.get(i).setLetter('d');
					dragons.get(i).setPos(maze, dragons.get(i).pos);	
			}			
		}
	}

	private void endGame(String state){
		if (state.equals("lose"))
		{
			System.out.println("\n\nGAME OVER\n\n");
			this.setDefeat(true);
			//handle game over
		}
		else if (state.equals("win"))
		{
			System.out.println("\n\nWIN\n\n");
			this.setVictory(true);
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

	public void pcMove(Dragon dragon){
		
		if ( !GameRunning || dragon.isDead() || level == 1) //if level 1(pc move don't exist) or game ended... return
			return;
		else if (level == 2)
			sleepyDragon(maze, 50,dragon); //probabilidade de o Dragao estar a dormir. Podemos fazer: Rand.nextInt(2); 
		
		Random Rand = new Random();
		int move = Rand.nextInt(4);//5);    // tirei o 5 para ele andar sempre pelo menos uma vez
		
		if (level == 2 && dragon.isSleepy())
			return; //if dragon is taking a nap... 			
					
		switch (move){
			// Cima
			case 0:
				if ( !checkPos('W', dragon) )
					pcMove(dragon);
				break;
			//Baixo
			case 1:
				if ( !checkPos('S', dragon) )
					pcMove(dragon);
				break;
			//Direita
			case 2:
				if ( !checkPos('D', dragon) )
					pcMove(dragon);
				break;
			//Esquerda
			case 3:
				if ( !checkPos('A', dragon) )
					pcMove(dragon);
				break;
			default: pcMove(dragon);
			//case 4: don't move	
		}
	};
	
	public void sleepyDragon(Maze maze, int p, Dragon dragon) {
		Random Rand = new Random();
		int sleep = Rand.nextInt(100);
		
		if (sleep >= 100 || sleep < 0)
			sleepyDragon(maze, p,dragon);
		
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

	public boolean checkPos (char c, Element el) throws IllegalArgumentException {
		Point newPos = new Point(0, 0);
		
		try{
			newPos = el.newPosition(c);
		} catch (IllegalArgumentException e )
		{
			throw new IllegalArgumentException();
		}

		
	//BOTH
		//check if it is a wall
		if( maze.charAt(newPos) == 'X' )
			return false;
		
	//HERO
		if (el.getLetter() == 'H' || el.getLetter() == 'A')
		{
			//check if he picked sword
			if ( maze.charAt(newPos) == 'E' )
			{
				hero.setArmed(true);
				sword.setVisible(false);
				el.setLetter('A');
			}
			// encounter with dragon - hero wins
			else if ( dragon.pos.adjacentTo(newPos) && ( hero.isArmed() || maze.charAt(newPos) == 'E' ) )
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
			// encounter with dragon - hero loses -> GAME OVER
			else if ( dragon.pos.adjacentTo(newPos) && !hero.isArmed() && (dragon.getLetter() != 'd') )
			{
				hero.setDead(true);
				hero.setLetter(' ');
				hero.setPos(maze, hero.pos);
				endGame("lose");
				return true;
			}
			//avoids colision of coords when dragon is sleeping
			else if ( maze.charAt(newPos) == 'd' )
			{
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
				hero.setPos(maze, new Point(exit.pos.getX(), exit.pos.getY()) );
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
				sword.setPos(maze, new Point(sword.pos.getX(), sword.pos.getY()));

				el.setPos(maze, new Point(newPos.getX(), newPos.getY()) );	

				sword.setPos(maze, new Point(sword.pos.getX(), sword.pos.getY()));
			}
			//check if dragon and sword are in the same position
			else if ( maze.charAt(newPos) == 'E' )
			{
				el.setLetter('F');
			}
			//dragon and exit in same position - don't update
			else if( maze.charAt(newPos) == 'S' )
				pcMove();	
			// encounter with hero - hero wins
			else if ( hero.pos.adjacentTo(newPos) && hero.isArmed() )
			{
				dragon.setDead(true);
				dragon.setLetter(' ');
				dragon.setPos(maze, dragon.pos);
			}
			// encounter with hero - hero loses -> GAME OVER
			else if ( hero.pos.adjacentTo(newPos) && !hero.isArmed() )
			{
				hero.setDead(true);
				hero.setLetter(' ');
				hero.setPos(maze, hero.pos);
				endGame("lose");
				return true;
			}
		}
				
		el.setPos(maze, new Point(newPos.getX(), newPos.getY()));	
		return true;
	}

	public boolean isDefeat() {
		return Defeat;
	}

	public void setDefeat(boolean defeat) {
		Defeat = defeat;
	}

	public boolean isVictory() {
		return Victory;
	}

	public void setVictory(boolean victory) {
		Victory = victory;
	}
}