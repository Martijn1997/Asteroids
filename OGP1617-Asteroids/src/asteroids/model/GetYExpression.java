package asteroids.model;

import asteroids.part3.programs.SourceLocation;

public class GetYExpression extends UnaryExpression<Expression<?, WorldObject>, Double>{
	/**
	 * constructor for the getY expression
	 * @param operand
	 */
	public GetYExpression(Expression<?,WorldObject> operand, SourceLocation sourceLocation){
		super(operand,  sourceLocation);
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
