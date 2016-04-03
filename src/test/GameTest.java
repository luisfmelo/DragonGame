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
