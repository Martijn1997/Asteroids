package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * A variable can be any type (double, boolean and WorldObject)
 * @author Martijn & Flor
 *
 */
public class VariableExpression extends BasicExpression<Object> {
	
	/**
	 * constructor for a variable
	 * @param varName
	 * @param variable
	 */
	public VariableExpression(String varName, Statement statement){
		super(varName, statement);
		// store the literal in the specified variable
		this.literal = this.getStatement().getProgram().getGlobals().get(this.getName());
	}
	
	/**
	 * Basic getter for values of type Object
	 * @return 
	 */
	@Basic
	public Object evaluate(){
		return getLiteralExpression().evaluate();
	}
	
	public LiteralExpression<?> getLiteralExpression(){
		return this.literal;
	}
	
	private final LiteralExpression<?> literal;
}
