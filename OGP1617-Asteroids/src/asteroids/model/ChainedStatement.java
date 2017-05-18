package asteroids.model;

public abstract class ChainedStatement extends NormalStatement {
	
	public ChainedStatement(){
		super();
	}
	
	@Override
	public boolean canHaveAsFunction(Function function){
		if(!super.canHaveAsFunction(function)){
			return false;
		}else{
			 return hasAllNormalSubStatement();
		}
	}
	
	/**
	 * @return If the enclosed statement is also a chained statement check if all the chained statements
	 * are also normal sub-statements
	 * 
	 * @return If the enclosed statement is not a chained statement, check if it is a Normal Statement
	 * 
	 */
	public boolean hasAllNormalSubStatement(){
		if(this.getStatement() instanceof ChainedStatement){
			return ((ChainedStatement) this.getStatement()).hasAllNormalSubStatement();
		}else{
			return this.getStatement() instanceof NormalStatement;
		}
	}
	
	/**
	 * basic getter for substatements
	 * @return
	 */
	public abstract Statement getStatement();
	
	@Override
	protected void setFunction(Function function) throws IllegalStateException{
		
		Statement statement = this.getStatement();
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
		// also don't forget to set the associated function of the chained function to the provided function
		super.setFunction(function);
	}
	
	@Override
	protected void setProgram(Program program){
		
		Statement statement = this.getStatement();
		// if the underlying statement is also a chained statement, set the chained statements
		// associated programs to program
		statement.setProgram(program);
		super.setProgram(program);
	}
	

}
