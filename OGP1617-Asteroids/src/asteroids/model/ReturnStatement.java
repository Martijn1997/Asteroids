package asteroids.model;

import exceptions.ReturnException;

/**
 * Statement that upon execution throws a ReturnException containing the return value (literal)
 * @author Martijn
 *
 */
public class ReturnStatement extends NormalStatement implements ExpressionStatement<Expression<?,?>>{
	
	public ReturnStatement(Expression<?,?> value){
		super();
		this.setExpression(value);
	}
	
	public void executeStatement() throws ReturnException{
		//super.executeStatement()
		throw new ReturnException(this.getValue());
	}
	
	/** 
	 * @return a literalExpression containing the value of the evaluated expression
	 */
	public LiteralExpression<?> getValue(){
		
		Object evaluation = this.value.evaluate();

		
		if(evaluation instanceof WorldObject){
			LiteralExpression<WorldObject> returnValue = new LiteralExpression<WorldObject>((WorldObject)evaluation);
			returnValue.setStatement(this);
			return returnValue;
			
		}else if( evaluation instanceof Double){
			LiteralExpression<Double> returnValue = new LiteralExpression<Double>((Double)evaluation);
			returnValue.setStatement(this);
			return returnValue;

		}else if (evaluation instanceof LiteralExpression){
			return (LiteralExpression<?>)evaluation;
			
		}else{
			LiteralExpression<Boolean> returnValue = new LiteralExpression<Boolean>((Boolean)evaluation);
			returnValue.setStatement(this);
			return returnValue;
		}
		

	}
	
	@Override
	public boolean canHaveAsProgram(Program program){
		return false;
	}
	
	public void setExpression(Expression<?,?> expression){
		this.value = expression;
		expression.setStatement(this);
	}
	
	public Expression<?,?> getExpression(){
		return this.value;
	}
	
	private Expression<?,?> value;
}
