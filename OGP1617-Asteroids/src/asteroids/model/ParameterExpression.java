package asteroids.model;

import java.util.Map;

import asteroids.part3.programs.SourceLocation;

/**
 * filling in parameter's is done by providing expressions
 * @author Martijn
 *
 */
public class ParameterExpression extends BasicExpression<LiteralExpression<?>>{
	
	public ParameterExpression(String parName, SourceLocation sourceLocation){
		super(parName,  sourceLocation);
	}
	//TODO ADD PRINT FOR CURRENT VALUE
	public LiteralExpression<?> evaluate(){
		//TODO FIX INFINITE LOOP
		Function function = null;
		try{
			NormalStatement statement = (NormalStatement) this.getStatement();
			function = statement.getFunction();
		}catch (ClassCastException exc){
			throw new IllegalStateException("Not normal expression");
		}
		
		if(function == null){
		throw new IllegalStateException("parameter not in function!");
		}
		
		FunctionCallExpression currentCall = function.readTopStack();
		
		//find the corresponding argument for this parameter and adjusting the index
		int index = Integer.parseInt(this.getName().substring(1)) - 1;
		
		
		LiteralExpression<?> argument = currentCall.getEvalArguments().get(index);
		
		
		return LiteralExpression.generateLiteral(argument.evaluate(), this.getSourceLocation());
		
	}
	
//	public LiteralExpression<?> generateLiteral(Object value){
//		//Check if the value is worldObject
//		if(value instanceof WorldObject){
//			return new LiteralExpression<WorldObject>((WorldObject) value);
//		//Check if the value is Double
//		}else if(value instanceof Double){
//			return new LiteralExpression<Double>((Double) value);
//		//check if value is Boolean
//		}else if(value instanceof Boolean){
//			return new LiteralExpression<Boolean>((Boolean) value);
//		//otherwise the value is of type literal
//		}else{
//			return generateLiteral((LiteralExpression<?>)value);
//		}
//	}
		
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
