package gui;

import javax.swing.JPanel;

import logic.Game;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MyFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private int num_dragons = 1;
	private int size = 11; 
	private int level = 1;
	private GameBoard gamePanel;
	
	private JPanel bottomPanel;
	private JButton btnOptions;
	private HelpPanel helpPanel;
	private OptionsPanel optionPanel;
	private JButton btnExit;
	private JPanel topPanel;
	
	protected Game myGame = new Game();
	private JButton btnCreate;

	protected BuildPanel build;
	
	public MyFrame() throws IOException {
		setResizable(false);
		setSize(650, 725);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Dragon Game");
		
		getContentPane().setFocusTraversalPolicyProvider(true);
		getContentPane().setBounds(new Rectangle(0, 0, 650, 450));
		getContentPane().setSize(650,450);
		getContentPane().setLayout(null);
		
		bottomPanel = new JPanel();
		bottomPanel.setBounds(0, 675, 650, 25);
		getContentPane().add(bottomPanel);
		bottomPanel.setLayout(null);
		
		btnExit = new JButton("Exit");
		btnExit.setBounds(429, 0, 221, 25);
		bottomPanel.add(btnExit);
		
		btnOptions = new JButton("Options");
		btnOptions.setBounds(0, 0, 215, 25);
		bottomPanel.add(btnOptions);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.setBounds(214, 0, 215, 25);
		bottomPanel.add(btnHelp);
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				helpPanel = new HelpPanel();
				helpPanel.setLocationRelativeTo(null);
				helpPanel.setVisible(true);
			}
		});
		btnOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				optionPanel = new OptionsPanel();
				//optionPanel.setDefaultCloseOperation(optionPanel.setVisible(false);
				optionPanel.setLocationRelativeTo(null);
				optionPanel.setVisible(true);
			}
		});
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int exit = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to exit the Game?");
				if( exit == JOptionPane.YES_OPTION )
					System.exit(0);
			}
		});
		
		topPanel = new JPanel();
		topPanel.setBounds(0, 0, 650, 25);
		getContentPane().add(topPanel);
		topPanel.setLayout(null);
		
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//int ng = JOptionPane.showConfirmDialog(null, "Are you sure you want to start a new Game?");
				//if( ng == JOptionPane.YES_OPTION )
				//{
					try {
						gamePanel.start();
					} catch (NumberFormatException | IOException e1) {
						e1.printStackTrace();
					}
				//}
			}
		});
		btnNewGame.setBounds(0, 0, 450, 25);
		topPanel.add(btnNewGame);
		
		btnCreate = new JButton("Build New Maze");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				build = new BuildPanel();
				//optionPanel.setDefaultCloseOperation(optionPanel.setVisible(false);
				
				build.setVisible(true);
			}
		});
		btnCreate.setBounds(450, 0, 200, 25);
		topPanel.add(btnCreate);
		
		gamePanel = new GameBoard(level, size, num_dragons);
		gamePanel.setBounds(0, 25, 650, 650);
		getContentPane().add(gamePanel);
		gamePanel.setLayout(null);
		
	}
}
