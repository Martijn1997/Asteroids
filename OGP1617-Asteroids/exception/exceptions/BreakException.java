package exceptions;
import asteroids.Programs.WhileStatement;

public class BreakException extends RuntimeException{
	
	public BreakException(WhileStatement statement){
		this.statement = statement;
	}
	
	public WhileStatement getValue(){
		return this.statement;
	}
	
	private WhileStatement statement;
}
