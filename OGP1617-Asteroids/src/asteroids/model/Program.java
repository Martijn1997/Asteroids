package asteroids.model;

import java.util.HashMap;
import java.util.Map;

public class Program {
	
	public Program(Statement statement){
		//TODO implement constructor
	}
	
	public void excecuteProgram(){
		//TODO implement the execution of the program
	}
	
	/**
	 * basic getter for the associated program
	 * @return
	 */
	public Statement getStatement(){
		return this.associatedStatement;
	}
	
	/**
	 * sets the associated statement to statement (most of the time a sequential statement)
	 * @param statement
	 */
	public void setStatement(Statement statement){
		this.associatedStatement = statement;
		statement.setProgram(this);
	}
	
	//TODO verify that the if statement effectively rejects non same type var's
	/**
	 * adds globals to the global list
	 * @param name
	 * @param variable
	 */
	public void addGlobalVariable(String name, LiteralExpression<?> variable){
		if(this.containsGlobalVariable(name)&&(!variable.evaluate().getClass().equals(this.getGlobals().get(name).evaluate().getClass()))){
			throw new IllegalArgumentException();
		}
		this.getGlobals().put(name, variable);
	}
	
	/**
	 * removes global variables based on name
	 * @param name
	 */
	public void removeGlobalVariable(String name){
		this.getGlobals().remove(name);
	}
	
	/**
	 * checks if there already exists a global variable with the given type
	 * @param name
	 * @return
	 */
	public boolean containsGlobalVariable(String name){
		return this.getGlobals().containsKey(name);
	}
	
	/**
	 * basic getter for the global values
	 * @return
	 */
	protected Map<String, LiteralExpression<?>> getGlobals(){
		return this.globals;
	}
	
	/**
	 * basic getter for the associated ship
	 * @return
	 */
	public Ship getShip(){
		return this.associatedShip;
	}
	
	public void setShip(Ship ship){
		this.associatedShip = ship;
		ship.setProgram(this);
	}
	
	private Statement associatedStatement;
	
	private Ship associatedShip;
	
	private Map<String, LiteralExpression<?>> globals = new HashMap<String, LiteralExpression<?>>();
}
