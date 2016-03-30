package test;
import static org.junit.Assert.*;
import org.junit.Test;

import logic.Dragon;
import logic.Exit;
import logic.Game;
import logic.Hero;
import logic.Maze;
import logic.Point;
import logic.Sword;

public class HeroTest {
	
	/**
	 * herói move-se uma posição em direção a uma célula livre;	
	 */
	@Test
	public void testMoveHeroToFreeCell() {
		Maze maze = new Maze();
		Hero h = new Hero();
		Dragon d = new Dragon();
		d.pos.setCoords(0, 0);
		maze.setTestMaze();
		
		Game g = new Game(maze, 1, h, d, new Sword());

		//test initial position
		h.setPos(maze, new Point(1,1));
		assertEquals(new Point(1, 1).toString(), h.pos.toString());
		
		//test move down - free cell
		g.checkPos('S', h);
		assertEquals(new Point(2, 1).toString(), h.pos.toString() );

		//test move right - free cell
		h.setPos(maze, new Point(1,1));
		g.checkPos('D', h);
		assertEquals(new Point(1, 2).toString(), h.pos.toString() );
		
		//test move left - free cell
		h.setPos(maze, new Point(3,3));
		g.checkPos('A', h);
		assertEquals(new Point(3, 2).toString(), h.pos.toString() );

		//test move up - free cell
		h.setPos(maze, new Point(3,3));
		g.checkPos('W', h);
		assertEquals(new Point(2, 3).toString(), h.pos.toString() );
	}
	
	/**
	 * herói tenta mover-se sem sucesso contra uma parede;
	 */
	@Test
	public void testMoveHeroToAgainstWall() {
		Maze maze = new Maze();
		maze.setTestMaze();

		Hero h = new Hero();
		
		Game g = new Game(maze, 1, h, new Dragon(), new Sword());

		//test initial position
		h.setPos(maze, new Point(1,1));
		assertEquals(new Point(1, 1).toString(), h.pos.toString());
		
		//test move left - wall
		g.checkPos('A', h);
		assertEquals(new Point(1, 1).toString(), h.pos.toString() );
		
		//test move up - wall
		h.setPos(maze, new Point(1,1));
		g.checkPos('W', h);
		assertEquals(new Point(1, 1).toString(), h.pos.toString() );

		//test move right - wall
		h.setPos(maze, new Point(3,3));
		g.checkPos('D', h);
		assertEquals(new Point(3, 3).toString(), h.pos.toString() );

		//test move down - wall
		h.setPos(maze, new Point(3,3));
		g.checkPos('S', h);
		assertEquals(new Point(3, 3).toString(), h.pos.toString() );
	}

	/**
	 * herói move-se para a posição da espada e apanha a espada;
	 */
	@Test
	public void testMoveHeroToSword() {
		Maze maze = new Maze();
		maze.setTestMaze();

		Hero h = new Hero();
		Sword s = new Sword();
		
		Game g = new Game(maze, 1, h, new Dragon(), s);

		//test initial position
		h.setPos(maze, new Point(1,1));
		s.setPos(maze, new Point(1,2));
		assertEquals(new Point(1, 1).toString(), h.pos.toString());
		assertEquals(new Point(1, 2).toString(), s.pos.toString());
		
		//test move right - sword
		g.checkPos('D', h);
		assertEquals('A', h.getLetter() );
		assertFalse(s.isVisible());
		
		//test move down - sword
		h.setPos(maze, new Point(1,1));
		s.setPos(maze, new Point(2,1));
		g.checkPos('S', h);
		assertEquals('A', h.getLetter() );
		assertFalse(s.isVisible());

		//test move left - sword
		h.setPos(maze, new Point(3,3));
		s.setPos(maze, new Point(3,2));
		g.checkPos('A', h);
		assertEquals('A', h.getLetter() );
		assertFalse(s.isVisible());
		
		//test move up - sword
		h.setPos(maze, new Point(3,3));
		s.setPos(maze, new Point(2,3));
		g.checkPos('W', h);
		assertEquals('A', h.getLetter() );
		assertFalse(s.isVisible());
	}

