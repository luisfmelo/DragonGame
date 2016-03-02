package logic;

public class Maze {
	private int len;
	public char[][] maze;
	
	/**
	 * Constructor
	 */
	public Maze(){
		this.maze = null;
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
	
	public void setMaze(String type) throws IllegalArgumentException {
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

	//ELIMINATE??
	public void getHeroPos(){
		/*		TODO (and for sword,dragon,...)		*/
	}

	public char[][] buildMaze(int size) throws IllegalArgumentException{
		if (size <= 0 )
			throw new NumberFormatException();	
		if (size % 2 == 0)
			throw new IllegalArgumentException();
		
		int sizeVisitedCellArray = (size - 1) / 2;
		
		char [][] visitedCellsDimension = new char[sizeVisitedCellArray][sizeVisitedCellArray];
		char [][] myMaze = new char[size][size];
				
		// 1. Fill Matrix with X
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
			{
				if ( i % 2 == 1 && j % 2 == 1)
					myMaze[i][j] = ' ';
				else 
					myMaze[i][j] = 'X';
			}
		
		// 2. Create Exit - S
		this.createExit("random");
		
		return myMaze;
	}
	
	private void createExit(String type) {
		Exit exit = new Exit();
		exit.setPos(this, 9, 5);
	}

	public void print() {
		for(char[] line: this.maze)
		{
			System.out.println(line);
		}
		
	};
	
}
