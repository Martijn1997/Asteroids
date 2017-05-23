package asteroids.model;

import asteroids.part3.programs.SourceLocation;

public class SelfExpression extends Expression<Ship,Ship> {
	
	public SelfExpression(SourceLocation sourceLocation){
		super( sourceLocation);
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
