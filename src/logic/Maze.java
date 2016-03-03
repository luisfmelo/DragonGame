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
	
	/*public void setMaze(String type) throws IllegalArgumentException {
		if ( type.equals("default") )
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
		else if ( type.equals("test") )
			this.maze = new char[][] {
					{'X', 'X', 'X', 'X','X'},
					{'X', ' ', ' ', 'H','X'},
					{'X', ' ', 'X', ' ','X'},
					{'X', 'E', ' ', 'D','X'},
					{'X', 'X', 'X', 'X','X'}
				};
		else if ( type.equals("random") )
		{	
			try{
				this.maze = this.buildMaze(this.len);
			}catch (NumberFormatException e1){
				System.out.println("Invalid Argument! Creating default 10x10 maze...");
				this.setMaze("default");
			}catch(IllegalArgumentException e2) {
				this.len ++;
				System.out.println("Size is not odd! Creating " + this.len + "x" + this.len + " maze...");
				this.maze = this.buildMaze(this.len);
			}
		}
		else
			throw new IllegalArgumentException();		
		
	}
	*/
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
		
		boolean flag = false;
		int sizeVisitedCellArray = (size - 1) / 2;
		int guideCell_i, guideCell_j, pos = 0, dir = 0;
		char [][] visitedCells = new char[sizeVisitedCellArray][sizeVisitedCellArray];
		Stack<String> pathHistory = new Stack<String>();
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
		while ( !flag )
		{
			// Get Exit Orientation
			dir = rand.nextInt(4) + 1; //1: N; 2:O; 3:S; 4:E
			//Get pos (impar)
			pos = rand.nextInt(size - 2) + 1;
			if (pos % 2 == 1 && dir > 0 && dir <= 4)
				flag = true;
		}

		switch(dir){
		case 1: 	//Norte
			exit.setPos(this, pos, 0); 
			guideCell_j = pos;
			guideCell_i = 1;
			break;
		case 2: 	//Oeste
			exit.setPos(this, size - 1, pos); 
			guideCell_j = size - 2;
			guideCell_i = pos;
			break;
		case 3:  	//Sul
			exit.setPos(this, pos, size - 1); 
			guideCell_j = pos;
			guideCell_i = size - 2;
			break;
		case 4:  	//Este
			exit.setPos(this, 0, pos);
			guideCell_j = 1;
			guideCell_i = pos;
			break;
		default:
			System.out.println("Ocorreu um erro...");
			throw new NumberFormatException();							//VER ISTO
		}
		//this.maze[guideCell_i][guideCell_j] = '+';

	// 3. Create Visited Cells
		for (int i = 0; i < sizeVisitedCellArray; i++)
			for (int j = 0; j < sizeVisitedCellArray; j++)
			{
				if (  	i * 2 + 1 == guideCell_i && 
						j * 2 + 1  == guideCell_j)
					visitedCells[i][j] = '+';
				else 
					visitedCells[i][j] = '.';
			}
	
	// 4. Add Path History (of Guide Cell) to Stack
		guideCell_i = (guideCell_i-1)/2;
		guideCell_j = (guideCell_j-1)/2;
		pathHistory.push(guideCell_i + "," + guideCell_j);
	
	// 5. Algorithm
		while(!pathHistory.empty())
		{
			if ( ( guideCell_i + 1 == sizeVisitedCellArray || visitedCells[guideCell_i + 1][guideCell_j] == '+') &&
				 ( guideCell_i == 0  || visitedCells[guideCell_i - 1][guideCell_j] == '+') &&
				 ( guideCell_j + 1 == sizeVisitedCellArray || visitedCells[guideCell_i][guideCell_j + 1] == '+') &&
				 ( guideCell_j == 0 || visitedCells[guideCell_i][guideCell_j - 1] == '+') ) //back trace stack
				{
					// pop
					pathHistory.pop();
					if (pathHistory.empty())
						break;
					//update previous coords
					String s = pathHistory.peek();
					guideCell_i = Integer.parseInt(s.split(",")[0]);
					guideCell_j = Integer.parseInt(s.split(",")[1]);
				}
			
			dir = rand.nextInt(4) + 1; // 1: N; 2:O; 3:S; 4:E
			switch(dir)
			{
			case 1: //N
				if( guideCell_i - 1 >= sizeVisitedCellArray || 
					guideCell_j >= sizeVisitedCellArray || 
					guideCell_i - 1 < 0 || 
					guideCell_j < 0 ||
					visitedCells[guideCell_i - 1][guideCell_j] == '+') //out of bound
					continue;
				//open space
				this.maze[guideCell_i * 2][guideCell_j * 2 + 1] = ' ';
				
				guideCell_i = guideCell_i - 1;
				break;
			case 2: //O
				if( guideCell_i >= sizeVisitedCellArray || 
				guideCell_j + 1 >= sizeVisitedCellArray || 
				guideCell_i < 0 || 
				guideCell_j + 1 < 0 ||
				visitedCells[guideCell_i][guideCell_j + 1] == '+') //out of bound
					continue;
				//open space
				this.maze[guideCell_i * 2 + 1][( guideCell_j + 1 ) * 2] = ' ';
				
				guideCell_j = guideCell_j + 1;
				break;
			case 3: //S
				if( guideCell_i + 1 >= sizeVisitedCellArray || 
				guideCell_j >= sizeVisitedCellArray || 
				guideCell_i + 1 < 0 || 
				guideCell_j < 0 ||
				visitedCells[guideCell_i + 1][guideCell_j] == '+') //out of bound
					continue;
				//open space
				this.maze[( guideCell_i + 1 ) * 2][guideCell_j * 2 + 1] = ' ';
				
				guideCell_i = guideCell_i + 1;
				break;
				
			case 4: //E
				if( guideCell_i >= sizeVisitedCellArray || 
				guideCell_j - 1 >= sizeVisitedCellArray || 
				guideCell_i < 0 || 
				guideCell_j - 1 < 0 ||
				visitedCells[guideCell_i][guideCell_j - 1] == '+') //out of bound
					continue;
				//open space
				this.maze[guideCell_i * 2 + 1][guideCell_j * 2] = ' ';

				guideCell_j = guideCell_j - 1;
				break;
			default: continue; 
			}			
			
			
		//time to hit a new one
			//push to stack
			pathHistory.push(guideCell_i + "," + guideCell_j);
			//put + in that pos
			visitedCells[guideCell_i][guideCell_j] = '+';
		}

		return this.maze;
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
