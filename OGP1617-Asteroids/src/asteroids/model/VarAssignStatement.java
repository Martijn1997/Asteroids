package asteroids.model;

/**
 * Class to create a variable assignment statement
 */
public class VarAssignStatement extends NormalStatement implements ExpressionStatement<Expression<?,?>>{
	
	public VarAssignStatement(Expression<?,?> expression, String varName){
		super();
		this.setExpression(expression);
		this.setName(varName);
	}

	public void executeStatement(){
		
		// if the statement is associated with a program, write the variable to the globals
		if(this.isAssociatedWithProgram()){
			LiteralExpression<Object> var = new LiteralExpression<Object>(this.getExpression().evaluate());
			var.setStatement(this);
			this.getProgram().addGlobalVariable(this.getName(), var);
		}else{
			// otherwise write the variable to the locals
			LiteralExpression<Object> var = new LiteralExpression<Object>(this.getExpression().evaluate());
			var.setStatement(this);
			this.getFunction().addLocalVariable(this.getName(), var);
		}
	}
	
	/**
	 * evaluates the value of the variable
	 */
	public Object getValue(){
		return variable.evaluate();
	}
	
	@Override
	public void setProgram(Program program){
		super.setProgram(program);
		this.getProgram().addUninitGlobal(this.getName());
		
	}
	
	/**
	 * Setter for the variable
	 */
	@Override
	public void setExpression(Expression<?,?> expression){
		// use the outcome of the expression to create a new variable
		this.variable = expression;
		expression.setStatement(this);
	}
	
	/**
	 * basic getter for the variable of the varAssignment
	 */
	public Expression<?,?> getExpression(){
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
	
	private Expression<?,?> variable;
	
	private String varName;
}