	/**
	 * herói desarmado move-se para uma posição adjacente ao dragão e é morto por ele (derrota);
	 */
	@Test
	public void testDefeat() {
		Maze maze = new Maze();
		maze.setTestMaze();

		Dragon d = new Dragon();
		Hero h = new Hero();

		Game g = new Game(maze, 2, h, d, new Sword());

		//test initial position
		h.setPos(maze, new Point(1,1));
		d.setPos(maze, new Point(1,3));
		assertEquals(new Point(1, 1).toString(), h.pos.toString());
		assertEquals(new Point(1, 3).toString(), d.pos.toString());
		
		//test move right - defeat
		g.checkPos('D', h);
		assertEquals(true, h.isDead() );
		assertEquals(false, g.isGameRunning() );
		assertEquals(true, g.isDefeat() );
		
		//test move down - defeat
		maze = new Maze();
		maze.setTestMaze();
		h = new Hero();
		d = new Dragon();
		g = new Game(maze, 2, h, d,new Sword());
		h.setPos(maze, new Point(1,1));
		d.setPos(maze, new Point(3,1));
		g.checkPos('S', h);
		assertEquals(true, h.isDead() );
		assertEquals(false, g.isGameRunning() );
		assertEquals(true, g.isDefeat() );

		//test move left - defeat
		maze = new Maze();
		maze.setTestMaze();
		h = new Hero();
		d = new Dragon();
		g = new Game(maze, 2, h, d, new Sword());
		h.setPos(maze, new Point(1,3));
		d.setPos(maze, new Point(1,1));
		g.checkPos('A', h);
		assertEquals(true, h.isDead() );
		assertEquals(false, g.isGameRunning() );
		assertEquals(true, g.isDefeat() );
		
		//test move up - defeat
		maze = new Maze();
		maze.setTestMaze();
		h = new Hero();
		d = new Dragon();
		g = new Game(maze, 2, h, d, new Sword());
		h.setPos(maze, new Point(3,1));
		d.setPos(maze, new Point(1,1));
		g.checkPos('W', h);
		assertEquals(true, h.isDead() );
		assertEquals(false, g.isGameRunning() );
		assertEquals(true, g.isDefeat() );
	}

	/**
	 * herói armado move-se para uma posição adjacente ao dragão e mata-o;
	 */
	@Test
	public void testDragonsDead() {
		Maze maze = new Maze();
		maze.setTestMaze();

		Dragon d = new Dragon();
		Hero h = new Hero();
		h.setArmed(true);

		Game g = new Game(maze, 2, h, d, new Sword());

		//test initial position
		h.setPos(maze, new Point(1,1));
		d.setPos(maze, new Point(1,3));
		assertEquals(new Point(1, 1).toString(), h.pos.toString());
		assertEquals(new Point(1, 3).toString(), d.pos.toString());
		
		//test move right
		g.checkPos('D', h);
		assertEquals(true, d.isDead() );
		
		//test move down
		h.setPos(maze, new Point(1,1));
		d.setPos(maze, new Point(3,1));
		g.checkPos('S', h);
		assertEquals(true, d.isDead() );

		//test move left 
		h.setPos(maze, new Point(3,1));
		d.setPos(maze, new Point(1,1));
		g.checkPos('A', h);
		assertEquals(true, d.isDead() );
		
		//test move up
		h.setPos(maze, new Point(3,1));
		d.setPos(maze, new Point(1,1));
		g.checkPos('W', h);
		assertEquals(true, d.isDead() );
	}

	/**
	 * herói move-se para a saída após apanhar a espada e matar o dragão (vitória);
	 */
	@Test
	public void testWinning() {
		Maze maze = new Maze();
		maze.setTestMaze();

		Dragon d = new Dragon();
		Hero h = new Hero();
		Exit e = new Exit();
		
		h.setArmed(true);
		d.setDead(true);

		Game g = new Game(maze, 2, h, d, new Sword());

		//test initial position
		h.setPos(maze, new Point(1,3));
		e.setPos(maze, new Point(1,4));
		assertEquals(new Point(1, 3).toString(), h.pos.toString());
		assertEquals(new Point(1, 4).toString(), e.pos.toString());
		
		//test move right
		g.checkPos('D', h);
		assertEquals(false, g.isGameRunning() );
		assertEquals(true, g.isVictory() );
		
		//test move down 
		maze = new Maze();
		maze.setTestMaze();
		h = new Hero();
		d = new Dragon();
		g = new Game(maze, 2, h, d, new Sword());
		h.setArmed(true);
		h.setPos(maze, new Point(2,1));
		d.setPos(maze, new Point(3,1));
		e.setPos(maze, new Point(4,1));
		g.checkPos('S', h);
		g.checkPos('S', h);
		
		maze.print();
		assertEquals(false, g.isGameRunning() );
		assertEquals(true, g.isVictory() );

		//test move left 
		maze = new Maze();
		maze.setTestMaze();
		h = new Hero();
		g = new Game(maze, 2, h, d, new Sword());
		h.setArmed(true);
		h.setPos(maze, new Point(2,2));
		e.setPos(maze, new Point(2,0));
		d.setPos(maze, new Point(2,1));
		g.checkPos('A', h);
		g.checkPos('A', h);
		assertEquals(false, g.isGameRunning() );
		assertEquals(true, g.isVictory() );
		
		//test move up 
		maze = new Maze();
		maze.setTestMaze();
		h = new Hero();
		h.setArmed(true);
		g = new Game(maze, 2, h, d, new Sword());
		h.setPos(maze, new Point(2,2));
		d.setPos(maze, new Point(1,2));
		e.setPos(maze, new Point(0,2));
		g.checkPos('W', h);
		g.checkPos('W', h);
		assertEquals(false, g.isGameRunning() );
		assertEquals(true, g.isVictory() );
	}


