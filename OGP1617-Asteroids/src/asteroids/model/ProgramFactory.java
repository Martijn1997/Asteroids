package asteroids.model;

import java.util.List;
import asteroids.part3.programs.SourceLocation;

@SuppressWarnings("unchecked")
public class ProgramFactory implements asteroids.part3.programs.IProgramFactory<Expression<?,?>,Statement,Function,Program>{
	
	public ProgramFactory(){
	}
	/* PROGRAM */

	/**
	 * Create a program from the given arguments.
	 * 
	 * @param functions
	 *            The function definitions for the program.
	 * @param main
	 *            The main statement of the program. Most likely this is a
	 *            sequence statement.
	 * @return A new program.
	 */
	@Override
	public Program createProgram(List<Function> functions, Statement main){
		return new Program(functions, main);
	}
		
	/* FUNCTION DEFINITIONS */

	/**
	 * Create a function definition involving the given function name,
	 * parameters and body.
	 * 
	 * @param functionName
	 *            The name of the function
	 * @param body
	 *            The body of the function.
	 */
	@Override
	public Function createFunctionDefinition(String functionName, Statement body, SourceLocation sourceLocation){
		return new Function(functionName, body);
	}

	/* STATEMENTS */

	/**
	 * Create a statement that represents the assignment of a variable.
	 * 
	 * @param variableName
	 *            The name of the assigned variable
	 * @param value
	 *            An expression that evaluates to the assigned value
	 */
	@Override
	public Statement createAssignmentStatement(String variableName, Expression<?,?> value, SourceLocation sourceLocation){
		return new VarAssignStatement(value, variableName);
	}

	/**
	 * Create a statement that represents a while loop.
	 * 
	 * @param condition
	 *            The condition of the while loop
	 * @param body
	 *            The body of the loop (most likely this is a sequence
	 *            statement).
	 */
	@Override
	public Statement createWhileStatement(Expression<?,?> condition, Statement body, SourceLocation sourceLocation){
			return new WhileStatement((Expression<?,Boolean>)condition, body);
	}

	/**
	 * Create a statement that represents a break statement.
	 */
	@Override
	public Statement createBreakStatement(SourceLocation sourceLocation){
		return new BreakStatement();
	}

	/**
	 * Create a statement that represents a return statement.
	 * 
	 * @param value
	 *            An expression that evaluates to the value to be returned
	 */
	@Override
	public Statement createReturnStatement(Expression<?,?> value, SourceLocation sourceLocation){
		return new ReturnStatement(value);
	}

	/**
	 * Create an if-then-else statement.
	 * 
	 * @param condition
	 *            The condition of the if statement
	 * @param ifBody
	 *            The body of the if-part, which must be executed when the
	 *            condition evaluates to true
	 * @param elseBody
	 *            The body of the else-part, which must be executed when the
	 *            condition evaluates to false. Can be null if no else clause is
	 *            specified.
	 */
	@Override
	public Statement createIfStatement(Expression<?,?> condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation){
		return new IfStatement((Expression<?,Boolean>)condition, ifBody, elseBody);
	}

	/**
	 * Create a print statement that prints the value obtained by evaluating the
	 * given expression.
	 * 
	 * @param value
	 *            The expression to evaluate and print
	 */
	public Statement createPrintStatement(Expression<?,?> value, SourceLocation sourceLocation){
		return new PrintStatement(value);
	}

	/**
	 * Create a sequence of statements.
	 * 
	 * @param statements
	 *            The statements that must be executed in the given order.
	 */
	public Statement createSequenceStatement(List<Statement> statements, SourceLocation sourceLocation){
		return new SequenceStatement(statements);
	}

	/* EXPRESSIONS */

	/**
	 * Create an expression that evaluates to the current value of the given
	 * variable.
	 * 
	 * @param variableName
	 *            The name of the variable to read.
	 */
	public Expression<?,?>createReadVariableExpression(String variableName, SourceLocation sourceLocation){
		return new VariableExpression(variableName);
	}

	/**
	 * Create an expression that evaluates to the current value of the given
	 * parameter.
	 * 
	 * @param parameterName
	 *            The name of the parameter to read.
	 */
	public Expression<?,?> createReadParameterExpression(String parameterName, SourceLocation sourceLocation){
		return new ParameterExpression(parameterName);
	}

