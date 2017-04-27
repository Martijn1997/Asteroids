package asteroids.Programs;

import asteroids.model.WorldObject;

public class GetRadiusExpression extends UnaryExpression<Expression<?, WorldObject>, Double>{
	
	public GetRadiusExpression(Expression<?,WorldObject> operand){
		super(operand);
	}
		
	public Double evaluate(){
		return this.getOperand().evaluate().getRadius();
	}
}
