package asteroids.model;

public class ThrustOffStatement extends ActionStatement {
	
	public ThrustOffStatement(){
		super();
	}
	
	@Override
	public void executeStatement() {
		super.executeStatement();
		Ship self = this.getProgram().getShip();
		self.thrustOff();
	}

}
