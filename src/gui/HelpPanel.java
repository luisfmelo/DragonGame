package gui;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class HelpPanel extends JFrame{
	
	
	
	public HelpPanel() {
		ArrayList<Integer> arr = GameBoard.readFromFile();
		
		setFont(new Font("Consolas", Font.PLAIN, 11));
		this.setSize(220, 350);
		getContentPane().setLayout(null);

		if ( arr.get(1) < MyInterface.MIN_SIZE)
			inSize.setText( Integer.toString(MyInterface.MIN_SIZE) );
		else if ( arr.get(1) > MyInterface.MAX_SIZE)
			inSize.setText( Integer.toString(MyInterface.MAX_SIZE) );
		else
			inSize.setText( Integer.toString(arr.get(1)) );
		
		if ( arr.get(0) < MyInterface.MIN_DRAGONS)
			inND.setText( Integer.toString(MyInterface.MIN_DRAGONS) );
		else if ( arr.get(0) > MyInterface.MAX_DRAGONS)
			inND.setText( Integer.toString(MyInterface.MAX_DRAGONS) );
		else
			inND.setText( Integer.toString(arr.get(0)) );
		
				
		ButtonGroup group = new ButtonGroup();

		if ( arr.get(2) == 1)
			lvl1.setSelected(true);
		else if ( arr.get(2) == 2)
			lvl2.setSelected(true);
		else if ( arr.get(2) == 3)
			lvl3.setSelected(true);
		else
			lvl1.setSelected(true);
		
		JLabel lblHelp = new JLabel("Help");
		lblHelp.setHorizontalAlignment(SwingConstants.CENTER);
		lblHelp.setFont(new Font("Consolas", Font.BOLD, 16));
		lblHelp.setBounds(10, 11, 184, 26);
		getContentPane().add(lblHelp);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int res = JOptionPane.showConfirmDialog (null, "Would You Like to Save this Configuration?","Warning",JOptionPane.YES_NO_OPTION);
				
				if(res == JOptionPane.YES_OPTION){
					PrintWriter writer;
					try {
						writer = new PrintWriter(".config", "UTF-8");
						String str = "";
					
						// CHECK LEVEL
						if ( lvl1.isSelected() )
							str += "1";
						else if ( lvl2.isSelected() )
							str += "2";
						else if ( lvl3.isSelected() )
							str += "3";
						else 
							str += "1";
											
					// CHECK SIZE
						if ( Integer.parseInt( inSize.getText() ) < MyInterface.MIN_SIZE )
							str += MyInterface.MIN_SIZE + "\n";
						else if ( Integer.parseInt( inSize.getText() ) > MyInterface.MAX_SIZE )
							str += MyInterface.MAX_SIZE + "\n";
						else 
						{
							int n = Integer.parseInt( inSize.getText() );
							if ( n % 2 == 0 )
								n++;
							str += n + "\n";
						}
						
					// CHECK NUMBER OF DRAGONS
						if ( Integer.parseInt( inND.getText() ) < MyInterface.MIN_DRAGONS )
							str += MyInterface.MIN_DRAGONS + "\n";
						else if ( Integer.parseInt( inND.getText() ) > MyInterface.MAX_DRAGONS )
							str += MyInterface.MAX_DRAGONS + "\n";
						else 
							str += inND.getText() + "\n";
						
						writer.println(str);
						writer.close();
						setVisible(false);
					} catch (FileNotFoundException | UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		btnOk.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnOk.setBounds(50, 275, 100, 25);
		getContentPane().add(btnOk);
		
		JTextArea txtrGetTheSword = new JTextArea();
		txtrGetTheSword.setFont(new Font("Monospaced", Font.PLAIN, 10));
		txtrGetTheSword.setText("Get the sword, kill all dragons\\nand escape the dungeon!");
		txtrGetTheSword.setBounds(10, 50, 119, 55);
		getContentPane().add(txtrGetTheSword);
		
	}
}
