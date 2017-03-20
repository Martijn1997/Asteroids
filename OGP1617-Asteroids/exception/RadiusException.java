import be.kuleuven.cs.som.annotate.*;

/**
 * A class for signaling an illegal Radius
 * @author Martijn & Flor
 * @version 1.0
 */
public class RadiusException extends RuntimeException{
	/**
	 * initialize this new radius exception with given value
	 */
	public RadiusException(double value){
		this.value = value;
	}
	
	@Basic @Immutable
	public double getValue(){
		return this.value;
	}
	
	private final double value;
	// why serial UID?
	// documentation with checkers
	// how does git work?
}
