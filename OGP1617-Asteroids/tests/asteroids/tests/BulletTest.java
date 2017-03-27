package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.Bullet;
import asteroids.model.Ship;
public class BulletTest {
	
	private static final double EPSILON = 0.0001;
	private static final double EPSILON2 = 1;
	
	private static Ship ship1;
	private static Bullet bullet1;
	
	@Before
	public void setupMutableFixture(){
		ship1 = new Ship();
		bullet1 = new Bullet();
	}
	
	@Test
	public final void setShip_test(){
		bullet1.loadBulletOnShip(ship1);
		assertEquals(ship1, bullet1.getShip());
//		Ship newShip = bullet1.getShip();
//		newShip.setVelocity(10, 10);
//		System.out.println(ship1.getXVelocity());
//		assertFalse(newShip.getXVelocity() == ship1.getXVelocity());
	}
	@Test
	public final void getShip_nullTest(){
		assertEquals(null, bullet1.getShip());
	}
}
