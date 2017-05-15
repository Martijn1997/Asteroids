package asteroids.model;
//TODO ask TA if this is good practice?
//public class MultiplicationExpression extends BinaryExpression<Expression<?, Double>, Double>

public class MultiplicationExpression extends BinaryExpression<Expression<?, Double>, Double>{
	
	public MultiplicationExpression(Expression<?, Double> leftOperand, Expression<?, Double> rightOperand){
		super(leftOperand, rightOperand);
	}
	
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
		return leftResult * rightResult;
		
	}
	
	@Override
	public String getOperatorString(){
		return "*";
	}
	
//	public boolean isValidOperand(T operand){
//		return super.isValidOperand(operand)&&correctReturnType(operand);
//	}

}
