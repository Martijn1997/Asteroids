package exceptions;

import asteroids.model.ParameterExpression;

public class ParameterException extends RuntimeException{
	
	public ParameterException(String paramName){
		this.setParameter(paramName);
	}
	
	public String getParameter() {
		return this.parameter;
	}

	public void setParameter(String paramName) {
		this.parameter = parameter;
	}
	
	private String parameter;


}
