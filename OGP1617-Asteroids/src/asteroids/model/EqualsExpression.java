package asteroids.model;

public class EqualsExpression<T> extends BinaryExpression<T, Boolean>{
	
	
	public EqualsExpression(T leftOperand, T rightOperand, Statement statement){
		super(leftOperand, rightOperand, statement);
	}
	
	public Boolean evaluate(){
		T leftOperand = this.getLeftOperand();
		T rightOperand = this.getRightOperand();
		
		if(leftOperand == null || rightOperand == null){
			return false;
		}
		
		if(((Expression<?,?>) leftOperand).evaluate().equals(((Expression<?,?>) rightOperand).evaluate())){
			return true;
		}else{
			return false;
		}
	}
	
	
	
	

}
