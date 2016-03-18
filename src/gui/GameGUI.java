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

public class GameGUI {
	

	JFrame frame;
	private JTextField maze_size;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	protected int round = 0;
	/**
	 * Create the application.
	 */
	public GameGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 620, 448);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblMazeSize = new JLabel("Maze Size");
		lblMazeSize.setBounds(23, 41, 50, 14);
		frame.getContentPane().add(lblMazeSize);
		
		final JTextField n_dragons = new JTextField();
		n_dragons.setText("1");
		n_dragons.setBounds(23, 129, 86, 20);
		lblMazeSize.setLabelFor(n_dragons);
		frame.getContentPane().add(n_dragons);
		n_dragons.setColumns(10);
		
		JLabel lblNoDragons = new JLabel("Type of Dragons");
		lblNoDragons.setBounds(136, 104, 84, 14);
		frame.getContentPane().add(lblNoDragons);
		
		maze_size = new JTextField();
		maze_size.setText("11");
		maze_size.setColumns(10);
		maze_size.setBounds(23, 66, 86, 20);
		frame.getContentPane().add(maze_size);
		
		String[] typeOfDragons = { "1. Static", "2. Moving/Sleepy", "3. Always Moving"};
		final JComboBox level = new JComboBox(typeOfDragons);
		level.setBounds(137, 129, 98, 20);
		level.setSelectedIndex(0);
		frame.getContentPane().add(level);
		
		JLabel label = new JLabel("No Dragons");
		label.setBounds(23, 104, 84, 14);
		frame.getContentPane().add(label);
		
		JButton btn_Exit = new JButton("Exit");
		btn_Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btn_Exit.setBounds(494, 375, 83, 23);
		frame.getContentPane().add(btn_Exit);
		
		final JTextArea maze_area = new JTextArea();
		maze_area.setFont(new Font("Courier New", Font.PLAIN, 13));
		maze_area.setEditable(false);
		maze_area.setBounds(245, 54, 324, 297);
		frame.getContentPane().add(maze_area);
	
		final JButton btnW = new JButton("W");
		btnW.setEnabled(false);
		
		buttonGroup.add(btnW);
		btnW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnW.setBounds(66, 193, 46, 29);
		frame.getContentPane().add(btnW);
		
		final JButton btnS = new JButton("S");
		btnS.setEnabled(false);
		btnS.setBounds(66, 252, 43, 29);
		frame.getContentPane().add(btnS);
		
		final JButton btnD = new JButton("D");
		btnD.setEnabled(false);
		btnD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnD.setBounds(119, 226, 43, 29);
		frame.getContentPane().add(btnD);
		
		final JButton btnA = new JButton("A");
		btnA.setEnabled(false);
		btnA.setBounds(13, 226, 43, 29);
		frame.getContentPane().add(btnA);
		

		final JLabel state = new JLabel("You can generate a new Maze");
		state.setBounds(255, 362, 229, 14);
		frame.getContentPane().add(state);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					//n_dragons.getText();
				}catch(NumberFormatException e){
					//do default maze;
				}
				btnW.setEnabled(true);
				btnA.setEnabled(true);
				btnS.setEnabled(true);
				btnD.setEnabled(true);
				state.setText("You can Play");
				
				Game myGame = new Game(level.getSelectedIndex() + 1, maze_size.getText(), n_dragons.getText());
				myGame.setGameRunning(true);
				
				
				
				while( myGame.isGameRunning() )
				{
				//0. Round Status Maze
					maze_area.setText( myGame.getMazeString() );
					
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
*/
					round ++;
				}	
			}
		});
		
		btnNewGame.setBounds(136, 65, 99, 23);
		frame.getContentPane().add(btnNewGame);
		
		JLabel lblDragonGame = new JLabel("Dragon Game");
		lblDragonGame.setHorizontalAlignment(SwingConstants.CENTER);
		lblDragonGame.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDragonGame.setBounds(0, 11, 604, 32);
		frame.getContentPane().add(lblDragonGame);
		
	}
}
