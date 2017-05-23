package asteroids.model;

import asteroids.part3.programs.SourceLocation;

public class EqualsExpression<T extends Expression<?,?>> extends BinaryExpression<T, Boolean>{
	
	
	public EqualsExpression(T leftOperand, T rightOperand, SourceLocation sourceLocation){
		super(leftOperand, rightOperand, sourceLocation);
	}
	
	public Boolean evaluate(){
		T leftOperand = this.getLeftOperand();
		T rightOperand = this.getRightOperand();
		
		if(leftOperand == null || rightOperand == null){
			return false;
		}
		
		if((this.leftOperandEvaluated()).equals(this.rightOperandEvaluated())){
			return true;
		}else{
			return false;
		}
	}
	
	
	@Override
	public String getOperatorString(){
		return "==";
	}
	

}
