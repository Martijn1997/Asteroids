package asteroids.model;

public class GetYExpression extends UnaryExpression<Expression<?, WorldObject>, Double>{
	/**
	 * constructor for the getY expression
	 * @param operand
	 */
	public GetYExpression(Expression<?,WorldObject> operand, Statement statement){
		super(operand, statement);
	}
	
	/**
	 * returns the Y position of the provided ship
	 */
	public Double evaluate(){
		return this.getOperand().evaluate().getYPosition();
	}
}
