package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.*;
public class BulletTest {
	
	private static final double EPSILON = 0.0001;
	private static final double EPSILON2 = 1;
	
	private static Ship ship1, ship2;
	private static Bullet bullet1;
	private static World world1;
	
	@Before
	public void setupMutableFixture(){
		ship1 = new Ship(100,100,0,10,0,0,0);
		bullet1 = new Bullet();
		ship2 = new Ship(200,200,0,10,0,0,0);
		world1 = new World(1000,1000);
	}
	
	
	// mass & density tests
	@Test
	public final void setDensity_Valid(){
		bullet1.setMass(WorldObject.volumeSphere(bullet1.getRadius())*bullet1.getMinimumDensity());
		assertEquals(WorldObject.volumeSphere(bullet1.getRadius())*bullet1.getMinimumDensity(), bullet1.getMass(), EPSILON);
	}
	
	@Test
	public final void setDensity_False(){
		bullet1.setMass(WorldObject.volumeSphere(bullet1.getRadius())*bullet1.getMinimumDensity() - 1);
		assertEquals(WorldObject.volumeSphere(bullet1.getRadius())*bullet1.getMinimumDensity(), bullet1.getMass(), EPSILON);
	}
	
	// bullet-ship interaction
	@Test
	public final void loadBulletOnShip_valid(){
		bullet1.loadBulletOnShip(ship1);
		assertEquals(ship1,bullet1.getShip());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void loadBulletOnShip_nullPtr(){
		bullet1.loadBulletOnShip(null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void loadBulletOnShip_wrongShip(){
		world1.addWorldObject(ship1);
		world1.addWorldObject(ship2);
		bullet1.loadBulletOnShip(ship1);
		ship1.fireBullet();
		bullet1.loadBulletOnShip(ship2);
		
	}
	
	@Test
	public final void loadBulletOnShip_ownBullet(){
		world1.addWorldObject(ship1);
		bullet1.loadBulletOnShip(ship1);
		ship1.fireBullet();
		bullet1.incementBounceCount();
		bullet1.incementBounceCount();
		bullet1.loadBulletOnShip(ship1);
		assertFalse(world1.getAllWorldObjects().contains(bullet1));
		assertEquals(bullet1.getPosition(), ship1.getPosition());
		assertEquals(0, bullet1.getBounceCount());
	}
	
		
		
}
