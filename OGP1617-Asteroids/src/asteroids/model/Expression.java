package asteroids.model;
//R the return Type, T the type of the operands
public abstract class Expression<T, R> {
	
//		@Override
//		public abstract String toString();
		
		public abstract R evaluate();
		
		/**
		 * basic getter for the return statement
		 * @return
		 */
		public Statement getStatement(){
			return this.statement;
		}
		
		protected void setStatement(Statement statement){
			this.statement = statement;
		}
		
		private Statement statement;		
}
