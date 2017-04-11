package asteroids.tests;
import asteroids.model.World;
import asteroids.model.Ship;
import asteroids.model.Vector2D;
import asteroids.model.WorldObject;
import asteroids.model.Bullet;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class EvolveTests {
	
	private static final double EPSILON = 0.0001;
	private static final double EPSILON2 = 1;
	
	private static Ship ship1, ship2;
	private static World world;
	private static Bullet bullet1, bullet2;
	
	@Before
	public void setUpMutableFixture() {
		ship1 = new Ship(100, 100, 0, 10, 0, 0, 0);
		ship2 = new Ship(200, 200, 0, 10, 0, 0, 0);
		bullet1 = new Bullet(150, 150, 1, 0, 0, 0);
		bullet2 = new Bullet(50, 150, 1, 0, 0, 0);
		world = new World(1000,1000);
	}
	
	@Test
	public void canManipulate_allShips(){
		world.addWorldObject(ship1);
		world.addWorldObject(ship2);
		
		HashSet<Ship> allShips = world.getAllShips();
		for(Ship ship: allShips){
			ship.setVelocity(10,10);
		}
	
		allShips = world.getAllShips();
		for(Ship ship: allShips){
			assertEquals(new Vector2D(10,10),ship.getVelocity());
		}
	}
	@Test
	public void advanceTimeTest(){
		world.addWorldObject(ship1);
		world.addWorldObject(ship2);
		ship1.setVelocity(20, 0);
		ship2.thrustOn();
		world.advanceTime(1);
		assertEquals(new Vector2D(100 + 20, 100), ship1.getPosition());
		assert(ship2.getXVelocity() >0);
		
	}
}
