package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import logic.Dragon;
import logic.Game;
import logic.Maze;

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
	private Maze maze;
	protected Game myGame;

	public GameBoard(int level, int size, int num_dragons) throws IOException{

		ArrayList<Integer> numbers = new ArrayList<>();
		for (String line : Files.readAllLines(Paths.get("config"))) {
		    for (String part : line.split("\\s+")) {
		        Integer i = Integer.valueOf(part);
		        numbers.add(i);
		    }
		}
		
		myGame = new Game(numbers.get(0), numbers.get(1).toString(), numbers.get(2).toString());
		
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
        this.addKeyListener(this);
		//addKeyListener(new MyKeyListener(myGame));
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

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		//this shit is not good..... why??????
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

	@Override
	public void keyReleased(KeyEvent e) {}
	
	public void goGetIt(String key){
		switch (key) {
			case "W": key="A"; break;
			case "A": key="W"; break;
			case "S": key="D"; break;
			case "D": key="S"; break;
		}
		try {
			if ( myGame.checkPos(key.charAt(0), myGame.hero) )
			{
				for( int i = 0; i < myGame.dragons.size(); i++)
					myGame.pcMove(myGame.dragons.get(i));
								
				System.out.println(myGame.isAllDragonsDead() + "already?");
				
				boolean allDeath = true;
				
				System.out.println("start");
				for (Dragon d : myGame.dragons) {
					System.out.println("Dragon: " + d.isDead());
					if ( !d.isDead() )
						allDeath = false;
				}
				System.out.println("end");
				
				if ( allDeath )
				{
					myGame.exit.setLetter('s');
					myGame.exit.setPos(myGame.maze, myGame.exit.pos);					
				}
				repaint();
				if ( myGame.isDefeat() )
					System.out.println("Defeat");
				else if ( myGame.isVictory() )
					System.out.println("Victory");
			}
		} catch (IllegalArgumentException e2) {
		}
	}

}