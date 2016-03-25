package gui;

import javax.swing.JPanel;

import logic.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MyFrame extends JFrame{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int num_dragons = 1;
	private int size = 11; 
	private int level = 1;
	private GameBoard gamePanel;
	
	protected Game myGame;
	
	public MyFrame() throws IOException {
		setSize(650, 450);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Dragon Game");
		setResizable(false);
		
		getContentPane().setFocusTraversalPolicyProvider(true);
		getContentPane().setBounds(new Rectangle(0, 0, 650, 450));
		getContentPane().setSize(650,450);
		getContentPane().setLayout(null);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBounds(0, 400, 650, 25);
		getContentPane().add(bottomPanel);
		bottomPanel.setLayout(null);
		
		JButton btnOptions = new JButton("Options");
		btnOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame optionPanel = new JFrame();
				optionPanel.setSize(200, 300);
				optionPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				optionPanel.setTitle("Options");
				optionPanel.setResizable(false);
			}
		});
		btnOptions.setBounds(0, 0, 325, 25);
		bottomPanel.add(btnOptions);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int exit = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to exit the Game?");
				if( exit == JOptionPane.YES_OPTION )
					System.exit(0);
			}
		});
		btnExit.setBounds(325, 0, 325, 25);
		bottomPanel.add(btnExit);
		
		JPanel topPanel = new JPanel();
		topPanel.setBounds(0, 0, 650, 25);
		getContentPane().add(topPanel);
		topPanel.setLayout(null);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamePanel.start();
			}
		});
		btnNewGame.setBounds(0, 0, 650, 25);
		topPanel.add(btnNewGame);
		
		gamePanel = new GameBoard(level, size, num_dragons);
		gamePanel.setBounds(0, 25, 650, 375);
		getContentPane().add(gamePanel);
		gamePanel.setLayout(null);
		
	}
}
