package asteroids.model;

/**
 * Class for a literal creation
 * @author Martijn &Flor
 *
 * @param <T>
 * 
 * @Invar 	the literal shall never change value during its lifetime
 * 
 * @Invar	The parameter T shall be of type Double, Boolean or WorldObject
 * 			| isValidValue(T value)
 */
public class LiteralExpression<T> implements Expression<T,T> {
	
	public LiteralExpression(T value, Statement statement) throws IllegalArgumentException{
		if(!isValidValue(value)){
			throw new IllegalArgumentException();
		}
		this.value = value;
		this.setStatement(statement);
	}
	
	/**
	 * checks if the literal is of type double, boolean or WorldObject
	 * @param value
	 * @return 	true if and only if the value is a boolean, double or WorldObject
	 * 			|result == value instanceof Double|| value instanceof Boolean || value instanceof WorldObject
	 */
	public boolean isValidValue(T value){
		if(value instanceof Double|| value instanceof Boolean || value instanceof WorldObject){
			return true;
		}else{
			return false;
		}
	}
	
	public T evaluate(){
		return this.value;
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
	
	private final T value;
	
	private Statement associatedStatement;
}
