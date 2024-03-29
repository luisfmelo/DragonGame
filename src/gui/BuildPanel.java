package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.Point;
import javax.swing.JButton;
import javax.swing.JSplitPane;

/**
 * Panel contained in the frame {@link gui.MyFrame}
 * In this panel, the user can build a new maze according to chosen size and number of dragons
 * @author Luis
 * @author Teresa
 */
public class BuildPanel extends JPanel implements MouseListener{
	
	private BufferedImage backgroundImage;
	private BufferedImage wall;
	private BufferedImage path;
	private BufferedImage hero_unarmed;
	private BufferedImage hero_armed;
	private BufferedImage dragon;
	private BufferedImage sleepy_dragon;
	private BufferedImage sword;
	private BufferedImage open_door;
	private BufferedImage closed_door;
	private BufferedImage dragon_sword;
	
	private char[][] matrix;
	private char[][] p1 =new char[][] {
		{'X',' '},{' ', 'X'}
	};
	private char[][] p2 =new char[][] {
		{' ','X'},{'X',' '}
	};
	
	private char[][] p3 =new char[][] {
		{' ',' '},{' ',' '}
	};
	private char[][] p4 =new char[][] {
		{'X','X','X'},{'X', 'X','X'},{'X', 'X','X'}
	};

	boolean[][] wasHere;
	boolean[][] correctPath; 
	
	private int size = 7;
	private int n_drag;
	private Point m_Point;
	private String state = "INIT";
	private Object[] possibleValues = {"DOOR", "PATH", "HERO","SWORD","WALL","DRAGON"};
	private Object selectedValue= null;


	/**
	 * Builder for the Build Maze panel - {@link gui.BuildPanel}.
	 */
	public BuildPanel() {
	}
	
	/**
	 * Builder for the Build Maze panel - {@link gui.BuildPanel}.
	 * @param t 
	 */
	
