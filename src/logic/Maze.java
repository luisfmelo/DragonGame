package logic;

public class Maze {
	private int len = 10;
	public char[][] maze = null;
	public char[][] getMaze(){
		return this.maze;
	};
	
	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}
	
	public void newMaze(){
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
	}
	
	//ELIMINATE??
	public void getHeroPos(){
		/*		TODO (and for sword,dragon,...)		*/
	}

	public void print() {
		for(char[] line: this.maze)
		{
			System.out.println(line);
		}
		
	};
	
}
