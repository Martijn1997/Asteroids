package asteroids.Programs;

import asteroids.model.WorldObject;
import be.kuleuven.cs.som.annotate.*;

public abstract class BasicExpression<T> implements Expression<T,T>{
	
	/**
	 * Constructor for a BasicExpression
	 * @param 	name
	 * @post	sets the name of the BasicExpression to name
	 * 			| new.getName() = name
	 */
	@Model @Raw
	protected BasicExpression(String name, Statement statement){
		this.name = name;
		this.setStatement(statement);
	}
	
	/**
	 * basic getter for the value of the basic expression
	 * @return
	 */
	public abstract T evaluate();
	
	/**
	 * basic getter for the name of the basicExpression
	 * @return
	 */
	public String getName(){
		return this.name;
	}
	
	
	/**
	 * checker if the current operand is valid
	 * @param operand
	 * @return true if and only if the expression can have the operand as operand
	 */
	public boolean canHaveAsValue(T value){
		if(value!=null&&(value instanceof Double || value instanceof Boolean || value instanceof WorldObject || value instanceof Expression)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * variable that stores the variableName
	 */
	private final String name;
	
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
	
	private Statement associatedStatement;
}
