package asteroids.model;


public class GetdirExpression extends Expression<WorldObject, Double>{
	
	public GetdirExpression(){
		
	}
	
	public Double evaluate(){	
		Ship self;
		if(this.getStatement().getProgram() != null){
			self = this.getStatement().getProgram().getShip();
		}else if(this.getStatement() instanceof NormalStatement){
			if(((NormalStatement) this.getStatement()).getFunction() != null){
				self = ((NormalStatement) this.getStatement()).getFunction().getProgram().getShip();
			}else{
				throw new IllegalStateException();
			}
		}else{
			throw new IllegalStateException();
		}
		
		return self.getOrientation();
	}
	
	public void setStatement(Statement statement){
		this.associatedStatement = statement;
	}
	
	public Statement getStatement(){
		return this.associatedStatement;
	}
	
	private Statement associatedStatement;
	
	
}
