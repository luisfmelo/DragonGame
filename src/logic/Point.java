package logic;

public class Point {
	private int x;
	private int y;

	public Point(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setCoords(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Point getCoords (Point p){
		return ( new Point(p.getX(), p.getY()) );
	}
	
	public boolean adjacentTo(Point p) {
		return Math.abs(p.x - this.x) + Math.abs(p.y - this.y) == 1;
	}
	
	public String toString(){
		return (this.x+","+this.y);
	}
	
}