	/**
	 * Create an expression that evaluates to result of calling the given
	 * function with the given list of actual arguments.
	 * 
	 * @param functionName
	 *            The name of the function to call.
	 * @param actualArgs
	 *            A list of expressions that act as actual arguments.
	 */
	public Expression<?,?> createFunctionCallExpression(String functionName, List<Expression<?,?>> actualArgs, SourceLocation sourceLocation){
		return new FunctionCallExpression(functionName, actualArgs);
	}

	/**
	 * Create an expression that evaluates to the given expression with changed
	 * sign (i.e., negated).
	 * 
	 * @param expression
	 */
	@Override
	public Expression<Expression<?,Double>,Double> createChangeSignExpression(Expression<?,?> expression, SourceLocation sourceLocation){
		return new NegationExpression((Expression<?,Double>)expression);
	
	}

	/**
	 * Create an expression that evaluates to true when the given expression
	 * evaluates to false, and vice versa.
	 * 
	 * @param expression
	 */
	@Override
	public Expression<Expression<?,Boolean>,Boolean> createNotExpression(Expression<?,?> expression, SourceLocation sourceLocation){
		return new NotExpression((Expression<?,Boolean>) expression);
	}

	/**
	 * Creates an expression that represents a literal double value.
	 */
	@Override
	public Expression<Double,Double> createDoubleLiteralExpression(double value, SourceLocation location){
		return new LiteralExpression<Double>(value);
	}

	/**
	 * Creates an expression that represents the null value.
	 */
	@Override
	public Expression<WorldObject,WorldObject> createNullExpression(SourceLocation location){
		return new NullExpression();
	}

	/**
	 * Creates an expression that represents the self value, evaluating to the
	 * ship that executes the program.
	 */
	@Override
	public Expression<Ship,Ship> createSelfExpression(SourceLocation location){
		return new SelfExpression();
	}

	/**
	 * Creates an expression that evaluates to the ship that is closest to the
	 * ship that is executing the program.
	 */
	@Override
	public Expression<Ship,Ship> createShipExpression(SourceLocation location){
		return new ClosestWorldObject<Ship>("asteroids.model.Ship");
	}

	/**
	 * Creates an expression that evaluates to the asteroid that is closest to
	 * the ship that is executing the program.
	 */
	@Override
	public Expression<Asteroid,Asteroid> createAsteroidExpression(SourceLocation location){
		return new ClosestWorldObject<Asteroid>("asteroids.model.Asteroid");
	}

	/**
	 * Creates an expression that evaluates to the planetoid that is closest to
	 * the ship that is executing the program.
	 */
	@Override
	public Expression<Planetoid,Planetoid> createPlanetoidExpression(SourceLocation location){
		return new ClosestWorldObject<Planetoid>("asteroids.model.Planetoid");
	}

	/**
	 * Creates an expression that evaluates to one of the bullets fired by the
	 * ship that executes the program.
	 */
	@Override
	public Expression<Bullet,Bullet> createBulletExpression(SourceLocation location){
		return new BulletExpression();
	}

	/**
	 * Creates an expression that evaluates to the minor planet that is closest
	 * to the ship that is executing the program.
	 */
	@Override
	public Expression<MinorPlanet,MinorPlanet> createPlanetExpression(SourceLocation location){
		return new ClosestWorldObject<MinorPlanet>("asteroids.model.MinorPlanet");
	}

	/**
	 * Creates an expression that evaluates to an arbitrary entity in the world
	 * of the ship that is executing the program.
	 */
	@Override
	public Expression<?,?> createAnyExpression(SourceLocation location){
		return new AnyExpression();
	}

	/**
	 * Returns an expression that evaluates to the position along the x-axis of
	 * the entity to which the given expression evaluates.
	 */
	@Override
	public Expression<Expression<?,WorldObject>,Double> createGetXExpression(Expression<?,?> e, SourceLocation location){
		return new GetXExpression((Expression<?,WorldObject>)e);
	}

	/**
	 * Returns an expression that evaluates to the position along the y-axis of
	 * the entity to which the given expression evaluates.
	 */
	@Override
	public Expression<Expression<?,WorldObject>,Double> createGetYExpression(Expression<?,?> e, SourceLocation location){
		return new GetYExpression((Expression<?, WorldObject>) e);
	}

