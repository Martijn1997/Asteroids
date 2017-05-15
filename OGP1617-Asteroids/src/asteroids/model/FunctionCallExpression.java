package asteroids.model;

import java.util.ArrayList;
import java.util.List;

import exceptions.ReturnException;

public class FunctionCallExpression extends Expression<Expression<?,?>,LiteralExpression<?>>{
	
	public FunctionCallExpression(String functionName, List<Expression<?,?>> actualArgs){
		this.setFunctionName(functionName);
		this.setArguments(actualArgs);
	}
	
	public String getFunctionName(){
		return this.functionName;
	}
	
	public void setFunctionName(String functionName){
		this.functionName = functionName;
	}
	
	private String functionName;
	
	public LiteralExpression<?> evaluate() throws IndexOutOfBoundsException, IllegalArgumentException{
		Function function = this.getFunction();
		List<Expression<?,?>> arguments = this.getArguments();
		/*
		 * parameters are added as actual variables with given names '$1' etc
		 */
		System.out.println(arguments.size());
		for(int index = 1; index == arguments.size(); index++){
			System.out.println('$' + Integer.toString(index));
			if(function.getLocalVariables().containsKey('$' + Integer.toString(index))){
				function.addLocalVariable('$' + Integer.toString(index), generateLiteral(arguments.get(index-1).evaluate()));
			}else
				throw new IndexOutOfBoundsException();
		}
		
		return function.evaluate();
		
	}
	
	public LiteralExpression<?> generateLiteral(Object value){
		//Check if the value is worldObject
		if(value instanceof WorldObject){
			return new LiteralExpression<WorldObject>((WorldObject) value);
		//Check if the value is Double
		}else if(value instanceof Double){
			return new LiteralExpression<Double>((Double) value);
		//check if value is Boolean
		}else if(value instanceof Boolean){
			return new LiteralExpression<Boolean>((Boolean) value);
		//otherwise the value is of type literal
		}else{
			return (LiteralExpression<?>)value;
		}
	}
	
	public Function getFunction(){
		List<Function> functions = null;
		
		Statement statement = this.getStatement();
		if(statement instanceof NormalStatement){
			// if the current statement is a normal statement in a function
			if(((NormalStatement)statement).getProgram() == null){
				Function assocFunct = ((NormalStatement)statement).getFunction();
				Program program = assocFunct.getProgram();
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
	
	private List<Expression<?,?>> getArguments(){
		return this.arguments;
	}
	
	public void setArguments(List<Expression<?,?>> expressions){
		for(Expression<?,?> expression: expressions){
			arguments.add(expression);
		}
	}
	
	private List<Expression<?,?>> arguments = new ArrayList<Expression<?,?>>();
	
	@Override
	public String toString(){
		String string = this.getFunction().getFunctionName() + "( ";
		
		for(Expression<?,?> argument : this.getArguments()){
			string += argument.toString() + ","; 
		}
		
		string = string.substring(0, string.length()-1) + ")";	
		return string;
	}
	
	public void scanForBreakStatement(WhileStatement whileState){
		Statement statement = this.getFunction().getStatement();
		if(statement instanceof BreakStatement){
			((BreakStatement) statement).setWhileStatement(whileState);
		}else if(statement instanceof ChainedStatement){
			((ChainedStatement) statement).lookForBreakStatement(whileState);
		}
	}
	
}
