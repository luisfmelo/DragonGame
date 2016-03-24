package gui;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import logic.Point;

/**
 * LPOO 2015/16.
 * This examples demonstrates how to draw images, handle keyboard events, handle mouse events,
 * and implement simple animations with timers.
 */
@SuppressWarnings("serial")
public class GraphicsDemo extends JPanel 
implements MouseListener, MouseMotionListener, KeyListener {

	// Coordinates of the bounding rectangle for drawing the example image in the screen.
	private int x = 0, y = 0, width = 30, height=30;
	private int STEP =15;

	// in-memory representation of an example image to be displayed in the screen
	private BufferedImage hero;

	// current speed (displacement per timer tick)
	//private int dy = 0, dx = 0;
	
	// timer for controlling image animation
	//private Timer myTimer;
	
	// Constructor. Initiates listeners, 
	public GraphicsDemo() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
		
		try {
			hero =  ImageIO.read(new File("hero.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
       /* myTimer = new Timer(10, (arg) -> {imageAnimationStep();} );
		myTimer.start();*/
		requestFocus();
	}
	
	
	public void imageAnimationStep() {
		/*if (dx != 0 || dy != 0) {
			// updates object position based on current speeed (dx, dy)
			if (x + dx >= 0 && x + dx + width <= this.getWidth())
				x += dx; 
			else
				dx = 0;
			if (y + dy >= 0 && y + dy + height <= this.getHeight())
				y += dy; 
			else
				dy = 0;
			
			// tells the system that the panel must be repainted
			repaint();
		}
		repaint();*/	
	}
				
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // clears the backgorund ...		
		g.drawImage(hero, x, y, x + width - 1, y + height - 1, 0, 0, hero.getWidth(), hero.getHeight(), null);
	}

	// on mouse down, move the object to the mouse position and stop animation
	public void mousePressed(MouseEvent e) {
		//x = e.getX();
		//y = e.getY();
		//myTimer.stop();
		//repaint();
	}

	// on mouse drag, update the object position 
	public void mouseDragged(MouseEvent e) {
		//x = e.getX();
		//y = e.getY();
		//repaint();			
	}

	// on mouse up, restart animation 
	public void mouseReleased(MouseEvent e) {
		//myTimer.start();
		requestFocus();
	}

	// ignored events
	public void mouseMoved(MouseEvent arg0) { }
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	// Use the keyboard arrows to increase/decrease the object vertical and horizontal speed.
	// Use the '+' and '-' keys to increase/decrease the object size.
	// Use the Home key to move the object to the origin (0, 0).
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT:
			x--; 
			break;
			
		case KeyEvent.VK_RIGHT: 
			x++; 
			break;

		case KeyEvent.VK_UP: 
			y--; 
			break;

		case KeyEvent.VK_DOWN: 
			y++; 
			break;
		
		case KeyEvent.VK_PLUS:
			width ++;
			height++;
			break;

		case KeyEvent.VK_MINUS:
			width --;
			height--;
			break;
			
		case KeyEvent.VK_HOME:
			x = 0;
			y = 0;						
			break;
		}
		repaint();
		
	}

	// Ignored keyboard events
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

	public Point get_Pos(){
		return new Point(this.x,this.y);
		
	}
	public void set_Pos(Point pos){
		this.y=pos.getX();
		this.x=pos.getY();
		repaint();
	}
	// Main program.
	// Creates a frame containing the panel
	public static void main(String[] args) {
		JFrame f = new JFrame("Graphics Demo");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		f.setPreferredSize(new Dimension(500, 500));
		JPanel panel = new GraphicsDemo();
		f.getContentPane().add(panel);
        f.pack(); 
        f.setVisible(true);
        panel.requestFocus(); // to receive keyboard events       
	}
}