package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.Vector2D;

public class VectorTests {
	private static final double EPSILON = 0.0001;
	private static final double EPSILON2 = 1;
	
	public static Vector2D vector1, vector2;
	
	@Before
	public void setUpMutableFixture(){
		vector1 = new Vector2D(0,0);
		vector2 = new Vector2D(3,4);
	}
	
	@Test
	public final void getVector_vector2(){
		double[] vector2Copy = vector2.getVector2D();
		assertEquals(3, vector2Copy[0], EPSILON);
		assertEquals( 4, vector2Copy[1], EPSILON);	
	}
	
	@Test
	public final void getXComponent_vector2(){
		assertEquals(vector2.getVector2D()[0], vector2.getXComponent(), EPSILON);
	}
	
	@Test
	public final void getYComponent_vector2(){
		assertEquals(vector2.getVector2D()[1], vector2.getYComponent(), EPSILON);
	}
	
	@Test
	public final void difference_vector1and2(){
		Vector2D diffVector = vector1.difference(vector2);
		assertEquals(-3, diffVector.getXComponent(), EPSILON);
		assertEquals(-4, diffVector.getYComponent(), EPSILON);
	}
	
	@Test
	public final void equalsTest_true(){
		Vector2D vector3 = new Vector2D(3,4);
		assertEquals(vector2,vector3);
	}
	
	@Test
	public final void equalsTest_false(){
		assertFalse(vector1.equals(vector2));
	}
	
	@Test
	public final void distanceTest(){
		double distance = vector1.distanceTo(vector2);
		assertEquals(5, distance, EPSILON);
	}
	
	@Test
	public final void dotProductTest(){
		assertEquals(0, vector1.dotProduct(vector2), EPSILON);
	}
}
