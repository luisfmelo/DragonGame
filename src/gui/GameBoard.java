package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.Game;
import logic.Point;

/**
 * Panel contained in the frame MyFrame
 * In this panel, if the user is in the middle of a game, it will display, graphically the game
 * if not, it will display the background image with the title of the Game
 * @author Luis
 * @author Teresa
 */
public class GameBoard extends JPanel implements KeyListener{

	private static final long serialVersionUID = 4398508935921388250L;
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
	protected Game myGame;
	
	private boolean playing = true;

	/**
	 * Builder for the game panel.
	 * @param level 
	 * @param size
	 * @param num_dragons
	 */
	public GameBoard(int level, int size, int num_dragons){

		myGame = new Game(1, 7, 1);
		
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
	
	/**
	 * Method invoked at the beginning of the game. Prepare everything that is important for the next actions.
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public void start() throws NumberFormatException, IOException
	{
		playing = true;
		ArrayList<Integer> numbers = readFromFile();
		myGame = null;
		myGame = new Game(numbers.get(2), numbers.get(0), numbers.get(1));
		myGame.setGameRunning(true);
			
        repaint();
        
        doSomeMagic();
	}
	
	/**
	 * Method invoked after user finished his maze. Prepare everything that is important for the next actions.
	 * @param n
	 * @param size
	 * @param maze
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public void start(int n, int size, char[][] maze) throws NumberFormatException, IOException
	{
		ArrayList<Integer> numbers = readFromFile();
		myGame = null;
		myGame = new Game(numbers.get(2), size, n, maze);
		myGame.setGameRunning(true);
			
        repaint();
        
	    doSomeMagic();
	}
	/**
	 * Method responsible for printing the game state on the user's screen.
	 */
	@Override 
	protected void paintComponent(Graphics g){
		int size = this.myGame.getMaze().getLen();
		int width = this.getWidth() - (this.getWidth() % size);
		int height = this.getHeight() - (this.getHeight() % size);
		
		if ( !myGame.isGameRunning() || !playing)
			g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), Color.WHITE, null);
		else
		{
			g.drawImage(wall, 0, 0, this.getWidth(), this.getHeight(), Color.WHITE, null);
			for ( int i = 0; i < myGame.getMaze().getLen(); i++ )
				for ( int j = 0; j < myGame.getMaze().getLen(); j++ )
				{
					if ( myGame.getMaze().charAt( new Point(j,i) ) == 'X')
						g.drawImage(wall, i * width / size, j * height / size, width / size , height / size , null);
					else if ( myGame.getMaze().charAt( new Point(j,i) ) == ' ')
						g.drawImage(path, i * width / size, j * height / size, width / size, height / size, null);
					else if ( myGame.getMaze().charAt( new Point(j,i) ) == 'H' )
						g.drawImage(hero_unarmed, i * width / size, j * height / size, width / size, height / size, null);
					else if ( myGame.getMaze().charAt( new Point(j,i) ) == 'A' )
						g.drawImage(hero_armed, i * width / size, j * height / size, width / size, height / size, null);
					else if ( myGame.getMaze().charAt( new Point(j,i) ) == 'D' )
						g.drawImage(dragon, i * width / size, j * height / size, width / size, height / size, null);
					else if ( myGame.getMaze().charAt( new Point(j,i) ) == 'd' )
						g.drawImage(sleepy_dragon, i * width / size, j * height / size, width / size, height / size, null);
					else if ( myGame.getMaze().charAt( new Point(j,i) ) == 's' )
						g.drawImage(open_door, i * width / size, j * height / size, width / size, height / size, null);
					else if ( myGame.getMaze().charAt( new Point(j,i) ) == 'S' )
						g.drawImage(closed_door, i * width / size, j * height / size, width / size, height / size, null);
					else if ( myGame.getMaze().charAt( new Point(j,i) ) == 'E' )
						g.drawImage(sword, i * width / size, j * height / size, width / size, height / size, null);
					else if ( myGame.getMaze().charAt( new Point(j,i) ) == 'F' )
						g.drawImage(dragon_sword, i * width / size, j * height / size, width / size, height / size, null);
				}
		}
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void keyTyped(KeyEvent e) {}

	/**
	 * Does nothing.
	 */
	@Override
	public void keyPressed(KeyEvent e) {}

	/**
	 * Handle the user movement... lets players use the arrow keys or WASD.
	 */
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

	/**
	 * Depending on the User's move, sends the information... updates the window and, if necessary, calls the victory(@see handleWin() ) or defeat(@see handleDefeat() ) functions.
	 */
	public void goGetIt(String key){
		try {
			if ( myGame.checkPos(key.charAt(0), myGame.getHero() ) )
			{
				for( int i = 0; i < myGame.getDragons().size(); i++)
					myGame.pcMove(myGame.getDragons().get(i));
				
				repaint();
				
				if ( myGame.isDefeat() )
					handleDefeat();
				else if ( myGame.isVictory() )
					handleWin();
					
			}
		} catch (IllegalArgumentException e2) {
		}
	}

	/**
	 * Lê o ficheiro de configurações.
	 * @return Array de inteiros com configurações lidas do ficheiro .config 
	 */
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
	
	/**
	 * Invoked when the user wins the game.
	 */
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
	
	/**
	 * Invoked when the user loses the game.
	 */
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
	
	/**
	 * Allows MyFrame to go back to Home Panel, ending the game and printing the main panel.
	 */
	public void repaint(boolean b) {
		playing = b;
	}
	
	/**
	 * Allows the game to continue if the user clicks one of the following menus: Options(@see OptionsPanel) or Help(@see HelpPanel), in the middle of a game.
	 */
	public void doSomeMagic(){
        this.removeKeyListener(this);
        this.addKeyListener(this);
        
		requestFocus();
	}
}