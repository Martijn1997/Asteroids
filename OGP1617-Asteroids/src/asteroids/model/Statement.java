package asteroids.model;

import asteroids.part3.programs.SourceLocation;

public abstract class Statement {
	
	public Statement(SourceLocation sourceLocation){
		this.setSourceLocation(sourceLocation);
		
	}
	
	
	protected SourceLocation getSourceLocation() {
		return sourceLocation;
	}

	protected void setSourceLocation(SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}


	private SourceLocation sourceLocation;
	
	protected Program getProgram(){
		return this.associatedProgram;
	}
	
	protected void setProgram(Program program){
		if(!canHaveAsProgram(program)){
			throw new IllegalArgumentException();
		}
		this.associatedProgram = program;
	}
	
	public abstract boolean canHaveAsProgram(Program program);
	
	public abstract void executeStatement();
	
//	public void setExecuted(){
//		this.executionFlag = true;
//	}
//	
//	public boolean isExecuted(){
//		return this.executionFlag;
//	}
//	
//	private boolean executionFlag = false;
	
	private Program associatedProgram;
}

