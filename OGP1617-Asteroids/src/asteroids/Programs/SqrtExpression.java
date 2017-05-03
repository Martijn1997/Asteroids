package asteroids.Programs;

public class SqrtExpression extends UnaryExpression<Expression<?, Double>, Double>{
		
		/**
		 * Constructor for a Square root expression
		 * @param operand
		 * @effect	UnaryExpression(operand)
		 */
		public SqrtExpression(Expression<?, Double> operand, Statement statement){
			super(operand, statement);
		}
		
		/**
		 * returns the sqrtExpression value
		 */
		public Double evaluate(){
			return Math.sqrt(this.getOperand().evaluate());
		}
}
