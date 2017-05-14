package asteroids.model;

import exceptions.BreakException;

public class BreakStatement extends NormalStatement{
	public BreakStatement(){
		super();
	}
	
	//super.executeStatement() zodat flag wordt geraised
	public void executeStatement(){
		if(this.assocWhile != null){
			throw new BreakException(this.getWhileStatement());
		}else{
			throw new IllegalStateException();
		}
	}
	
	public WhileStatement getWhileStatement(){
		return this.assocWhile;
	}
	
	public void setWhileStatement(WhileStatement statement){
		this.assocWhile = statement;
	}
	
	private WhileStatement assocWhile;
}