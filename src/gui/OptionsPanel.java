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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OptionsPanel extends JFrame{
	private JTextField inSize;
	private JTextField inND;
	
	
	
	public OptionsPanel(GameBoard gamePanel) {
		ArrayList<Integer> arr = GameBoard.readFromFile();
		
		setFont(new Font("Consolas", Font.PLAIN, 11));
		this.setSize(220, 350);
		getContentPane().setLayout(null);
		
		inSize = new JTextField();
		inSize.setBounds(50, 65, 100, 20);
		getContentPane().add(inSize);
		inSize.setColumns(10);

		if ( arr.get(0) < MyInterface.MIN_SIZE)
			inSize.setText( Integer.toString(MyInterface.MIN_SIZE) );
		else if ( arr.get(0) > MyInterface.MAX_SIZE)
			inSize.setText( Integer.toString(MyInterface.MAX_SIZE) );
		else
			inSize.setText( Integer.toString(arr.get(0)) );
		
		inND = new JTextField();
		//inND.setText("1");
		inND.setColumns(10);
		inND.setBounds(50, 115, 100, 20);
		getContentPane().add(inND);
		
		if ( arr.get(1) < MyInterface.MIN_DRAGONS)
			inND.setText( Integer.toString(MyInterface.MIN_DRAGONS) );
		else if ( arr.get(1) > MyInterface.MAX_DRAGONS)
			inND.setText( Integer.toString(MyInterface.MAX_DRAGONS) );
		else
			inND.setText( Integer.toString(arr.get(1)) );
		
				
		ButtonGroup group = new ButtonGroup();
		
		JRadioButton lvl1 = new JRadioButton("Level 1");
		lvl1.setHorizontalAlignment(SwingConstants.CENTER);
		lvl1.setFont(new Font("Consolas", Font.PLAIN, 11));
		lvl1.setBounds(6, 165, 188, 25);
		getContentPane().add(lvl1);
		group.add(lvl1);
		
		JRadioButton lvl2 = new JRadioButton("Level 2");
		lvl2.setHorizontalAlignment(SwingConstants.CENTER);
		lvl2.setFont(new Font("Consolas", Font.PLAIN, 11));
		lvl2.setBounds(10, 190, 180, 25);
		getContentPane().add(lvl2);
		group.add(lvl2);
		
		JRadioButton lvl3 = new JRadioButton("Level 3");
		lvl3.setHorizontalAlignment(SwingConstants.CENTER);
		lvl3.setFont(new Font("Consolas", Font.PLAIN, 11));
		lvl3.setBounds(6, 215, 188, 25);
		getContentPane().add(lvl3);
		group.add(lvl3);

		if ( arr.get(2) == 1)
			lvl1.setSelected(true);
		else if ( arr.get(2) == 2)
			lvl2.setSelected(true);
		else if ( arr.get(2) == 3)
			lvl3.setSelected(true);
		else
			lvl1.setSelected(true);
		
		JLabel labelSize = new JLabel("Size");
		labelSize.setHorizontalAlignment(SwingConstants.CENTER);
		labelSize.setFont(new Font("Consolas", Font.PLAIN, 11));
		labelSize.setBounds(10, 50, 184, 15);
		getContentPane().add(labelSize);
		
		JLabel lblOptions = new JLabel("Options");
		lblOptions.setHorizontalAlignment(SwingConstants.CENTER);
		lblOptions.setFont(new Font("Consolas", Font.BOLD, 16));
		lblOptions.setBounds(10, 11, 184, 26);
		getContentPane().add(lblOptions);
		
		JLabel labelNDragons = new JLabel("Number of Dragons");
		labelNDragons.setHorizontalAlignment(SwingConstants.CENTER);
		labelNDragons.setFont(new Font("Consolas", Font.PLAIN, 11));
		labelNDragons.setBounds(10, 100, 184, 15);
		getContentPane().add(labelNDragons);
		
		JLabel labelLevel = new JLabel("Difficulty");
		labelLevel.setHorizontalAlignment(SwingConstants.CENTER);
		labelLevel.setFont(new Font("Consolas", Font.PLAIN, 11));
		labelLevel.setBounds(10, 150, 184, 14);
		getContentPane().add(labelLevel);
		
		JButton btnSaveOptions = new JButton("Save");
		btnSaveOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int res = JOptionPane.showConfirmDialog (null, "Would You Like to Save this Configuration?","Warning",JOptionPane.YES_NO_OPTION);
				
				if(res == JOptionPane.YES_OPTION){
					PrintWriter writer;
					try {
						writer = new PrintWriter(".config", "UTF-8");
						String str = "";
															
					// CHECK SIZE
						if ( Integer.parseInt( inSize.getText() ) < MyInterface.MIN_SIZE )
							str += MyInterface.MIN_SIZE + ",";
						else if ( Integer.parseInt( inSize.getText() ) > MyInterface.MAX_SIZE )
							str += MyInterface.MAX_SIZE + ",";
						else 
						{
							int n = Integer.parseInt( inSize.getText() );
							if ( n % 2 == 0 )
								n++;
							str += n + ",";
						}
						
					// CHECK NUMBER OF DRAGONS
						if ( Integer.parseInt( inND.getText() ) < MyInterface.MIN_DRAGONS )
							str += MyInterface.MIN_DRAGONS + ",";
						else if ( Integer.parseInt( inND.getText() ) > MyInterface.MAX_DRAGONS )
							str += MyInterface.MAX_DRAGONS + ",";
						else 
							str += inND.getText() + ",";

					// CHECK LEVEL
						if ( lvl1.isSelected() )
							str += "1,";
						else if ( lvl2.isSelected() )
							str += "2,";
						else if ( lvl3.isSelected() )
							str += "3,";
						else 
							str += "1,";
						writer.println(str);
						writer.close();
						setVisible(false);
					} catch (FileNotFoundException | UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				dispose();
				gamePanel.doSomeMagic();
			}
		});
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				gamePanel.doSomeMagic();
			}
		});
		btnSaveOptions.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnSaveOptions.setBounds(50, 250, 100, 25);
		getContentPane().add(btnSaveOptions);
	}
}
