package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Class who lets the User create a custom Maze
 * @author Luis
 * @author Teresa
 */
public class BuildPanel extends JPanel {
	
	private static final long serialVersionUID = 2362472189193020913L;
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
	private int size = 7;

	/**
	 * Constructor to build the panel
	 */
	public BuildPanel(boolean t) {
		if ( t == false )
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
		newMaze();
		
		final ImageIcon icon = new ImageIcon("imgs/closed_door.png");
        JOptionPane.showMessageDialog(null, "Blah blah blah", "About", JOptionPane.INFORMATION_MESSAGE, icon);
		
		
	}

	private void newMaze() {
		matrix = new char[size][size];
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if ( i == 0 || i == size - 1 || j == 0 || j == size - 1 )
					matrix[i][j] = 'X';
				else
					matrix[i][j] = ' ';
			}
		}
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
				if ( matrix[j][i] == 'X')
					g.drawImage(wall, i * width / size, j * height / size, width / size , height / size , null);
				else if ( matrix[j][i] == ' ')
					g.drawImage(path, i * width / size, j * height / size, width / size, height / size, null);
				else if ( matrix[j][i] == 'H' )
					g.drawImage(hero_unarmed, i * width / size, j * height / size, width / size, height / size, null);
				else if ( matrix[j][i] == 'A' )
					g.drawImage(hero_armed, i * width / size, j * height / size, width / size, height / size, null);
				else if ( matrix[j][i] == 'D' )
					g.drawImage(dragon, i * width / size, j * height / size, width / size, height / size, null);
				else if ( matrix[j][i] == 'd' )
					g.drawImage(sleepy_dragon, i * width / size, j * height / size, width / size, height / size, null);
				else if ( matrix[j][i] == 's' )
					g.drawImage(open_door, i * width / size, j * height / size, width / size, height / size, null);
				else if ( matrix[j][i] == 'S' )
					g.drawImage(closed_door, i * width / size, j * height / size, width / size, height / size, null);
				else if ( matrix[j][i] == 'E' )
					g.drawImage(sword, i * width / size, j * height / size, width / size, height / size, null);
				else if ( matrix[j][i] == 'F' )
					g.drawImage(dragon_sword, i * width / size, j * height / size, width / size, height / size, null);
			}
		}
	}
	
	
	
	
	
	
	
	public void print() {
		for(char[] line: matrix)
			System.out.println(line);		
	}
	
}

