package logic;

import java.util.Random;

/**
 * Class who creates a new Element in the game
 * This class is "The Mother" of @link{Dragon} @link{Hero} @link{Exit} @link{Sword}
 * @author Luis
 * @author Teresa
 */
public class Element {
	
	private Point pos = new Point();
	private char letter;
	
	public char getLetter() {
		return letter;
	}

	/**
	 * Method to change the letter that identifies this {@link logic.Element}
	 * @return
	 */
	public void setLetter(char letter) {
		this.letter = letter;
	}

	/**
	 * Method to change the position of this element
	 * @return
	 */
	public void setPos(Maze maze, Point p){
		// (y,x) para atribuir
		
		if (!(this.pos.getX() == 0 && this.pos.getY() == 0 ))
			maze.maze[this.pos.getX()][this.pos.getY()] = ' ';
		this.pos.setX( p.getX() );
		this.pos.setY( p.getY() );
		maze.maze[this.pos.getX()][this.pos.getY()] = this.letter;
	}

	/**
	 * Method to randomly choose a movement.
	 * If the movement is invalid... It will invoke itself until some valid move
	 * @return
	 */
	public void setRandomPos(Maze maze)
	{
		Point p = new Point();
		Random rand = new Random(); 
		// Get 2 random numbers between 0 and ( size - 1 )
		p.setX( rand.nextInt(maze.getLen()) );
		p.setY( rand.nextInt(maze.getLen()) );
		//check if pos is blank -> atribuicao de chars na matriz ao contrario (y,x)
		if ( maze.charAt(p) != ' ' )
			this.setRandomPos(maze);
		else
			this.setPos(maze, p);
	}
	
	/**
	 * Method which will retrieve the Point of the chosen move in order to process if it's a valid move or not
	 * @return
	 */
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

	/**
	 * Method to get the current Position of this Element
	 * @return
	 */
	public Point getPos() {
		return pos;
	}
}
