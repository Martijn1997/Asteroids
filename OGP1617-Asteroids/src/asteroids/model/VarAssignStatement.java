package asteroids.model;

/**
 * Class to create a variable assignment statement
 */
public class VarAssignStatement<T> extends NormalStatement{
	
	public VarAssignStatement(Expression<?,T> expression, String varName){
		super();
		this.setExpression(expression);
	}

	public void executeStatement(){
		
		// if the statement is associated with a program, write the variable to the globals
		if(this.isAssociatedWithProgram()){
			LiteralExpression<T> var = new LiteralExpression<T>(this.getVariable().evaluate(), this);
			this.getProgram().addGlobalVariable(this.getName(), var);
		}else{
			// otherwise write the variable to the locals
			LiteralExpression<T> var = new LiteralExpression<T>(this.getVariable().evaluate(), this);
			this.getFunction().addLocalVariable(this.getName(), var);
		}
	}
	
	/**
	 * evaluates the value of the variable
	 */
	public T getValue(){
		return variable.evaluate();
	}
	
	/**
	 * Setter for the variable
	 */
	private void setExpression(Expression<?,T> expression){
		// use the outcome of the expression to create a new variable
		this.variable = expression;
	}
	
	/**
	 * basic getter for the variable of the varAssignment
	 */
	public Expression<?,T> getVariable(){
		return this.variable;
	}
	
	/**
	 * basic setter for the name
	 * @param varName
	 */
	public void setName(String varName){
		this.varName = varName;
	}
	
	/**
	 * basic getter for the name
	 */
	public String getName(){
		return this.varName;
	}
	
	private Expression<?,T> variable;
	
	private String varName;
}
