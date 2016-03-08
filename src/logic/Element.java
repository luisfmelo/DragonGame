package logic;

import java.util.Random;

public class Element {
	public Point pos = new Point(1,1);
	private char letter;
	
	public char getLetter() {
		return letter;
	}
	public void setLetter(char letter) {
		this.letter = letter;
	}
	
	public void setPos(Maze maze, Point p){
		// (y,x) para atribuir
		maze.maze[this.pos.getX()][this.pos.getY()] = ' ';
		this.pos.setX( p.getX() );
		this.pos.setY( p.getY() );
		maze.maze[this.pos.getX()][this.pos.getY()] = this.letter;
	}
	
	public void setRandomPos(Maze maze)
	{
		Point p = new Point(0, 0);
		Random rand = new Random(); 
		// Get 2 random numbers between 0 and ( size - 1 )
		p.setX( rand.nextInt(maze.getLen()) );
		p.setY( rand.nextInt(maze.getLen()) );
		//check if pos is blank -> atribuiçao de chars na matriz ao contrario (y,x)
		if (maze.maze[p.getY()][p.getX()] == ' ')
			this.setPos(maze, p);
		else
			this.setRandomPos(maze);
	}
	
	public Point newPosition(char c) throws IllegalArgumentException{
		switch( Character.toUpperCase(c) )
		{
			case 'W': return new Point(this.pos.getX() - 1, this.pos.getY());
			case 'D': return new Point(this.pos.getX(), this.pos.getY() + 1);
			case 'S': return new Point(this.pos.getX() + 1, this.pos.getY());
			case 'A': return new Point(this.pos.getX(), this.pos.getY() - 1);
			default: throw new IllegalArgumentException();
		}		
	}
	/*
	public Point move_left(Maze maze){
		Point p = new Point(0,0);
		p.setCoords(this.pos.getX(), this.pos.getY() - 1);

		this.setPos(maze, p);
		return p;
		
		
	}
	public Point move_right(Maze maze){
		Point p =new Point(0,0);
		p.setCoords(pos.getX(), pos.getY() + 1);

		if( maze.charAt(p) == 'X' )
		{
			return null;
		}

		this.setPos(maze, p);
		return p;
		
		
	}
	public Point move_up(Maze maze){
		Point p =new Point(0,0);
		p.setCoords(pos.getX() - 1, pos.getY());
		
		if( maze.charAt(p) == 'X' )
		{
			return null;
		}
	
		this.setPos(maze, p);
		return p;
	
	
	}
	//faltam coisas...
	public Point move_down(Maze maze){
		Point p = new Point(0,0);
		p.setCoords(pos.getX() + 1, pos.getY());
		
		if( maze.charAt(p) == 'X' )
		{
			return null;
		}
	
		this.setPos(maze, p);
		return p;	
	}
*/
}
