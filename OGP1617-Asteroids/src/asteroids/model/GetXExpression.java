package asteroids.model;

public class GetXExpression extends UnaryExpression<Expression<?, WorldObject>, Double>{
	
	/**
	 * constructor for the getX expression
	 * @param operand
	 */
	public GetXExpression(Expression<?,WorldObject> operand, Statement statement){
		super(operand, statement);
	}
	
	/**
	 * returns the x position of the provided ship
	 */
	public Double evaluate(){
		return this.getOperand().evaluate().getXPosition();
	}
}
