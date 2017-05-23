package asteroids.model;

import asteroids.part3.programs.SourceLocation;

// return type is boolean and the nested expression needs to have a boolean as return type as well
public class NotExpression extends UnaryExpression<Expression<?,Boolean>, Boolean> {
	
	/**
	 * constructor for a NotExpression
	 * @param operand
	 * @effect UnaryExpression(operand)
	 */
	public NotExpression(Expression<?, Boolean> operand, SourceLocation sourceLocation){
		super(operand, sourceLocation);
	}
	
	/**
	 * getter for the value of the operand
	 */
	public Boolean evaluate(){
		return !(Boolean)this.operandEvaluated();
	}
	
	@Override
	public String getOperatorString(){
		return "!";
	}
}
