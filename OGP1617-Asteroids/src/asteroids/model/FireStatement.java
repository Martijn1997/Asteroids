package asteroids.model;

public class FireStatement extends ActionStatement {
	
	public FireStatement(){
		super();
	}

	@Override
	public void executeStatement() {
		super.executeStatement();
		Ship self = this.getProgram().getShip();
		self.fireBullet();
	}

}