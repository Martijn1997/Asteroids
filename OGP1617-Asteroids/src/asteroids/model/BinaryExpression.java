package asteroids.model;


import be.kuleuven.cs.som.annotate.*;

public abstract class BinaryExpression<T,R> implements Expression<T,R> {
	
	/**
	 * Constructor for a binary expression
	 * @param leftOperand
	 * @param rightOperand
	 */
	@Model @Raw
	protected BinaryExpression(T leftOperand, T rightOperand, Statement statement) throws IllegalArgumentException{
		this.setLeftOperand(leftOperand);
		this.setRightoperand(rightOperand);
		this.setStatement(statement);
	}
	
	/**
	 * @return the left operand of the binaryExpression
	 */
	public T getLeftOperand(){
		return this.leftOperand;
	}
	/**
	 * @return the right operand of the binaryExpression
	 */
	public T getRightOperand(){
		return this.rightOperand;
	}
	
	/**
	 * basic setter for the left operand
	 * @param 	operand
	 * @post 	the value of the left operand is set to leftOperand
	 * 			|new.getLeftOperand() == leftOperand
	 * @throws	IllegalArgumentException
	 */
	@Model @Basic @Raw
	protected void setLeftOperand(T operand) throws IllegalArgumentException{
		if(!isValidOperand(operand)){
			throw new IllegalArgumentException();
		}
		this.leftOperand = operand;
	}
	
	/**
	 * Basic setter for the right operand
	 * @param 	operand
	 * @post	the value of the right operand is set to rightOperand
	 * 			| new.getRightOperand() == rightOperand
	 @throws	IllegalArgumentException
	 */
	@Model @Basic @Raw
	protected void setRightoperand(T operand){
		if(!isValidOperand(operand)){
			throw new IllegalArgumentException();
		}
		this.rightOperand = operand;
	}
	
	/**
	 * checker if the current operand is valid
	 * @param operands
	 * @return true if and only if the expression can have the operand as operand
	 */
	public boolean isValidOperand(T operand){
		if(operand instanceof Double || operand instanceof Boolean || operand instanceof WorldObject || operand instanceof Expression ){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Basic getter for a statement
	 */
	public Statement getStatement(){
		return this.associatedStatement;
	}
	
	/**
	 * Basic setter for a associated statement
	 * @param statement
	 */
	private void setStatement(Statement statement){
		this.associatedStatement = statement;
	}
	
	
	private T leftOperand;
		
	private T rightOperand;
	
	private Statement associatedStatement;

}
