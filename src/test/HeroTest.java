package test;
import static org.junit.Assert.*;
import org.junit.Test;

import logic.Element;
import logic.Game;
import logic.Hero;
import logic.Maze;
import logic.Point;

	public class HeroTest {
		
	@Test
	public void testMoveHeroToFreeCell() {
		
		Maze maze = new Maze();
		maze.setTestMaze();
		//Game myGame = new Game(maze, level, len);
		//Element hero = maze.getElement('H');
		Hero h = new Hero();
		h.pos= new Point(maze.getElementPos('H').getX(),maze.getElementPos('H').getY());
		System.out.println(maze.getElementPos('H').getX() +" "+maze.getElementPos('H').getY());

		assertEquals(new Point(1, 3).toString(), maze.getElementPos('H').toString());
		
		h.setPos(maze, new Point(1,2));
		System.out.println(maze.getElementPos('H').getX() +" "+maze.getElementPos('H').getY());

		assertEquals(new Point(1, 2), maze.getElementPos('H'));
	}
	//@Test
	public void testHeroDies() {
		
		
		
		//maze.moveHeroDown();
		//assertEquals(MazeStatus.HeroDied, maze.getStatus());
	}
}
