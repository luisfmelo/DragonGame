package test;

import static org.junit.Assert.*;

import org.junit.Test;

public class HeroTest {
	
	Maze maze = new Maze();
	maze.setMaze()
	@Test
	public void testMoveHeroToFreeCell() {
		//Maze maze = new Maze();
		assertEquals(new Point(1, 3), maze.getHeroPosition());
		maze.moveHeroLeft();
		assertEquals(new Point(1, 2), maze.getHeroPosition());
	}
	
	@Test
	public void testHeroDies() {
		Maze maze = new Maze(m1);
		assertEquals(MazeStatus.HeroUnarmed, maze.getStatus());
		maze.moveHeroDown();
		class Logical View
		assertEquals(MazeStatus.HeroDied,
		maze.getStatus());
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