	/**
	 * Returns an expression that evaluates to the velocity along the x-axis of
	 * the entity to which the given expression evaluates.
	 */
	@Override
	public Expression<Expression<?,WorldObject>,Double> createGetVXExpression(Expression<?,?> e, SourceLocation location){
		return new GetVxExpression((Expression<?, WorldObject>) e);
	}
	/**
	 * Returns an expression that evaluates to the velocity along the y-axis of
	 * the entity to which the given expression evaluates.
	 */
	@Override
	public Expression<Expression<?,WorldObject>,Double> createGetVYExpression(Expression<?,?> e, SourceLocation location){
		return new GetVyExpression((Expression<?, WorldObject>) e);
	}
	/**
	 * Returns an expression that evaluates to the radius of the entity to which
	 * the given expression evaluates.
	 */
	@Override
	public Expression<Expression<?, WorldObject>,Double> createGetRadiusExpression(Expression<?,?> e, SourceLocation location){
		return new GetRadiusExpression((Expression<?, WorldObject>) e);
	}

	/**
	 * Returns an expression that evaluates to true if the evaluation of the
	 * first expression yields a value that is less than the value obtained by
	 * evaluating the second expression.
	 */
	@Override
	public Expression<Expression<?,Double>, Boolean> createLessThanExpression(Expression<?,?> e1, Expression<?,?> e2, SourceLocation location){
		return new SmallerThanExpression((Expression<?,Double>)e1,(Expression<?,Double>)e2);
	}

	/**
	 * Returns an expression that evaluates to true if the evaluation of the
	 * first expression yields a value that is equal to the value obtained by
	 * evaluating the second expression.
	 */
	@Override
	public Expression<?,Boolean> createEqualityExpression(Expression<?,?> e1, Expression<?,?> e2, SourceLocation location){
		return new EqualsExpression<Expression<?,? extends Expression<?,?>>>((Expression<?,? extends Expression<?,?>>)e1,
				(Expression<?,? extends Expression<?,?>>)e2);
	}

	/**
	 * Returns an expression that evaluates to the addition of the values
	 * obtained by evaluating the first and second given expressions.
	 */
	@Override
	public Expression<Expression<?,Double>,Double> createAdditionExpression(Expression<?,?> e1, Expression<?,?> e2, SourceLocation location){
		return new AdditionExpression((Expression<?,Double>)e1,(Expression<?,Double>)e2);
	}

	/**
	 * Returns an expression that evaluates to the multiplication of the values
	 * obtained by evaluating the first and second given expressions.
	 */
	@Override
	public Expression<Expression<?,Double>,Double> createMultiplicationExpression(Expression<?,?> e1, Expression<?,?> e2, SourceLocation location){
		return new MultiplicationExpression((Expression<?,Double>)e1,(Expression<?,Double>)e2);
	}

	/**
	 * Returns an expression that evaluates to the square root of the value
	 * obtained by evaluating the given expression.
	 */
	@Override
	public Expression<Expression<?, Double>,Double> createSqrtExpression(Expression<?,?> e, SourceLocation location){
		return new SqrtExpression((Expression<?, Double>) e);
	}

	/**
	 * Returns an expression that evaluates to the direction (in radians) of the
	 * ship executing the program.
	 */
	@Override
	public Expression<?,?> createGetDirectionExpression(SourceLocation location){
		return new GetdirExpression();
	}

	/**
	 * Returns a statement that turns the thruster of the ship executing the
	 * program on.
	 */
	@Override
	public Statement createThrustOnStatement(SourceLocation location){
		return new ThrustOnStatement();
	}

	/**
	 * Returns a statement that turns the thruster of the ship executing the
	 * program off.
	 */
	@Override
	public Statement createThrustOffStatement(SourceLocation location){
		return new ThrustOffStatement();
	}

	/**
	 * Returns a statement that fires a bullet from the ship that is executing
	 * the program.
	 */
	@Override
	public Statement createFireStatement(SourceLocation location){
		return new FireStatement();
	}

	/**
	 * Returns a statement that makes the ship that is executing the program
	 * turn by the given amount.
	 */
	@Override
	public Statement createTurnStatement(Expression<?,?> angle, SourceLocation location){
		return new TurnStatement((Expression<?, Double>) angle);

	}

	/**
	 * Returns a statement that does nothing.
	 */
	@Override
	public Statement createSkipStatement(SourceLocation location){
		return new SkipStatement();
	}
}