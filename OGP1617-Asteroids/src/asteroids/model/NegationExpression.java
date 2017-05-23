package asteroids.model;

import asteroids.part3.programs.SourceLocation;

public class NegationExpression extends UnaryExpression<Expression<?, Double>,Double> {
	
	/**
	 * Basic constructor for a negation expression
	 * @param 	operand
	 * 			type double
	 * @effect 	constructs the unary expression of a negation
	 * 			|UnaryExpression(Operand)
	 */
	public NegationExpression(Expression<?, Double> operand, SourceLocation sourceLocation){
		super(operand,  sourceLocation);
		
	}
	
	/**
	 * returns the value of the operand
	 * @return	the value of the operand
	 * 			|-this.getOperand()
	 */
	public Double evaluate(){
		// because the cast is checked in the isValid the warning may be surpressed
		return -(Double)this.operandEvaluated();
	}
	
	@Override
	public String getOperatorString(){
		return "-";
	}
	
//	@SuppressWarnings("unchecked")
//	@Override
//	public boolean isValidOperand(T operand){
//		if(!super.isValidOperand(operand)){
//			return false;
//		}else{
//			try{
//				//the operand is always an expression
//				// if the expression cannot be cast to type double the 
//				// operand is invalid
//				Double testVar = ((Expression<?, Double>) operand).getValue();
//				return true;
//			}catch (ClassCastException exc){
//				return false;
//			}
//		}
//	}

	
//	/**
//	 * Checks if the operand is valid
//	 * @effect	the operand is valid if the type is double and is a valid unaryExpression operand
//	 * 			| result == operand instanceof Double && super.isValidOperand(operand)
//	 */			
//	@Override
//	public boolean isValidOperand(T operand){
//		return (operand instanceof Double) || super.isValidOperand(operand);
//	}

}
