package asteroids.model;

public interface ExpressionStatement<R extends Expression<?,?>> {
	
	public void setExpression(R expression);
	
	public R getExpression();
}
