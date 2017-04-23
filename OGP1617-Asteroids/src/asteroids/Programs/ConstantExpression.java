package asteroids.Programs;

public class ConstantExpression<T> extends BasicExpression<T>{
	
	/**
	 * Constructor for a constantExpression
	 * @param 	value
	 * 			the desired value
	 * @param 	name
	 * 			the name of the constant
	 * @effect	the name of the constant is set to the provided name
	 * 			|BasicExpression(name)
	 * @post	the constant value is set to value
	 * 			|new.getValue = value
	 * @throws 	IllegalArgumentException
	 * 			thrown if the value is not valid
	 * 			|canHaveAsValue(value)
	 */
	public ConstantExpression(String name, T value) throws IllegalArgumentException{
		super(name);
		if(!canHaveAsValue(value)){
			throw new IllegalArgumentException();
		}
		this.constantValue = value;
	}
	
	/**
	 * Basic getter for the constant value
	 */
	public T getValue(){
		return this.constantValue;
	}
	
	/**
	 * constant that stores the value of the constant
	 */
	private final T constantValue;

	/**
	 * checks if the provided object obj equals with the prime object
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConstantExpression other = (ConstantExpression) obj;
		if (this.getValue() == null) {
			if (other.getValue() != null)
				return false;
		} else if (!this.getValue().equals(other.getValue()))
			return false;
		return true;
	}
	
	

}
