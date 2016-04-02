package gui;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import java.awt.ComponentOrientation;
import java.awt.Component;
import java.awt.Point;
import javax.swing.UIManager;
import java.awt.SystemColor;

public class HelpPanel extends JFrame{
	
	
	public HelpPanel() {
		getContentPane().setName("");
		ArrayList<Integer> arr = GameBoard.readFromFile();
		
		setFont(new Font("Consolas", Font.PLAIN, 11));
		this.setSize(481, 468);
		getContentPane().setLayout(null);

		
		JLabel lblHelp = new JLabel("Help");
		lblHelp.setHorizontalAlignment(SwingConstants.CENTER);
		lblHelp.setFont(new Font("Consolas", Font.BOLD, 27));
		lblHelp.setBounds(140, 11, 184, 26);
		getContentPane().add(lblHelp);
		
		
		JTextArea txtrGetTheSword = new JTextArea();
		txtrGetTheSword.setWrapStyleWord(true);
		txtrGetTheSword.setLineWrap(true);
		txtrGetTheSword.setBackground(UIManager.getColor("Button.background"));
		txtrGetTheSword.setFont(new Font("Consolas", Font.PLAIN, 11));
		txtrGetTheSword.setText("Catch the sword, kill all dragons and escape the dungeon! \r\nBE CAREFULL! If you are in an adjacent position to the dragon you will kill it if you have the sword but if you don't have it you'll die!\r\n");
		txtrGetTheSword.setBounds(20, 48, 419, 43);
		getContentPane().add(txtrGetTheSword);
		
		JLabel lblKeys = new JLabel();
		lblKeys.setBounds(320, 302, 99, 64);
		getContentPane().add(lblKeys);
		
		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("imgs/arrow_keys.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}

		Image imgd= img.getScaledInstance(lblKeys.getWidth(), lblKeys.getHeight(), Image.SCALE_SMOOTH);
		
		ImageIcon keys = new ImageIcon(imgd);
		lblKeys.setIcon(keys);
		
		JLabel label = new JLabel("Commands");
		label.setFont(new Font("Tahoma", Font.BOLD, 15));
		label.setBounds(293, 199, 82, 14);
		getContentPane().add(label);
		
		JTextArea txtrUseKeyboardArrows = new JTextArea();
		txtrUseKeyboardArrows.setWrapStyleWord(true);
		txtrUseKeyboardArrows.setLineWrap(true);
		txtrUseKeyboardArrows.setFont(new Font("Consolas", Font.PLAIN, 11));
		txtrUseKeyboardArrows.setBackground(UIManager.getColor("Button.background"));
		txtrUseKeyboardArrows.setText("Use Keyboard arrows to move the hero");
		txtrUseKeyboardArrows.setBounds(293, 234, 162, 32);
		getContentPane().add(txtrUseKeyboardArrows);
		
		JTextArea txtrThereAre = new JTextArea();
		txtrThereAre.setWrapStyleWord(true);
		txtrThereAre.setLineWrap(true);
		txtrThereAre.setText("Level 1:\r\n The dragon/s are always sleeping. You have to kill them but they won't warm you.\r\n\r\nLevel 2:\r\n  The dragon/s can be sleeping or awake. If they are awake they can move randomly and kill you\r\n\r\nLevel 3:\r\n The dragon/s are always awake and randomly moving");
		txtrThereAre.setFont(new Font("Consolas", Font.PLAIN, 11));
		txtrThereAre.setBackground(SystemColor.menu);
		txtrThereAre.setBounds(20, 224, 244, 205);
		getContentPane().add(txtrThereAre);
		
		JLabel lblLevels = new JLabel("Levels");
		lblLevels.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblLevels.setBounds(10, 199, 82, 14);
		getContentPane().add(lblLevels);
		
		JLabel lblOptions = new JLabel("Options");
		lblOptions.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblOptions.setBounds(10, 99, 82, 14);
		getContentPane().add(lblOptions);
		
		JTextArea txtrToChangeThe = new JTextArea();
		txtrToChangeThe.setWrapStyleWord(true);
		txtrToChangeThe.setText("To change the type of your game click in Options. You can choose the length of your maze (minimum: 7, maximum: 17). \r\nFurthermore you may choose the n\u00BA of dragons (minimum: 0, maximum:3) and the level of dificulty.");
		txtrToChangeThe.setLineWrap(true);
		txtrToChangeThe.setFont(new Font("Consolas", Font.PLAIN, 11));
		txtrToChangeThe.setBackground(SystemColor.menu);
		txtrToChangeThe.setBounds(21, 124, 418, 64);
		getContentPane().add(txtrToChangeThe);
	}
}
