package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.*;

import asteroids.model.Bullet;
import asteroids.model.Ship;
import asteroids.model.WorldObject;

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
		ship2 = new Ship(200,200,Math.PI/4,10,0,0,0);
		bullet1 = new Bullet();
		bullet2 = new Bullet();
	}
	
	// radius test
	@Test (expected = IllegalStateException.class)
	public final void setRadius_AlreadyInitialized(){
		ship1.setRadius(20);
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
	
	@Test (expected = IllegalArgumentException.class)
	public final void loadBullet_nullBullet(){
		Ship nullShip = null;
		bullet1.loadBulletOnShip(nullShip);
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
	
	@Test
	public final void TotalMass_noBullets(){
		assertEquals(ship1.getMass(), ship1.getTotalMass(), EPSILON);
	}
	
	@Test
	public final void TotalMass_2Bullets(){
		ship1.loadBullets(bullet1, bullet2);
		assertEquals(ship1.getMass() + bullet1.getMass()+ bullet2.getMass(), ship1.getTotalMass(), EPSILON );
	}
	// thrust status tests
	
	@Test
	public final void getTrusterStatus_initialized_value(){
		assertFalse(ship1.getThrusterStatus());
	}
	
	@Test
	public final void setTrusterStatus_thrust_on(){
		ship1.thrustOn();
		assertEquals(ship1.getThrusterStatus(), true);
	}
	
	// tests getter thrust status
	@Test
	public final void getTrustForceConstant(){
		assertEquals(1.1E21, ship1.getThrustForce(), EPSILON);
	}
	
	
	// test thrust functionality
	@Test
	public final void Thrust_Time1(){
		double xVel = ship1.getThrustForce()/ship1.getTotalMass();
		ship1.thrustOn();
		ship1.thrust(1);
		assertEquals(xVel, ship1.getXVelocity(), EPSILON);
	}
	
	@Test
	public final void Thrust_lightspeed(){
		ship1.thrustOn();
		ship1.thrust(10);
		assertEquals(WorldObject.LIGHT_SPEED, ship1.getXVelocity(), EPSILON);
	}
	
	@Test
	public final void Thrust_invalidTime(){
		ship1.thrustOn();
		ship1.thrust(-5);
		assertEquals(0, ship1.getXVelocity(), EPSILON);
	}
	
	@Test
	public final void Thrust_thrusterNotEnabled(){
		ship1.thrust(200);
		assertEquals(0, ship1.getXVelocity(), EPSILON);
	}
	
	@Test
	public final void thrust45degrees1second(){
		ship2.thrustOn();
		ship2.thrust(1);
		double xVel = Math.sin(Math.PI/4)*ship2.getThrustForce()/ship2.getTotalMass();
		double yVel = Math.cos(Math.PI/4)*ship2.getThrustForce()/ship2.getTotalMass();
		
		assertEquals(xVel, ship2.getXVelocity(), EPSILON);
		assertEquals(yVel, ship2.getYVelocity(), EPSILON);
	}
	
	// collisionTests
}
