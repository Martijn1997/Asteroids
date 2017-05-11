package asteroids.model;
//R the return Type, T the type of the operands
public abstract class Expression<T, R> {
	
//		@Override
//		public abstract String toString();
		
		//create constructor
	
		public abstract R evaluate();
		
		/**
		 * basic getter for the return statement
		 * @return
		 */
		public Statement getStatement(){
			return this.statement;
		}
		
		protected void setStatement(Statement statement)throws IllegalArgumentException{
			if(canHaveAsStatement(statement)){
				this.statement = statement;
			}else
				throw new IllegalArgumentException();
			
		}
		
		public boolean canHaveAsStatement(Statement statement){
			return statement != null;
		}
		
		private Statement statement;		
}
