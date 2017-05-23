package asteroids.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import asteroids.part3.programs.SourceLocation;



public class SequenceStatement extends ChainedStatement implements Iterable<Statement>{
	
	public SequenceStatement(List<Statement> statements, SourceLocation sourceLocation){
		super(sourceLocation);
		this.setStatementSequence(statements);
	}
	
	/**
	 * execute the sequence statement by iteration trough all the statements in the list
	 */
	public void executeStatement(){
		for(Statement statement: this.getStatementSequence()){
			statement.executeStatement();
		}
		
	}
	
	/**
	 * Basic getter for the list of statements
	 */
	public List<Statement> getStatementSequence(){
		return this.statementSequence;
	}
	
	private void setStatementSequence(List<Statement> statements){
		this.statementSequence = statements;
	}
	
	/**
	 * returns the first statement of the statement sequence
	 */
	public Statement getStatement(){
		return this.iterator().next();
	}

	@Override
	public boolean hasAllNormalSubStatement(){
		for(int index = 0; index < this.getStatementSequence().size(); index++ ){
			if(!super.hasAllNormalSubStatement()){
				return false;
			}
		}
		return true;
		
	}
	
	@Override
	public Iterator<Statement> iterator(){
		return new Iterator<Statement>(){
			
			public boolean hasNext(){
				return index >= 0 && index < SequenceStatement.this.getStatementSequence().size();
			}
			
			public Statement next() throws IndexOutOfBoundsException{
				if(!hasNext()){
					setIndex(0);
					throw new IndexOutOfBoundsException();
				}else{
					setIndex(getIndex()+1);
					return SequenceStatement.this.getStatementSequence().get(getIndex()-1);
					
				}
			}
			
			private int getIndex(){
				return this.index;
			}
			
			private void setIndex(int index){
				this.index = index;
			}
			
			private int index = 0;
		};
	}
	
	/**
	 * Set the associated function for each statement
	 * if there is non normal function in the statement sequence, throw IllegalStateException
	 */
	@Override
	protected void setFunction(Function function)throws IllegalStateException{

			for(Statement statement: this.getStatementSequence()){
				// if the underlying statement is also chained, re-invoke
				if(statement instanceof ChainedStatement){
					((ChainedStatement) statement).setFunction(function);
					
				}else if(statement instanceof NormalStatement){
					//if the underlying statement is a normal statement set the function for the underlying one
					((NormalStatement) statement).setFunction(function);
					
				}else{
					// if the underlying statement is not a chained or normal statement, throw exception
					throw new IllegalStateException();
				}

			}
			// also don't forget to set the associated function of the chained function to the provided function
			super.setFunction(function);
	}
	
	
	/**
	 * Sets the program of all the underlying statements to the program associated with the sequence statement
	 */
	@Override
	protected void setProgram(Program program)throws IllegalStateException{

			List<Statement> sequence = this.getStatementSequence();
			for(Statement statement: sequence){
				statement.setProgram(program);
			}
			super.setProgram(program);

	}
	
	
	/**
	 * List that stores all the statements belonging to the statement sequences
	 */
	private List<Statement> statementSequence = new ArrayList<Statement>();
	

}
