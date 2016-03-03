package test;

import static org.junit.Assert.*;

import org.junit.Test;

import logic.Hero;
import logic.Maze;

public class HeroTest {
	
	@Test
	public void testMoveHeroToFreeCell() {
		
		
		Maze maze = new Maze();
		maze.setTestMaze();
		Hero hero = new Hero();
		maze.print();
		//actual position of Hero
		assertEquals("1,3", maze.getElementPos('H'));
		hero.setPosX(3);
		hero.setPosY(1);
		//move Hero to Left (1,2)
		hero.setPos(maze, 2, 1);
		maze.print();
		assertEquals("1,2", maze.getElementPos('H'));
	}
	
	@Test
	public void testHeroDies() {
		//Maze maze = new Maze(m1);
		//assertEquals(MazeStatus.HeroUnarmed, maze.getStatus());
		//maze.moveHeroDown();
		//class Logical View
		//assertEquals(MazeStatus.HeroDied,
		//maze.getStatus());
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
