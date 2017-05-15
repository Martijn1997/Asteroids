package asteroids.model;

public class ThrustOnStatement extends ActionStatement {
	
	public ThrustOnStatement(){
		super();
	}

	@Override
	public void executeStatement() {
		super.executeStatement();
		Ship self = this.getProgram().getShip();
		self.thrustOn();;
	}
}
