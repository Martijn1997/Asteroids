package asteroids.Programs;

import asteroids.model.WorldObject;

public class GetXExpression extends UnaryExpression<Expression<?, WorldObject>, Double>{
	
	/**
	 * constructor for the getX expression
	 * @param operand
	 */
	public GetXExpression(Expression<?,WorldObject> operand){
		super(operand);
	}
	
	/**
	 * returns the x position of the provided ship
	 */
	public Double getValue(){
		return this.getOperand().getValue().getXPosition();
	}
}
