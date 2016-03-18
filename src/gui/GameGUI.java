package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import logic.Game;

public class GameGUI extends JFrame{
	
	private JButton btnExit;
	private JButton btnNewGame;
	private JButton btnW;
	private JButton btnS;
	private JButton btnA;
	private JButton btnD;

	private JLabel state;
	private JLabel lblMazeSize;
	private JLabel lblNoDragons;
	private JLabel label;
	private JLabel lblDragonGame;

	private JTextField maze_size;
	private JTextField n_dragons;
	
	private JTextArea maze_area;
	
	private JComboBox<String> level;
	
	private Game myGame;
	
	private final ButtonGroup buttonGroup = new ButtonGroup();
	protected int round = 0;
	
	
	/**
	 * Create the application.
	 */
	public GameGUI() {
		initializeFrame();
		createConfiguration();
		createButtons();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeFrame() {
		setTitle("Dragon Game");
		setBounds(100, 100, 620, 448);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);			
	}
		
	/**
	 * Create Buttons: start, exit, direction.
	 */
	private void createButtons() {
	// Button: Exit
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(494, 375, 83, 23);
		getContentPane().add(btnExit);	
		
	// Button: UP - W
		btnW = new JButton("W");
		btnW.setEnabled(false);
		btnW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newMove("W");
			}
		});
		
		buttonGroup.add(btnW);
		btnW.setBounds(66, 193, 46, 29);
		getContentPane().add(btnW);

	// Button: DOWN - S
		btnS = new JButton("S");
		btnS.setEnabled(false);		
		btnS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newMove("S");
			}
		});		
		btnS.setBounds(66, 252, 43, 29);
		getContentPane().add(btnS);

	// Button: RIGHT - D	
		final JButton btnD = new JButton("D");
		btnD.setEnabled(false);
		btnD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newMove("D"); 
			}
		});
		btnD.setBounds(119, 226, 43, 29);
		getContentPane().add(btnD);

	// Button: LEFT - A
		final JButton btnA = new JButton("A");
		btnA.setEnabled(false);
		btnS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newMove("A");
			}
		});	
		btnA.setBounds(13, 226, 43, 29);
		getContentPane().add(btnA);
		
	// Button: New Game
		btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					//n_dragons.getText();
				}catch(NumberFormatException e){
					//do default maze;
				}
				readyToStart();
			}
		});
		
		btnNewGame.setBounds(136, 65, 99, 23);
		getContentPane().add(btnNewGame);
	}

	
	/**
	 * Create Configuration: number of Dragons, size of maze,...
	 */
	private void createConfiguration() {
		lblMazeSize = new JLabel("Maze Size");
		lblMazeSize.setBounds(23, 41, 50, 14);
		getContentPane().add(lblMazeSize);
		
		n_dragons = new JTextField();
		n_dragons.setText("1");
		n_dragons.setBounds(23, 129, 86, 20);
		lblMazeSize.setLabelFor(n_dragons);
		getContentPane().add(n_dragons);
		n_dragons.setColumns(10);
		
		lblNoDragons = new JLabel("Type of Dragons");
		lblNoDragons.setBounds(136, 104, 84, 14);
		getContentPane().add(lblNoDragons);
		
		maze_size = new JTextField();
		maze_size.setText("11");
		maze_size.setColumns(10);
		maze_size.setBounds(23, 66, 86, 20);
		getContentPane().add(maze_size);
		
		String[] typeOfDragons = { "1. Static", "2. Moving/Sleepy", "3. Always Moving"};
		level = new JComboBox(typeOfDragons);
		level.setBounds(137, 129, 98, 20);
		level.setSelectedIndex(0);
		getContentPane().add(level);
		
		label = new JLabel("No Dragons");
		label.setBounds(23, 104, 84, 14);
		getContentPane().add(label);
		
		maze_area = new JTextArea();
		maze_area.setFont(new Font("Courier New", Font.PLAIN, 13));
		maze_area.setEditable(false);
		maze_area.setBounds(245, 54, 324, 297);
		getContentPane().add(maze_area);

		state = new JLabel("You can generate a new Maze");
		state.setBounds(255, 362, 229, 14);
		getContentPane().add(state);
		
		lblDragonGame = new JLabel("Dragon Game");
		lblDragonGame.setHorizontalAlignment(SwingConstants.CENTER);
		lblDragonGame.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDragonGame.setBounds(0, 11, 604, 32);
		getContentPane().add(lblDragonGame);
	}

	private void readyToStart() {
		btnW.setEnabled(true);
		btnA.setEnabled(true);
		btnS.setEnabled(true);
		btnD.setEnabled(true);
		state.setText("You can Play");
		
		myGame = new Game(level.getSelectedIndex() + 1, maze_size.getText(), n_dragons.getText());
		myGame.setGameRunning(true);
		
		
		maze_area.setText( myGame.getMazeString() );

		/*while( myGame.isGameRunning() )
		{
		//0. Round Status Maze
			
		//1. receive command     ->      events
			
		//2. Check
		/*	try {
				if ( !myGame.checkPos(key.charAt(0), myGame.hero) )
					continue;
			} catch (IllegalArgumentException e) {
				continue;
			}*/
			
		//3. pc faz o seu move
			/*for(int i=0; i<myGame.dragons.size();i++)
				myGame.pcMove(myGame.dragons.get(i));

			round ++;
		}	*/
	}


	private void newMove(String key) {
	// 2. Check
		try {
			if ( myGame.checkPos(key.charAt(0), myGame.hero) )
			{
				for(int i=0; i<myGame.dragons.size();i++)
					myGame.pcMove(myGame.dragons.get(i));

				round ++;
			}
		} catch (IllegalArgumentException e) {
		}
		
		maze_area.setText( myGame.getMazeString() );

		/*while( myGame.isGameRunning() )
		{
		//0. Round Status Maze
			
		//1. receive command     ->      events
			
		//2. Check
		/*	try {
				if ( !myGame.checkPos(key.charAt(0), myGame.hero) )
					continue;
			} catch (IllegalArgumentException e) {
				continue;
			}*/
			
		//3. pc faz o seu move
			/*for(int i=0; i<myGame.dragons.size();i++)
				myGame.pcMove(myGame.dragons.get(i));

			round ++;
		}	*/
	}

	
}
