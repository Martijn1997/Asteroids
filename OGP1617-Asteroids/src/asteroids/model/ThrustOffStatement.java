package asteroids.model;

import exceptions.OutOfTimeException;

public class ThrustOffStatement extends ActionStatement {
	
	public ThrustOffStatement(){
		super();
	}
	
	@Override
	public void executeStatement() throws OutOfTimeException{
		super.executeStatement();
		if((this.getProgram().getLastStatement() == this) || (this.getProgram().getLastStatement() == null)){
			this.getProgram().setTime(this.getProgram().getTime() - 0.2);
			Ship self = this.getProgram().getShip();
			self.thrustOff();
			this.getProgram().setLastStatement(null);
		}
	}

}