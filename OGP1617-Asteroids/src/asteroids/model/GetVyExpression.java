package asteroids.model;

public class GetVyExpression extends UnaryExpression<Expression<?, WorldObject>, Double>{
	
	/**
	 * constructor for the getVy expression
	 * @param operand
	 */
	public GetVyExpression(Expression<?,WorldObject> operand){
		super(operand);
	}
	
	/**
	 * returns the y velocity of the provided ship
	 */
	public Double evaluate(){
		return this.getOperand().evaluate().getYVelocity();
	}
	
	@Override
	public String getOperatorString(){
		return "getvy";
	}
}
