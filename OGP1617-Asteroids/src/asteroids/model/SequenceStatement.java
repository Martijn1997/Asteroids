package asteroids.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SequenceStatement extends ChainedStatement implements Iterable<Statement>{
	
	public SequenceStatement(List<Statement> statements){
		super();
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
	
	//TODO create iterator, everytime getStatement is invoked, the next statement is selected in the selection
	// sequence
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
				return iteratorIndex > 0 && iteratorIndex < SequenceStatement.this.getStatementSequence().size();
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
		};
	}
	
	/**
	 * Set the associated function for each statement
	 * if there is non normal function in the statement sequence, throw IllegalStateException
	 */
	@Override
	protected void setFunction(Function function)throws IllegalStateException{
		try{
		for(int index = 0; index < this.getStatementSequence().size(); index++){
			super.setFunction(function);
		}
		}catch (Throwable exc){
			this.setIndex(0);
		}
	}
	
	/**
	 * List that stores all the statements belonging to the statement sequences
	 */
	private List<Statement> statementSequence = new ArrayList<Statement>();
	
	private int getIndex(){
		return this.iteratorIndex;
	}
	
	private void setIndex(int index){
		this.iteratorIndex = index;
	}
	
	private int iteratorIndex = 0;
}
