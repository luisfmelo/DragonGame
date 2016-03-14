package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.ButtonGroup;

public class Interface {

	private JFrame frame;
	private JTextField n_dragons;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface window = new Interface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Interface() {
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
		lblMazeSize.setBounds(23, 23, 50, 14);
		frame.getContentPane().add(lblMazeSize);
		
		JTextField maze_size = new JTextField();
		maze_size.setText("1");
		maze_size.setBounds(23, 114, 86, 20);
		lblMazeSize.setLabelFor(maze_size);
		frame.getContentPane().add(maze_size);
		maze_size.setColumns(10);
		
		JLabel lblNoDragons = new JLabel("Type of Dragons");
		lblNoDragons.setBounds(136, 88, 84, 14);
		frame.getContentPane().add(lblNoDragons);
		
		n_dragons = new JTextField();
		n_dragons.setText("11");
		n_dragons.setColumns(10);
		n_dragons.setBounds(23, 48, 86, 20);
		frame.getContentPane().add(n_dragons);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(137, 114, 83, 20);
		frame.getContentPane().add(comboBox);
		
		JLabel label = new JLabel("No Dragons");
		label.setBounds(23, 89, 84, 14);
		frame.getContentPane().add(label);
		
		JButton btn_Exit = new JButton("Exit");
		btn_Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btn_Exit.setBounds(494, 375, 83, 23);
		frame.getContentPane().add(btn_Exit);
		
		JTextArea maze_area = new JTextArea();
		maze_area.setFont(new Font("Courier New", Font.PLAIN, 13));
		maze_area.setEditable(false);
		maze_area.setBounds(245, 46, 324, 305);
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
				state.setText("Pode Jogar");
			}
		});
		
		btnNewGame.setBounds(137, 47, 83, 23);
		frame.getContentPane().add(btnNewGame);
		
	}
}
