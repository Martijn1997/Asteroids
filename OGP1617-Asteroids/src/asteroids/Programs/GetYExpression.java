package asteroids.Programs;

import asteroids.model.WorldObject;

public class GetYExpression extends UnaryExpression<Expression<?, WorldObject>, Double>{
	/**
	 * constructor for the getY expression
	 * @param operand
	 */
	public GetYExpression(Expression<?,WorldObject> operand){
		super(operand);
	}
	
	/**
	 * returns the Y position of the provided ship
	 */
	public Double getValue(){
		return this.getOperand().getValue().getYPosition();
	}
}
