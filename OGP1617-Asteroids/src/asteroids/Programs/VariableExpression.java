package asteroids.Programs;

import asteroids.model.WorldObject;
import be.kuleuven.cs.som.annotate.*;

public class VariableExpression extends BasicExpression<Expression<?,?>> {
	
	/**
	 * constructor for a variable
	 * @param varName
	 * @param variable
	 */
	public VariableExpression(String varName, Statement statement){
		super(varName, statement);
	}
	
	/**
	 * Basic getter for values of type T
	 */
	@Basic
	public Expression<?, ?> evaluate(){
		Program program = this.getStatement().getProgram();
		return program.getGlobals().get(this.getName());
	}


	

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		VariableExpression other = (VariableExpression) obj;
//		if (this.evaluate() == null) {
//			if (other.evaluate() != null)
//				return false;
//		} else if (!this.evaluate().equals(other.evaluate()))
//			return false;
//		return true;
//	}

}
