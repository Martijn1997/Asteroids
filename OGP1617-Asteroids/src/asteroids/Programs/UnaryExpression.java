package asteroids.Programs;

import asteroids.model.WorldObject;
import be.kuleuven.cs.som.annotate.*;


public abstract class UnaryExpression<T,R> implements Expression<T,R> {
	
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
	protected UnaryExpression(T operand, Statement statement){
		this.setOperand(operand);
		this.setStatement(statement);
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
		if(operand!=null&&(operand instanceof Double || operand instanceof Boolean || operand instanceof WorldObject || operand instanceof Expression)){
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
	

	/**
	 * variable that stores the operand
	 */
	private T operand;
	
	
	private Statement associatedStatement;
}
