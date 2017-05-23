package asteroids.tests;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Set;

import asteroids.model.Asteroid;
import asteroids.model.Planetoid;
import asteroids.model.Ship;
import asteroids.model.World;

public class PlanetoidTest {

	private static final double EPSILON = 0.0001;

	private static Planetoid planetoid,planetoid1;
	private static World world;
	
	@Before
	public void setUpMutableFixture(){
	planetoid = new Planetoid(500, 100, 10, -10, 0, 1000000, 0);
	planetoid1 = new Planetoid(100, 100, 50, -10, 0, 1000000, 0);
	world = new World(10000,10000);
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
	
	@Test
	public final void testTerminate() {
		world.addWorldObject(planetoid);
		world.addWorldObject(planetoid1);
		planetoid.terminate();
		Set<Asteroid> asteroids = world.getAllAsteroids();
		assertEquals(0,asteroids.size());
		assertTrue(planetoid.isTerminated());
		planetoid1.terminate();
		Set<Asteroid> asteroids1 = world.getAllAsteroids();
		assertEquals(2,asteroids1.size());
		assertTrue(planetoid1.isTerminated());
	}
}
