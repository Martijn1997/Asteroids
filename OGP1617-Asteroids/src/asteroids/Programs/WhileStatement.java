package asteroids.Programs;

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
	
	public void executeStatement(){
		while(this.getCondition().evaluate()){
			this.getStatement().executeStatement();
		}
	}
	
	/**
	 * Break class only accesable by While statements encolsing the break
	 * @author Martijn
	 *
	 */
	public class BreakStatement extends Statement implements NormalStatement{
		public BreakStatement(Program program){
			super(program);
		}
		
		public void executeStatement(){
			//TODO implement break statement
		}
	}
	
	private Expression<?, Boolean> condition;
	private Statement statement;
}
