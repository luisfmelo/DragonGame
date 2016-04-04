package test;

import static org.junit.Assert.*;
import org.junit.Test;

import logic.Dragon;
import logic.Game;
import logic.Hero;
import logic.Maze;
import logic.Sword;

/**
 * Test some Game functionalities
 * @author Luis
 * @author Teresa
 */
public class GameTest {

	/**
	 * Test Defeat Or lose Sleepy
	 */
	@Test
	public void testSleepyDragon() {
		Game g = new Game(new Maze(), 2, new Hero(), new Dragon(), new Sword());

		assertFalse( g.isDefeat() );
		assertFalse( g.isVictory() );
		assertFalse( g.defeatOrVictory() );

		g.setVictory(true);

		assertFalse( g.isDefeat() );
		assertTrue( g.isVictory() );
		assertTrue( g.defeatOrVictory() );
		
		g = new Game(new Maze(), 2, new Hero(), new Dragon(), new Sword());
		g.setDefeat(true);

		assertTrue( g.isDefeat() );
		assertFalse( g.isVictory() );
		assertTrue( g.defeatOrVictory() );	
	}
	
	/**
	 * Test GAME RUNNING
	 */
	@Test
	public void testGameRunning() {
		char [][] lab = new char[][]{
			{'X','S','X','X','X'},
			{'X','H',' ','D','X'},
			{'X','E','X',' ','X'},
			{'X','d','X',' ','X'},
			{'X','X','X','X','X'}
		};
		
		Game g = new Game(1, 5, 2, lab);
		
		assertFalse( g.isGameRunning() );
		g.setGameRunning(true);
		assertTrue( g.isGameRunning() );
		assertTrue( g.getExit().getLetter() == 'S' );
		
		
		
		lab = new char[][]{
			{'X','S','X','X','X'},
			{'X','H',' ','d','X'},
			{'X','E','X',' ','X'},
			{'X',' ','X',' ','X'},
			{'X','X','X','X','X'}
		};
		g = new Game(2, 5, 1, lab);
		
		// Without Dragons
		lab = new char[][]{
			{'X','S','X','X','X'},
			{'X','H',' ',' ','X'},
			{'X','E','X',' ','X'},
			{'X',' ','X',' ','X'},
			{'X','X','X','X','X'}
		};
		g = new Game(2, 5, 0, lab);
		
		assertTrue( g.getExit().getLetter() == 's' );
	}
	
	/**
	 * Test get Maze String
	 */
	@Test
	public void testGetMazeString() {
		char [][] lab = new char[][]{
			{'X','X','X','X'},
			{'X',' ',' ','X'},
			{'X',' ','X','X'},
			{'X','X','X','X'}
		};
		Maze m = new Maze(lab);
		
		Game g = new Game(m, 2, new Hero(), new Dragon(), new Sword());
		
		assertEquals( g.getMazeString(), "XXXX\nX  X\nX XX\nXXXX\n" );
		assertFalse( g.getMazeString() == "XXXX\nX  X\n XXX\nXXXX" );
	}
}
