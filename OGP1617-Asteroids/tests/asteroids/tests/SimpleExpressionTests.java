package asteroids.tests;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asteroids.Programs.*;
import asteroids.model.AdditionExpression;
import asteroids.model.EqualsExpression;
import asteroids.model.Expression;
import asteroids.model.LiteralExpression;
import asteroids.model.MultiplicationExpression;
import asteroids.model.NegationExpression;
import asteroids.model.Ship;
import asteroids.model.VariableExpression;

public class SimpleExpressionTests {
	private static final double EPSILON = 0.0001;
	private static final double EPSILON2 = 1;
	
	private static LiteralExpression<Double> double1, double2, double3, zero;
	private static LiteralExpression<Boolean> bool1, bool2;
	private static LiteralExpression<Ship> ship1, ship2;
	
	@Before
	public final void setupMutableFixture(){
		double1 = new LiteralExpression<Double>(100.0);
		double2 = new LiteralExpression<Double>(-100.0);
		double3 = new LiteralExpression<Double>(1.0);
		zero = new LiteralExpression<Double>(0.0);
		
		bool1 = new LiteralExpression<Boolean>(true);
		bool2 = new LiteralExpression<Boolean>(false);
	}
	
	@Test
	public final void setVariableTest(){
		VariableExpression<Double> myVar = new VariableExpression<Double>("myVar", 100.0);
		assertEquals("myVar", myVar.getName());
		assertEquals(100, myVar.evaluate(),EPSILON);
	}
	
	@Test
	public final void equalstest_literal(){
		EqualsExpression<Expression> myExpression = new EqualsExpression<Expression>(double1,double1);
		assertEquals(true, myExpression.evaluate());
	}
	
	@Test
	public final void coidEqualsTest_false_literal(){
		EqualsExpression<Expression> myExpression = new EqualsExpression<Expression>(double1,double2);
		assertFalse(myExpression.evaluate());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void equalsTest_invalidType_literal(){
		EqualsExpression<Integer> myExpression = new EqualsExpression<Integer>(100,100);
	}
	
	@Test
	public final void equalsTest_Expression_true(){
		VariableExpression<Double> myVar1 = new VariableExpression<Double>("myVar1",1.0);
		VariableExpression<Double> myVar2 = new VariableExpression<Double>("myVar2",1.0);
		EqualsExpression<Expression> equals1 = new EqualsExpression<Expression>(myVar1, myVar2);
		assert(equals1.evaluate());
	}
	
	@Test
	public final void equalsTest_Expression_true_ships(){
		Ship ship1 = new Ship();
		Ship ship2 = ship1;
		VariableExpression<Ship> myVar1 = new VariableExpression<Ship>("myVar1",ship1);
		VariableExpression<Ship> myVar2 = new VariableExpression<Ship>("myVar2",ship1);
		VariableExpression<Ship> myVar3 = new VariableExpression<Ship>("myVar3",ship2);
		EqualsExpression<Expression> equals1 = new EqualsExpression<Expression>(myVar1, myVar2);
		EqualsExpression<Expression> equals2 = new EqualsExpression<Expression>(myVar1, myVar3);
		assert(equals1.evaluate());
		assert(equals2.evaluate());
		
	}
	
	@Test
	public final void negationTest_double_literal(){
		NegationExpression negation = new NegationExpression(double1);
		assertEquals(-100.0, negation.evaluate(),EPSILON);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public final void negationTest_double_variable(){
		VariableExpression<Double> var1 = new VariableExpression<Double>("Var1", 100.0);
		NegationExpression negation = new NegationExpression(var1);
		VariableExpression<Double> var2 = new VariableExpression<Double>("Var2",-100.0);
		EqualsExpression<Expression> equals1 = new EqualsExpression<Expression>(var2, negation);
		assert(equals1.evaluate());		
	}
	

//	@Test (expected = IllegalArgumentException.class)
//	public final void negationTest_boolean_literal(){
//		NegationExpression negation = new NegationExpression(bool1);
//		System.out.println(negation.getValue());
//	}
	
	@Test
	public final void addition(){
		AdditionExpression addition = new AdditionExpression(double1, zero);
		assertEquals(100.0, addition.evaluate(), EPSILON);
	}
	
//	@Test (expected = IllegalArgumentException.class)
//	public final void addition_wrong_use(){
//		@SuppressWarnings("rawtypes")
//		AdditionExpression<Expression> addition = new AdditionExpression<Expression>(double1, bool1);
//	}
	
	@Test
	public final void multiplication(){
		MultiplicationExpression multiplication = new MultiplicationExpression(double1, double3);
		assertEquals(100.0, multiplication.evaluate(), EPSILON);
	}
	
//	@Test (expected = IllegalArgumentException.class)
//	public final void multiplication_wrong_use(){
//		MultiplicationExpression multiplication = //new MultiplicationExpression(double1, bool1);
//
//	}
	
	@Test
	public final void addition_mult_compound(){

		MultiplicationExpression mult = new MultiplicationExpression(double1, double3);
		AdditionExpression add = new AdditionExpression(mult, zero);
		assertEquals(100, add.evaluate(), EPSILON);
	}
	
	@Test
	public final void mult_change_var(){
		VariableExpression<Double> myVar1 = new VariableExpression<Double>("myVar", 100.0);
		VariableExpression<Double> myVar2 = new VariableExpression<Double>("myVar", 1.0);
		MultiplicationExpression mult1 = new MultiplicationExpression(myVar1, myVar2);
		assertEquals(100.0, mult1.evaluate(), EPSILON);
		myVar1.setValue(1.0);
		assertEquals(1.0, mult1.evaluate(), EPSILON);
		
	}
}
