package logic;

import java.util.Random;

public class Element {
	
	private Point pos = new Point();
	private char letter;
	
	public char getLetter() {
		return letter;
	}
	
	public void setLetter(char letter) {
		this.letter = letter;
	}
	
	public void setPos(Maze maze, Point p){
		// (y,x) para atribuir
		//System.out.println(this.getClass().getSimpleName() + p.toString() + "-" + this.pos.toString());
		
		if (!(this.pos.getX() == 0 && this.pos.getY() == 0 ))
			maze.maze[this.pos.getX()][this.pos.getY()] = ' ';
		this.pos.setX( p.getX() );
		this.pos.setY( p.getY() );
		maze.maze[this.pos.getX()][this.pos.getY()] = this.letter;
	}
	
	public void setRandomPos(Maze maze)
	{
		Point p = new Point();
		Random rand = new Random(); 
		// Get 2 random numbers between 0 and ( size - 1 )
		p.setX( rand.nextInt(maze.getLen()) );
		p.setY( rand.nextInt(maze.getLen()) );
		//check if pos is blank -> atribuiçao de chars na matriz ao contrario (y,x)
		if ( maze.charAt(p) != ' ' )
			this.setRandomPos(maze);
		else
			this.setPos(maze, p);
	}
	
	public Point newPosition(char c) throws IllegalArgumentException{
		switch( Character.toUpperCase(c) )
		{
			case 'W': return new Point(this.getPos().getX() - 1, this.getPos().getY());
			case 'D': return new Point(this.getPos().getX(), this.getPos().getY() + 1);
			case 'S': return new Point(this.getPos().getX() + 1, this.getPos().getY());
			case 'A': return new Point(this.getPos().getX(), this.getPos().getY() - 1);
			default: throw new IllegalArgumentException();
		}		
	}

	
	public Point getPos() {
		return pos;
	}
}
