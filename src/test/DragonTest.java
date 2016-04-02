package test;

import static org.junit.Assert.*;
import org.junit.Test;

import logic.Dragon;
import logic.Game;
import logic.Hero;
import logic.Maze;
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
		g.sleepyDragon(maze, 100,g.getDragons().get(0));
		assertTrue(d.isSleepy());
		
	}
}