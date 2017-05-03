package asteroids.Programs;

// return type is boolean and the nested expression needs to have a boolean as return type as well
public class NotExpression extends UnaryExpression<Expression<?,Boolean>, Boolean> {
	
	/**
	 * constructor for a NotExpression
	 * @param operand
	 * @effect UnaryExpression(operand)
	 */
	public NotExpression(Expression<?, Boolean> operand, Statement statement){
		super(operand, statement);
	}
	
	/**
	 * getter for the value of the operand
	 */
	public Boolean evaluate(){
		return !this.getOperand().evaluate();
	}
}
