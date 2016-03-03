package logic;

import java.util.Random;

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
	
	public void setRandomPos(Maze maze)
	{
		int x = 0, y = 0;
		Random rand = new Random(); 
		// Get 2 random numbers between 0 and ( size - 1 )
		x = rand.nextInt(maze.getLen());
		y = rand.nextInt(maze.getLen());
		//check if pos is blank
		if (maze.maze[x][y] == ' ')
			this.setPos(maze, y, x);
		else
			this.setRandomPos(maze);
		
		//this.setPos(maze, x, y);
	}

}
