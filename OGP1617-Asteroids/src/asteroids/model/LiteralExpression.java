package asteroids.model;

import asteroids.part3.programs.SourceLocation;

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
public class LiteralExpression<T> extends Expression<T,T> {
	
	public LiteralExpression(T value, SourceLocation sourceLocation) {
		super( sourceLocation);
		this.value = value;
	}
	
	/**
	 * checks if the literal is of type double, boolean or WorldObject
	 * @param value
	 * @return 	true if and only if the value is a boolean, double or WorldObject
	 * 			|result == value instanceof Double|| value instanceof Boolean || value instanceof WorldObject
	 */
	public boolean isValidValue(T value){
		if(value instanceof Double|| value instanceof Boolean || value instanceof WorldObject || value instanceof LiteralExpression){
			return true;
		}else{
			return false;
		}
	}
	
	public T evaluate() throws IllegalArgumentException{
		
		T value = this.getValue();
		
		if(isValidValue(value)){
			if(value instanceof LiteralExpression){
				return ((LiteralExpression<T>) value).evaluate();
			}else{
			return this.getValue();
			}
		}else{
			throw new IllegalArgumentException();
		}
	}
	
	private final T value;
	
	public T getValue(){
		return this.value;
	}
	
	@Override
	public String toString(){
		return value.toString();
				
	}
	
	public static LiteralExpression<?> generateLiteral(Object value, SourceLocation sourceLocation){
		//Check if the value is worldObject
		if(value instanceof WorldObject){
			return new LiteralExpression<WorldObject>((WorldObject) value,  sourceLocation);
		//Check if the value is Double
		}else if(value instanceof Double){
			return new LiteralExpression<Double>((Double) value,sourceLocation);
		//check if value is Boolean
		}else if(value instanceof Boolean){
			return new LiteralExpression<Boolean>((Boolean) value,  sourceLocation);
		//otherwise the value is of type literal
		}else{
			return generateLiteral(((LiteralExpression<?>) value).evaluate(),  sourceLocation);
		}
	}
	
}
