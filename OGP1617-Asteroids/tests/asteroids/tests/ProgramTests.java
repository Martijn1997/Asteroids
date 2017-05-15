package asteroids.tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.Bullet;
import asteroids.model.Program;
import asteroids.model.ProgramFactory;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.part3.programs.SourceLocation;
import asteroids.part3.programs.internal.ProgramParser;

public class ProgramTests {

	World filledWorld;
	Ship ship1, ship2, ship3;
	Bullet bullet1;
	
	 @Before
	 public void setUp(){
		filledWorld = new World(2000, 2000);
		ship1 = new Ship(100, 120, 0, 50, 10, 5, 1.0E20);
		 for (int i = 1; i < 10; i++) {
	      Bullet bulletToLoad = new Bullet(100, 120, 10, 0 , 0, 0);
	      ship1.loadBullets(bulletToLoad);
	    }
	    filledWorld.addWorldObject(ship1);
	 }
	  
	 @Test
	  public void testThrusterOnStatement_EnoughTimeLeft() {
	      String code = "thrust; " + "print 0.4; ";
	      ProgramFactory programFactory = new ProgramFactory();
	      Program program = ProgramParser.parseProgramFromString(code, programFactory);
	      System.out.print(program);
	      ship1.setProgram(program);
	      List<Object> results = ship1.getProgram().excecuteProgram(0.45);
	      assertTrue(ship1.getThrusterStatus());
	      Object[] expecteds = { 0.4 };
	      assertArrayEquals(expecteds, results.toArray());
	  }
}
