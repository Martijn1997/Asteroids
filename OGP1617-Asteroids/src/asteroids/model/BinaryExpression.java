package asteroids.model;


import be.kuleuven.cs.som.annotate.*;

public abstract class BinaryExpression<T extends Expression<?,?>,R> extends Expression<T,R>/* implements SubExprExpression*/{
	
	/**
	 * Constructor for a binary expression
	 * @param leftOperand
	 * @param rightOperand
	 */
	@Model @Raw
	protected BinaryExpression(T leftOperand, T rightOperand) throws IllegalArgumentException{
		this.setLeftOperand(leftOperand);
		this.setRightoperand(rightOperand);
	}
	
	/**
	 * @return the left operand of the binaryExpression
	 */
	public T getLeftOperand(){
		return this.leftOperand;
	}
	/**
	 * @return the right operand of the binaryExpression
	 */
	public T getRightOperand(){
		return this.rightOperand;
	}
	
	/**
	 * basic setter for the left operand
	 * @param 	operand
	 * @post 	the value of the left operand is set to leftOperand
	 * 			|new.getLeftOperand() == leftOperand
	 * @throws	IllegalArgumentException
	 */
	@Model @Basic @Raw
	protected void setLeftOperand(T operand) throws IllegalArgumentException{
		if(!isValidOperand(operand)){
			throw new IllegalArgumentException();
		}
		this.leftOperand = operand;
	}
	
	/**
	 * Basic setter for the right operand
	 * @param 	operand
	 * @post	the value of the right operand is set to rightOperand
	 * 			| new.getRightOperand() == rightOperand
	 @throws	IllegalArgumentException
	 */
	@Model @Basic @Raw
	protected void setRightoperand(T operand){
		if(!isValidOperand(operand)){
			throw new IllegalArgumentException();
		}
		this.rightOperand = operand;
	}
	
	/**
	 * checker if the current operand is valid
	 * @param operands
	 * @return true if and only if the expression can have the operand as operand
	 */
	public boolean isValidOperand(T operand){
		return operand instanceof Expression;
	}
	
	/**
	 * Set statement for a Binary expression
	 */
	@Override
	protected void setStatement(Statement statement){
		this.getLeftOperand().setStatement(statement);
		this.getRightOperand().setStatement(statement);
		super.setStatement(statement);
	}
	
	public Object leftOperandEvaluated(){
		
		Expression<?,?> leftOperand = this.getLeftOperand();
		
		if(leftOperand instanceof VariableExpression){
			return  ((VariableExpression)leftOperand).evaluate().evaluate() ;
		}else if(leftOperand instanceof ParameterExpression){
			return((ParameterExpression)leftOperand).evaluate().evaluate();
		}else if (leftOperand instanceof FunctionCallExpression){
			return((FunctionCallExpression)leftOperand).evaluate().evaluate();
		}else{
			return leftOperand.evaluate();
		}
	}
	
	public Object rightOperandEvaluated(){
		
		Expression<?,?> rightOperand = this.getRightOperand();
		if(rightOperand instanceof VariableExpression){
			return  ((VariableExpression)rightOperand).evaluate().evaluate() ;
		}else if(rightOperand instanceof ParameterExpression){
			return((ParameterExpression)rightOperand).evaluate().evaluate();
		}else if (rightOperand instanceof FunctionCallExpression){
			return((FunctionCallExpression)rightOperand).evaluate().evaluate();
		}else{
			return rightOperand.evaluate();
		}

		
	}
	
	
	private T leftOperand;
		
	private T rightOperand;
	
//	@Override
//	public void scanForParameter(Function function){
//		//first see for the left operand
//		if(this.getLeftOperand() instanceof ParameterExpression){
//			function.addParameter(((ParameterExpression) this.getLeftOperand()).getName(), null);
//		}else if(this.getLeftOperand() instanceof SubExprExpression){
//				((SubExprExpression) this.getLeftOperand()).scanForParameter(function);		
//		}
//		
//		//second see for the right operand
//		if(this.getRightOperand() instanceof ParameterExpression){
//			function.addParameter(((ParameterExpression) this.getRightOperand()).getName(), null);
//		}else if(this.getRightOperand() instanceof SubExprExpression){
//				((SubExprExpression) this.getRightOperand()).scanForParameter(function);		
//		}
//	}
	

	@Override
	public String toString(){
		
		return "(" + getLeftOperand().toString() + " " + this.getOperatorString() + " "+ getRightOperand().toString() + ")";
	}
	
	public abstract String getOperatorString();
}
