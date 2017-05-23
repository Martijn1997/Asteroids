package asteroids.model;

import asteroids.part3.programs.SourceLocation;
import exceptions.IllegalAngleException;
import exceptions.OutOfTimeException;

public class TurnStatement extends ActionStatement implements ExpressionStatement<Expression<?, Double>>{

	public TurnStatement(Expression<?, Double> angleExpression, SourceLocation sourceLocation){
		super( sourceLocation);
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
	public void executeStatement() throws OutOfTimeException{
		super.executeStatement();
		if((this.getProgram().getLastStatement() == this) || (this.getProgram().getLastStatement() == null)){
			this.getProgram().setTime(this.getProgram().getTime() - 0.2);
			Ship self = this.getProgram().getShip();
			double angle = this.getExpression().evaluate();
			this.getProgram().setLastStatement(null);
			try{
				self.turn(angle);
			}catch (AssertionError err){
				throw new IllegalAngleException();
			}
		}
	}
	
	private Expression<?, Double> angle;

}