package asteroids.model;

import exceptions.OutOfTimeException;

public abstract class ActionStatement extends Statement{
	
	@Override
	public boolean canHaveAsProgram(Program program){
		if (this.getProgram() == null)
			return true;
		else
			return false;
	}
	
	@Override
	public void executeStatement() throws OutOfTimeException{
		if (this.getProgram().getTime() <= 0.2)
			throw new OutOfTimeException();
		else{
			this.getProgram().setTime(this.getProgram().getTime() - 0.2);
			super.executeStatement();
		}
	}
	
}
