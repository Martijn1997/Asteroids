package asteroids.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asteroids.part3.programs.SourceLocation;
import exceptions.AlreadyInStackException;

//Important note on the working principles:
/*
 * 1. when creating a function, the arguments are supplied in general form
 * 2. upon evaluation of the expression, the expressions are evaluated and filled in
 * 3. when a recursive call is issued, the functionc all is pushed on the call stack within the the function class
 * 	  The new arguments of the call are changed but the evaluated arguments remain unchanged until the call is pushed on the stack
 *    meaning that if the same call object gets new arguments filled in the effective arguments remain unchainged
 * 4. if the call is already on the stack, create a new function call with the new 'filled in' arguments of the 
 * 	  already-in-stack function call (with the new values)
 * 5. evaluate the newly created function call.
 */
public class FunctionCallExpression extends Expression<Expression<?,?>,LiteralExpression<?>>{
	
	public FunctionCallExpression(String functionName, List<Expression<?,?>> actualArgs, SourceLocation sourceLocation){
		super( sourceLocation);
		this.setFunctionName(functionName);
		this.setArguments(actualArgs);
	}
	
	/**
	 * getter for the function name
	 */
	public String getFunctionName(){
		return this.functionName;
	}
	
	/**
	 * setter for the function name
	 */
	public void setFunctionName(String functionName){
		this.functionName = functionName;
	}
	
	/**
	 * variable that stores the function name
	 */
	private String functionName;
	
	
	public LiteralExpression<?> evaluate()throws IndexOutOfBoundsException, IllegalArgumentException{
		Function function = this.getFunction();
		try{
			return function.evaluate(this);
			//if the function call is already present in the stack make a new call.
		}catch( AlreadyInStackException exc){
			//create new FunctionCall
			FunctionCallExpression newCall = new FunctionCallExpression(this.getFunctionName(),this.getArguments(), this.getSourceLocation());
			newCall.setStatement(this.getStatement());
			//newCall.setLocalScope(function.getLocals());
			return newCall.evaluate();
		}
		
	}
	
	/**
	 * stores the local variables used in function call
	 * @return
	 */
	protected Map<String, LiteralExpression<?>> getLocalScope(){
		return this.localScope;
	}
	
	/**
	 * Adds variable to the local scope
	 */
	public void addLocalScope(String name, LiteralExpression<?> variable){
		this.getLocalScope().put(name, variable);
	}
	
	/**
	 * set the local scope given the map of literal expressions
	 */
	public void setLocalScope(Map<String, LiteralExpression<?>> map){
		this.getLocalScope().clear();
		
		for(String name: map.keySet()){
			this.getLocalScope().put(name, map.get(name));
		}
	}
	
	/**
	 * Map that stores the local scope
	 */
	private Map<String, LiteralExpression<?>> localScope= new HashMap<String, LiteralExpression<?>>();

	
	public Function getFunction(){
		List<Function> functions = null;
		
		Statement statement = this.getStatement();
		if(statement instanceof NormalStatement){
			// if the current statement is a normal statement in a function
			if(((NormalStatement)statement).getProgram() == null){
				Function assocFunct = ((NormalStatement)statement).getFunction();
				Program program = assocFunct.getProgram();
				functions = program.getFunctions();
			}else{
				Program program = statement.getProgram();
				functions = program.getFunctions();
			}
		}else{
			Program program = statement.getProgram();
			functions = program.getFunctions();
		}
//		List<Function> functions = this.getStatement().getProgram().getFunctions();
		for(Function function: functions){
			if(function.getFunctionName().equals(this.getFunctionName())){
				this.associatedFunction = function;
				return function;
			}
		}
		throw new IllegalArgumentException("Cannot set function, no function with same name found in program");
	}
	
	private Function associatedFunction;
	
	@Override
	public void setStatement(Statement statement){
		for(Expression<?,?> expression: this.getArguments()){
			expression.setStatement(statement);
		}
		
		super.setStatement(statement);
	}
	
	
	protected List<Expression<?,?>> getArguments(){
		return this.arguments;
	}
	
	public void setArguments(List<Expression<?,?>> expressions){
		for(Expression<?,?> expression: expressions){
			arguments.add(expression);
		}
	}
	
	private List<Expression<?,?>> arguments = new ArrayList<Expression<?,?>>();
	
	
	public List<LiteralExpression<?>> evaluateArguments(){
		List<LiteralExpression<?>> evalArgs = new ArrayList<LiteralExpression<?>>();
		for(Expression<?,?> expression: this.getArguments()){
			evalArgs.add(LiteralExpression.generateLiteral(expression.evaluate(), this.getSourceLocation()));
		}
		return evalArgs;
	}
	
	protected List<LiteralExpression<?>> getEvalArguments() {
		return evalArguments;
	}

	protected void setEvalArguments(List<LiteralExpression<?>> evalArguments) {
		this.evalArguments = evalArguments;
	}
	
	private List<LiteralExpression<?>> evalArguments = new ArrayList<LiteralExpression<?>>();
	


	@Override
	public String toString(){
		String string = this.getFunction().getFunctionName() + "( ";
		
		for(Expression<?,?> argument : this.getArguments()){
			string += argument.toString() + ","; 
		}
		
		string = string.substring(0, string.length()-1) + ")";	
		return string;
	}
	
	
}
