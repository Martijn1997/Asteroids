package asteroids.model;

import java.util.HashMap;
import java.util.Map;

import exceptions.ReturnException;

public class Function {
	
	public Function(String functionName, NormalStatement statement){
		
	}
	
	/**
	 * Basic getter for the function name
	 */
	public String getFunctionName(){
		return this.functionName;
	}
	
	/**
	 * Basic setter for the function name
	 * @param name
	 */
	public void setFunctionName(String name){
		this.functionName = name;
	}
	
	/**
	 * Variable that stores the function name
	 */
	private String functionName;
	
	/**
	 * Evaluates the function and returns a LiteralExpression containing the return value
	 */
	public LiteralExpression<?> evaluate(){
		try{
			this.getStatement().executeStatement();
		}catch (ReturnException returnVal){
			return returnVal.getValue();
		}
		return null; // return null if no value was returned (default case)
	}
	
	/**
	 * Basic getter for the statement
	 * @return
	 */
	public NormalStatement getStatement(){
		return this.associatedStatement;
	}
	
	
	// semantics: the upper statement has a bidirectional relation to the function
	// all the nested statements have a unidirectional relation to the function
	/**
	 * setter for the statement part of the function
	 * @param statement
	 * 
	 * @Post 	|new.getStatement = statement
	 * 
	 * @throws 	IllegalArgumentException
	 * 			thrown if the function cannot have the statement as statement
	 */
	public void setStatement(NormalStatement statement) throws IllegalArgumentException{
		if(!this.canHaveAsStatement(statement)){
			throw new IllegalArgumentException();
		}else{
			this.associatedStatement = statement;
			statement.setFunction(this);
		}
	}
	
	/**
	 * checker if the provided statement can be set as a statement
	 * @param statement
	 * @return
	 */
	public boolean canHaveAsStatement(NormalStatement statement){
		return statement != null && statement.getFunction() == null && statement.canHaveAsFunction(this);
			
	}
	
	/**
	 * return the associated program
	 * @return
	 */
	public Program getProgram(){
		return this.associatedProgram;
	}
	
	/**
	 * set the program associated with the function
	 */
	protected void setProgram(Program program){
		if(program == null||!program.getFunctions().contains(this)){
			throw new IllegalArgumentException();
		}
		this.associatedProgram = program;
	}
	
	private Program associatedProgram;
	
	protected Map<String, LiteralExpression<?>> getLocalVariables(){
		return this.localVariables;
	}
	
	public void addLocalVariable(String name, LiteralExpression<?> variable){
		if(!canHaveAsLocalVar(variable)){
			throw new IllegalArgumentException();
		}
		this.getLocalVariables().put(name, variable);
	}
	
	public boolean canHaveAsLocalVar(LiteralExpression<?> variable){
		return (variable.evaluate() instanceof WorldObject || variable.evaluate() instanceof Double || variable.evaluate() instanceof Boolean);
		
	}
	
	private Map<String, LiteralExpression<?>> localVariables = new HashMap<String, LiteralExpression<?>>();
	
	
	private NormalStatement associatedStatement;
	
//	/**
//	 * adds a parameter to the parameter Map
//	 * @param name
//	 * @param expression
//	 */
//	public void addParameter(String name, Expression<?,?> expression){
//		this.getParameters().put(name, expression);
//	}
//	
//	/**
//	 * returns the parameters
//	 * @return
//	 */
//	private Map<String, Expression<?,?>> getParameters(){
//		return this.parameters;
//	}
//	
//	private Map<String, Expression<?,?>> parameters = new HashMap<String, Expression<?,?>>();
	
}
