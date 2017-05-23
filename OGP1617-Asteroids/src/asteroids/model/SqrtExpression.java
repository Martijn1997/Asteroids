package asteroids.model;

import asteroids.part3.programs.SourceLocation;

public class SqrtExpression extends UnaryExpression<Expression<?, Double>, Double>{
		
		/**
		 * Constructor for a Square root expression
		 * @param operand
		 * @effect	UnaryExpression(operand)
		 */
		public SqrtExpression(Expression<?, Double> operand, SourceLocation sourceLocation){
			super(operand, sourceLocation);
		}
		
		/**
		 * returns the sqrtExpression value
		 */
		public Double evaluate(){
			return Math.sqrt((Double)this.operandEvaluated());
		}
		
		@Override
		public String getOperatorString(){
			return "Sqrt";
		}
}
