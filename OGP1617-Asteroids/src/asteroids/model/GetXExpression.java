package asteroids.model;

import asteroids.part3.programs.SourceLocation;

public class GetXExpression extends UnaryExpression<Expression<?, WorldObject>, Double>{
	
	/**
	 * constructor for the getX expression
	 * @param operand
	 */
	public GetXExpression(Expression<?,WorldObject> operand,SourceLocation sourceLocation){
		super(operand,  sourceLocation);
	}
	
	/**
	 * returns the x position of the provided ship
	 */
	public Double evaluate(){
		if(this.getOperand().evaluate() == null){
			throw new IllegalArgumentException();
		}
		return ((WorldObject) this.operandEvaluated()).getXPosition();
	}
	
	@Override
	public String getOperatorString(){
		return "getx";
	}
}
