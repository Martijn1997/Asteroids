package asteroids.model;

import exceptions.BreakException;

public class WhileStatement extends ChainedStatement{
	
	public WhileStatement(Program program, Expression<?, Boolean> condition, Statement statement){
		super();
		
	}
	
	
	@Override
	public Statement getStatement(){
		return this.statement;
	}
	
	public void setStatement(Statement statement){
		this.statement = statement;
	}
	
	public Expression<?, Boolean> getCondition(){
		return this.condition;
	}
	
	// the statements are responsible to set the condition-statement bidirectional relation
	public void setCondition(Expression<?, Boolean> condition){
		this.condition = condition;
		condition.setStatement(this);
		
	}
	
	@Override
	public void executeStatement() throws IllegalStateException{
		try{
		while(this.getCondition().evaluate()){
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
	
	/**
	 * Break class only accessible by While statements enclosing the break
	 * @author Martijn
	 *
	 */
	public class BreakStatement extends NormalStatement{
		public BreakStatement(Program program){
			super();
		}
		
		public void executeStatement(){
			throw new BreakException(WhileStatement.this);
		}
		
	}
	
	private Expression<?, Boolean> condition;
	private Statement statement;
}
