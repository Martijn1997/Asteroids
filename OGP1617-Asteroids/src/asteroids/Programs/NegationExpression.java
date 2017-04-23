package asteroids.Programs;

public class NegationExpression<T> extends UnaryExpression<T,Double> {
	
	/**
	 * Basic constructor for a negation expression
	 * @param 	operand
	 * 			type double
	 * @effect 	constructs the unary expression of a negation
	 * 			|UnaryExpression(Operand)
	 */
	public NegationExpression(T operand){
		super(operand);
	}
	
	/**
	 * returns the value of the operand
	 * @return	the value of the operand
	 * 			|-this.getOperand()
	 */
	public Double getValue(){
		return  -(Double)this.getOperand();
	}

	
	/**
	 * Checks if the operand is valid
	 * @effect	the operand is valid if the type is double and is a valid unaryExpression operand
	 * 			| result == operand instanceof Double && super.isValidOperand(operand)
	 */			
	@Override
	public boolean isValidOperand(T operand){
		return (operand instanceof Double) && super.isValidOperand(operand);
	}

}
