package asteroids.model;

import java.util.ArrayList;
import exceptions.OutOfTimeException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import exceptions.BuilderException;

import exceptions.OutOfTimeException;

// the program sets the associations with the statements
public class Program {
	
	public Program(List<Function> functions, Statement statement){
		try{
			this.setStatement(statement);
			this.setFunctions(functions);
		}catch(BuilderException exc){
			this.setBuildFault();
		}
		
	}
	
	public List<Object> excecuteProgram(double deltaTime){

		if(this.getBuildFault()){
			throw new BuilderException();
		}
		
		this.setTime(deltaTime + this.getTime());
		if (!this.getStatement().isExecuted() && (this.getTime() >= 0.2)){
			try{
				this.getStatement().executeStatement();
			}catch (OutOfTimeException exc){
			}
		}

		if(this.getBuildFault()){
			throw new BuilderException();
		}

		if (this.getPrintedObjects().isEmpty())
			return null;
		else
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
	
	public boolean getBuildFault(){
		return this.buildFault;
	}
	
	public void setBuildFault(){
		this.buildFault = true;
	}
	
	private boolean buildFault = false;
	
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
			try{
			statement.setProgram(this);
			}catch (IllegalArgumentException exc){
				throw new BuilderException();
			}catch (IllegalStateException exc){
				throw new BuilderException();
			}
		} else{
			throw new BuilderException();
		} 
	}
	
	/**
	 * variable that stores the associatedStatement
	 */
	private Statement associatedStatement;
	
	
	public List<Function> getFunctions(){
		//System.out.print(this.functions);
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
				throw new BuilderException();
			}
		}
		HashSet<Function> temp_set = new HashSet<Function>(functions);
		if(temp_set.size() != functions.size()){
			throw new BuilderException();
		}
		this.functions.addAll(functions);
		for(Function function: functions){
			function.setProgram(this);
		}
		
		System.out.println(functions.size());
	}
	
	public boolean canHaveAsFunction(Function function){
		if(function != null /*&&function.getProgram()==this*/){
			System.out.println("I was here");
			System.out.println(!this.containsGlobalVariable(function.getFunctionName())&&!this.containsUninitGlobal(function.getFunctionName()));
			return (!this.containsGlobalVariable(function.getFunctionName())&&!this.containsUninitGlobal(function.getFunctionName()));
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
		if(!this.containsUninitGlobal(name)&&(this.containsGlobalVariable(name)&&(!variable.evaluate().getClass().equals(this.getGlobals().get(name).evaluate().getClass())))){
			throw new IllegalArgumentException();
		}
		this.getGlobals().put(name, variable);
		if(this.containsGlobalVariable(name)){
			this.getUninitGlobals().remove(name);
		}
	}
	
	/**
	 * removes global variables based on provided name
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
	
	protected Set<String> getUninitGlobals(){
		return this.uninit_globals;
	}
	
	public void addUninitGlobal(String name){
		if(!this.containsGlobalVariable(name)){
			this.uninit_globals.add(name);
		}else{
			throw new IllegalArgumentException();
		}
	}
	
	public boolean containsUninitGlobal(String name){
		return this.getUninitGlobals().contains(name);
	}
	
	private Set<String> uninit_globals = new HashSet<String>();
	
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
