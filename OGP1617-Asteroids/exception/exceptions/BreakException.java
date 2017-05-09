package exceptions;
import asteroids.model.WhileStatement;

public class BreakException extends RuntimeException{
	
	public BreakException(WhileStatement statement){
		this.statement = statement;
	}
	
	public WhileStatement getValue(){
		return this.statement;
	}
	
	private WhileStatement statement;
}
