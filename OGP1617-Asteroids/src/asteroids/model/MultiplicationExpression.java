package asteroids.model;
//TODO ask TA if this is good practice?
//public class MultiplicationExpression extends BinaryExpression<Expression<?, Double>, Double>

public class MultiplicationExpression extends BinaryExpression<Expression<?, Double>, Double>{
	
	public MultiplicationExpression(Expression<?, Double> leftOperand, Expression<?, Double> rightOperand){
		super(leftOperand, rightOperand);
	}
	
	public Double evaluate(){
		Expression<?, Double> leftOperand = this.getLeftOperand();
		Expression<?, Double> rightOperand = this.getRightOperand();
		
		return ((Expression<?,Double>) leftOperand).evaluate() * ((Expression<?, Double>) rightOperand).evaluate();
		
	}
	
	@Override
	public String getOperatorString(){
		return "*";
	}
	
//	public boolean isValidOperand(T operand){
//		return super.isValidOperand(operand)&&correctReturnType(operand);
//	}

}