	/**
	 * herói tenta mover-se sem sucesso para a saída sem ter apanhado a espada;
	 */
	@Test
	public void testTryingToGetOutWithoutSword() {
		Maze maze = new Maze();
		maze.setTestMaze();

		Dragon d = new Dragon();
		Hero h = new Hero();
		Exit e = new Exit();
		
		h.setArmed(false);

		Game g = new Game(maze, 2, h, d, new Sword());

		//test initial position
		h.setPos(maze, new Point(1,3));
		e.setPos(maze, new Point(1,4));
		assertEquals(new Point(1, 3).toString(), h.pos.toString());
		assertEquals(new Point(1, 4).toString(), e.pos.toString());
		
		//test move right
		g.checkPos('D', h);
		assertEquals(false, g.isVictory() );
		assertEquals(new Point(1, 3).toString(), h.pos.toString());
		
		//test move down 
		maze = new Maze();
		maze.setTestMaze();
		h = new Hero();
		g = new Game(maze, 2, h, d, new Sword());
		h.setPos(maze, new Point(3,1));
		e.setPos(maze, new Point(4,1));
		g.checkPos('S', h);
		maze.print();
		assertEquals(false, g.isVictory() );		
		assertEquals(new Point(3, 1).toString(), h.pos.toString());

		//test move left 
		maze = new Maze();
		maze.setTestMaze();
		h = new Hero();
		g = new Game(maze, 2, h, d, new Sword());
		h.setPos(maze, new Point(2,1));
		e.setPos(maze, new Point(2,0));
		g.checkPos('A', h);
		assertEquals(false, g.isVictory() );
		assertEquals(new Point(2, 1).toString(), h.pos.toString());
		
		//test move up 
		maze = new Maze();
		maze.setTestMaze();
		h = new Hero();
		g = new Game(maze, 2, h, d, new Sword());
		h.setPos(maze, new Point(1,2));
		e.setPos(maze, new Point(0,2));
		g.checkPos('W', h);
		assertEquals(false, g.isVictory() );
		assertEquals(new Point(1, 2).toString(), h.pos.toString());

	}


	/**
	 * herói tenta mover-se sem sucesso para a saída armado mas sem ter morto o dragão.
	 */
	@Test
	public void testTryingToGetOutWithoutKillingDragon() {
		Maze maze = new Maze();
		maze.setTestMaze();

		Dragon d = new Dragon();
		Hero h = new Hero();
		Exit e = new Exit();
		
		d.setDead(false);

		Game g = new Game(maze, 2, h, d, new Sword());

		//test initial position
		h.setPos(maze, new Point(1,3));
		e.setPos(maze, new Point(1,4));
		assertEquals(new Point(1, 3).toString(), h.pos.toString());
		assertEquals(new Point(1, 4).toString(), e.pos.toString());
		
		//test move right
		g.checkPos('D', h);
		assertEquals(false, g.isVictory() );
		assertEquals(new Point(1, 3).toString(), h.pos.toString());
		
		//test move down 
		maze = new Maze();
		maze.setTestMaze();
		h = new Hero();
		g = new Game(maze, 2, h, d, new Sword());
		h.setPos(maze, new Point(3,1));
		e.setPos(maze, new Point(4,1));
		g.checkPos('S', h);
		maze.print();
		assertEquals(false, g.isVictory() );		
		assertEquals(new Point(3, 1).toString(), h.pos.toString());

		//test move left 
		maze = new Maze();
		maze.setTestMaze();
		h = new Hero();
		g = new Game(maze, 2, h, d, new Sword());
		h.setPos(maze, new Point(2,1));
		e.setPos(maze, new Point(2,0));
		g.checkPos('A', h);
		assertEquals(false, g.isVictory() );
		assertEquals(new Point(2, 1).toString(), h.pos.toString());
		
		//test move up 
		maze = new Maze();
		maze.setTestMaze();
		h = new Hero();
		g = new Game(maze, 2, h, d, new Sword());
		h.setPos(maze, new Point(1,2));
		e.setPos(maze, new Point(0,2));
		g.checkPos('W', h);
		assertEquals(false, g.isVictory() );
		assertEquals(new Point(1, 2).toString(), h.pos.toString());

	}
	

}
