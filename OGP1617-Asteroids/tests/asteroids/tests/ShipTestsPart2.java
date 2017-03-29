package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.*;

import asteroids.model.Bullet;
import asteroids.model.Ship;

public class ShipTestsPart2 {
	
	private static final double EPSILON = 0.0001;
	private static final double EPSILON2 = 1;
	
	private static Ship ship1, ship2;
	private static Bullet bullet1, bullet2;
	/**
	 * Set up a Mutable test fixture.
	 */
	
	@Before
	public void setUpMutableFixture(){
		ship1 = new Ship(100,100,0,10,0,0,0);
		ship2 = new Ship(200,200,0,10,0,0,0);
		bullet1 = new Bullet();
		bullet2 = new Bullet();
	}
	
	// mass tests
	@Test
	public final void getMass_standardMassShip1(){
		assertEquals(4.0/3.0 * Math.PI*Math.pow(ship1.getRadius(),3)*ship1.getDensity(), ship1.getMass(),EPSILON);
	}
	
	@Test
	public final void setMass_valid(){
		double oldShipMass = ship1.getMass();
		ship1.setMass(oldShipMass+20);
		assertEquals( oldShipMass + 20, ship1.getMass(), EPSILON);
	}
	
	@Test
	public final void setMass_invalidMass(){
		double oldShipMass = ship1.getMass();
		ship1.setMass(oldShipMass - 20);
		assertEquals(4.0/3.0 * Math.PI*Math.pow(ship1.getRadius(),3)*ship1.getDensity(), ship1.getMass(), EPSILON);
		
	}
	
	// loadBullet tests
	
	@Test
	public final void loadBullet_oneBullet(){
		bullet1.loadBulletOnShip(ship1);
		assert(ship1.containsBullet(bullet1));
	}
	
	@Test
	public final void loadBullets_multipleBullets(){
		ship1.loadBullets(bullet1, bullet2);
		assert(ship1.containsBullet(bullet1));
		assert(ship1.containsBullet(bullet2));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void LoadBullets_multipleBullets_invalid(){
		Bullet nullBullet = null;
		ship1.loadBullets(bullet1, bullet2, nullBullet);
	}
	
	// total mass tests
	
	// thrust tests
	
	// collisionTests
}
