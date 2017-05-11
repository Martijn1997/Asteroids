package asteroids.model;

import java.util.ArrayList;
import java.util.List;

import exceptions.ReturnException;

public class FunctionCallExpression extends Expression<Expression<?,?>,LiteralExpression<?>>{
	
	public FunctionCallExpression(String functionName, List<Expression<?,?>> actualArgs){
		this.setFunction(functionName);
		this.setArguments(actualArgs);
	}
	
	public LiteralExpression<?> evaluate() throws IndexOutOfBoundsException{
		Function function = this.getFunction();
		List<Expression<?,?>> arguments = this.getArguments();
		/*
		 * parameters are added as actual variables with given names '$1' etc
		 */
		for(int index = 1; index + 1 ==  arguments.size(); index++){
			if(function.getLocalVariables().containsKey('$' + Integer.toString(index))){
				function.addLocalVariable('$' + Integer.toString(index), new LiteralExpression<Object>(arguments.get(index-1).evaluate()));
			}else
				throw new IndexOutOfBoundsException();
		}
		
		try{
			function.evaluate();
		}catch (ReturnException returnVal){
			return returnVal.getValue();
		}
		
		return null;
		
	}
	
	public Function getFunction(){
		return this.associatedFunction;
	}
	
	public void setFunction(String name) throws IllegalArgumentException{
		List<Function> functions = this.getStatement().getProgram().getFunctions();
		for(Function function: functions){
			if(function.getFunctionName().equals(name)){
				this.associatedFunction = function;
				break;
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
		String string = this.getFunction().getFunctionName() + "(";
		int size = this.getArguments().size();
		for(Expression<?,?> argument: this.getArguments().subList(0, size - 2)){
			string += argument.toString() + ", ";
		}
		
		string += this.getArguments().get(size-1) + ")";
		
		return string;
	}
	
}
