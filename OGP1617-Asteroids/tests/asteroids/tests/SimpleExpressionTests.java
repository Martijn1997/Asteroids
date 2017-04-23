package asteroids.tests;
import static org.junit.Assert.*;

import org.junit.Test;

import asteroids.Programs.*;
import asteroids.model.Ship;

public class SimpleExpressionTests {
	private static final double EPSILON = 0.0001;
	private static final double EPSILON2 = 1;
	
	@Test
	public final void setVariableTest(){
		VariableExpression<Double> myVar = new VariableExpression<Double>("myVar", 101.0);
		assertEquals("myVar", myVar.getName());
		assertEquals(101.0, myVar.getValue(),EPSILON);
	}
	
	@Test
	public final void equalstest_literal(){
		EqualsExpression<Double> myExpression = new EqualsExpression<Double>(100.0,100.0);
		assertEquals(true, myExpression.getValue());
	}
	
	@Test
	public final void coidEqualsTest_false_literal(){
		EqualsExpression<Double> myExpression = new EqualsExpression<Double>(100.0,1.0);
		assertFalse(myExpression.getValue());
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
		assert(equals1.getValue());
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
		assert(equals1.getValue());
		assert(equals2.getValue());
		
	}
	
	@Test
	public final void negationTest_double_literal(){
		NegationExpression<Double> negation = new NegationExpression<Double>(100.0);
		assertEquals(-100.0, negation.getValue(),EPSILON);
	}
	
	@Test
	public final void negationTest_double_variable(){
		VariableExpression<Double> var1 = new VariableExpression<Double>("Var1", 100.0);
		NegationExpression<Expression> negation = new NegationExpression<Expression>(var1);
		VariableExpression<Double> var2 = new VariableExpression<Double>("Var2",-100.0);
		EqualsExpression<Expression> equals1 = new EqualsExpression<Expression>(var2, negation);
		assert(equals1.getValue());		
	}
	
	
}
