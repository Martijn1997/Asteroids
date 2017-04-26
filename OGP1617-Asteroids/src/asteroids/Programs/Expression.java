package asteroids.Programs;
//R the return Type, T the type of the operands
public interface Expression<T, R> {
	
		@Override
		public String toString();
		
		public R getValue();
		
}
