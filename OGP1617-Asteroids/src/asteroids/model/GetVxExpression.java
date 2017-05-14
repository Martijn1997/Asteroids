package asteroids.model;

public class GetVxExpression extends UnaryExpression<Expression<?, WorldObject>, Double>{
	/**
	 * constructor for the getVx expression
	 * @param operand
	 */
	public GetVxExpression(Expression<?,WorldObject> operand){
		super(operand);
	}
	
	/**
	 * returns the x velocity of the provided ship
	 */
	public Double evaluate(){
		if(this.getOperand().evaluate() == null){
			throw new IllegalArgumentException();
		}
		return this.getOperand().evaluate().getXVelocity();
	}
	
	@Override
	public String getOperatorString(){
		return "getvx";
	}
}
