package asteroids.model;

public class IfStatement extends ChainedStatement implements ExpressionStatement<Expression<?, Boolean>>{
	
	public IfStatement(Expression<?,Boolean> condition, Statement ifstatement, Statement elseStatement){
		super();
		this.setStatement(ifstatement);
		this.setExpression(condition);
		if(elseStatement != null){
			this.createElseStatement(elseStatement);
		}
		
	}
	
	
	/**
	 * executes the if - (else) statement
	 */
	@Override
	public void executeStatement(){
		
		if(this.getExpression().evaluate()){
			this.getStatement().executeStatement();
		} else if(this.hasElseStatement()){
			this.getElseStatement().executeStatement();
		}
	}
	
	/**
	 * Basic getter for the condition of the if statement
	 */
	public Expression<?, Boolean> getExpression(){
		return this.condition;
	}
	
	/**
	 * sets the condition of the if statement
	 * @param condition
	 * @post new getCondition == condition;
	 */
	public void setExpression(Expression<?, Boolean> condition){
		this.condition = condition;
		condition.setStatement(this);
	}

	/**
	 * Basic getter for the statement of the if statement
	 */
	@Override
	public Statement getStatement(){
		return this.statement;
	}
	
	/**
	 * sets the statement of the if statement
	 * @post new.getStatement == statement
	 */
	public void setStatement(Statement statement){
		this.statement = statement;
	}
	
	
	/**
	 * getter for the associated else statement
	 */
	public ElseStatement getElseStatement(){
		return this.associatedElse;
	}
	
	
	/**
	 * sets the else statement of the if statement
	 * @param elseStatement
	 */
	public void createElseStatement(Statement statement){
		this.associatedElse = this.new ElseStatement(statement);
		this.hasElseStatement = true;
	}
	
	public boolean hasElseStatement(){
		return this.hasElseStatement;
	}
	
	/**
	 * variable that stores the condition of the if statement
	 */
	private Expression<?, Boolean> condition;
	
	/**
	 * variable that stores the statement of the if statement
	 */
	private Statement statement;
	
	/**
	 * variable that stores the associated else statement of the if statement
	 */
	private ElseStatement associatedElse;
	
	/**
	 * flag if the if-statement has a chained else statement
	 */
	private boolean hasElseStatement = false;
	
	
	
	/**
	 * Class of else statements chained to a given if statement
	 * @Invar an else statement can only belong to one specific if statement 
	 * (enforced by private class and pseudo constructor)
	 *
	 */
	private class ElseStatement extends ChainedStatement{
		
		public ElseStatement(Statement Statement){
			super();
			this.setStatement(statement);
		}
		
		@Override
		public void executeStatement(){
			this.getStatement().executeStatement();
		}
		
		@Override
		public Statement getStatement(){
			return this.statement;
		}
		
		private void setStatement(Statement statement){
			this.statement = statement;
		}
		/**
		 * variable that stores the statement for the else statement
		 */
		private Statement statement;
		
	}
}
