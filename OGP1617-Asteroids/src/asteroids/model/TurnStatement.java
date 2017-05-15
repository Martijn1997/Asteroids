package asteroids.model;

import exceptions.IllegalAngleException;

public class TurnStatement extends ActionStatement implements ExpressionStatement<Expression<?, Double>>{

	public TurnStatement(Expression<?, Double> angleExpression){
		super();
		this.setExpression(angleExpression);
	}

	@Override
	public void setExpression(Expression<?, Double> expression) {
		this.angle = expression;
		expression.setStatement(this);
	}

	@Override
	public Expression<?, Double> getExpression() {
		return this.angle;
	}
	
	@Override
	public void executeStatement() {
		super.executeStatement();
		Ship self = this.getProgram().getShip();
		double angle = this.getExpression().evaluate();
		try{
			self.turn(angle);
		}catch (AssertionError err){
			throw new IllegalAngleException();
		}	
	}
	
	private Expression<?, Double> angle;

}