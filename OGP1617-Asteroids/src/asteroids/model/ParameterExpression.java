package asteroids.model;

/**
 * filling in parameter's is done by providing expressions
 * @author Martijn
 *
 */
public class ParameterExpression extends BasicExpression<LiteralExpression<?>>{
	
	public ParameterExpression(String parName){
		super(parName);
	}
	
	public LiteralExpression<?> evaluate(){
		// get the expression filled in at the function level
		Function assocFunction = null;
		
		try{
			assocFunction = ((NormalStatement)this.getStatement()).getFunction();
		}catch (ClassCastException exc){
			throw new IllegalStateException("Not normal expression");
		}
		
		if(assocFunction == null){
			throw new IllegalStateException("parameter not in function!");
		}
		
		Expression<?,?> expression = ((NormalStatement)this.getStatement()).getFunction().getLocalVariables().get(this.getName());
		// execute and return a literalExpression of an object type
		return new LiteralExpression<Object>(expression.evaluate());
		//if an classcastException is thrown	
	}
	
	@Override
	public boolean canHaveAsStatement(Statement statement){
		return (statement!=null && statement instanceof Statement);
	}
	
}
