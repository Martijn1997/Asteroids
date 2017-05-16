package asteroids.model;

import exceptions.OutOfTimeException;

public class ThrustOnStatement extends ActionStatement {
	
	public ThrustOnStatement(){
		super();
	}

	@Override
	public void executeStatement() throws OutOfTimeException{
		super.executeStatement();
		if((this.getProgram().getLastStatement() == this) || (this.getProgram().getLastStatement() == null)){
			this.getProgram().setTime(this.getProgram().getTime() - 0.2);
			Ship self = this.getProgram().getShip();
			self.thrustOn();
			this.getProgram().setLastStatement(null);
		}
	}
}
