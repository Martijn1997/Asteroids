package asteroids.Programs;

/**
 * Class to create a variable assignment statement
 */
public class VarAssignStatement<T> extends Statement implements NormalStatement{
	
	public VarAssignStatement(Program program, Expression<?,T> expression, String varName){
		super(program);
		this.setVariableExpression(expression, varName);
	}

	public void executeStatement(){
		VariableExpression<T> var = this.getVariable();
		this.getProgram().addGlobalVariable(var.getName(), var);
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
	private void setVariableExpression(Expression<?,T> expression, String varName){
		// use the outcome of the expression to create a new variable
		this.variable = new VariableExpression<T>(varName, expression.evaluate());
	}
	
	/**
	 * basic getter for the variable of the varAssignment
	 */
	public VariableExpression<T> getVariable(){
		return this.variable;
	}
	
	
	private VariableExpression<T> variable;
}
