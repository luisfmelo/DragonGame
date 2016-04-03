package logic;

/**
 * Class who creates a new Point
 * @author Luis
 * @author Teresa
 */
public class Point {
	private int x;
	private int y;

	/**
	 * Builder for the Point (x,y)
	 */
	public Point(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	
	/**
	 * Builder for the Point 
	 */
	public Point() {
	}

	/**
	 * Method to get the abscissa(x coord) of this point
	 */
	public int getX() {
		return x;
	}

	/**
	 * Method to set the abscissa(x coord) of this point
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Method to get the ordinate(y coord) of this point
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Method to set the ordinate(y coord) of this point
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Method to set the coordinates(x and y coord) of this point
	 */
	public void setCoords(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Method to get the coordinates(x and y coord) of this point
	 */
	public Point getCoords (){
		return new Point( this.getX(), this.getY() );
	}
	
	/**
	 * Method to check if two Points are adjacent
	 */
	public boolean adjacentTo(Point p) {
		return Math.abs(p.x - this.x) + Math.abs(p.y - this.y) == 1;
	}
	
	/**
	 * Method to print the coordenates(x and y coord) of this point
	 */
	@Override
	public String toString(){
		return (this.x + "," + this.y);
	}
	
	/**
	 * Method to check if two points have the same coordenates(x and y coord)
	 */
	 @Override
	public boolean equals(Object obj) {
	       if (!(obj instanceof Point))
	            return false;
	        if (obj == this)
	            return true;

	        Point p = (Point) obj;
	        if (this.x == p.getX() && this.y == p.getY())
	        	return true;
	        
	        return false;
	 }
}
