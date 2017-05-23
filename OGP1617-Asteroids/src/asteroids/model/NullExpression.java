package asteroids.model;

import asteroids.part3.programs.SourceLocation;

public class NullExpression extends Expression<WorldObject,WorldObject>{
	
	public NullExpression(SourceLocation sourceLocation){
		super( sourceLocation);
	}
	
	public WorldObject evaluate(){
		return null;
	}
	
	public String toString(){
		return "null";
	}
	
}
