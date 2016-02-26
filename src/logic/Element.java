package logic;

public class Element {
	private int posX = 1;
	private int posY = 1;
	private char letter;
	
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	
	public char getLetter() {
		return letter;
	}
	public void setLetter(char letter) {
		this.letter = letter;
	}
	
	public void setPos(Maze maze, int x, int y){
		//char c = maze.maze[this.posX][this.posY];
		maze.maze[this.posY][this.posX] = ' ';
		this.posX = x;
		this.posY = y;
		maze.maze[this.posY][this.posX] = this.letter;
	}

}
