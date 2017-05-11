package asteroids.model;

public class SelfExpression extends Expression<Ship,Ship> {
	
	public SelfExpression(){
		
	}
	
	public Ship evaluate(){
		return this.getStatement().getProgram().getShip();
	}
	
}
