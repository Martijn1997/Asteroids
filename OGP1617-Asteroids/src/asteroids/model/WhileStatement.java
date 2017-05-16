package asteroids.model;

import exceptions.BreakException;

public class WhileStatement extends ChainedStatement implements ExpressionStatement<Expression<?,Boolean>>{
	
	public WhileStatement(Expression<?, Boolean> condition, Statement statement){
		super();
		this.setExpression(condition);
		this.setStatement(statement);
//		this.lookForBreakStatement(this);
	}
	
	
	@Override
	public Statement getStatement(){
		return this.statement;
	}
	

	public void setStatement(Statement statement){
		this.statement = statement;


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

		}
	}
	

	
	
	private Expression<?, Boolean> condition;
	
	private Statement statement;
}
