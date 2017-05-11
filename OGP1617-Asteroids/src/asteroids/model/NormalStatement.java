package asteroids.model;

public abstract class NormalStatement extends Statement{
	
	public NormalStatement(){
		super();
	}
	
	public Function getFunction(){
		return this.associatedFunction;
	}
	
	// the function associates the statement with the function
	protected void setFunction(Function function){
		if(!canHaveAsFunction(function)){
			throw new IllegalArgumentException();
		}
		this.associatedFunction = function;
		
		// scan for underlying parameters
		//searchUnderlyingParameters(function);
		
	}

//	/**
//	 * method that searches for underlying parameters
//	 * @param function
//	 */
//	protected void searchUnderlyingParameters(Function function) {
//		if(this instanceof ExpressionStatement){
//			//first see if the expression is a parameter
//			Expression<?,?> expression = ((ExpressionStatement<?>)this).getExpression();
//			if(expression instanceof ParameterExpression){
//				this.getFunction().addParameter(((ParameterExpression) expression).getName(), expression);
//			//secondly see if the expression has subexpressions, if so, scan for parameters, otherwise do nothing
//			}else if(expression instanceof SubExprExpression){
//				((SubExprExpression) expression).scanForParameter(function);
//			}
//		}
//	}
	
	/**
	 * checks if the statement can have the given function associated
	 * @param function
	 * @return 	true if and only if the function is valid
	 * 			a function can be added if the statement is not yet associated with a program
	 * 
	 */
	public boolean canHaveAsFunction(Function function){
		return this.getProgram() == null;
	}
	
	/**
	 * Returns true if and only if the statement is not yet associated
	 * with a function
	 */
	public boolean canHaveAsProgram(Program program){
		return this.getFunction() == null;
	}
	
	public boolean isAssociatedWithProgram(){
		return this.getProgram() != null;
	}
	
	private Function associatedFunction;
}
