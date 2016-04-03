package logic;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class who creates a new Game
 * @author Luis
 * @author Teresa
 */
public class Game {
	protected Hero hero = new Hero(); 		
	protected ArrayList <Dragon> dragons = new ArrayList<Dragon> ();  	
	private Sword sword = new Sword(); 		
	protected Exit exit = new Exit(); 
	private boolean GameRunning = false;
	private int level = 1;
	protected Maze maze = new Maze();
	private boolean Victory = false;
	private boolean Defeat = false;
	private boolean allDragonsDead = false;
	private int DRAGONS_ALIVE;
	
	/**
	 * Game with Maze built by the User for test purposes
	 * @param m
	 * @param lvl
	 * @param h
	 * @param d
	 * @param s
	 */
	public Game(Maze m,int lvl, Hero h, Dragon d, Sword s){
		level = lvl;
		maze = m;
		this.dragons.add(d);
		this.hero = h;
		this.sword = s;
		this.GameRunning = true;
	}
	
	/**
	 * Constructor for Game with a random Maze with size user-specified
	 * @param lvl level of the game
	 * @param maze_size: size of the maze
	 * @param n_Drag: number of Dragons
	 */
	public Game(int lvl, String maze_size, String n_Drag){
		int len = Integer.parseInt(maze_size);
		int n_Dragons = Integer.parseInt(n_Drag);
		
		DRAGONS_ALIVE = n_Dragons;
		
		level = lvl;	
		for (int i = 0; i < n_Dragons; i++)
			this.dragons.add(new Dragon());		
		
		try{
			maze.buildMaze(len, exit);
			for (Dragon dragon : dragons) {
				dragon.setRandomPos(maze);
			}
			
			while ( true )
			{
				hero.setRandomPos(maze);
				boolean b = true;
				for (Dragon dragon : dragons) {
					if ( dragon.getPos().adjacentTo(hero.getPos()) )
						b = false;
				}
				
				if( b )
					break;
			}

			sword.setRandomPos(maze);

		}catch (NumberFormatException e){
			System.out.println("Invalid Argument! Creating default 10x10 maze...");
			maze.setDefaultMaze();
			dragons.clear();
			dragons.add(new Dragon());

			dragons.get(0).setPos( maze, new Point(3,1) );
			sword.setPos( maze, new Point(8,1) );
			exit.setPos( maze, new Point(5,9) );
			hero.setPos( maze, new Point(1,1) );
		}
		
		if ( level == 1)
		{
			for(int i=0;i<n_Dragons;i++){
					dragons.get(i).setLetter('d');
					dragons.get(i).setPos(maze, dragons.get(i).getPos());	
			}			
		}
		if ( DRAGONS_ALIVE == 0 )
		{
			exit.setLetter('s');
			exit.setPos(maze, exit.getPos());
			setAllDragonsDead(true);
		}
	}

	/**
	 * Handles end of the game
	 * @param state: lose or win depending of the situation
	 */
	public void endGame(String state){
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
		
		//setGameRunning(false);
	};
	
	/**
	 * Check if the game is still running
	 * @return
	 */
	public boolean isGameRunning() {
		return GameRunning;
	}

	/**
	 * Set end or start of the game
	 * @param gameRunning
	 */
	public void setGameRunning(boolean gameRunning) {
		GameRunning = gameRunning;
	};

