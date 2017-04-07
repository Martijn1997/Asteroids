package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.Bullet;
import asteroids.model.Ship;
import asteroids.model.WorldObject;
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
	
	
	// mass & density tests
	@Test
	public final void setDensity_Valid(){
		bullet1.setDensity(bullet1.getMinimumDensity() + 1);
		assertEquals(bullet1.getMinimumDensity()+1, bullet1.getDensity(), EPSILON);
		assertEquals(WorldObject.volumeSphere(bullet1.getRadius())*bullet1.getDensity(), bullet1.getMass(), EPSILON);
	}
	
	@Test
	public final void setDensity_False(){
		bullet1.setDensity(bullet1.getMinimumDensity() - 1);
		assertEquals(bullet1.getMinimumDensity(), bullet1.getDensity(), EPSILON);
	}
	
	// bullet-ship interaction
	
		
		
}
