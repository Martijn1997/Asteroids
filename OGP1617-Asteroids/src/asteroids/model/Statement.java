package asteroids.model;

public abstract class Statement {
	
	public Statement(Program program){
		//TODO implement constructor
	}
	
	protected Program getProgram(){
		return this.associatedProgram;
	}
	
	protected void setProgram(Program program){
		this.associatedProgram = program;
	}
	
	public abstract void executeStatement();
	
	private Program associatedProgram;
}
