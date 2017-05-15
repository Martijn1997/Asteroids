package asteroids.model;

import exceptions.BreakException;

public class WhileStatement extends ChainedStatement implements ExpressionStatement<Expression<?,Boolean>>{
	
	public WhileStatement(Expression<?, Boolean> condition, Statement statement){
		super();
		this.setExpression(condition);
		this.setStatement(statement);
		this.lookForBreakStatement(this);
	}
	
	
	@Override
	public Statement getStatement(){
		return this.statement;
	}
	

	public void setStatement(Statement statement){
		this.statement = statement;
		if(statement instanceof BreakStatement){
			((BreakStatement) statement).setWhileStatement(this);
		}
	}
	
	public Expression<?, Boolean> getExpression(){
		return this.condition;
	}
	
	// the statements are responsible to set the condition-statement bidirectional relation
	public void setExpression(Expression<?, Boolean> condition){
		this.condition = condition;
		condition.setStatement(this);
		
	}
	
	@Override
	public void executeStatement() throws IllegalStateException{
		try{
		while(this.getExpression().evaluate()){
			this.getStatement().executeStatement();
		}
		// if during the execution a break is thrown catch the BreakException
		}catch (BreakException exc){
			// check if the break does not belong to this while statement, throw exception
			if(exc.getValue() != this){
				throw new IllegalStateException();
			}
		}
	}
	
//	/**
//	 * Break class only accessible by While statements enclosing the break
//	 * @author Martijn
//	 *
//	 */
//	public class BreakStatement extends NormalStatement{
//		public BreakStatement(Program program){
//			super();
//		}
//		
//		public void executeStatement(){
//			throw new BreakException(WhileStatement.this);
//		}
//		
//	}
	
	
	private Expression<?, Boolean> condition;
	
	private Statement statement;
}
