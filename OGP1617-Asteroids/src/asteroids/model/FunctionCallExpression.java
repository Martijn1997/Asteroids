package asteroids.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		this.setEvalArguments(this.evaluateArguments());
		this.setLocalScope(function.getLocals());
		
		//TODO evaluate the arguments before executing the code
		

		
		return function.evaluate(this);
		
	}
	
	protected Map<String, LiteralExpression<?>> getLocalScope(){
		return this.localScope;
	}
	
	public void setLocalScope(Map<String, LiteralExpression<?>> map){
		this.getLocalScope().clear();
		
		for(String name: map.keySet()){
			this.getLocalScope().put(name, map.get(name));
		}
	}
	
	private Map<String, LiteralExpression<?>> localScope= new HashMap<String, LiteralExpression<?>>();
	
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
		//otherwise the value is of type literal.
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
			evalArgs.add(generateLiteral(expression.evaluate()));
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
	
//	public void scanForBreakStatement(WhileStatement whileState){
//		Statement statement = this.getFunction().getStatement();
//		if(statement instanceof BreakStatement){
//			((BreakStatement) statement).setWhileStatement(whileState);
//		}else if(statement instanceof ChainedStatement){
//			((ChainedStatement) statement).lookForBreakStatement(whileState);
//		}
//	}
	
}
