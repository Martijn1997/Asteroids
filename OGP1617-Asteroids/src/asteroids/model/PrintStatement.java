package asteroids.model;

public class PrintStatement extends Statement {
	
	public PrintStatement(Expression<?,?> expression){
		this.setExpression(expression);
	}
	
	@Override
	public void executeStatement(){
		System.out.println(this.getExpression().toString());
		Object evaluation = this.getExpression().evaluate();
		
		if(evaluation instanceof LiteralExpression){
			this.getProgram().addPrintedObject(((LiteralExpression<?>)evaluation).evaluate());
		}else{

		this.getProgram().addPrintedObject(evaluation);
		}
	}
	
	public Expression<?,?> getExpression(){
		return this.expression;
	}
	
	public void setExpression(Expression<?,?> expression){
		this.expression = expression;
		expression.setStatement(this);
	}
	
	private Expression<?,?> expression;
	
	@Override
	public boolean canHaveAsProgram(Program program){
		return true;
	}
	
}
