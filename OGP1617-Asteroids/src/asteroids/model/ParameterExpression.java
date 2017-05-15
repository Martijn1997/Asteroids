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
		Function function = null;
		try{
			function = ((NormalStatement)this.getStatement()).getFunction();
		}catch (ClassCastException exc){
			throw new IllegalStateException("Not normal expression");
		}
		
		if(function == null){
		throw new IllegalStateException("parameter not in function!");
		}
		
		FunctionCallExpression currentCall = function.readTopStack();
		
		//find the corresponding argument for this parameter and adjusting the index
		int index = Integer.parseInt(this.getName().substring(1)) - 1;
//		System.out.print("argument size: ");
//		System.out.println(currentCall.getArguments().size());
//		System.out.print("current index: ");
//		System.out.println(index);
		Expression<?,?> argument = currentCall.getArguments().get(index);
		
		return generateLiteral(argument.evaluate());
		
	}
	
	public LiteralExpression<?> generateLiteral(Object value){
		//Check if the value is worldObject
		if(value instanceof WorldObject){
			return new LiteralExpression<WorldObject>((WorldObject) value);
		//Check if the value is Double
		}else if(value instanceof Double){
			return new LiteralExpression<Double>((Double) value);
		//check if value is Boolean
		}else if(value instanceof Boolean){
			return new LiteralExpression<Boolean>((Boolean) value);
		//otherwise the value is of type literal
		}else{
			return (LiteralExpression<?>)value;
		}
	}
		
//		// get the expression filled in at the function level
//		Function assocFunction = null;
//		try{
//			assocFunction = ((NormalStatement)this.getStatement()).getFunction();
//		}catch (ClassCastException exc){
//			throw new IllegalStateException("Not normal expression");
//		}
//		

//		
//		Expression<?,?> expression = ((NormalStatement)this.getStatement()).getFunction().getLocalVariables().get(this.getName());
//		// execute and return a literalExpression of an object type
//		return new LiteralExpression<Object>(expression.evaluate());
//		//if an classcastException is thrown	

	
	@Override
	public boolean canHaveAsStatement(Statement statement){
		return (statement!=null && statement instanceof Statement);
	}
	
}
