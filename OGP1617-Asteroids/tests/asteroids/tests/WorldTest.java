package asteroids.tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.Bullet;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.Vector2D;
import asteroids.model.WorldObject;

public class WorldTest {
	
	private static final double EPSILON = 0.0001;
	
	private static World world1, world2;
	private static Ship ship1, ship2, ship3;
	private static Bullet bullet1;
	
	@Before
	public void setUpMutableFixture(){
		world1 = new World(500000,400000);
		world2 = new World(1000000,1000000);
		ship1 = new Ship(100,100,0,10,0,0,0);
		ship2 = new Ship(103,100,0,10,0,0,0);
		ship3 = new Ship(200,50,0,10,0,0,0);
		bullet1 = new Bullet(500, 400, 2, 0, 0, 1);
	}
	
	
	@Test
	public final void Constructor() {
		assertEquals(500000, world1.getWidth(), EPSILON);
		assertEquals(400000, world1.getHeight(), EPSILON);
	}
	
	@Test
	public final void Termination(){
		world1.addWorldObject(ship1);
		world1.terminate();
		assert(world1.isTerminated());
		assert(world1.getAllWorldObjects().isEmpty());
		assert(ship1.getWorld() == null);
	}
	
	@Test
	public final void addWO_LegalCase(){
		world1.addWorldObject(ship1);
		assert(world1.getAllWorldObjects().contains(ship1));
		assert(ship1.getWorld() == world1);
	}

	@Test (expected = IllegalArgumentException.class)
	public final void addWO_IllegalCase(){
		world1.addWorldObject(ship1);
		world2.addWorldObject(ship1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void addWO_Overlap(){
		world1.addWorldObject(ship1);
		world1.addWorldObject(ship2);
	}
	
	@Test
	public final void testGetAllWOpositions(){
		world1.addWorldObject(ship1);
		world1.addWorldObject(ship3);
		world1.addWorldObject(bullet1);
		Vector2D pos1 = ship1.getPosition();
		Vector2D pos2 = ship3.getPosition();
		Vector2D pos3 = bullet1.getPosition();
		Set<Vector2D> positions = new HashSet<>(world1.getAllWorldObjectPositions());
		assert(positions.contains(pos1));
		assert(positions.contains(pos2));
		assert(positions.contains(pos3));
	}
	
	@Test
	public final void testGetAllWO(){
		world1.addWorldObject(ship1);
		world1.addWorldObject(ship3);
		world1.addWorldObject(bullet1);
		Set<WorldObject> objects = new HashSet<>(world1.getAllWorldObjects());
		assert(objects.contains(ship1));
		assert(objects.contains(ship3));
		assert(objects.contains(bullet1));
	}
	
	@Test
	public final void testGetAllShips(){
		world1.addWorldObject(ship1);
		world1.addWorldObject(ship3);
		world1.addWorldObject(bullet1);
		Set<WorldObject> objects = new HashSet<>(world1.getAllShips());
		assert(objects.contains(ship1));
		assert(objects.contains(ship3));
		assert(!objects.contains(bullet1));
	}
	
	@Test
	public final void testGetAllBullets(){
		world1.addWorldObject(ship1);
		world1.addWorldObject(ship3);
		world1.addWorldObject(bullet1);
		Set<WorldObject> objects = new HashSet<>(world1.getAllBullets());
		assert(!objects.contains(ship1));
		assert(!objects.contains(ship3));
		assert(objects.contains(bullet1));
	}
	
	@Test
	public final void removeFromWorld_LegalCase(){
		world1.addWorldObject(ship1);
		world1.addWorldObject(ship3);
		world1.addWorldObject(bullet1);
		world1.removeFromWorld(ship3);
		Set<WorldObject> objects = new HashSet<>(world1.getAllWorldObjects());
		assert(objects.contains(ship1));
		assert(!objects.contains(ship3));
		assert(objects.contains(bullet1));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void removeFromWorld_IllegalCase(){
		world1.addWorldObject(ship1);
		world1.addWorldObject(ship3);
		world1.addWorldObject(bullet1);
		world1.removeFromWorld(ship2);
	}
	
	@Test
	public final void getEntityAt_TrueCase(){
		world1.addWorldObject(ship1);
		world1.addWorldObject(ship3);
		world1.addWorldObject(bullet1);
		Vector2D position = ship1.getPosition();
		assert(ship1 == world1.getEntityAt(position));
	}
	
	@Test
	public final void getEntityAt_FalseCase(){
		world1.addWorldObject(ship1);
		world1.addWorldObject(ship3);
		world1.addWorldObject(bullet1);
		Vector2D position = ship2.getPosition();
		assert(null == world1.getEntityAt(position));
	}
	
}



