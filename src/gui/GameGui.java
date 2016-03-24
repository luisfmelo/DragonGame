package gui;

import javax.swing.JFrame;
import java.awt.GridLayout;

public class GameGui extends JFrame{
	
	public GameGui() {
		setTitle("Dragon Game");
		setBounds(100, 100, 650, 450);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);//new BorderLayout());			
	}

}
