package asteroids.Programs;

public class SqrtExpression extends UnaryExpression<Expression<?, Double>, Double>{
		
		/**
		 * Constructor for a Square root expression
		 * @param operand
		 * @effect	UnaryExpression(operand)
		 */
		public SqrtExpression(Expression<?, Double> operand){
			super(operand);
		}
		
		/**
		 * returns the sqrtExpression value
		 */
		public Double getValue(){
			return Math.sqrt(this.getOperand().getValue());
		}
}
