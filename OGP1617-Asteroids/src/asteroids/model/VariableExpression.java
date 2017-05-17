package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * A variable can be any type (double, boolean and WorldObject)
 * @author Martijn & Flor
 *
 */
public class VariableExpression extends BasicExpression<LiteralExpression<?>> {
	
	/**
	 * constructor for a variable
	 * @param varName
	 * @param variable
	 */
	public VariableExpression(String varName){
		super(varName);
		// store the literal in the specified variable
	}
	
	/**
	 * Basic getter for values of type Object
	 * @return  if the associated statement is part of a function
	 * 			check if the function contains a local variable with the given name
	 * 
	 * @return  else read the global variable if it is present in the globals of the program
	 * 
	 * @throws 	IllegalArgumentException
	 * 			thrown if the varName cannot be resolved to a local or global variable
	 */
	@Basic
	public LiteralExpression<?> evaluate() throws IllegalArgumentException{
		Statement statement = this.getStatement();
		
		if(statement instanceof NormalStatement){

			if(((NormalStatement) statement).getFunction() != null){

				if(((NormalStatement) statement).getFunction().getLocalVariables().containsKey(this.getName())){
					return ((NormalStatement) statement).getFunction().
							getLocalVariables().get(this.getName());
				}else if(((NormalStatement) statement).getFunction().getProgram().containsGlobalVariable(this.getName())){

					return((NormalStatement) statement).getFunction().
							getProgram().getGlobals().get(this.getName());	
				}else{
					throw new IllegalArgumentException();
				}
			}
		}
		
		// check if the associated statement is a normal statement
		// if so check also if it is connected to a function or not
//		if(statement instanceof NormalStatement){
//
//			if(((NormalStatement) statement).getFunction() != null){
//
//				if(((NormalStatement) statement).getFunction().getLocalVariables().containsKey(this.getName())){
//					return generateLiteral(((NormalStatement) statement).getFunction().
//							getLocalVariables().get(this.getName()));
//				}else if(((NormalStatement) statement).getFunction().getProgram().containsGlobalVariable(this.getName())){
//
//					return generateLiteral(((NormalStatement) statement).getFunction().
//							getProgram().getGlobals().get(this.getName()));	
//				}else{
//					throw new IllegalArgumentException();
//				}
//			}
//		}
		
		// else read the global variable
		if(statement.getProgram().getGlobals().containsKey(this.getName())){
			return generateLiteral(this.getStatement().getProgram().getGlobals().get(this.getName()));
		}else{
			throw new IllegalArgumentException();
		}
	}
	
	public String toString(){
		Object var = this.evaluate().evaluate();
		return var.toString();
	}
	
	public static LiteralExpression<?> generateLiteral(Object value){
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
	
	public Object LiteralEvaluation(){
		Statement statement = this.getStatement();
		// check if the associated statement is a normal statement
		// if so check also if it is connected to a function or not
		if(statement instanceof NormalStatement){
			
			if(((NormalStatement) statement).getFunction() != null){

				if(((NormalStatement) statement).getFunction().getLocalVariables().containsKey(this.getName())){
					return ((NormalStatement) statement).getFunction().
							getLocalVariables().get(this.getName()).evaluate();
				}else if(((NormalStatement) statement).getFunction().getProgram().containsGlobalVariable(this.getName())){

					return((NormalStatement) statement).getFunction().
							getProgram().getGlobals().get(this.getName()).evaluate();	
				}else{
					throw new IllegalArgumentException();
				}
			}
		}
		return null;
	}
	
}
