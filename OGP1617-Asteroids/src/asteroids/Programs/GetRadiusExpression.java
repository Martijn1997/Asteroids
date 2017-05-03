package asteroids.Programs;

import asteroids.model.WorldObject;

public class GetRadiusExpression extends UnaryExpression<Expression<?, WorldObject>, Double>{
	
	public GetRadiusExpression(Expression<?,WorldObject> operand, Statement statement){
		super(operand, statement);
	}
		
	public Double evaluate(){
		return this.getOperand().evaluate().getRadius();
	}
}
