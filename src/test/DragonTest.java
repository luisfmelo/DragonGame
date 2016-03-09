package test;

import static org.junit.Assert.*;

import org.junit.Test;

import logic.Dragon;
import logic.Game;
import logic.Hero;
import logic.Maze;
import logic.Point;
import logic.Sword;

public class DragonTest {

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
		g.sleepyDragon(maze, 100);
		assertTrue(d.isSleepy());
	}

	/**
	 * Test Dragon and Sword same pos
	 */
	@Test
	public void testSwordDragonSamePos() {
		char[][] c = new char[][] {
			{'X', 'X', 'X', 'X','X'},
			{'X', ' ', ' ', ' ','X'},
			{'X', ' ', 'X', ' ','X'},
			{'X', ' ', 'X', ' ','X'},
			{'X', 'X', 'X', 'X','X'}
		};
		
		Maze maze = new Maze(c);
		Dragon d = new Dragon();
		Sword s = new Sword();
		d.setPos(maze, new Point(3, 1));
		s.setPos(maze, new Point(2, 1));
		Game g = new Game(maze, 3, new Hero(), d, s);
		
		assertEquals(new Point(3,1), d.pos.getCoords());
		
		g.pcMove();

		assertEquals(new Point(2,1), d.pos.getCoords());
		assertNotEquals('D', d.getLetter());
		assertNotEquals('d', d.getLetter());
		assertEquals('F', d.getLetter());
		
		g.pcMove();

		assertNotEquals(new Point(2,1), d.pos.getCoords());
		assertTrue(d.pos.getCoords().equals(new Point(3,1)) || d.pos.getCoords().equals(new Point(1,1)));
		assertEquals('D', d.getLetter());
		assertNotEquals('F', d.getLetter());
	}

	/**
	 * Test Dragon encounters hero unarmed
	 */
	@Test
	public void testDragonKillsHero() {
		char[][] c = new char[][] {
			{'X', 'X', 'X', 'X','X'},
			{'X', ' ', ' ', ' ','X'},
			{'X', ' ', 'X', ' ','X'},
			{'X', ' ', 'X', ' ','X'},
			{'X', 'X', 'X', 'X','X'}
		};
		
		Maze maze = new Maze(c);
		Dragon d = new Dragon();
		Hero h = new Hero();
		d.setPos(maze, new Point(3, 1));
		h.setPos(maze, new Point(1, 1));
		
		Game g = new Game(maze, 3, h, d, new Sword());

		assertEquals(new Point(3,1), d.pos.getCoords());
		assertEquals(new Point(1,1), h.pos.getCoords());
		assertFalse(h.isDead());
		assertFalse(h.isArmed());
		
		g.pcMove();

		assertNotEquals(new Point(3,1), d.pos.getCoords());
		assertEquals(new Point(2,1), d.pos.getCoords());
		assertTrue(h.isDead());
		assertFalse(d.isDead());
	}

	/**
	 * Test Dragon encounters hero armed
	 */
	@Test
	public void testHeroKillsDragon() {
		char[][] c = new char[][] {
			{'X', 'X', 'X', 'X','X'},
			{'X', ' ', ' ', ' ','X'},
			{'X', ' ', 'X', ' ','X'},
			{'X', ' ', 'X', ' ','X'},
			{'X', 'X', 'X', 'X','X'}
		};
		
		Maze maze = new Maze(c);
		Dragon d = new Dragon();
		Hero h = new Hero();
		d.setPos(maze, new Point(3, 1));
		h.setPos(maze, new Point(1, 1));
		h.setArmed(true);
		
		Game g = new Game(maze, 3, h, d, new Sword());

		assertEquals(new Point(3,1), d.pos.getCoords());
		assertEquals(new Point(1,1), h.pos.getCoords());
		assertFalse(h.isDead());
		assertTrue(h.isArmed());
		
		g.pcMove();

		assertEquals(new Point(1,1), h.pos.getCoords());
		assertTrue(d.isDead());
		assertFalse(h.isDead());
		assertTrue(h.isArmed());
	}
	
}
