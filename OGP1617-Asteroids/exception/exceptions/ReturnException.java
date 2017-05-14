package exceptions;

import asteroids.model.LiteralExpression;

/**
 * Class of exceptions used for return statements
 * @author Martijn & Flor
 *
 */
public class ReturnException extends RuntimeException {
	
	public ReturnException(LiteralExpression<?> value){
		this.value = value;
	}
	
	public LiteralExpression<?>getValue(){
		return this.value;
	}
	
	private final LiteralExpression<?> value;
}
