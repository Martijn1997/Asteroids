package asteroids.model;

import exceptions.OutOfTimeException;

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
		if(this.getProgram() ==  null){
//			if(this.isAssociatedWithProgram()){
//				LiteralExpression<?> var = generateLiteral(this.getExpression().evaluate());
//				var.setStatement(this);
//				this.getProgram().addGlobalVariable(this.getName(), var);
//			}else{
				// otherwise write the variable to the locals
				LiteralExpression<?> var = LiteralExpression.generateLiteral(this.getExpression().evaluate());
				var.setStatement(this);
				this.getFunction().addLocalVariable(this.getName(), var);
//			}
		}else{
			if (this.getProgram().getTime() < 0.2){
				throw new OutOfTimeException(this);
			}
			if((this.getProgram().getLastStatement() == this) || (this.getProgram().getLastStatement() == null)){
				this.getProgram().setLastStatement(null);
				// if the statement is associated with a program, write the variable to the globals
				if(this.isAssociatedWithProgram()){
					LiteralExpression<?> var = LiteralExpression.generateLiteral(this.getExpression().evaluate());
					var.setStatement(this);
					this.getProgram().addGlobalVariable(this.getName(), var);
				}else{
					// otherwise write the variable to the locals
					LiteralExpression<?> var =LiteralExpression.generateLiteral(this.getExpression().evaluate());
					var.setStatement(this);
					this.getFunction().addLocalVariable(this.getName(), var);
				}
			}
		}
	}
	
//	public LiteralExpression<?> generateLiteral(Object value){
//		//Check if the value is worldObject
//		if(value instanceof WorldObject){
//			return new LiteralExpression<WorldObject>((WorldObject) value);
//		//Check if the value is Double
//		}else if(value instanceof Double){
//			return new LiteralExpression<Double>((Double) value);
//		//check if value is Boolean
//		}else if(value instanceof Boolean){
//			return new LiteralExpression<Boolean>((Boolean) value);
//		//otherwise the value is of type literal
//		}else{
//			return (LiteralExpression<?>)value;
//		}
//	}
	
	
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
