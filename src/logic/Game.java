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
	private boolean allDragonsDead = false;
	
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
					if ( dragon.pos.adjacentTo(hero.pos) )
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
					if ( d.pos.adjacentTo(newPos))
					{
						d.setDead(true);
						d.setLetter(' ');
						d.setPos(maze, d.pos);
					}
				}
			}
			// encounter with dragon - hero loses -> GAME OVER
			if ( !h.isArmed() )
			{
				for (Dragon d : dragons) {
					if ( d.pos.adjacentTo(newPos) && (d.getLetter() != 'd') )
					{
						h.setDead(true);
						h.setLetter(' ');
						h.setPos(maze, h.pos);
						endGame("lose");
						maze.print();
						return true;
					}
				}
			}
			//avoids colision of coords when dragon is sleeping
			if ( maze.charAt(newPos) == 'd' )
			{ 
				h.setPos(maze, h.pos.getCoords());	
				return false;
			}
			if( maze.charAt(newPos) == 'S' )
			{
				allDragonsDead = true;
				for (Dragon d : dragons) {
					//check if dragon alive and hero wants to get out
					if ( !d.isDead() )
					{
						newPos.setX( hero.pos.getX() );
						newPos.setY( hero.pos.getY() );
						System.out.println("Some Dragon is alive!");
						allDragonsDead = false;
					}
				}					
				//check if dragon is dead and hero wants to get out -> WIN
				if( allDragonsDead )
				{
					hero.setPos(maze, new Point(exit.pos.getX(), exit.pos.getY()) );
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
				sword.setPos(maze, new Point(sword.pos.getX(), sword.pos.getY()));

				d.setPos(maze, new Point(newPos.getX(), newPos.getY()) );	

				sword.setPos(maze, new Point(sword.pos.getX(), sword.pos.getY()));
			}
			//check if dragon and sword are in the same position
			if ( maze.charAt(newPos) == 'E' )
			{
				d.setLetter('F');
			}
			//dragon and exit in same position - don't update
			if( maze.charAt(newPos) == 'S' )
			{
				newPos.setCoords(d.pos.getX(), d.pos.getY());
			}
			// encounter with hero - hero wins
			if ( hero.pos.adjacentTo(newPos) && hero.isArmed() )
			{
				d.setDead(true);
				d.setLetter(' ');
				d.setPos(maze, d.pos);
			}
			// encounter with hero - hero loses -> GAME OVER
			if ( hero.pos.adjacentTo(newPos) && !hero.isArmed() )
			{
				hero.setDead(true);
				hero.setLetter(' ');
				hero.setPos(maze, hero.pos);
				endGame("lose");
				return true;
			}
			if ( maze.charAt(newPos) == 'd' || maze.charAt(newPos) == 'D' )
				newPos.setCoords(d.pos.getX(), d.pos.getY());
				
			d.setPos(maze, new Point(newPos.getX(), newPos.getY()));	
		}
				
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