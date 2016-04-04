package gui;

import javax.swing.JPanel;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

/**
 * Main Frame of the whole game
 * This frame is always visible and is the "Mother" of all Panels
 * @author Luis
 * @author Teresa
 */
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
	
	private JButton btnCreate;

	protected BuildPanel build;
	
	/**
	 * Builder for the Frame
	 * @throws IOException
	 */
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
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int exit = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to exit the Game?");
				if( exit == JOptionPane.YES_OPTION )
					System.exit(0);
			}
		});
		
		btnOptions = new JButton("Options");
		btnOptions.setBounds(0, 0, 215, 25);
		bottomPanel.add(btnOptions);
		btnOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optionPanel = new OptionsPanel(gamePanel);
				optionPanel.setLocationRelativeTo(null);
				optionPanel.setVisible(true);
			}
		});
		
		JButton btnHelp = new JButton("Help");
		btnHelp.setBounds(214, 0, 215, 25);
		bottomPanel.add(btnHelp);
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				helpPanel = new HelpPanel(gamePanel);
				helpPanel.setLocationRelativeTo(null);
				helpPanel.setVisible(true);
			}
		});
		
		topPanel = new JPanel();
		topPanel.setBounds(0, 0, 650, 25);
		getContentPane().add(topPanel);
		topPanel.setLayout(null);
		
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(btnCreate.getText().equals("Path Done") || btnCreate.getText().equals("Finalize Maze")){
						btnCreate.setText("Build New Maze");
					}
				
					gamePanel.start();
					build.setVisible(false);
					gamePanel.setVisible(true);
				} catch (NumberFormatException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewGame.setBounds(50, 0, 400, 25);
		topPanel.add(btnNewGame);
		
		btnCreate = new JButton("Build New Maze");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(btnCreate.getText().equals("Build New Maze")){
					build = new BuildPanel(true);				
					build.setVisible(true);
					build.setBounds(0, 25, 650, 650);
					getContentPane().add(build);
					build.setLayout(null);
					gamePanel.setVisible(false);
					btnCreate.setText("Path Done");
					build.draw_Maze();
				}
				else if(btnCreate.getText().equals("Path Done")){
					if(build.check_maze()){
						btnCreate.setText("Finalize Maze");
						build.setState("ELEMENTS");
					}
					
				}
				else{
					if(build.check_maze())
					{
						try {
							if(JOptionPane.showConfirmDialog(rootPane, "Do you want to start new game with this maze?",null,JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
							{	
								gamePanel.start(build.getN_drag(),build.get_Size(),build.getMatrix());
								build.setVisible(false);
								btnCreate.setText("Build New Maze");
								gamePanel.setVisible(true);
								gamePanel.doSomeMagic();
							}
							
						} catch (NumberFormatException | IOException e1) {
							e1.printStackTrace();
						}
						
					}
					
					
					
				}
			}
		});
		btnCreate.setBounds(450, 0, 200, 25);
		topPanel.add(btnCreate);
		
		JButton home = new JButton("");
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamePanel.myGame.setGameRunning(false);
				gamePanel.repaint();
				//gamePanel.repaint(false);
				build.setVisible(false);
				gamePanel.setVisible(true);
				
				btnCreate.setText("Build New Maze");
				/*build = new BuildPanel(false);				
				build.setVisible(true);
				build.setBounds(0, 25, 650, 650);
				getContentPane().add(build);
				build.setLayout(null);*/
			}
		});
		home.setIcon(new ImageIcon("imgs/homeSymb.png"));
		home.setBounds(0, 0, 50, 25);
		topPanel.add(home);
		
		gamePanel = new GameBoard(level, size, num_dragons);
		
		gamePanel.setBounds(0, 25, 650, 650);
		getContentPane().add(gamePanel);
		gamePanel.setLayout(null);		
		
		build = new BuildPanel(false);
		build.setVisible(false);
		
	}
}
