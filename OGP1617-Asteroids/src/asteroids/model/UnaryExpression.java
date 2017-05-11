package asteroids.model;

import be.kuleuven.cs.som.annotate.*;


public abstract class UnaryExpression<T extends Expression<?,?>,R> extends Expression<T,R> /*implements SubExprExpression*/ {
	
	/**
	 * Constructor for a unary expression
	 * @param	operand
	 * 			the desired operand
	 * @effect	the value for operand is set to operand
	 * 			|setOperand(operand)
	 * @throws 	IllegalArgumentException
	 * 			thrown if the operand is not valid
	 * 			| !isValidOperand(operand)
	 */
	@Model @Raw
	protected UnaryExpression(T operand){
		this.setOperand(operand);
	}
	
	/**
	 * Basic getter for the operand
	 */
	@Basic @Raw
	public T getOperand(){
		return this.operand;
	}
	
	/**
	 * basic setter for an operand
	 * @param 	operand
	 * @post	the value of the operand is set to operand
	 * 			|new.getOperand() == operand
	 */
	public void setOperand(T operand){
		if(!isValidOperand(operand)){
			throw new IllegalArgumentException();
		}
		
		this.operand = operand;
	}
	
	/**
	 * basic checker if the operand is valid
	 * @param operand
	 * @return
	 */
	public boolean isValidOperand(T operand){
		return operand instanceof Expression;

	}

	
	/**
	 * variable that stores the operand
	 */
	private T operand;
	
	
	/**
	 * Sets the statement of the operand to statement (cascade)
	 * set the associated statement to statement
	 */
	@Override
	protected void setStatement(Statement statement){
		this.getOperand().setStatement(statement);
		super.setStatement(statement);
	}
	
	/**
	 * scans if the operands have a parameter as expression
	 */
//	@Override
//	public void scanForParameter(Function function){
//		if(this.getOperand() instanceof ParameterExpression){
//			function.addParameter(((ParameterExpression) this.getOperand()).getName(), null);
//		}else if(this.getOperand() instanceof SubExprExpression){
//				((SubExprExpression) this.getOperand()).scanForParameter(function);		
//		}
//	}
	
	@Override
	public String toString(){
		
		String string = getOperatorString() + "( " + getOperand().toString() + ") ";
		
		return string;
		
	}
	
	public abstract String getOperatorString();
	
	
}
