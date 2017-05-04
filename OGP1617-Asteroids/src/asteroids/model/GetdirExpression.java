package asteroids.model;


public class GetdirExpression implements Expression<WorldObject, Vector2D>{
	
	public GetdirExpression(Statement statement){
		this.setStatement(statement);
	}
	
	public Vector2D evaluate(){	
		return this.getStatement().getProgram().getShip().getVelocity();
	}
	
	public void setStatement(Statement statement){
		this.associatedStatement = statement;
	}
	
	public Statement getStatement(){
		return this.associatedStatement;
	}
	
	private Statement associatedStatement;
}
