package asteroids.model;

import asteroids.part3.programs.SourceLocation;

public class GetdirExpression extends Expression<WorldObject, Double>{
	
	public GetdirExpression(SourceLocation sourceLocation){
		super(sourceLocation);
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
