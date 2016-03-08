package test;

import static org.junit.Assert.*;
import org.junit.Test;

import logic.Dragon;
import logic.Element;
import logic.Game;
import logic.Hero;
import logic.Maze;
import logic.Point;
import logic.Sword;

public class otherTest {

	/**
	 * Test new legal Position
	 */
	@Test
	public void testNewPos() {
		Element el = new Element();

		//up
		el.pos.setCoords(1, 1);
		assertEquals(new Point(0, 1).toString(), el.newPosition('W').toString());
		
		//right
		el.pos.setCoords(1, 1);
		assertEquals(new Point(1, 2).toString(), el.newPosition('D').toString());
		
		//down
		el.pos.setCoords(1, 1);
		assertEquals(new Point(2, 1).toString(), el.newPosition('S').toString());
		
		//left
		el.pos.setCoords(1, 1);
		assertEquals(new Point(1, 0).toString(), el.newPosition('A').toString());
		
	}
	
	/**
	 * Test new Illegal Position
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testNewIllegalPos() {
		Element el = new Element();

		el.pos.setCoords(1, 1);
		el.newPosition('F');
	}

	/**
	 * Test Adjacent Positions
	 */
	@Test
	public void testAdjacentPos() {
		Point p1 = new Point(1,1);
		Point p2 = new Point(1,0);
		Point p3 = new Point(0,0);
		
		assertTrue( p1.adjacentTo(p2) );
		assertTrue(p3.adjacentTo(p2));
		assertFalse(p1.adjacentTo(p3));
	}

	/**
	 * Test Get Coordenates
	 */
	@Test
	public void testGetCoords() {
		Point p = new Point(1,1);

		p.setCoords(1, 1);
		
		assertTrue( p.equals(new Point(1,1)) );
	}
	
	/**
	 * Test Dragon Sleepy
	 */
	@Test
	public void testSleepyDragon() {
		Maze maze = new Maze();
		maze.setTestMaze();
		Dragon d = new Dragon();
		
		Game g = new Game(maze, 2, new Hero(), d, new Sword());
		
		//he will sleep
		//g.sleepyDragon(maze, 100);
		//assertTrue(d.isSleepy());
		
	}
}
