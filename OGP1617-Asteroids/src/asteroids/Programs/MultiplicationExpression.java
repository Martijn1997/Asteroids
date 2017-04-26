package asteroids.Programs;
//TODO ask TA if this is good practice?
//public class MultiplicationExpression extends BinaryExpression<Expression<?, Double>, Double>

public class MultiplicationExpression extends BinaryExpression<Expression<?, Double>, Double>{
	
	public MultiplicationExpression(Expression<?, Double> leftOperand, Expression<?, Double> rightOperand){
		super(leftOperand, rightOperand);
	}
	
	public Double getValue(){
		Expression<?, Double> leftOperand = this.getLeftOperand();
		Expression<?, Double> rightOperand = this.getRightOperand();
		
		return ((Expression<?,Double>) leftOperand).getValue() * ((Expression<?, Double>) rightOperand).getValue();
		
	}
	
//	public boolean isValidOperand(T operand){
//		return super.isValidOperand(operand)&&correctReturnType(operand);
//	}

}
