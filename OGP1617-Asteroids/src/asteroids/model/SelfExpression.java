package asteroids.model;

public class SelfExpression extends Expression<Ship,Ship> {
	
	public SelfExpression(){
		
	}
	
	public Ship evaluate(){
		Statement statement = this.getStatement();
		Program program = statement.getProgram();
		return program.getShip();
	}
	
	public String toString(){
		return "Self";
	}
	
}
