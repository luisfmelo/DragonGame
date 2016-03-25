package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import logic.Game;

public class MyKeyListener implements KeyListener{
	private String key = "";
	private Game myGame;
	
    public MyKeyListener(Game g) {
		this.myGame = g;
	}

    public void keyPressed(KeyEvent e) {
    	String key = "";
        switch (e.getKeyCode()) {
 			case 65: key = "A";
 			case 68: key = "D";
 			case 83: key = "S";
 			case 87: key = "W";
 			case 38: key = "W";
 			case 40: key = "S";
 			case 37: key = "A";
 			case 39: key = "D";
        }
        
        try {
        	System.out.println("typed: " + key);
			myGame.checkPos(key.charAt(0), myGame.hero);
		} catch (IllegalArgumentException e2) {
		}
    }

    public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

 }