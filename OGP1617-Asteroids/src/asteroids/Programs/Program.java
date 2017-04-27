package asteroids.Programs;

public class Program {
	
	public Program(Statement statement){
		//TODO implement constructor
	}
	
	public void excecuteProgram(){
		//TODO implement the execution of the program
	}
	
	public Statement getStatement(){
		return this.associatedStatement;
	}
	
	public void setStatement(Statement statement){
		this.associatedStatement = statement;
		statement.setProgram(this);
	}
	
	private Statement associatedStatement;
}
