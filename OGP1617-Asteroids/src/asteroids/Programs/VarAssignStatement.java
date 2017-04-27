package asteroids.Programs;

public class VarAssignStatement<T> extends Statement implements NormalStatement{
	
	public VarAssignStatement(Program program, Expression<?,T> expression, String varName){
		super(program);
		this.setVariableExpression(expression, varName);
	}

	public void executeStatement(){
		VariableExpression<T> var = this.getVariable();
		this.getProgram().addGlobalVariable(var.getName(), var);
	}
	
	public T getValue(){
		return variable.evaluate();
	}
	
	private void setVariableExpression(Expression<?,T> expression, String varName){
		this.variable = new VariableExpression<T>(varName, expression.evaluate());
	}
	
	/**
	 * basic getter for the variable of the varAssignment
	 * @return
	 */
	public VariableExpression<T> getVariable(){
		return this.variable;
	}
	
	
	private VariableExpression<T> variable;
}
