package asteroids.tests;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import asteroids.model.Planetoid;
import asteroids.model.Ship;

public class PlanetoidTest {

	private static final double EPSILON = 0.0001;
	
	private static Ship ship;
	private static Planetoid planetoid;
	
	@Before
	public void setUpMutableFixture(){
	ship = new Ship(100,100,0,10,0,0,0);
	planetoid = new Planetoid(500, 100, 10, -10, 0, 1000000, 0);
	}
	
	@Test
	public final void testSetRadius(){
		assertEquals(9, planetoid.getRadius(), EPSILON);
	}
	
	@Test
	public final void isValidRadius_TrueCase() {
		assertTrue(planetoid.canHaveAsRadius(100));
	}
	
	@Test
	public final void isValidRadius_NegRadius() {
		assertFalse(planetoid.canHaveAsRadius(-10));
	}
}
