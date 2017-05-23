package asteroids.model;

import asteroids.part3.programs.SourceLocation;

public class GetVxExpression extends UnaryExpression<Expression<?, WorldObject>, Double>{
	/**
	 * constructor for the getVx expression
	 * @param operand
	 */
	public GetVxExpression(Expression<?,WorldObject> operand, SourceLocation sourceLocation){
		super(operand,  sourceLocation);
	}
	
	/**
	 * returns the x velocity of the provided ship
	 */
	public Double evaluate(){
		if(this.getOperand().evaluate() == null){
			throw new IllegalArgumentException();
		}
		return ((WorldObject) this.operandEvaluated()).getXVelocity();
	}
	
	@Override
	public String getOperatorString(){
		return "getvx";
	}
}
