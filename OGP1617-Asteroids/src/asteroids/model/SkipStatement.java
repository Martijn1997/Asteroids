package asteroids.model;

import asteroids.part3.programs.SourceLocation;
import exceptions.OutOfTimeException;

public class SkipStatement extends ActionStatement {
	
	public SkipStatement(SourceLocation sourceLocation){
		super( sourceLocation);
	}
	
	@Override
	public void executeStatement() throws OutOfTimeException{
		if (this.getProgram().getTime() < 0.2){
			throw new OutOfTimeException(this);
		}
		if((this.getProgram().getLastStatement() == this) || (this.getProgram().getLastStatement() == null)){
			this.getProgram().setTime(this.getProgram().getTime() - 0.2);
			this.getProgram().setLastStatement(null);
		}
	}

}