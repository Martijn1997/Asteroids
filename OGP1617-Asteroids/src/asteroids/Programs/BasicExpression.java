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
	protected BasicExpression(String name){
		this.name = name;
	}
	
	/**
	 * basic getter for the value of the basic expression
	 * @return
	 */
	public abstract T getValue();
	
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
}
