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
		// if the underlying statement is also chained, re-invoke
		if(this.getStatement() instanceof ChainedStatement){
			((ChainedStatement) this.getStatement()).setFunction(function);
			
		}else if(this.getStatement() instanceof NormalStatement){
			//if the underlying statement is a normal statement set the function for the underlying one
			((NormalStatement) this.getStatement()).setFunction(function);
			
		}else{
			// if the underlying statement is not a chained or normal statement, throw exception
			throw new IllegalStateException();
		}
		// also don't forget to set the associated function of the chained function to the provided function
		super.setFunction(function);
	}
	
	@Override
	protected void setProgram(Program program){
		// if the underlying statement is also a chained statement, set the chained statements
		// associated programs to program
		if(this.getStatement() instanceof ChainedStatement){
			((ChainedStatement) this.getStatement()).setProgram(program);
		}else{
			// else if it is a non chained statement, set the program to program without further ado
			this.getStatement().setProgram(program);
		}
	}
}
