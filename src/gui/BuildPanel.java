package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.Game;
import logic.Point;
import javax.swing.JSplitPane;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JLayeredPane;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;

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
	

	boolean[][] wasHere;
	boolean[][] correctPath; 
	
	private int size = 7;
	private int n_drag;
	private Point m_Point;
	private String state= "INIT";
	private Object[] possibleValues = {"DOOR", "PATH", "HERO", "DRAGON","SWORD","WALL" };
	private Object selectedValue= null;


	public BuildPanel() {
		
		
	}
	
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
	
	public void draw_Maze(){
		setState("DOOR");
	}
	
	private void newMaze() {
		setMatrix(new char[size][size]);
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				//if ( i == 0 || i == size - 1 || j == 0 || j == size - 1 )
					matrix[i][j] = 'X';
				//else
					//matrix[i][j] = ' ';
			}
		}
	}

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
	
	private Point get_Point(int x, int y){
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
	private void draw_element(String el){
		Point p;
		switch (el){
			case "DOOR":
			{	if( getMatrix()[m_Point.getY()][m_Point.getX()]=='X' && (m_Point.getX() == 0 || m_Point.getY()==0  || m_Point.getX() == size-1 ||  m_Point.getY() == size-1)){
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
					if(p!=null)
						matrix[p.getY()][p.getX()]=' ';
					matrix[m_Point.getY()][m_Point.getX()]='H';
					}
			}
			break;
			case "SWORD":
			{	
				if(m_Point.getX() == 0 || m_Point.getY()==0)
			        JOptionPane.showMessageDialog(null, "You can't put the SWORD on the Borders", "About", JOptionPane.INFORMATION_MESSAGE);
				else{
				p=getElement('E');
				if(p!=null)
					matrix[p.getY()][p.getX()]=' ';
				matrix[m_Point.getY()][m_Point.getX()]='E';
				}
			}
			break;
			case "DRAGON":
			{	
				if(m_Point.getX() == 0 || m_Point.getY()==0)
			        JOptionPane.showMessageDialog(null, "You can't put a DRAGON on the Borders", "About", JOptionPane.INFORMATION_MESSAGE);
				else{
					if(get_nDragons()>=getN_drag())
						if(getN_drag()==0)
							JOptionPane.showMessageDialog(null, 
					        		"You didn't choose to have any DRAGONS"
					        		, "About", JOptionPane.INFORMATION_MESSAGE);
						else	
							JOptionPane.showMessageDialog(null, 
				        		"You already have the desired nº of DRAGONS\n\nIf you want to add a Dragon on this square\nyou need to remove one of the others by\nreplacing its square for another element"
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

	@Override
	public void mouseClicked(MouseEvent e) {
	    m_Point=get_Point(e.getX(),e.getY());
	    if(state=="PATH"){
	    	selectedValue = JOptionPane.showInputDialog(null,
		    "Choose the Element", "Input",
		    JOptionPane.INFORMATION_MESSAGE, null,
		    possibleValues, possibleValues[0]);
	    	draw_element(selectedValue.toString());
	    }
	    else if(state=="DOOR")
	    	draw_element(state);

	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public String getState() {
		return state;
	}

	public void setState(String s) {
		String old =this.state;
		this.state = s;
		stateChanged(old);
	}
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
			    JOptionPane.showMessageDialog(null, "Now you can click on a square and\nchoose which element you want to draw", "About", JOptionPane.INFORMATION_MESSAGE);
			}break;	
		
		}
		
	
    }
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
	
	public boolean check_maze(){
		
		if(get_nDragons()!=getN_drag()){
		    JOptionPane.showMessageDialog(null, "There are some DRAGONS missing", "About", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		else if(getElement('H')==null){
			JOptionPane.showMessageDialog(null, "The HERO is missing", "About", JOptionPane.INFORMATION_MESSAGE);
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
		else if (!this.solvable_maze()){
			JOptionPane.showMessageDialog(null, "There is no possible way from HERO to the DOOR. Change your PATH!", "About", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
	}
	
	private boolean solvable_maze(){
		 // The solution to the maze

		wasHere = new boolean[size][size];
		correctPath = new boolean[size][size];
		Point hero= getElement('H');
		Point door =getElement('S');
		

		    for (int row = 0; row < size; row++)  
		        // Sets boolean Arrays to default values
		        for (int col = 0; col < size; col++){
		            wasHere[row][col] = false;
		            correctPath[row][col] = false;
		        }
		    return recursiveSolve(hero.getX(), hero.getY(),door);
		    // Will leave you with a boolean array (correctPath) 
		    // with the path indicated by true values.
		    // If b is false, there is no solution to the maze
		}
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

		public char[][] getMatrix() {
			return matrix;
		}

		public void setMatrix(char[][] matrix) {
			this.matrix = matrix;
		}

		public int getN_drag() {
			return n_drag;
		}

		public void setN_drag(int n_drag) {
			this.n_drag = n_drag;
		}
		

	
	

}