	public BuildPanel(boolean t) {		
		if ( !t )
			return;
		try{
			backgroundImage = ImageIO.read( new File("imgs/background.png"));	
			wall = ImageIO.read( new File("imgs/wall.png"));	
			path = ImageIO.read( new File("imgs/path.png"));	
			hero_unarmed = ImageIO.read( new File("imgs/hero_unarmed.png"));
			hero_armed = ImageIO.read( new File("imgs/hero_armed.png"));
			dragon = ImageIO.read( new File("imgs/dragon.png"));
			sleepy_dragon = ImageIO.read( new File("imgs/sleepy_dragon.png"));
			sword = ImageIO.read( new File("imgs/sword.png"));
			open_door = ImageIO.read( new File("imgs/open_door.png"));
			closed_door = ImageIO.read( new File("imgs/closed_door.png"));
			dragon_sword = ImageIO.read( new File("imgs/dragonSword.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	      
		
	 		
	 		
	 		
		this.addMouseListener(this);
	      
	       
		ArrayList<Integer> temp = new ArrayList<>();
		
		for ( int num = MyInterface.MIN_SIZE, j = 0; num <= MyInterface.MAX_SIZE; num += 2, j++)
		{
			temp.add(j, num);
		}

		repaint();
		Object[] options = temp.toArray();
		Object value = JOptionPane.showInputDialog(null, 
           "What should be the size of the matrix?", 
           "Build a new Matrix", 
            JOptionPane.QUESTION_MESSAGE, 
            null,
            options, 
            options[0]);
		size = (Integer)value;

		temp.clear();
		for ( int j = MyInterface.MIN_DRAGONS; j <= MyInterface.MAX_DRAGONS; j++)
		{
			temp.add(j, j);
		}
		options = temp.toArray();
		value = JOptionPane.showInputDialog(null, 
           "How many Dragons?", 
           "Build a new Matrix", 
            JOptionPane.QUESTION_MESSAGE, 
            null,
            options, 
            options[0]);
		setN_drag((Integer)value);
		

		newMaze();
		
		
	}
	
	/**
	 * Method invoked at the beginning of the maze's drawing. Set the initial state . 
	 */
	public void draw_Maze(){
		setState("DOOR");
	}
	
	/**
	 * Method invoked in the builder of the BuildPanel to initialize the Maze's matrix
	 */
	private void newMaze() {
		setMatrix(new char[size][size]);
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
					matrix[i][j] = 'X';
			}
		}
	}

	/**
	 * 
	 * @return size/lenght of the maze's matrix
	 */
	public int get_Size(){
		return this.size;
	}
	@Override 
	
	protected void paintComponent(Graphics g){
		
		int width = this.getWidth() - (this.getWidth() % size);
		int height = this.getHeight() - (this.getHeight() % size);
		
		g.drawImage(wall, 0, 0, this.getWidth(), this.getHeight(), Color.WHITE, null);
		for ( int i = 0; i < size; i++ )
		{
			for ( int j = 0; j < size; j++ )
			{
				if ( getMatrix()[j][i] == 'X')
					g.drawImage(wall, i * width / size, j * height / size, width / size , height / size , null);
				else if ( getMatrix()[j][i] == ' ')
					g.drawImage(path, i * width / size, j * height / size, width / size, height / size, null);
				else if ( getMatrix()[j][i] == 'H' )
					g.drawImage(hero_unarmed, i * width / size, j * height / size, width / size, height / size, null);
				else if ( getMatrix()[j][i] == 'A' )
					g.drawImage(hero_armed, i * width / size, j * height / size, width / size, height / size, null);
				else if ( getMatrix()[j][i] == 'D' )
					g.drawImage(dragon, i * width / size, j * height / size, width / size, height / size, null);
				else if ( getMatrix()[j][i] == 'd' )
					g.drawImage(sleepy_dragon, i * width / size, j * height / size, width / size, height / size, null);
				else if ( getMatrix()[j][i] == 's' )
					g.drawImage(open_door, i * width / size, j * height / size, width / size, height / size, null);
				else if ( getMatrix()[j][i] == 'S' )
					g.drawImage(closed_door, i * width / size, j * height / size, width / size, height / size, null);
				else if ( getMatrix()[j][i] == 'E' )
					g.drawImage(sword, i * width / size, j * height / size, width / size, height / size, null);
				else if ( getMatrix()[j][i] == 'F' )
					g.drawImage(dragon_sword, i * width / size, j * height / size, width / size, height / size, null);
			}
		}
		
		
	}
	/**
	 * Convert mouse clicked point in the frame to its corresponding Point (x,y) in the maze's matrix
	 * @param x
	 * @param y
	 * @return matrix Point if parameters are correct. Otherwise returns null
	 */
	private Point get_Point(int x, int y){
		if(x<0 ||y<0)
			return null;
		Point p=new Point();
		int i,j;
		int width = this.getWidth() - (this.getWidth() % size);
		int height = this.getHeight() - (this.getHeight() % size);
		
		i=x*size/width;
		j=y*size/height;
		p.setX(i);
		p.setY(j);
		return p;
	}
	
/**
 *  Method draws (if possible) or deletes the desired element on the last mouse_clicked point on its corresponding matrix point. 
 *  Repaints the panel in the ende
 * @param el
 */
	private void draw_element(String el){
		Point p;
		switch (el){
			case "DOOR":
			{	if( getMatrix()[m_Point.getY()][m_Point.getX()]=='X' && (m_Point.getX() == 0 || m_Point.getY()==0  || m_Point.getX() == size-1 ||  m_Point.getY() == size-1)){
					if((m_Point.getX()==0 && m_Point.getY()==0) ||
					   (m_Point.getX()==0 && m_Point.getY()==size-1) ||
					   (m_Point.getX()==size-1 && m_Point.getY()==size-1) ||
					   (m_Point.getX()==size-1 && m_Point.getY()==0)
					   ){
				        	JOptionPane.showMessageDialog(null, "The DOOR can't be placed in a CORNER", "About", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					p=getElement('S');
					matrix[m_Point.getY()][m_Point.getX()]='S';
					if(p!=null){
						matrix[p.getY()][p.getX()]='X';
					}
					else{
						repaint();
						setState("PATH");
					}
				}
				else{
			        JOptionPane.showMessageDialog(null, "The DOOR must be placed on a BORDER WALL", "About", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			break;
			case "PATH":
			{	
				if(m_Point.getX() == 0 || m_Point.getY()==0 || m_Point.getX() == size-1 ||  m_Point.getY() == size-1)
			        JOptionPane.showMessageDialog(null, "You can't put a PATH Square on the Borders", "About", JOptionPane.INFORMATION_MESSAGE);
				else
				{
					if(state=="PATH"){
						if(	matrix[m_Point.getY()][m_Point.getX()]==' ')
							matrix[m_Point.getY()][m_Point.getX()]='X';
						else
							matrix[m_Point.getY()][m_Point.getX()]=' ';
					}
					else
						matrix[m_Point.getY()][m_Point.getX()]=' ';
					
				}

			}
			break;
			case "WALL":
			{	
				matrix[m_Point.getY()][m_Point.getX()]='X';
			}
			break;
			case "HERO":
			{	
				if(m_Point.getX() == 0 || m_Point.getY()==0)
			        JOptionPane.showMessageDialog(null, "You can't put the HERO on the Borders", "About", JOptionPane.INFORMATION_MESSAGE);
				else{
					p=getElement('H');
					if(p!=null && !p.equals(m_Point)){
				        JOptionPane.showMessageDialog(null, "You already have a HERO!\nIf you want to change its position, click on him to delete it", "About", JOptionPane.INFORMATION_MESSAGE);						
					}
					else if(p!=null && p.equals(m_Point))
						matrix[m_Point.getY()][m_Point.getX()]=' ';
					else
						matrix[m_Point.getY()][m_Point.getX()]='H';

					}
				}
			break;
			case "SWORD":
			{	
				if(m_Point.getX() == 0 || m_Point.getY()==0)
			        JOptionPane.showMessageDialog(null, "You can't put a SWORD on the Borders", "About", JOptionPane.INFORMATION_MESSAGE);
				else{
					p=getElement('E');
					if(p!=null && !p.equals(m_Point)){
				        JOptionPane.showMessageDialog(null, "You already have a SWORD!\nIf you want to change its position, click on it to delete it", "About", JOptionPane.INFORMATION_MESSAGE);						
					}
					else if(p!=null && p.equals(m_Point))
						matrix[m_Point.getY()][m_Point.getX()]=' ';
					else
						matrix[m_Point.getY()][m_Point.getX()]='E';

				}
			}
			break;
			case "DRAGON":
			{	
				
				if(m_Point.getX() == 0 || m_Point.getY()==0)
			        JOptionPane.showMessageDialog(null, "You can't put a DRAGON on the Borders", "About", JOptionPane.INFORMATION_MESSAGE);
				else{
					if(getDragons_pos()!=null)
						if(getDragons_pos().contains(m_Point)){
							matrix[m_Point.getY()][m_Point.getX()]=' ';
							repaint();
							return;
						}
					if(get_nDragons()>=getN_drag())
						if(getN_drag()==0)
							JOptionPane.showMessageDialog(null, 
					        		"You didn't choose to have any DRAGONS"
					        		, "About", JOptionPane.INFORMATION_MESSAGE);
						else	
							JOptionPane.showMessageDialog(null, 
				        		"You already have the desired n� of DRAGONS\n\nIf you want to add a Dragon on this square\nyou need to remove one of the others by clicking it"
				        		, "About", JOptionPane.INFORMATION_MESSAGE);
					else
						matrix[m_Point.getY()][m_Point.getX()]='D';
				}
			}
			break;
		}
		repaint();
		
	}	public void print() {
		for(char[] line: getMatrix())
			System.out.println(line);		
	}

	/**
	 * Handles the mouseClick regarding the state of the drawing and the clicked square/element. 
	 * Calls the draw_element method of the chosen element (if possible)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
	    m_Point=get_Point(e.getX(),e.getY());
	    

    	if(state=="ELEMENTS"){
    		Point hero=getElement('H');
    		Point sword = getElement('E');
    		
    		if(hero!=null){
	    		if(getElement('H').equals(m_Point)){
	    	    	draw_element("HERO");
	    	    	return;
	    		}
    		} 
    		if(sword!=null){
	    		if(getElement('E').equals(m_Point)){
	    	    	draw_element("SWORD");
	    	    	return;
	    		}
    		} 
    		
        	
        	if(this.n_drag!=0){
        		if(getDragons_pos()!=null){
        			if(getDragons_pos().contains(m_Point)){
        				draw_element("DRAGON");
	    	    		return;
        			}
        		}
		    	selectedValue = JOptionPane.showInputDialog(null,
			    "Choose the Element", "Input",
			    JOptionPane.INFORMATION_MESSAGE, null,
			    possibleValues, possibleValues[0]);
		    	draw_element(selectedValue.toString());
		    }
	    	else{
	    		selectedValue = JOptionPane.showInputDialog(null,
	    			    "Choose the Element", "Input",
	    			    JOptionPane.INFORMATION_MESSAGE, null,
	    			    Arrays.copyOfRange(possibleValues, 0, 5), possibleValues[0]);
	    		    	draw_element(selectedValue.toString());;

	    	}
	    }
	    else if(state=="DOOR" || state=="PATH")
	    	draw_element(state);

	}

	/**
	 * Does nothing.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Does nothing.
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Does nothing.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Does nothing.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Getter of the BuildMaze's state
	 * @return current state of the building
	 */
	public String getState() {
		return state;
	}
/**
 * Setter of the BuildMaze's state
 * @param desired state to be written
 */
	public void setState(String s) {
		String old =this.state;
		this.state = s;
		stateChanged(old);
	}
	
   /**
    * Handles the desired actions when the state of the building is changed. Called by setState
    * @param old_state - previous BuildMaze's state
    */
	public void stateChanged(String old_state){
		switch(old_state){
			case "INIT":
			{
				final ImageIcon icon = new ImageIcon("imgs/closed_door.png");
			    JOptionPane.showMessageDialog(null, "First click on the black square where\nyou wish to place your door", "About", JOptionPane.INFORMATION_MESSAGE, icon);
			}break;
			case "DOOR":
			{
				//final ImageIcon icon = new ImageIcon("imgs/path.png");
			    JOptionPane.showMessageDialog(null, "Now you click on the squares to draw your PATH!\nIf you want to remove a Path square just click again\nWhen you're done click PATH DONE", "About", JOptionPane.INFORMATION_MESSAGE);
			}break;
			case "ELEMENTS":
			{
				//final ImageIcon icon = new ImageIcon("imgs/path.png");
			    JOptionPane.showMessageDialog(null, "Now you click on the squares and choose your ELEMENTS!\nWhen you're done click FINALIZE PATH", "About", JOptionPane.INFORMATION_MESSAGE);
			}break;	
		
		}
		
	
    }
	
/**
 *  Gets the matrix point of a given Element (Hero or Sword)
 * @param el
 * @return Matrix Point (x,y) of the Element or null if the element doesn't exist
 */
	private Point getElement(char el){
		if(getMatrix()==null)
			return null;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if ( getMatrix()[i][j] == el)
					return new Point(j,i);
			}
		}
		return null;
	}
	
	/**
	 * @return current number of dragons already present in the matrix
	 */
	private int get_nDragons(){
		if(getMatrix()==null)
			return -1;
		int cont=0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if ( getMatrix()[i][j] == 'D')
					cont++;
			}
		}
		return cont;
	}
	
	/**
	 * Checks if the current maze fulfill all necessary parameters for the game
	 * @return true if it's OK, false if there is something wrong
	 */
	public boolean check_maze(){

		if(state=="ELEMENTS"){
			if(getElement('H')==null){
				JOptionPane.showMessageDialog(null, "The HERO is missing", "About", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			else if(get_nDragons()!=getN_drag()){
			    JOptionPane.showMessageDialog(null, "There are some DRAGONS missing", "About", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			
			else if(getElement('E')==null){
				JOptionPane.showMessageDialog(null, "The SWORD is missing", "About", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			else if(getElement('S')==null){
				JOptionPane.showMessageDialog(null, "The DOOR is missing", "About", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			
			ArrayList <Point> dragons=getDragons_pos();
			if(dragons!=null && get_nDragons()!=0){
				for(int i=0; i<dragons.size();i++)
				{
					if(getElement('H').adjacentTo(dragons.get(i))){
					    JOptionPane.showMessageDialog(null, "The Dragon can't be next to the HERO", "About", JOptionPane.INFORMATION_MESSAGE);
						return false;
					}
				}
			}
		}
		if(check_squares(p1) || check_squares(p2)){
			JOptionPane.showMessageDialog(null, "No squares with PATH on a diagonal and WALL on the other", "About", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		else if (check_squares(p3)){
			JOptionPane.showMessageDialog(null, "No 2x2 squares with only PATH squares", "About", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		else if(check_squares(p4)){
			JOptionPane.showMessageDialog(null, "No 3x3 squares with only WALL's!", "About", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if ( getMatrix()[i][j] == ' ' || getMatrix()[i][j] == 'E' || getMatrix()[i][j] == 'S' || getMatrix()[i][j] == 'D')
					if(!solvable_maze(new Point(j,i)))
					{
						JOptionPane.showMessageDialog(null, "There is some Path way with no possible way to the DOOR. Change your PATH!", "About", JOptionPane.INFORMATION_MESSAGE);
						return false;
					}
			}
		}		
		return true;
	}
	/**
	 * Checks if the maze can reach to the end (DOOR) beginning in the given Point
	 * @param begin
	 * @return true if the maze is solvable and false otherwise
	 */
	private boolean solvable_maze(Point begin){
		 // The solution to the maze

		wasHere = new boolean[size][size];
		correctPath = new boolean[size][size];
		Point door =getElement('S');
		

		    for (int row = 0; row < size; row++)  
		        // Sets boolean Arrays to default values
		        for (int col = 0; col < size; col++){
		            wasHere[row][col] = false;
		            correctPath[row][col] = false;
		        }
		    return recursiveSolve(begin.getX(), begin.getY(),door);
		    // Will leave you with a boolean array (correctPath) 
		    // with the path indicated by true values.
		    // If b is false, there is no solution to the maze
		}
	
	/**
	 * Method invoked by solvable_maze to solve the maze recursively 
	 * @param x - parameter x of the begin point
	 * @param y - parameter y of the begin point
	 * @param door - final point
	 * @return
	 */
	public boolean recursiveSolve(int x, int y, Point door) {

		    if (x == door.getX() && y == door.getY()){    		
	    		return true;
		    	
		    }  // If you reached the end
		    if (getMatrix()[y][x] == 'X' || wasHere[x][y])
		    	{
		    		return false;  
		    	}
		    // If you are on a wall or already were here
		    wasHere[x][y] = true;
		    if (x != 0) // Checks if not on left edge
		        if (recursiveSolve(x-1, y,door)) { // Recalls method one to the left
		            correctPath[x][y] = true; // Sets that path value to true;
		            return true;
		        }
		    if (x != size - 1) // Checks if not on right edge
		        if (recursiveSolve(x+1, y,door)) { // Recalls method one to the right
		            correctPath[x][y] = true;
		            return true;
		        }
		    if (y != 0)  // Checks if not on top edge
		        if (recursiveSolve(x, y-1,door)) { // Recalls method one up
		            correctPath[x][y] = true;
		            return true;
		        }
		    if (y != size- 1) // Checks if not on bottom edge
		        if (recursiveSolve(x, y+1,door)) { // Recalls method one down
		            correctPath[x][y] = true;
		            return true;
		        }
		    return false;
		}

	/**
	 * Getter of the BuildMaze's matrix
	 * @return matrix
	 */
	public char[][] getMatrix() {
			return matrix;
	}

	/**
	 * Setter of the BuildMaze's matrix
	 * @param matrix
	 */
	public void setMatrix(char[][] matrix) {
			this.matrix = matrix;
	}

	/**
	 * Getter of BuildMaze's n_drag
	 * @return the desired number of dragons chosen by the user
	 */
	public int getN_drag() {
			return n_drag;
	}
	/**
	 * Setter of BuildMaze's n_drag 
	 * @param n_drag - the desired number of dragons chosen by the user
	 */
	public void setN_drag(int n_drag) {
			this.n_drag = n_drag;
	}
	/**
	 * 	
	 * @return ArrayList with the Point's location in the matrix of Dragons already drawn. Null if there weren't any Dragons drawn
	 */
	private ArrayList <Point> getDragons_pos(){
		ArrayList <Point> dragons = new ArrayList<Point>();
	
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
					if(matrix[i][j] == 'D')
						dragons.add(new Point(j,i));		
			}
		
		}
		if(dragons.isEmpty())
			return null;
		return dragons;

	}
	/**
	 * Checks if some pattern (matrix of characters) can be found in the BuildMaze's matrix
	 * @param pattern to check
	 * @return true if the pattern is in the matrix, false otherwise
	 */
	private boolean check_squares( char pattern [][]){
		
		for (int or = 0; or <= size - pattern.length; or++) {
			    outerCol:
			    for (int oc = 0; oc <= matrix[or].length - pattern[0].length; oc++) {
			        for (int ir = 0; ir < pattern.length; ir++)
			            for (int ic = 0; ic < pattern[ir].length; ic++){
			            	if(matrix[or + ir][oc + ic]=='H' || matrix[or + ir][oc + ic]=='D' || matrix[or + ir][oc + ic]=='E'){
			            		if (pattern[ir][ic]!=' ')
			            			continue outerCol;
			            	}
			            	else if( matrix[or + ir][oc + ic] != pattern[ir][ic])
			            			continue outerCol;
			            	
			            }
			        return true;
			    }
			}
		return false;
	}
	
}
