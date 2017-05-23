package exceptions;

import asteroids.model.Statement;

public class OutOfTimeException extends RuntimeException{

	public OutOfTimeException(Statement statement){
		this.setStatement(statement);
	}
	
	public Statement getStatement(){
		return this.statement;
	}
	
	public void setStatement(Statement statement){
		this.statement = statement;
	}
	
	private Statement statement;
}
