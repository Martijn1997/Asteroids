package asteroids.model;

public abstract class Statement {
	
	public Statement(){
		//TODO implement constructor
	}
	
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

