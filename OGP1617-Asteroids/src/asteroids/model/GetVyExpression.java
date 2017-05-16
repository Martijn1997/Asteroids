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
		if(this.getOperand().evaluate() == null){
			throw new IllegalArgumentException();
		}
		return ((WorldObject) this.operandEvaluated()).getYVelocity();
	}
	
	@Override
	public String getOperatorString(){
		return "getvy";
	}
}
