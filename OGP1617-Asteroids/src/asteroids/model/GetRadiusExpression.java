package asteroids.model;

import asteroids.part3.programs.SourceLocation;

public class GetRadiusExpression extends UnaryExpression<Expression<?, WorldObject>, Double>{
	
	public GetRadiusExpression(Expression<?,WorldObject> operand, SourceLocation sourceLocation){
		super(operand, sourceLocation);
	}
		
	public Double evaluate(){
		if(this.getOperand().evaluate() == null){
			throw new IllegalArgumentException();
		}
		return ((WorldObject) this.operandEvaluated()).getRadius();
	}
	
	@Override
	public String getOperatorString(){
		return "getradius";
	}
}
