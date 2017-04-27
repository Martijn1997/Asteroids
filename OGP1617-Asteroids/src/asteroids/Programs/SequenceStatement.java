package asteroids.Programs;

import java.util.ArrayList;
import java.util.List;

public class SequenceStatement extends Statement implements NormalStatement {
	
	public SequenceStatement(Program program, List<Statement> statements){
		super(program);
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
	
	public List<Statement> getStatementSequence(){
		return this.statementSequence;
	}
	
	private void setStatementSequence(List<Statement> statements){
		this.statementSequence = statements;
	}
	
	private List<Statement> statementSequence = new ArrayList<Statement>();
}
