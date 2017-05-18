package asteroids.model;

import asteroids.part3.programs.SourceLocation;
import exceptions.OutOfTimeException;

public class FireStatement extends ActionStatement {
	
	public FireStatement(SourceLocation sourceLocation){
		super( sourceLocation);
	}

	@Override
	public void executeStatement() throws OutOfTimeException{
		super.executeStatement();
		if((this.getProgram().getLastStatement() == this) || (this.getProgram().getLastStatement() == null)){
			this.getProgram().setTime(this.getProgram().getTime() - 0.2);
			Ship self = this.getProgram().getShip();
			self.fireBullet();
		}
	}

}