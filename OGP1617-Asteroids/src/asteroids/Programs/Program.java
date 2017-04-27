package asteroids.Programs;

import java.util.HashMap;
import java.util.Map;

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
	
	public void addGlobalVariable(String name, VariableExpression<?> variable){
		this.getGlobals().put(name, variable);
	}
	
	public void removeGlobalVariable(String name){
		this.getGlobals().remove(name);
	}
	
	private Map<String, VariableExpression<?>> getGlobals(){
		return this.globals;
	}
	
	private Statement associatedStatement;
	private Map<String, VariableExpression<?>> globals = new HashMap<String, VariableExpression<?>>();
}
