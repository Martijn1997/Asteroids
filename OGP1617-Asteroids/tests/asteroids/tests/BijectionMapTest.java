package asteroids.tests;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.*;
import asteroids.model.*;

public class BijectionMapTest {
	
	private static Vector2D myVect1, myVect2;
	private static Ship ship1, ship2;
	private static BMap<Vector2D, Ship> myMap;
	
	@Before
	public void SetUpMutableFixture(){
		myVect1 = new Vector2D(0,0);
		myVect2 = new Vector2D(10,10);
		ship1 = new Ship(100,100,0,10,0,0,0);
		ship2 = new Ship(200,200,0,10,0,0,0);
		myMap = new BMap<Vector2D, Ship>();
		
	}
	
	@Test
	public final void constructortest(){
		BijectionMap<Vector2D, WorldObject> Map1 = new BMap<Vector2D, WorldObject>();
		assertFalse( Map1.containsKey(new Vector2D(0,0)));
	}
	@Test
	public final void putKeyValue_valid(){
		myMap.put(myVect1, ship1);
		myMap.put(myVect2, ship2);
		assert(myMap.containsKey(myVect1));
		assert(myMap.containsKey(myVect2));
		assert(myMap.containsValue(ship1));
		assert(myMap.containsValue(ship2));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void putKeyValue_invalidValue(){
		myMap.put(myVect1, ship1);
		myMap.put(myVect2, ship1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void putKeyValue_invalidkey(){
		myMap.put(myVect1, ship1);
		myMap.put(myVect1, ship2);
	}
	
	@Test
	public final void getKey_true(){
		myMap.put(myVect1, ship1);
		myMap.put(myVect2, ship2);
		assertEquals(myVect1, myMap.getKey(ship1));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void getKey_false(){
		myMap.put(myVect1, ship1);
		myMap.getKey(ship2);
	}
	
	@Test
	public final void removeKey_valid(){
		myMap.put(myVect1, ship1);
		myMap.put(myVect2, ship2);
		myMap.removeKey(myVect1);
		assertFalse(myMap.containsKey(myVect1));
		assertFalse(myMap.containsValue(ship1));
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void removeKey_invalid(){
		myMap.put(myVect1, ship1);
		myMap.removeKey(myVect2);
	}
	
	@Test
	public final void removeValue_valid(){
		myMap.put(myVect1, ship1);
		myMap.put(myVect2, ship2);
		myMap.removeValue(ship1);
		assertFalse(myMap.containsKey(myVect1));
		assertFalse(myMap.containsValue(ship1));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void removeValue_invalid(){
		myMap.put(myVect1, ship1);
		myMap.removeValue(ship2);
	}
	
	@Test
	public final void maintainsChanges(){
		myMap.put(myVect1, ship1);
		ship1.setVelocity(100,100);
		assertEquals(new Vector2D(100,100), myMap.getValue(myVect1).getVelocity());
	}
	
	@Test
	public final void getAllKeys_full(){
		myMap.put(myVect1, ship1);
		myMap.put(myVect2, ship2);
		HashSet<Vector2D> myVectList = new HashSet<Vector2D>();
		myVectList.add(myVect1);
		myVectList.add(myVect2);
		assertEquals(myVectList, myMap.getAllKeys());
	}
	
	@Test
	public final void getAllkeys_empty(){
		assertEquals(new HashSet<Vector2D>(),myMap.getAllKeys());
	}
	@Test
	public final void getAllValues_full(){
		myMap.put(myVect1, ship1);
		myMap.put(myVect2, ship2);
		HashSet<Ship> shipList = new HashSet<Ship>();
		shipList.add(ship1);
		shipList.add(ship2);
		assertEquals(shipList, myMap.getAllValues());
	}
	
	@Test
	public final void getAllValues_empty(){
		assertEquals(new HashSet<Ship>(), myMap.getAllValues());
	}
	
}

