package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import logic.Element;
import logic.Hero;
import logic.Point;

public class PositionsTest {

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
	 * Test Equals
	 */
	@Test
	public void testEquals() {
		Point p = new Point(1,1);
		Hero h = new Hero();
		
		p.setCoords(2, 1);
		assertTrue( p.equals(new Point(2,1)) );
		p.setCoords(1, 2);
		assertTrue( p.equals(new Point(1,2)) );
		assertFalse( p.equals(p) );
		assertFalse( p.equals(h) );
	}

	/**
	 * Test Get Coords
	 */
	@Test
	public void testGetCoords() {
		Point p1 = new Point(2,2);
		
		assertTrue( p1.getCoords().getX() == 2 );
		assertTrue( p1.getCoords().getY() == 2 );
	}
	
	
	
	
}
