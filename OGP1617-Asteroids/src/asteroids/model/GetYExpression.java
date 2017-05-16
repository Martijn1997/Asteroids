package asteroids.model;

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
	public Double evaluate(){
		if(this.getOperand().evaluate() == null){
			throw new IllegalArgumentException();
		}
		return ((WorldObject) this.operandEvaluated()).getYPosition();
	}
	
	@Override
	public String getOperatorString(){
		return "gety";
	}
}
