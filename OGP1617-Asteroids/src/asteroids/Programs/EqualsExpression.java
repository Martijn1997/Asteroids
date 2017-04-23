package asteroids.Programs;

public class EqualsExpression<T> extends BinaryExpression<T, Boolean>{
	
	
	public EqualsExpression(T leftOperand, T rightOperand){
		super(leftOperand, rightOperand);
	}
	
//	/**
//	 * canHaveAsOperand (for later restrictions)
//	 */
//	public boolean canHaveAsOperand(T operand){
//		return true;
//	}
	
	
	public Boolean getValue(){
		T leftOperand = this.getLeftOperand();
		T rightOperand = this.getRightOperand();
		
		if(leftOperand == null || rightOperand == null){
			return false;
		}//check if the left and right operand are equal
		//TODO write function to check if the operands are equal (literal-expr, literal-literal and expr-expr)
		else if(leftOperand instanceof Expression && rightOperand instanceof Expression){
			return ((Expression) leftOperand).getValue()==((Expression<T, Boolean>) rightOperand).getValue()
		}
		
	}
	
	

}
