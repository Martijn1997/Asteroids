package asteroids.Programs;

import asteroids.model.WorldObject;
import be.kuleuven.cs.som.annotate.*;

public class VariableExpression<T> extends BasicExpression<T> {
	
	/**
	 * constructor for a variable
	 * @param varName
	 * @param variable
	 */
	public VariableExpression(String varName, T variable){
		super(varName);
		if(!isValidVarriable(variable)){
			throw new IllegalArgumentException();
		}
		this.setValue(variable);
	}
	
	/**
	 * Basic getter for values of type T
	 */
	@Basic
	public T getValue(){
		return this.varValue;
	}
	
	/**
	 * Basic setter for values of type T
	 */
	@Basic
	public void setValue(T value){
		this.varValue = value;
	}
	
	public boolean isValidVarriable(T value){
		return value instanceof Double || value instanceof Boolean || value instanceof WorldObject;
	}
	
	/**
	 * variable that stores the varValue of VarriableExpression
	 */
	private T varValue;
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VariableExpression other = (VariableExpression) obj;
		if (this.getValue() == null) {
			if (other.getValue() != null)
				return false;
		} else if (!this.getValue().equals(other.getValue()))
			return false;
		return true;
	}

}
