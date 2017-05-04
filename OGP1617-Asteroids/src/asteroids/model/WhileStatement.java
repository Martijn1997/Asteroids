package asteroids.model;

import exceptions.BreakException;

public class WhileStatement extends Statement implements NormalStatement{
	
	public WhileStatement(Program program, Expression<?, Boolean> condition, Statement statement){
		super(program);
		
	}
	
	
	public Statement getStatement(){
		return this.statement;
	}
	
	public void setStatement(Statement statement){
		this.statement = statement;
	}
	
	public Expression<?, Boolean> getCondition(){
		return this.condition;
	}
	
	public void setCondition(Expression<?, Boolean> condition){
		this.condition = condition;
	}
	
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
	public class BreakStatement extends Statement implements NormalStatement{
		public BreakStatement(Program program){
			super(program);
		}
		
		public void executeStatement(){
			throw new BreakException(WhileStatement.this);
		}
		
	}
	
	private Expression<?, Boolean> condition;
	private Statement statement;
}
