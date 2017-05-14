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
		
//		if(this.value.evaluate() instanceof WorldObject){
//			return new LiteralExpression<WorldObject>((WorldObject)this.value.evaluate());
//		}else if( this.value.evaluate() instanceof Double){
//			return new LiteralExpression<Double>((Double)this.value.evaluate());
//		}else{
//			return new LiteralExpression<Boolean>((Boolean)this.value.evaluate());
//		}
		
		return new LiteralExpression<Object>(this.value.evaluate());
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
