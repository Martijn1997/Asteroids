package asteroids.model;

import asteroids.part3.programs.SourceLocation;
import exceptions.OutOfTimeException;

public abstract class ActionStatement extends Statement{
	
	public ActionStatement(SourceLocation sourceLocation){
		super( sourceLocation);
	}
	
	@Override
	public boolean canHaveAsProgram(Program program){
		if(program == null)
			return false;
		else
			return true;
	}
	
	@Override
	public void executeStatement() throws OutOfTimeException{
		if (this.getProgram().getTime() < 0.2){
			throw new OutOfTimeException(this);
		}
	}
	
}