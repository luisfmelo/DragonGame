package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import logic.Dragon;
import logic.Game;
import logic.Maze;
import javax.swing.JSplitPane;
import javax.swing.JEditorPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class GameBoard extends JPanel implements KeyListener{

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
	private Maze maze;
	protected Game myGame;

	
	public GameBoard(){

		myGame = new Game(1, "7", "1");
		
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
	}

	public void start(int n, int size, char[][] maze) throws NumberFormatException, IOException
	{
		ArrayList<Integer> numbers = readFromFile();
		myGame = null;
		myGame = new Game(numbers.get(2).intValue(), size, n, maze);
		myGame.setGameRunning(true);
			
        repaint();
        
        this.removeKeyListener(this);
        this.addKeyListener(this);
        
		requestFocus();
	}
	public void start() throws NumberFormatException, IOException
	{
		ArrayList<Integer> numbers = readFromFile();
		myGame = null;
		myGame = new Game(numbers.get(2), numbers.get(0).toString(), numbers.get(1).toString());
		myGame.setGameRunning(true);
			
        repaint();
        
        this.removeKeyListener(this);
        this.addKeyListener(this);
        
		requestFocus();
	}
	
	@Override 
	protected void paintComponent(Graphics g){
		int size = this.myGame.maze.getLen();
		int width = this.getWidth() - (this.getWidth() % size);
		int height = this.getHeight() - (this.getHeight() % size);
		
		if ( !myGame.isGameRunning() )
			g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), Color.WHITE, null);
		else
		{
			g.drawImage(wall, 0, 0, this.getWidth(), this.getHeight(), Color.WHITE, null);
			for ( int i = 0; i < myGame.maze.getLen(); i++ )
				for ( int j = 0; j < myGame.maze.getLen(); j++ )
				{
					if ( myGame.maze.maze[j][i] == 'X')
						g.drawImage(wall, i * width / size, j * height / size, width / size , height / size , null);
					else if ( myGame.maze.maze[j][i] == ' ')
						g.drawImage(path, i * width / size, j * height / size, width / size, height / size, null);
					else if ( myGame.maze.maze[j][i] == 'H' )
						g.drawImage(hero_unarmed, i * width / size, j * height / size, width / size, height / size, null);
					else if ( myGame.maze.maze[j][i] == 'A' )
						g.drawImage(hero_armed, i * width / size, j * height / size, width / size, height / size, null);
					else if ( myGame.maze.maze[j][i] == 'D' )
						g.drawImage(dragon, i * width / size, j * height / size, width / size, height / size, null);
					else if ( myGame.maze.maze[j][i] == 'd' )
						g.drawImage(sleepy_dragon, i * width / size, j * height / size, width / size, height / size, null);
					else if ( myGame.maze.maze[j][i] == 's' )
						g.drawImage(open_door, i * width / size, j * height / size, width / size, height / size, null);
					else if ( myGame.maze.maze[j][i] == 'S' )
						g.drawImage(closed_door, i * width / size, j * height / size, width / size, height / size, null);
					else if ( myGame.maze.maze[j][i] == 'E' )
						g.drawImage(sword, i * width / size, j * height / size, width / size, height / size, null);
					else if ( myGame.maze.maze[j][i] == 'F' )
						g.drawImage(dragon_sword, i * width / size, j * height / size, width / size, height / size, null);
				}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_A: goGetIt("A"); break;
			case KeyEvent.VK_D: goGetIt("D"); break;
			case KeyEvent.VK_S: goGetIt("S"); break;
			case KeyEvent.VK_W: goGetIt("W"); break;
			case KeyEvent.VK_UP: goGetIt("W"); break;
			case KeyEvent.VK_DOWN: goGetIt("S"); break;
			case KeyEvent.VK_LEFT: goGetIt("A"); break;
			case KeyEvent.VK_RIGHT: goGetIt("D"); break;
		}
	}
	
	public void goGetIt(String key){
		try {
			if ( myGame.checkPos(key.charAt(0), myGame.hero) )
			{
				for( int i = 0; i < myGame.dragons.size(); i++)
					myGame.pcMove(myGame.dragons.get(i));
				
				repaint();
				
				if ( myGame.isDefeat() )
					handleDefeat();
				else if ( myGame.isVictory() )
					handleWin();
					
			}
		} catch (IllegalArgumentException e2) {
		}
	}

	protected static ArrayList<Integer> readFromFile(){
		
		ArrayList<Integer> numbers = new ArrayList<>();
	
		try {
			for (String line : Files.readAllLines(Paths.get(".config"))) {
			    for (String part : line.split(",")) {
			        Integer i = Integer.valueOf(part);
			        numbers.add(i);
			    }
			}
			
			// size
			if ( numbers.get(0) > MyInterface.MAX_SIZE )
				numbers.set(0, MyInterface.MAX_SIZE);
			else if ( numbers.get(0) < MyInterface.MIN_SIZE )
				numbers.set(0, MyInterface.MIN_LEVEL);				
							
			//number of Dragons
			if ( numbers.get(1) > MyInterface.MAX_DRAGONS )
				numbers.set(1, MyInterface.MAX_DRAGONS);
			else if ( numbers.get(1) < MyInterface.MIN_DRAGONS )
				numbers.set(1, MyInterface.MIN_DRAGONS);
			
		    // level
			if ( numbers.get(2) > MyInterface.MAX_LEVEL )
				numbers.set(2, MyInterface.MAX_LEVEL);
			else if ( numbers.get(2) < MyInterface.MIN_LEVEL )
				numbers.set(2, MyInterface.MIN_LEVEL);
		} catch (NumberFormatException | IOException e) {
			numbers.set(0, 11);
			numbers.set(1, 1);
			numbers.set(2, 1);
		}
		
		return numbers;
	}
	
	public void handleWin(){
        this.removeKeyListener(this);
        int res = JOptionPane.showConfirmDialog(null,
        		"You Win! Congratulations! Do you want to start a new Game?", "WIN", 
                JOptionPane.INFORMATION_MESSAGE,
                JOptionPane.PLAIN_MESSAGE);
        
        if ( res == JOptionPane.YES_OPTION )
        {
            myGame.setGameRunning(false);    
        	try {
    			this.start();
    		} catch (NumberFormatException | IOException e1) {
    			e1.printStackTrace();
    		}
        }        
        else if ( res == JOptionPane.NO_OPTION )
        {
        	try {
    			this.start();
    		} catch (NumberFormatException | IOException e1) {
    			e1.printStackTrace();
    		}

            myGame.setGameRunning(false);    
        } 
        else
            myGame.setGameRunning(false);    
	}
	
	public void handleDefeat() {  
        this.removeKeyListener(this);
        int res = JOptionPane.showConfirmDialog(null,
        		"You lose! Try Again!", "Defeat", 
                JOptionPane.INFORMATION_MESSAGE,
                JOptionPane.PLAIN_MESSAGE);
        
        if ( res == JOptionPane.YES_OPTION )
        {
            myGame.setGameRunning(false);    
        	try {
    			this.start();
    		} catch (NumberFormatException | IOException e1) {
    			e1.printStackTrace();
    		}
        }        
        else if ( res == JOptionPane.NO_OPTION )
        {
        	try {
    			this.start();
    		} catch (NumberFormatException | IOException e1) {
    			e1.printStackTrace();
    		}

            myGame.setGameRunning(false);    
        } 
        else
            myGame.setGameRunning(false);    
	}
}