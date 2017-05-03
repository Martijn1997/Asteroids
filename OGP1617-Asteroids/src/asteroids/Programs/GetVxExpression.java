package asteroids.Programs;

import asteroids.model.WorldObject;

public class GetVxExpression extends UnaryExpression<Expression<?, WorldObject>, Double>{
	/**
	 * constructor for the getVx expression
	 * @param operand
	 */
	public GetVxExpression(Expression<?,WorldObject> operand, Statement statement){
		super(operand, statement);
	}
	
	/**
	 * returns the x velocity of the provided ship
	 */
	public Double evaluate(){
		return this.getOperand().evaluate().getXVelocity();
	}
}