	/**
	 * Method responsible for the movement of dragons
	 * @param dragon
	 */
	public void pcMove(Dragon dragon){
		
		if ( !GameRunning || dragon.isDead() || level == 1) //if level 1(pc move don't exist) or game ended... return
			return;
		else if (level == 2 && dragon.getLetter() != 'F')
			sleepyDragon(maze, 50,dragon); //probabilidade de o Dragao estar a dormir. Podemos fazer: Rand.nextInt(2); 
		
		Random Rand = new Random();
		int move = Rand.nextInt(5);    
		
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
			//case 4: don't move			
			case 4: break;
		}
	};
	
	/**
	 * Method responsible to change sleeping state of the dragon .
	 * @param maze
	 * @param p: probability of the dragon be asleep
	 * @param dragon
	 */
	public void sleepyDragon(Maze maze, int p, Dragon dragon) {
		Random Rand = new Random();
		int sleep = Rand.nextInt(100);
		
		if (sleep >= 100 || sleep < 0)
			sleepyDragon(maze, p,dragon);
		
		if (sleep <= p) //go back to sleep
		{
			dragon.setSleepy(true);
			dragon.setLetter('d');
			dragon.setPos(maze, dragon.getPos());	
		}
		else if (sleep > p && dragon.isSleepy())//wake up sunshine
		{
			dragon.setSleepy(false);
			dragon.setLetter('D');
			dragon.setPos(maze, dragon.getPos());
		}	
	}

	/**
	 * Check if the movement of an Element (Hero or Dragon) is possible and handles special states 
	 * @param c: direction of the movement
	 * @param el: element which will move
	 * @return
	 * @throws IllegalArgumentException
	 */
	public boolean checkPos (char c, Element el) throws IllegalArgumentException {
		if ( DRAGONS_ALIVE == 0 )
		{
			exit.setLetter('s');
			exit.setPos(maze, exit.getPos());
			setAllDragonsDead(true);
		}
		
		Point newPos = new Point();
		
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
		if (el instanceof Hero)
		{
			Hero h = (Hero) el;
			//check if he picked sword
			if ( maze.charAt(newPos) == 'E' )
			{
				h.setArmed(true);
				sword.setVisible(false);
				h.setLetter('A');
			}
			// encounter with dragon - hero wins
			if ( h.isArmed() || maze.charAt(newPos) == 'E' )
			{
				if ( maze.charAt(newPos) == 'E' )
				{
					h.setArmed(true);
					sword.setVisible(false);
					h.setLetter('A');
				}
				for (Dragon d : dragons) {
					if ( d.getPos().adjacentTo(newPos) && !d.isDead())
					{
						d.setDead(true);
						d.setLetter(' ');
						d.setPos(maze, d.getPos());
						DRAGONS_ALIVE --;
						if ( DRAGONS_ALIVE == 0 )
						{
							exit.setLetter('s');
							exit.setPos(maze, exit.getPos());
							setAllDragonsDead(true);
						}
					}
				}
				
				//check if all Dragons are dead
				allDragonsDead = true;
				
				for (Dragon d : dragons) {
					if ( !d.isDead() )
						allDragonsDead = false;
				}
			}
			// encounter with dragon - hero loses -> GAME OVER
			if ( !h.isArmed() )
			{
				for (Dragon d : dragons) {
					if ( d.getPos().adjacentTo(newPos) && (d.getLetter() != 'd') )
					{
						h.setDead(true);
						h.setLetter(' ');
						h.setPos(maze, h.getPos());
						endGame("lose");
						maze.print();
						return true;
					}
				}
			}
			//avoids colision of coords when dragon is sleeping
			if ( maze.charAt(newPos) == 'd' )
			{ 
				h.setPos(maze, h.getPos().getCoords());	
				return false;
			}
			if( maze.charAt(newPos) == 'S' || maze.charAt(newPos) == 's' )
			{
				for (Dragon d : dragons) {
					//check if dragon alive and hero wants to get out
					if ( !d.isDead() )
					{
						newPos.setX( hero.getPos().getX() );
						newPos.setY( hero.getPos().getY() );
						System.out.println("Some Dragon is alive!");
						setAllDragonsDead(false);
					}
				}					
				//check if dragon is dead and hero wants to get out -> WIN
				if( isAllDragonsDead() )
				{
					hero.setPos(maze, new Point(exit.getPos().getX(), exit.getPos().getY()) );
					endGame("win");
					return true;
				}
			}
			h.setPos(maze, new Point(newPos.getX(), newPos.getY()));	
		}
	//DRAGON
		else if ( el instanceof Dragon )
		{
			Dragon d = (Dragon) el;
			// Dragon runs away from sword
			if( d.getLetter() == 'F' )
			{
				d.setLetter('D');
				sword.setPos(maze, new Point(sword.getPos().getX(), sword.getPos().getY()));

				d.setPos(maze, new Point(newPos.getX(), newPos.getY()) );	

				sword.setPos(maze, new Point(sword.getPos().getX(), sword.getPos().getY()));
			}
			//check if dragon and sword are in the same position
			if ( maze.charAt(newPos) == 'E' )
			{
				d.setLetter('F');
			}
			//dragon and exit in same position - don't update
			if( maze.charAt(newPos) == 'S' || maze.charAt(newPos) == 's')
			{
				newPos.setCoords(d.getPos().getX(), d.getPos().getY());
			}
			// encounter with hero - hero wins
			if ( hero.getPos().adjacentTo(newPos) && hero.isArmed() )
			{
				d.setDead(true);
				d.setLetter(' ');
				d.setPos(maze, d.getPos());
			}
			// encounter with hero - hero loses -> GAME OVER
			if ( hero.getPos().adjacentTo(newPos) && !hero.isArmed() )
			{
				hero.setDead(true);
				hero.setLetter(' ');
				hero.setPos(maze, hero.getPos());
				endGame("lose");
				return true;
			}
			if ( maze.charAt(newPos) == 'd' || maze.charAt(newPos) == 'D' )
				newPos.setCoords(d.getPos().getX(), d.getPos().getY());

			d.setPos(maze, new Point(newPos.getX(), newPos.getY()));	
		}
			
		return true;
	}
	
	/**
	 * Get the String which represents the Maze state
	 * @return
	 */
	public String getMazeString(){
		String res = "";
		
		for(char[] line: this.maze.maze)
		{
			for (char c : line) {
				res += c;
			}
			res += "\n";
		}
		return res;
	}

	/**
	 * Verifies if the user has lost the game
	 * @return
	 */
	public boolean isDefeat() {
		return Defeat;
	}

	/**
	 * Set Defeat of the user
	 * @param defeat
	 */
	public void setDefeat(boolean defeat) {
		Defeat = defeat;
	}

	/**
	 * Verifies if the user has won the game
	 * @return
	 */
	public boolean isVictory() {
		return Victory;
	}

	/**
	 * Set Victory of the user
	 * @param defeat
	 */
	public void setVictory(boolean victory) {
		Victory = victory;
	}
	
	/**
	 * Check if all dragons are dead - If so, the user can run to the exit
	 * @return
	 */
	public boolean isAllDragonsDead() {
		return allDragonsDead;
	}

	/**
	 * Set Dead for all Dragons
	 * @param allDragonsDead
	 */
	public void setAllDragonsDead(boolean allDragonsDead) {
		this.allDragonsDead = allDragonsDead;
	}

	/**
	 * Check if the game is already Over: The user won or lost the game
	 * @return
	 */
	public boolean defeatOrLose() {
		return this.Defeat || this.Victory;
	}

	/**
	 * Get the maze which is currently being used in this game
	 * @return
	 */
	public Maze getMaze() {
		return this.maze;
	}

	/**
	 * Get the Array of all Dragons which are currently being used in this game
	 * @return
	 */
	public ArrayList <Dragon> getDragons() {
		return this.dragons;
	}

	/**
	 * Get the Hero which is currently being used in this game
	 * @return
	 */
	public Element getHero() {
		return this.hero;
	}
}