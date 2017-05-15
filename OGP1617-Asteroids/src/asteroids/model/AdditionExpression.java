package asteroids.model;

public class AdditionExpression extends BinaryExpression<Expression<?, Double>, Double>{
	
	/**
	 * Constructor for the addition expression class
	 * @param leftOperand
	 * @param rightOperand
	 * 
	 * @effect BinaryExpression(leftOperand,rightOperand)
	 */
	public AdditionExpression(Expression<?, Double> leftOperand, Expression<?, Double> rightOperand){
		super(leftOperand, rightOperand);
	}
	
	/**
	 * returns the sum of the left operand and the right operand of the expression.
	 */
	public Double evaluate(){
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
		
		return leftResult + rightResult;
	}
	
	@Override
	public String getOperatorString(){
		return "+";
	}
	
//	/**
//	 * checks if the operand is valid
//	 * @param operand
//	 * @return 	true if the operand is a valid binary expression and
//	 * 			has a double return type
//	 */
//	@SuppressWarnings("unchecked") @Override
//	public boolean isValidOperand(T operand){
//		
//		if(!super.isValidOperand(operand)){
//			return false;
//		}else{
//			try{
//				double myVar = ((Expression<?, Double>) operand).getValue();
//				return true;
//			}catch (ClassCastException exc){
//				return false;
//			}
//		}
//	}
}
