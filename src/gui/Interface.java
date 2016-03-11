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
		lblMazeSize.setBounds(25, 89, 50, 14);
		frame.getContentPane().add(lblMazeSize);
		
		JTextField maze_size = new JTextField();
		maze_size.setBounds(23, 114, 86, 20);
		lblMazeSize.setLabelFor(maze_size);
		frame.getContentPane().add(maze_size);
		maze_size.setColumns(10);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewGame.setBounds(137, 113, 83, 23);
		frame.getContentPane().add(btnNewGame);
		
		JLabel lblNoDragons = new JLabel("Type of Dragons");
		lblNoDragons.setBounds(137, 23, 84, 14);
		frame.getContentPane().add(lblNoDragons);
		
		n_dragons = new JTextField();
		n_dragons.setColumns(10);
		n_dragons.setBounds(23, 48, 86, 20);
		frame.getContentPane().add(n_dragons);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(137, 48, 68, 20);
		frame.getContentPane().add(comboBox);
		
		JLabel label = new JLabel("No Dragons");
		label.setBounds(25, 23, 84, 14);
		frame.getContentPane().add(label);
		
		JButton btn_Exit = new JButton("Exit");
		btn_Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btn_Exit.setBounds(494, 375, 83, 23);
		frame.getContentPane().add(btn_Exit);
		
		JTextArea maze_area = new JTextArea();
		maze_area.setFont(new Font("Courier New", Font.PLAIN, 13));
		maze_area.setEditable(false);
		maze_area.setBounds(282, 46, 287, 275);
		frame.getContentPane().add(maze_area);
	
		JButton btnW = new JButton("U");
		
		buttonGroup.add(btnW);
		btnW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnW.setBounds(66, 193, 43, 29);
		frame.getContentPane().add(btnW);
		
		JButton btnS = new JButton("D");
		btnS.setBounds(66, 252, 43, 29);
		frame.getContentPane().add(btnS);
		
		JButton btnD = new JButton("R");
		btnD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnD.setBounds(119, 226, 43, 29);
		frame.getContentPane().add(btnD);
		
		JButton btnA = new JButton("L");
		btnA.setBounds(13, 226, 43, 29);
		frame.getContentPane().add(btnA);
	}
}
