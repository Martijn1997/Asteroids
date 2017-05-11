package asteroids.model;

public class PrintStatement extends Statement {
	
	public PrintStatement(Expression<?,?> expression){
		this.setExpression(expression);
	}
	
	@Override
	public void executeStatement(){
		System.out.println(this.getExpression().toString());
		this.getProgram().addPrintedObject(this.getExpression().evaluate());
	}
	
	public Expression<?,?> getExpression(){
		return this.expression;
	}
	
	public void setExpression(Expression<?,?> expression){
		this.expression = expression;
	}
	
	private Expression<?,?> expression;
	
	@Override
	public boolean canHaveAsProgram(Program program){
		return true;
	}
	
}
