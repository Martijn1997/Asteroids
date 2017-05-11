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
	
	private Program associatedProgram;
}

