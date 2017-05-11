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
	private static Ship ship1, ship2, ship3, ship4, ship5, ship6, ship7, ship8, ship9, ship10;
	private static Bullet bullet1, bullet2, bullet3, bullet4;
	
	@Before
	public void setUpMutableFixture(){
		world1 = new World(500000,400000);
		world2 = new World(1000000,1000000);
		ship1 = new Ship(100,100,0,10,0,0,0);
		ship2 = new Ship(103,100,0,10,0,0,0);
		ship3 = new Ship(200,50,0,10,0,0,0);
		ship4 = new Ship(-90,50,0,10,0,0,0);
		ship5 = new Ship(200,200,0,10,100,100,0);
		ship6 = new Ship(400,200,0,10,-100,100,0);
		ship7 = new Ship(440,2000,0,400,0,0,0);
		ship8 = new Ship(1000,200,0,10,-100,0,0);
		ship9 = new Ship(1000,200,0,10,100,0,0);
		ship10 = new Ship(1400,200,0,10,-100,0,0);
		bullet1 = new Bullet(500, 400, 2, 0, 0, 1);
		bullet2 = new Bullet(52, 700, 2, -100, 0, 1);
		bullet3 = new Bullet(1000, 1000, 2, 50,70, 1);
		bullet4 = new Bullet(1400, 200, 2, -100,0, 1);
	}
	
	@Test
	public final void Constructor() {
		World world = new World(500, 400);
		assertEquals(500, world.getWidth(), EPSILON);
		assertEquals(400, world.getHeight(), EPSILON);
	}
	
	@Test
	public final void Termination(){
		world1.addWorldObject(ship1);
		world1.terminate();
		assertTrue(world1.isTerminated());
		assertTrue(world1.getAllWorldObjects().isEmpty());
		assertTrue(ship1.getWorld() == null);
	}
	
	@Test
	public final void addWO_LegalCase(){
		world1.addWorldObject(ship1);
		assertTrue(world1.getAllWorldObjects().contains(ship1));
		assertTrue(ship1.getWorld() == world1);
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
	
	@Test (expected = IllegalArgumentException.class)
	public final void addWO_NotInBoundary(){
		world1.addWorldObject(ship4);
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
		assertTrue(positions.contains(pos1));
		assertTrue(positions.contains(pos2));
		assertTrue(positions.contains(pos3));
	}
	
	@Test
	public final void testGetAllWO(){
		world1.addWorldObject(ship1);
		world1.addWorldObject(ship3);
		world1.addWorldObject(bullet1);
		Set<WorldObject> objects = new HashSet<>(world1.getAllWorldObjects());
		assertTrue(objects.contains(ship1));
		assertTrue(objects.contains(ship3));
		assertTrue(objects.contains(bullet1));
	}
	
	@Test
	public final void testGetAllShips(){
		world1.addWorldObject(ship1);
		world1.addWorldObject(ship3);
		world1.addWorldObject(bullet1);
		Set<WorldObject> objects = new HashSet<>(world1.getAllShips());
		assertTrue(objects.contains(ship1));
		assertTrue(objects.contains(ship3));
		assertFalse(objects.contains(bullet1));
	}
	
	@Test
	public final void testGetAllBullets(){
		world1.addWorldObject(ship1);
		world1.addWorldObject(ship3);
		world1.addWorldObject(bullet1);
		Set<WorldObject> objects = new HashSet<>(world1.getAllBullets());
		assertFalse(objects.contains(ship1));
		assertFalse(objects.contains(ship3));
		assertTrue(objects.contains(bullet1));
	}
	
	@Test
	public final void removeFromWorld_LegalCase(){
		world1.addWorldObject(ship1);
		world1.addWorldObject(ship3);
		world1.addWorldObject(bullet1);
		world1.removeFromWorld(ship3);
		Set<WorldObject> objects = new HashSet<>(world1.getAllWorldObjects());
		assertTrue(objects.contains(ship1));
		assertFalse(objects.contains(ship3));
		assertTrue(objects.contains(bullet1));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void removeFromWorld_IllegalCase(){
		world1.addWorldObject(ship1);
		world1.addWorldObject(ship3);
		world1.addWorldObject(bullet1);
		world1.removeFromWorld(ship2);
	}
	
	@Test
	public final void getNextCollision_1Collision(){
		world1.addWorldObject(ship5);
		world1.addWorldObject(ship6);
		double[] nextCollision = world1.getNextCollision();
		assertEquals(ship5.getTimeToCollision(ship6), nextCollision[0], EPSILON);
		assertEquals(ship5.getCollisionPosition(ship6)[0], nextCollision[1], EPSILON);
		assertEquals(ship5.getCollisionPosition(ship6)[1], nextCollision[2], EPSILON);
	}
	
	@Test
	public final void getNextCollision_2Collision(){
		world1.addWorldObject(ship5);
		world1.addWorldObject(ship6);
		world1.addWorldObject(ship7);
		world1.addWorldObject(ship8);
		double[] nextCollision = world1.getNextCollision();
		assertEquals(0.9, nextCollision[0], EPSILON);
		assertEquals(300, nextCollision[1], EPSILON);
		assertEquals(290, nextCollision[2], EPSILON);
	}
	
	@Test
	public final void getNextCollision_CollisionBoundary(){
		world1.addWorldObject(ship5);
		world1.addWorldObject(ship6);
		world1.addWorldObject(bullet2);
		double[] nextCollision = world1.getNextCollision();
		assertEquals(0.5, nextCollision[0], EPSILON);
		assertEquals(0, nextCollision[1], EPSILON);
		assertEquals(700, nextCollision[2], EPSILON);
	}
	
	@Test
	public final void evolve_Thrust(){
		world1.addWorldObject(ship1);
		world1.addWorldObject(ship5);
		world1.addWorldObject(ship7);
		world1.addWorldObject(bullet3);
		Vector2D oldPos1 = ship1.getPosition();
		Vector2D newPos2 = new Vector2D(300, 300);
		Vector2D newPos3 = new Vector2D(1050, 1070);
		double xVel = ship7.getThrustForce()/ship7.getTotalMass();
		ship7.thrustOn();
		world1.evolve(1, null);
		assertEquals(oldPos1.getXComponent(), ship1.getPosition().getXComponent(), EPSILON);
		assertEquals(oldPos1.getYComponent(), ship1.getPosition().getYComponent(), EPSILON);
		assertEquals(newPos2.getXComponent(), ship5.getPosition().getXComponent(), EPSILON);
		assertEquals(newPos2.getYComponent(), ship5.getPosition().getYComponent(), EPSILON);
		assertEquals(newPos3.getXComponent(), bullet3.getPosition().getXComponent(), EPSILON);
		assertEquals(newPos3.getYComponent(), bullet3.getPosition().getYComponent(), EPSILON);
		assertEquals(xVel, ship7.getXVelocity(), EPSILON);
	}
	
	@Test
	public final void evolve_1Collision(){
		world1.addWorldObject(ship9);
		world1.addWorldObject(ship10);
		Vector2D newPos1 = new Vector2D(1080, 200);
		Vector2D newPos2 = new Vector2D(1320, 200);
		world1.evolve(3, null);
		assertEquals(newPos1.getXComponent(), ship9.getPosition().getXComponent(), EPSILON);
		assertEquals(newPos1.getYComponent(), ship9.getPosition().getYComponent(), EPSILON);
		assertEquals(newPos2.getXComponent(), ship10.getPosition().getXComponent(), EPSILON);
		assertEquals(newPos2.getYComponent(), ship10.getPosition().getYComponent(), EPSILON);
	}
	
	@Test
	public final void evolve_Death(){
		world1.addWorldObject(ship9);
		world1.addWorldObject(bullet4);
		world1.evolve(3, null);
		assertFalse(world1.getAllWorldObjects().contains(ship9));
		assertFalse(world1.getAllWorldObjects().contains(bullet4));
	}
	
	
	@Test
	public final void getEntityAt_TrueCase(){
		world1.addWorldObject(ship1);
		Vector2D position = ship1.getPosition();
		WorldObject result = world1.getEntityAt(position); 
		assertEquals(ship1, result);
	}
	
	@Test
	public final void getEntityAt_FalseCase(){
		world1.addWorldObject(ship1);
		Vector2D position = ship2.getPosition();
		assertTrue(null == world1.getEntityAt(position));
	}
	
}



