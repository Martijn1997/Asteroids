package asteroids.model;

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
		public Double evaluate(){
			return Math.sqrt(this.getOperand().evaluate());
		}
		
		@Override
		public String getOperatorString(){
			return "Sqrt";
		}
}
