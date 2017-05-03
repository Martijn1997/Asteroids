package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.Asteroid;
import asteroids.model.Ship;

public class AsteroidTest {
	
	private static Ship ship;
	private static Asteroid asteroid;
	
	@Before
	public void setUpMutableFixture(){
	ship = new Ship(100,100,0,10,0,0,0);
	asteroid = new Asteroid(500, 100, 10, -10, 0, 0);
	}

	@Test
	public final void testResolveCollision(){
		asteroid.resolveCollision(ship);
		assertTrue(ship.isTerminated());
		assertFalse(asteroid.isTerminated());
	}
	
	@Test
	public final void isValidRadius_TrueCase() {
		assertTrue(asteroid.canHaveAsRadius(100));
	}
	
	@Test
	public final void isValidRadius_NegRadius() {
		assertFalse(asteroid.canHaveAsRadius(-10));
	}
}
