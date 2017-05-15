package asteroids.model;

import exceptions.OutOfTimeException;

public class SkipStatement extends ActionStatement {
	
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