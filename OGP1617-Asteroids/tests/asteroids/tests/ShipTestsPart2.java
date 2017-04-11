package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.*;

import asteroids.model.Bullet;
import asteroids.model.Ship;
import asteroids.model.Vector2D;
import asteroids.model.World;
import asteroids.model.WorldObject;

public class ShipTestsPart2 {
	
	private static final double EPSILON = 0.0001;
	private static final double EPSILON2 = 1;
	
	private static Ship ship1, ship2, collisionShip1, collisionShip2, collisionShip3, collisionShip4;
	private static Bullet bullet1, bullet2;
	private static World world1;
	
	/**
	 * Set up a Mutable test fixture.
	 */
	@Before
	public void setUpMutableFixture(){
		ship1 = new Ship(100,100,0,10,0,0,0);
		ship2 = new Ship(200,200,Math.PI/4,10,0,0,0);
		collisionShip1 = new Ship(100,100,0,10,100,0,0);
		collisionShip2 = new Ship(310,100,0,10,-100,0,0);
		collisionShip3 = new Ship(100,90,0,10,0,100,0);
		collisionShip4 = new Ship(100,910,0,10,0,-100,0);
		world1 = new World(1000,1000);
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
	public final void setDensity_valid(){
		double oldShipMass = ship1.getDensity();
		ship1.setDensity(oldShipMass+20);
		assertEquals( oldShipMass + 20, ship1.getDensity(), EPSILON);
	}
	
	@Test
	public final void setDensity_invalidDensity(){
		double oldShipMass = ship1.getDensity();
		ship1.setDensity(oldShipMass - 20);
		assertEquals(4.0/3.0 * Math.PI*Math.pow(ship1.getRadius(),3)*ship1.getDensity(), ship1.getMass(), EPSILON);
		
	}

	
	// loadBullet tests
	
	@Test
	public final void loadBullet_oneBullet(){
		bullet1.loadBulletOnShip(ship1);
		assert(ship1.containsBullet(bullet1));
		assertEquals(ship1.getPosition(), bullet1.getPosition());
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
	// termination test
	@Test
	public final void Temination(){
		world1.addWorldObject(ship1);
		ship1.terminate();
		assert(ship1.isTerminated());
		assert(ship1.getWorld() == null);
		for(Bullet bullet: ship1.getBulletSet()){
			assert(bullet.isTerminated());
		}
		assertFalse(world1.getAllShips().contains(ship1));
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
		assertEquals(1.1E25, ship1.getThrustForce(), EPSILON);
	}
	
	
	// test thrust functionality
	@Test
	public final void Thrust_Time1(){
		double time = 1E-5;
		double xVel = ship1.getThrustForce()/ship1.getTotalMass()*time;
		ship1.thrustOn();
		ship1.thrust(time);
		assertEquals(xVel, ship1.getXVelocity(), EPSILON2);
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
		double time = 1E-5;
		ship2.thrustOn();
		ship2.thrust(time);
		double xVel = Math.sin(Math.PI/4)*ship2.getThrustForce()/ship2.getTotalMass()*time;
		double yVel = Math.cos(Math.PI/4)*ship2.getThrustForce()/ship2.getTotalMass()*time;
		
		assertEquals(xVel, ship2.getXVelocity(), EPSILON);
		assertEquals(yVel, ship2.getYVelocity(), EPSILON);
	}
	
	// collisionTests world
	@Test
	public final void worldCollsion_rightBoundary_and_Position(){
		world1.addWorldObject(collisionShip1);
		assertEquals(8.9, collisionShip1.getTimeToCollision(world1), EPSILON);
		double[] collision = collisionShip1.getCollisionPosition(world1);
		assertEquals(1000, collision[0], EPSILON);
		assertEquals(100, collision[1], EPSILON);
		
	}
	
	@Test
	public final void worldCollision_leftBoundary_and_Position(){
		world1.addWorldObject(collisionShip2);
		assertEquals(3, collisionShip2.getTimeToCollision(world1), EPSILON);
		double[] collision = collisionShip2.getCollisionPosition(world1);
		assertEquals(0, collision[0], EPSILON);
		assertEquals(100, collision[1], EPSILON);
	}
	
	@Test
	public final void worldCollision_topBoundary_and_Position(){
		world1.addWorldObject(collisionShip3);
		assertEquals(9, collisionShip3.getTimeToCollision(world1), EPSILON);
		double[] collision = collisionShip3.getCollisionPosition(world1);
		assertEquals(100, collision[0], EPSILON);
		assertEquals(1000, collision[1], EPSILON);
	}
	
	@Test
	public final void worldCollision_bottomBoundary_and_Position(){
		world1.addWorldObject(collisionShip4);
		assertEquals(9, collisionShip4.getTimeToCollision(world1), EPSILON);
		double[] collision = collisionShip4.getCollisionPosition(world1);
		assertEquals(100, collision[0], EPSILON);
		assertEquals(0, collision[1], EPSILON);
	}
	
	//shooting bullets
	@Test
	public final void FireBullet_ship1_bullet1(){
		world1.addWorldObject(ship1);
		bullet1.loadBulletOnShip(ship1);
		ship1.fireBullet();
		assertEquals(bullet1.getWorld(), ship1.getWorld());
		Vector2D supposedBulletPos = new Vector2D(100,100 + (ship1.getRadius()+bullet1.getRadius())*Ship.BULLET_OFFSET);
		assertEquals(bullet1.getPosition(), supposedBulletPos);
		Vector2D supposedBulletVel = new Vector2D(250,0);
		assertEquals(bullet1.getVelocity(), supposedBulletVel);
	}
	
	@Test
	public final void FireBullet_Ship2_bullet1(){
		world1.addWorldObject(ship2);
		bullet1.loadBulletOnShip(ship2);
		ship2.fireBullet();
		assertEquals((ship2.getRadius() + bullet1.getRadius())*Ship.BULLET_OFFSET, ship2.getPosition().distanceTo(bullet1.getPosition()), EPSILON);
		assertEquals(250*Math.cos(ship2.getOrientation()), bullet1.getVelocity().getXComponent(), EPSILON);
		assertEquals(250*Math.sin(ship2.getOrientation()),bullet1.getVelocity().getYComponent(),EPSILON);
	}
	
	// CollisionResolve Ship - World
	@Test
	public final void ResolveShipWorldCollision_right(){
		collisionShip1.setPosition(990, 0);
		collisionShip1.setWorld(world1);
		collisionShip1.resolveCollision(world1);
		Vector2D supposedVelocity = new Vector2D(-100,0);
		assertEquals(collisionShip1.getVelocity(), supposedVelocity);
	}
	
	@Test
	public final void ResolveShipWorldCollision_left(){
		collisionShip2.setPosition(10, 100);
		collisionShip2.setWorld(world1);
		collisionShip2.resolveCollision(world1);
		Vector2D supposedVelocity = new Vector2D(100,0);
		assertEquals(collisionShip2.getVelocity(), supposedVelocity);
	}
	
	
	@Test
	public final void ResolveShipWorldCollision_top(){
		collisionShip2.setPosition(100, 990);
		collisionShip2.setVelocity(0, 100);
		collisionShip2.setWorld(world1);
		collisionShip2.resolveCollision(world1);
		Vector2D supposedVelocity = new Vector2D(0,-100);
		assertEquals(collisionShip2.getVelocity(), supposedVelocity);
	}
	
	@Test
	public final void ResolveShipWorldCollision_bottom(){
		collisionShip2.setPosition(100, 10);
		collisionShip2.setVelocity(0, -100);
		collisionShip2.setWorld(world1);
		collisionShip2.resolveCollision(world1);
		Vector2D supposedVelocity = new Vector2D(0,100);
		assertEquals(collisionShip2.getVelocity(), supposedVelocity);
	}
	// collisionResolve Ship-Bullet
	
	@Test
	public final void ResolveShipBulletCollision_own_bullet(){
		world1.addWorldObject(ship1);
		bullet1.loadBulletOnShip(ship1);
		ship1.fireBullet();
		bullet1.setPosition(89, 100);
		bullet1.setVelocity(500, 0);
		assert(World.significantOverlap(bullet1, ship1));
		assertEquals(ship1, bullet1.getShip());
		ship1.resolveCollision(bullet1);
		assert(ship1.containsBullet(bullet1));
		assert(bullet1.getLoadedOnShip());
	}
	
	@Test
	public final void ResolveShipBulletCollision_other_bullet(){
		bullet1.setPosition(10, 10);
		world1.addWorldObject(ship1);
		world1.addWorldObject(bullet1);
		bullet1.setPosition(89, 100);
		bullet1.setVelocity(100, 0);
		assert(World.significantOverlap(bullet1,  ship1));
		assertFalse(bullet1.getShip()==ship1);
		ship1.resolveCollision(bullet1);
		assert(ship1.isTerminated());
		assert(bullet1.isTerminated());
	}
	
	
	// collisionResolve Ship-Ship
	@Test
	public final void ResolveShipShipCollsion_headOn_xAxis(){
		
		// set the ships in the world
		world1.addWorldObject(ship1);
		world1.addWorldObject(ship2);
		
		// set the ships such that they overlap
		ship1.setPosition(100, 100);
		ship2.setPosition(120,100);
		
		// set the velocity of the ships
		ship1.setVelocity(100, 0);
		ship2.setVelocity(-100, 0);
		
		// resolve the collision
		ship1.resolveCollision(ship2);
		
		Vector2D expected1 = new Vector2D(-100,0);
		Vector2D expected2 = new Vector2D(100, 0);

		assertEquals(expected1.getXComponent(),ship1.getXVelocity(), EPSILON);
		assertEquals(expected1.getYComponent(),ship1.getYVelocity(), EPSILON);
		assertEquals(expected2.getXComponent(),ship2.getXVelocity(), EPSILON);
		assertEquals(expected2.getYComponent(),ship2.getYVelocity(), EPSILON);
		
	}
	
	@Test
	public final void ResolveShipCollision_head_on_Angle(){
		// set the ships in the world
				world1.addWorldObject(ship1);
				world1.addWorldObject(ship2);
				
				// test multiple angles
				for(int index = 0 ; index<20; index++){
					double angle = index * Math.PI/20;
					ship1.setPosition(100, 100);
					ship2.setPosition(100 + 20*Math.cos(angle), 100+20*Math.sin(angle));
					
					// set the velocity of the ships
					ship1.setVelocity(100*Math.cos(angle), 100*Math.sin(angle));
					ship2.setVelocity(-100*Math.cos(angle), -100*Math.sin(angle));
					
					// resolve the collision
					ship1.resolveCollision(ship2);
					
					Vector2D expected1 = new Vector2D(-100*Math.cos(angle), -100*Math.sin(angle));
					Vector2D expected2 = new Vector2D(100*Math.cos(angle), 100*Math.sin(angle));
					
					/*
					System.out.println("current angle: " + angle);
					System.out.println(ship1.getXVelocity());
					//System.out.println(expected1.getXComponent());
					System.out.println(ship1.getYVelocity());
					//System.out.println(expected1.getYComponent());
					System.out.println(ship2.getXVelocity());
					//System.out.println(expected2.getXComponent());
					System.out.println(ship2.getYVelocity());
					//System.out.println(expected2.getYComponent());
					 */
					
					assertEquals(expected1.getXComponent(),ship1.getXVelocity(), EPSILON);
					assertEquals(expected1.getYComponent(),ship1.getYVelocity(), EPSILON);
					assertEquals(expected2.getXComponent(),ship2.getXVelocity(), EPSILON);
					assertEquals(expected2.getYComponent(),ship2.getYVelocity(), EPSILON);
					
				}
				
	}
	
	
}
