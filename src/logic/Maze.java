package logic;

import java.util.Random;
import java.util.Stack;


public class Maze implements IMazeBuilder{
	private int len;
	protected char[][] maze; 
	
	/**
	 * Constructor
	 */
	public Maze(){
		this.setDefaultMaze();
	}	
	
	public Maze(char[][] m){
		this.maze = m;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public void setDefaultMaze() {
		this.maze = new char[][]{
			{'X','X','X','X','X','X','X','X','X','X'},
			{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'},
			{'X',' ','X','X',' ','X',' ','X',' ','X'},
			{'X',' ','X','X',' ','X',' ','X',' ','X'},
			{'X',' ','X','X',' ','X',' ','X',' ','X'},
			{'X',' ',' ',' ',' ',' ',' ','X',' ','X'},
			{'X',' ','X','X',' ','X',' ','X',' ','X'},
			{'X',' ','X','X',' ','X',' ','X',' ','X'},
			{'X',' ','X','X',' ',' ',' ',' ',' ','X'},
			{'X','X','X','X','X','X','X','X','X','X'}
		};
	};

	public void setMaze(char[][] m, int size) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				this.maze[i][j] = m[i][j];
			}
		}
	};


	public char[][] getMaze() {
		return this.maze;
	};
	
	public void setTestMaze() {
		this.len = 5;
		this.maze = new char[][] {
			{'X', 'X', 'X', 'X','X'},
			{'X', ' ', ' ', ' ','X'},
			{'X', ' ', 'X', ' ','X'},
			{'X', ' ', ' ', ' ','X'},
			{'X', 'X', 'X', 'X','X'}
		};
	};

	public char[][] buildMaze(int size, Exit exit) throws IllegalArgumentException, NumberFormatException{
		if (size <= 4 )
			throw new NumberFormatException();	
		
		if ( size % 2 == 0)
		{
			size++;
			System.out.println("Size is not odd! Creating " + size + "x" + size + " maze...");
			this.maze = new char[size][size];		
		}
		else
			this.maze = new char[size][size];	
		
		this.len = size;
		
		//boolean flag = false;
		int sizeVisitedCellArray = (this.len - 1) / 2;
		Point guideCell = new Point();
		Point temp = new Point();
		int pos = 0, dir = 0;
		char [][] visitedCells = new char[sizeVisitedCellArray][sizeVisitedCellArray];
		Stack<Point> pathHistory = new Stack<Point>();
		Random rand = new Random(); 

	// 1. Fill Matrix with X
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
			{
				if ( i == 0 && j == 0)
					this.maze[i][j] = 'X';
				else if ( i % 2 == 1 && j % 2 == 1)
					this.maze[i][j] = ' ';
				else 
					this.maze[i][j] = 'X';
			}

	// 2. Create Exit - S
		while ( true )
		{
			// Get Exit Orientation
			dir = rand.nextInt(4) + 1; //1: N; 2:O; 3:S; 4:E
			//Get pos (impar)
			pos = rand.nextInt(size - 1) + 1;
			if (pos % 2 == 1 && dir > 0 && dir <= 4)
				break;
		}

		switch(dir){
		case 1: 	//Norte
			exit.setPos(this, new Point(0, pos)); 
			guideCell.setCoords(1, pos);
			break;
		case 2: 	//Oeste
			exit.setPos(this, new Point(pos, size - 1)); 
			guideCell.setCoords(pos, size - 2);
			break;
		case 3:  	//Sul
			exit.setPos(this, new Point(size - 1, pos)); 
			guideCell.setCoords(size - 2, pos);
			break;
		case 4:  	//Este
			exit.setPos(this, new Point(pos, 0)); 
			guideCell.setCoords(pos, 1);
			break;
		default:
			System.out.println("Ocorreu um erro...");
			throw new NumberFormatException();							//VER ISTO
		}

	// 3. Create Visited Cells
		for (int i = 0; i < sizeVisitedCellArray; i++)
			for (int j = 0; j < sizeVisitedCellArray; j++)
			{
				if (  	i * 2 + 1 == guideCell.getX() && 
						j * 2 + 1  == guideCell.getY())
					visitedCells[i][j] = '+';
				else 
					visitedCells[i][j] = '.';
			}
		
	// 4. Add Path History (of Guide Cell) to Stack
		guideCell.setCoords( (guideCell.getX()-1)/2 , (guideCell.getY()-1)/2 );
		pathHistory.push(guideCell);
	
	// 5. Algorithm
		while(!pathHistory.empty())
		{
			if ( ( guideCell.getX() + 1 == sizeVisitedCellArray || visitedCells[guideCell.getX() + 1][guideCell.getY()] == '+') &&
				 ( guideCell.getX() == 0  || visitedCells[guideCell.getX() - 1][guideCell.getY()] == '+') &&
				 ( guideCell.getY() + 1 == sizeVisitedCellArray || visitedCells[guideCell.getX()][guideCell.getY() + 1] == '+') &&
				 ( guideCell.getY() == 0 || visitedCells[guideCell.getX()][guideCell.getY() - 1] == '+') ) //back trace stack
				{
					// pop
					pathHistory.pop();
					if (pathHistory.empty())
						break;
					//update previous coords
					guideCell = pathHistory.peek();
				}
			//this.print();
			dir = rand.nextInt(4) + 1; // 1: N; 2:O; 3:S; 4:E
			switch(dir)
			{
			case 1: //N
				if( guideCell.getX() - 1 >= sizeVisitedCellArray || 
					guideCell.getY() >= sizeVisitedCellArray || 
					guideCell.getX() - 1 < 0 || 
					guideCell.getY() < 0 ||
					visitedCells[guideCell.getX() - 1][guideCell.getY()] == '+') //out of bound
					continue;
				//open space<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
				this.maze[guideCell.getX() * 2][guideCell.getY() * 2 + 1] = ' ';
				
				guideCell.setX( guideCell.getX() - 1 );
				break;
			case 2: //O
				if( guideCell.getX() >= sizeVisitedCellArray || 
					guideCell.getY() + 1 >= sizeVisitedCellArray || 
					guideCell.getX() < 0 || 
					guideCell.getY() + 1 < 0 ||
					visitedCells[guideCell.getX()][guideCell.getY() + 1] == '+') //out of bound
						continue;
				//open space<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
				this.maze[guideCell.getX() * 2 + 1][( guideCell.getY() + 1 ) * 2] = ' ';
				
				guideCell.setY( guideCell.getY() + 1 );
				break;
			case 3: //S
				if( guideCell.getX() + 1 >= sizeVisitedCellArray || 
					guideCell.getY() >= sizeVisitedCellArray || 
					guideCell.getX() + 1 < 0 || 
					guideCell.getY() < 0 ||
					visitedCells[guideCell.getX() + 1][guideCell.getY()] == '+') //out of bound
						continue;
				//open space<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
				this.maze[( guideCell.getX() + 1 ) * 2][guideCell.getY() * 2 + 1] = ' ';
				
				guideCell.setX( guideCell.getX() + 1 );
				break;
				
			case 4: //E
				if( guideCell.getX() >= sizeVisitedCellArray || 
					guideCell.getY() - 1 >= sizeVisitedCellArray || 
					guideCell.getX() < 0 || 
					guideCell.getY() - 1 < 0 ||
					visitedCells[guideCell.getX()][guideCell.getY() - 1] == '+') //out of bound
						continue;
				//open space
				this.maze[guideCell.getX() * 2 + 1][guideCell.getY() * 2] = ' ';

				guideCell.setY( guideCell.getY() - 1 );
				break;
			default: continue; 
			}			
			
		//time to hit a new one
			//push to stack
			temp.setX(guideCell.getX());
			temp.setY(guideCell.getY());
			pathHistory.push(new Point(guideCell.getX(), guideCell.getY()));
			//put + in that pos
			visitedCells[guideCell.getX()][guideCell.getY()] = '+';
		}
		this.maze[0][0] ='X';
		return this.maze;
	}

	public char charAt(Point p){
		return this.maze[p.getX()][p.getY()];
	}
	
	public Point getElementPos(char el){
		for (int i = 0; i < this.getLen(); i++)
			for (int j = 0; j < this.getLen(); j++)
				if ( this.maze[i][j] == el )
					return (new Point(i,j));
		return null;
	}
	
	public char[][] print() {
		for(char[] line: this.maze)
			System.out.println(line);
		return this.maze;
		
	}

	@Override
	public char[][] buildMaze(int size) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Maze maze =  new Maze();
		maze.buildMaze(size, new Exit());
		Sword s = new Sword();
		Dragon dragon = new Dragon();
		Hero hero = new Hero();
		
		while ( true)
		{
			s.setRandomPos(maze);
			dragon.setRandomPos(maze);
			hero.setRandomPos(maze);
			if ( 	!hero.getPos().adjacentTo(dragon.getPos()) && 
					maze.getElementPos(s.getLetter()) != null &&
					maze.getElementPos(hero.getLetter()) != null &&
					maze.getElementPos(dragon.getLetter()) != null )
						break;
		}

		maze.maze[0][0] ='X';
		return maze.getMaze();
		
	}
	
}
