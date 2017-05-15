package asteroids.model;

// return type is a double, the needed input is an expression with a double return type
public class SmallerThanExpression extends BinaryExpression<Expression<?, Double>, Boolean>{
	
	/**
	 * Constructor for a smaller than expression
	 * @param leftOperand
	 * @param rightOperand
	 * @effect binaryExpression(leftOperand, rightOperand)
	 */
	public SmallerThanExpression(Expression<?, Double> leftOperand, Expression<?, Double> rightOperand){
		super(leftOperand, rightOperand);
	}
	
	
	/**
	 * returns true if and only if the left operand is smaller than the right operand
	 */
	public Boolean evaluate(){
		Expression<?,?> leftOperand = this.getLeftOperand();
		Expression<?,?> rightOperand = this.getRightOperand();
		
		Double leftResult;
		Double rightResult;
		
		if(leftOperand instanceof VariableExpression){
			leftResult = (Double) ((VariableExpression)leftOperand).evaluate().evaluate();
		}else{
			leftResult = (Double) leftOperand.evaluate();
		}
		
		if(rightOperand instanceof VariableExpression){
			rightResult =	(Double) ((VariableExpression)rightOperand).evaluate().evaluate();
		}else{
			rightResult = (Double) rightOperand.evaluate();
		}
		
		return leftResult<rightResult;
	}
	
	@Override
	public String getOperatorString(){
		return "<";
	}
}
