package asteroids.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

// the program sets the associations with the statements
public class Program {
	
	public Program(List<Function> functions, Statement statement){
		this.setFunctions(functions);
		this.setStatement(statement);
	}
	
	public List<Object> excecuteProgram(double deltaTime){
		this.getStatement().executeStatement();
		this.setTime(deltaTime);
		return this.getPrintedObjects();
	}
	
	public double getTime(){
		return time;
	}
	
	public void setTime(double time){
		if(isValidTime(time)){
		this.time = time;
		}else{
			throw new IllegalArgumentException();
		}
	}
	
	public static boolean isValidTime(double time){
		return time>=0;
	}
	
	private double time;
	
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
	public void setStatement(Statement statement)throws IllegalArgumentException{
		// checks if the statement can be added to the program
		if(statement!= null&& statement.getProgram() == null&& statement.canHaveAsProgram(this)){
			this.associatedStatement = statement;
			statement.setProgram(this);
		} else{
			throw new IllegalArgumentException();
		} 
	}
	
	/**
	 * variable that stores the associatedStatement
	 */
	private Statement associatedStatement;
	
	
	public List<Function> getFunctions(){
		return this.functions;
	}
	
	/**
	 * set the function list to the provided functions
	 * @param functions
	 */
	//TODO check if there are no functions with the same name
	public void setFunctions(List<Function> functions){
		for(Function function: functions){
			if(!this.canHaveAsFunction(function)){
				throw new IllegalStateException();
			}
		}
		HashSet<Function> temp_set = new HashSet<Function>(functions);
		if(temp_set.size() != functions.size()){
			throw new IllegalArgumentException();
		}
		this.functions = functions;
		for(Function function: functions){
			function.setProgram(this);
		}
	}
	
	public boolean canHaveAsFunction(Function function){
		if(function != null && function.getProgram()==this){
			return !this.containsGlobalVariable(function.getFunctionName());
		}else{
			return false;
		}
	}
	
	/**
	 * List that stores all the functions associated with the program
	 */
	private List<Function> functions = new ArrayList<Function>();
	
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
	
	private Map<String, LiteralExpression<?>> globals = new HashMap<String, LiteralExpression<?>>();
	
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
	private Ship associatedShip;
	
	public List<Object> getPrintedObjects(){
		return this.printedObjects;
	}
	
	public void addPrintedObject(Object object){
		this.getPrintedObjects().add(object);
	}

	private List<Object> printedObjects = new ArrayList<Object>();

}
