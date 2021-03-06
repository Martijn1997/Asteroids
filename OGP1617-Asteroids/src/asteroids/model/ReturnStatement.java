package asteroids.model;

import asteroids.part3.programs.SourceLocation;
import exceptions.OutOfTimeException;
import exceptions.ReturnException;

/**
 * Statement that upon execution throws a ReturnException containing the return value (literal)
 * @author Martijn
 *
 */
public class ReturnStatement extends NormalStatement implements ExpressionStatement<Expression<?,?>>{
	
	public ReturnStatement(Expression<?,?> value,SourceLocation sourceLocation){
		super(sourceLocation);
		this.setExpression(value);
	}
	
	public void executeStatement() throws ReturnException{
		if(this.getProgram() ==  null){
			throw new ReturnException(this.getValue());
		}else{
			if (this.getProgram().getTime() < 0.2){
				throw new OutOfTimeException(this);
			}
			if((this.getProgram().getLastStatement() == this) || (this.getProgram().getLastStatement() == null)){
				this.getProgram().setLastStatement(null);
				throw new ReturnException(this.getValue());
			}
		}
	}
	
	/** 
	 * @return a literalExpression containing the value of the evaluated expression
	 */
	public LiteralExpression<?> getValue(){
		
		Object evaluation = this.value.evaluate();
		SourceLocation sourceLocation = this.value.getSourceLocation();
		
		if(evaluation instanceof WorldObject){
			LiteralExpression<WorldObject> returnValue = new LiteralExpression<WorldObject>((WorldObject)evaluation,  sourceLocation);
			returnValue.setStatement(this);
			return returnValue;
			
		}else if( evaluation instanceof Double){
			LiteralExpression<Double> returnValue = new LiteralExpression<Double>((Double)evaluation,  sourceLocation);
			returnValue.setStatement(this);
			return returnValue;

		}else if (evaluation instanceof LiteralExpression){
			return (LiteralExpression<?>)evaluation;
			
		}else{
			LiteralExpression<Boolean> returnValue = new LiteralExpression<Boolean>((Boolean)evaluation,  sourceLocation);
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
