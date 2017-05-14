package asteroids.model;

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
	public Double evaluate(){
		if(this.getOperand().evaluate() == null){
			throw new IllegalArgumentException();
		}
		return this.getOperand().evaluate().getXPosition();
	}
	
	@Override
	public String getOperatorString(){
		return "getx";
	}
}
