package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import logic.Game;
import logic.Maze;

public class GameBoard extends JPanel{

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
	private Maze maze;
	protected Game myGame;

	public GameBoard(int level, int size, int num_dragons) {

		myGame = new Game(level, Integer.toString(size), Integer.toString(num_dragons));
		
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
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void start()
	{
		myGame.setGameRunning(true);
        repaint();
		addKeyListener(new MyKeyListener(myGame));
		requestFocus();
		
	}
	
	@Override 
	protected void paintComponent(Graphics g){
		int width = this.getWidth();
		int height = this.getHeight();
		int size = this.myGame.maze.getLen();
		
		if ( !myGame.isGameRunning() )
			g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), Color.WHITE, null);
		else
		{
			g.drawImage(wall, 0, 0, this.getWidth(), this.getHeight(), Color.WHITE, null);

			for ( int i = 0; i < myGame.maze.getLen(); i++ )
				for ( int j = 0; j < myGame.maze.getLen(); j++ )
				{
					if ( myGame.maze.maze[i][j] == 'X')
						g.drawImage(wall, i * width / size, j * height / size, width / size, height / size, Color.WHITE, null);
					else if ( myGame.maze.maze[i][j] == ' ')
						g.drawImage(path, i * width / size, j * height / size, width / size, height / size, Color.WHITE, null);
					else if ( myGame.maze.maze[i][j] == 'H' )
						g.drawImage(hero_unarmed, i * width / size, j * height / size, width / size, height / size, Color.WHITE, null);
					else if ( myGame.maze.maze[i][j] == 'A' )
						g.drawImage(hero_armed, i * width / size, j * height / size, width / size, height / size, Color.WHITE, null);
					else if ( myGame.maze.maze[i][j] == 'D' )
						g.drawImage(dragon, i * width / size, j * height / size, width / size, height / size, Color.WHITE, null);
					else if ( myGame.maze.maze[i][j] == 'd' )
						g.drawImage(sleepy_dragon, i * width / size, j * height / size, width / size, height / size, Color.WHITE, null);
					else if ( myGame.maze.maze[i][j] == 's' )
						g.drawImage(open_door, i * width / size, j * height / size, width / size, height / size, Color.WHITE, null);
					else if ( myGame.maze.maze[i][j] == 'S' )
						g.drawImage(closed_door, i * width / size, j * height / size, width / size, height / size, Color.WHITE, null);
					else if ( myGame.maze.maze[i][j] == 'E' )
						g.drawImage(sword, i * width / size, j * height / size, width / size, height / size, Color.WHITE, null);
				}
		}

	}

}