package asteroids.model;

import asteroids.part3.programs.SourceLocation;
import be.kuleuven.cs.som.annotate.*;

public abstract class BasicExpression<T> extends Expression<T,T>{
	
	/**
	 * Constructor for a BasicExpression
	 * @param 	name
	 * @post	sets the name of the BasicExpression to name
	 * 			| new.getName() = name
	 */
	@Model @Raw
	protected BasicExpression(String name, SourceLocation sourceLocation){
		super(sourceLocation);
		this.name = name;
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
	
	@Override
	public String toString(){
		return this.getName();
	}
	
}
