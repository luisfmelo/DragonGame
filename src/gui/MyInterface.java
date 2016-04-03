package gui;
 
 import java.awt.EventQueue;
 
 public class MyInterface {
 
 	protected static final int MAX_DRAGONS = 3;
 	protected static final int MIN_DRAGONS = 0;
 	protected static final int MAX_SIZE = 17;
 	protected static final int MIN_SIZE = 7;
 	protected static final int MAX_LEVEL = 3;
 	protected static final int MIN_LEVEL = 1;
 	
	/**
	 * Main Class for <b>G</b>raphical <b>U</b>ser <b>I</b>nterface
 	 * Launch the application.	 
 	 * * @author Luis
	 * @author Teresa
	 */
 	public static void main(String[] args) {
 		EventQueue.invokeLater(new Runnable() {
 			@Override
 			public void run() {
 				try {
 					MyFrame startWindow = new MyFrame();
 					startWindow.setLocationRelativeTo(null);
 					startWindow.setVisible(true);
 				} catch (Exception e) {
 					e.printStackTrace();
 				}
 			}
 		});
 	}
 
 	
 }