package test;

import static org.junit.Assert.*;
import org.junit.Test;

import logic.Hero;
import logic.Sword;

/**
 * Test some Element functionalities
 * @author Luis
 * @author Teresa
 */
public class ElementsTest {

	/**
	 * Test Sword Visibility
	 */
	@Test
	public void testSwordVisibility() {
		Sword s = new Sword();
		
		assertTrue(s.isVisible());
		
		s.setVisible(false);
		assertFalse(s.isVisible());
	}
	
	/**
	 * Test if Hero can die
	 */
	@Test
	public void testHeroLife() {
		Hero h = new Hero();
		
		assertFalse(h.isDead());
		
		h.setDead(true);
		assertTrue(h.isDead());
	}
	
	/**
	 * Test if Hero can be armed
	 */
	@Test
	public void testHeroArmed() {
		Hero h = new Hero();
		
		assertFalse(h.isArmed());
		
		h.setArmed(true);
		assertTrue(h.isArmed());
	}
}
