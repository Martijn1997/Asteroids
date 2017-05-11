package asteroids.model;

public class NullExpression extends Expression<WorldObject,WorldObject>{
	
	public NullExpression(){
		super();
	}
	
	public WorldObject evaluate(){
		return null;
	}
	
	public String toString(){
		return "null";
	}
	
}
