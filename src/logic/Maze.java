package logic;

import java.util.Random;
import java.util.Stack;


public class Maze {
	private int len;
	public char[][] maze;
	
	/**
	 * Constructor
	 */
	public Maze(){

	}
	
	public char[][] getMaze(){
		return this.maze;
	};
	
	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public void setDefaultMaze() {
		this.maze = new char[][]{
			{'X','X','X','X','X','X','X','X','X','X'},
			{'X','H',' ',' ',' ',' ',' ',' ',' ','X'},
			{'X',' ','X','X',' ','X',' ','X',' ','X'},
			{'X',' ','X','X','D','X',' ','X',' ','X'},
			{'X',' ','X','X',' ','X',' ','X',' ','X'},
			{'X',' ',' ',' ',' ',' ',' ','X',' ','S'},
			{'X',' ','X','X',' ','X',' ','X',' ','X'},
			{'X',' ','X','X',' ','X',' ','X',' ','X'},
			{'X','E','X','X',' ',' ',' ',' ',' ','X'},
			{'X','X','X','X','X','X','X','X','X','X'}
		};
	};
	
	public void setTestMaze() {
		this.len = 5;
		this.maze = new char[][] {
			{'X', 'X', 'X', 'X','X'},
			{'X', ' ', ' ', 'H','X'},
			{'X', ' ', 'X', ' ','X'},
			{'X', 'E', ' ', 'D','X'},
			{'X', 'X', 'X', 'X','X'}
		};
	};
	
	public void setRandomMaze(int size, Exit exit){
		this.len = size;
		try{
			this.buildMaze(this.len, exit);
		}catch (NumberFormatException e){
			System.out.println("Invalid Argument! Creating default 10x10 maze...");
			this.setDefaultMaze();
		}
	}

	public char[][] buildMaze(int size, Exit exit) throws IllegalArgumentException{
		if (size <= 0 )
			throw new NumberFormatException();	
		
		if ( this.len % 2 == 0)
		{
			this.len ++;
			System.out.println("Size is not odd! Creating " + this.len + "x" + this.len + " maze...");
			this.maze = new char[this.len][this.len];		
		}
		else
			this.maze = new char[this.len][this.len];	
		
		//boolean flag = false;
		int sizeVisitedCellArray = (size - 1) / 2;
		Point guideCell = new Point(0,0);
		Point temp = new Point(0,0);
		int pos = 0, dir = 0;
		char [][] visitedCells = new char[sizeVisitedCellArray][sizeVisitedCellArray];
		Stack<Point> pathHistory = new Stack<Point>();
		Random rand = new Random(); 

	// 1. Fill Matrix with X
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
			{
				if ( i % 2 == 1 && j % 2 == 1)
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
			pos = rand.nextInt(size - 2) + 1;
			if (pos % 2 == 1 && dir > 0 && dir <= 4)
				break;;
		}

		switch(dir){
		case 1: 	//Norte
			guideCell.setCoords(1, pos);
			temp.setCoords(0, pos);
			exit.setPos(this, temp); 
			break;
		case 2: 	//Oeste
			guideCell.setCoords(pos, size - 2);
			temp.setCoords(pos, size - 1);
			exit.setPos(this, temp); 
			break;
		case 3:  	//Sul
			guideCell.setCoords(size - 2, pos);
			temp.setCoords(size -1, pos);
			exit.setPos(this, temp); 
			break;
		case 4:  	//Este
			guideCell.setCoords(pos, 1);
			temp.setCoords(pos, 0);
			exit.setPos(this, temp); 
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
					temp = pathHistory.peek();
					guideCell = temp;
				}
			
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
					visitedCells[guideCell.getX()][guideCell.getY()] == '+') //out of bound
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
			pathHistory.push(guideCell);
			//put + in that pos
			visitedCells[guideCell.getX()][guideCell.getY()] = '+';
		}

		return this.maze;
	}

	public char charAt(Point p){
		return this.maze[p.getY()][p.getX()];
	}
	
	//ELIMINATE??
	public String getElementPos(char el){
		for (int i = 0; i < this.getLen(); i++)
			for (int j = 0; j < this.getLen(); j++)
				if ( this.maze[i][j] == el )
					return (i + "," + j);
		return null;
	}
	
	public void print() {
		for(char[] line: this.maze)
		{
			System.out.println(line);
		}
		
	};
	
}
