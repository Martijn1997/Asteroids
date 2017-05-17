package asteroids.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.AlreadyInStackException;
import exceptions.BuilderException;
import exceptions.ReturnException;

public class Function {
	
	public Function(String functionName, Statement statement){
			this.setFunctionName(functionName);
			this.setStatement(statement);
		
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
	public LiteralExpression<?> evaluate(FunctionCallExpression expression){
		
		LiteralExpression<?> result = null;
		//push the provided call on the stack of the function
		this.pushStack(expression);

		try{
			this.getStatement().setFunction(this);
			this.getStatement().executeStatement();

		}catch (ReturnException returnVal){
			result = returnVal.getValue();
		}
		this.popStack();
		return result; 
	}
	
	/**
	 * Getter for the stack size, returns the amount of function calls on the stack
	 */
	public int getStackHeight(){
		return this.getStack().size();
	}
	
	/**
	 * Reads the top of the stack without popping the upper value
	 */
	public FunctionCallExpression readTopStack(){
		return this.getStack().get((this.getStack().size()-1));
	}
	
	/**
	 * Pushes new call on top of the stack
	 * if the call is already present throws an AlreadyInStackException
	 */
	public void pushStack(FunctionCallExpression expression) throws AlreadyInStackException{
		List<FunctionCallExpression> stack = this.getStack();
		if(!stack.contains(expression)){
			expression.setEvalArguments(expression.evaluateArguments());
			stack.add(expression);
		}else{
			throw new AlreadyInStackException();
		}

	}
	
	/**
	 * removes the latest call from the stack and returns it
	 */
	public FunctionCallExpression popStack(){
		FunctionCallExpression call =this.getStack().remove(this.getStack().size() -1);
		return call;
	}
	
	/**
	 * Returns the list containing the stack of the function
	 */
	private List<FunctionCallExpression> getStack(){
		return this.stack;
	}
	
	
	/**
	 * The stack that stores all the function calls
	 */
	private List<FunctionCallExpression> stack = new ArrayList<FunctionCallExpression>();
	
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
	public void setStatement(Statement statement) throws IllegalArgumentException{
		if(!this.canHaveAsStatement(statement)){
			this.setBuildError();
		}else{
			this.associatedStatement = (NormalStatement)statement;
		try{
		((NormalStatement) statement).setFunction(this);
		}catch (IllegalArgumentException exc){
			this.setBuildError();
		}
		}
	}
	
	/**
	 * checker if the provided statement can be set as a statement
	 * @param statement
	 * @return
	 */
	public boolean canHaveAsStatement(Statement statement){

		return statement != null &&(statement instanceof NormalStatement) && ((NormalStatement) statement).getFunction() == null && ((NormalStatement) statement).canHaveAsFunction(this) && (!(statement instanceof ActionStatement));
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
		if(program == null||!program.getFunctions().contains(this)||this.getBuildError()){
			throw new BuilderException();
		}
		this.associatedProgram = program;
	}
	
	private Program associatedProgram;
	
	protected Map<String, LiteralExpression<?>> getLocalVariables(){
		return this.readTopStack().getLocalScope();
		//return this.localVariables;
	}
	
	
//	protected Map<String, LiteralExpression<?>> getLocals(){
//		return this.localVariables;
//	}
	
	/**
	 * Adds local variable to the top function call in the stack
	 * @param name
	 * @param variable
	 */
	public void addLocalVariable(String name, LiteralExpression<?> variable){
		if(!canHaveAsLocalVar(variable)){
			throw new IllegalArgumentException();
		}
//		if(this.getStack().size() == 1){
//			this.localVariables.put(name, variable);
//		}

		this.readTopStack().addLocalScope(name, variable);
		
	}
	
	/**
	 * Checks if a local variable can be added to the local variables.
	 * @param variable
	 * @return
	 */
	//TODO check for right type if variable is already present
	public boolean canHaveAsLocalVar(LiteralExpression<?> variable){
		return (variable.evaluate() instanceof WorldObject || variable.evaluate() instanceof Double || variable.evaluate() instanceof Boolean);
		
	}
	
//	private Map<String, LiteralExpression<?>> localVariables = new HashMap<String, LiteralExpression<?>>();
	
	
	private NormalStatement associatedStatement;
	
	
	public boolean getBuildError(){
		return this.buildError;
	}
	
	public void setBuildError(){
		this.buildError = true;
	}
	
	private boolean buildError = false;
	
	public String toString(){
		return this.getFunctionName();
	}
	
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
