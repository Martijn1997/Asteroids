package asteroids.Programs;

public class EqualsExpression<T> extends BinaryExpression<T, Boolean>{
	
	
	public EqualsExpression(T leftOperand, T rightOperand){
		super(leftOperand, rightOperand);
	}
	
	public Boolean getValue(){
		T leftOperand = this.getLeftOperand();
		T rightOperand = this.getRightOperand();
		
		if(leftOperand == null || rightOperand == null){
			return false;
		}
		
		if(((Expression) leftOperand).getValue().equals(((Expression) rightOperand).getValue())){
			return true;
		}else{
			return false;
		}
	}
	
	
	
	

}
