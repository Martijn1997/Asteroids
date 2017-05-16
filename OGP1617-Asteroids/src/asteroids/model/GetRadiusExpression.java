package asteroids.model;

public class GetRadiusExpression extends UnaryExpression<Expression<?, WorldObject>, Double>{
	
	public GetRadiusExpression(Expression<?,WorldObject> operand){
		super(operand);
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
