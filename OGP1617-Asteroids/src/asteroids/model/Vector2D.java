package asteroids.model;

import java.util.Arrays;

import be.kuleuven.cs.som.annotate.*;


/**
 * A Value class of immutable 2D vectors
 * @author Martijn Sauwens & Flor Theuns
 *
 * @Invar 	The value of the vector components must be valid
 * 			|isValidVector(getXComponent(), getYComponent)
 */
@Value
public class Vector2D{
	
	
	/**
	 * Constructor for a Vector2D Value
	 * @param 	xComponent
	 * 			The x component of the 2D vector
	 * 
	 * @param 	yComponent
	 * 			The y component of the 2D vector
	 * 
	 * @Post	The values of the x and y components of the 2D vector equal to
	 * 			xComponent and yComponent respectively
	 * 			| new.getXComponent() == xComponent
	 * 			| new.getYComponent() == yComponent
	 * @throws 	IllegalArgumentException
	 * 			Thrown if the provided one of or both of the prvided components
	 * 			are not valid
	 * 			|!isValidVector(xComponent, yComponent)
	 */
	@Raw
	public Vector2D(double xComponent, double yComponent)throws IllegalArgumentException{
		
		// check if the provided values are valid
		if(!isValidVector(xComponent, yComponent))
			throw new IllegalArgumentException();
		// set the X and the Y components of the vector
		this.Vector2D[0] = xComponent;
		this.Vector2D[1] = yComponent;
	}
	
	
	// Make some tests in the test suite for this function
	/**
	 * Converts an array of 2 elements to an Vector2D value object
	 * @param 	array
	 * 			the 2 element array
	 * @return	a Vector2D with as x and y values the values of the array element in respective order
	 * 			|new.getVector2DArray == array
	 * 
	 * @throws 	IllegalArgumentException
	 * 			Thrown if the array is not of length 2 or the array is a null reference
	 * 			|array == null || array.lenght! = 2
	 */
	public static Vector2D array2Vector(double ... array)throws IllegalArgumentException{
		if(array == null || array.length!= 2)
			throw new IllegalArgumentException();
		return new Vector2D(array[0], array[1]);
		
	}
	
	/**
	 * Checks if the provided value can be used as a 2D vector component
	 * @param 	value
	 * 			The value to be checked
	 * 
	 * @return	True if and only if the value is not a NaN value
	 * 			| result == !isNaN(value)
	 */
	public static boolean isValidComponent(double value){
		return !Double.isNaN(value);
	}
	
	/**
	 * Checks if both values can be used as 2D vector components
	 * @param 	xValue
	 * 			The value of the x component to be checked
	 * 
	 * @param 	yValue
	 * 			The value of the y component to be checked
	 * 
	 * @effect	return true if and only if both values can be used as vector components
	 * 			| result == isValidComponent(xValue)&&isValidComponent(yValue)
	 */
	public boolean isValidVector(double xValue, double yValue){
		return isValidComponent(xValue)&&isValidComponent(yValue);
	}
	
	/**
	 * Basic getter for the 2D vector
	 * @return a 2D vector
	 */
	@Basic @Raw @Immutable
	public double[] getVector2DArray(){
		return this.Vector2D.clone();
	}
	
	/**
	 * Basic getter for the X component of the 2D vector
	 * @return The X component of the 2D vector
	 */
	@Basic @Raw @Immutable
	public double getXComponent(){
		return this.Vector2D[0];
	}
	
	/**
	 * Basic getter for the Y component of the 2D vector
	 * @return The Y component of the 2D vector
	 */
	@Basic @Raw @Immutable
	public double getYComponent(){
		return this.Vector2D[1];
	}
	
	/**
	 * The 2D vector represented as an array of size 2
	 */
	private double[] Vector2D = new double[2];
	
	
	/**
	 * Calculates the difference between two vectors (eg deltaR)
	 * @param 	other
	 * 			The other vector
	 * 
	 * @return	The difference between two vectors
	 * 			| result == Vector1 - Vector2
	 */
	@Immutable
	public Vector2D difference(Vector2D other){
		double newXComponent = this.getXComponent() - other.getXComponent();
		double newYComponent = this.getYComponent() - other.getYComponent();
		
		return new Vector2D(newXComponent, newYComponent);
	}
	
	/**
	 * Calculates the sum of two vectors 
	 * @param 	other
	 * 			The other vector
	 * 
	 * @return	The difference between two vectors
	 * 			| result == Vector1 + Vector2
	 */
	@Immutable
	public Vector2D vectorSum(Vector2D other){
		double newXComponent = this.getXComponent() + other.getXComponent();
		double newYComponent = this.getYComponent() + other.getYComponent();
		
		return new Vector2D(newXComponent, newYComponent);
	}
	
	/**
	 * Rescales a vector with the given scalar
	 * @param   scalar
	 * 			the factor which rescales the vector
	 * 
	 * @return	The rescaled vector
	 * 			| result == scalar*Vector
	 */
	public Vector2D rescale(double scalar){
		
		double newXComponent = this.getXComponent()*scalar;
		double newYComponent = this.getYComponent()*scalar;
		
		return new Vector2D(newXComponent, newYComponent);
	}
	
	/**
	 * Calculates the dot product of two 2D vectors
	 * @param 	other
	 * 			The other vector
	 * 
	 * @return	The dot product of two 2D vectors
	 * 			| result == getXComponent()*other.getXComponent + getYComponent()*other.getYComponent();
	 */
	public double dotProduct(Vector2D other){
		return this.getXComponent()*other.getXComponent() + this.getYComponent()*other.getYComponent();
	}
	
	
	/**
	 * Calculate the distance between two vectors
	 * @param 	other
	 * 			The other vector
	 * 
	 * @return 	The distance between two vectors
	 * 			|@see Implementation
	 */
	public double distanceTo(Vector2D other){
		Vector2D diffVector = this.difference(other);
		double xDiffSquared = Math.pow(diffVector.getXComponent(), 2);
		double yDiffSquared = Math.pow(diffVector.getYComponent(), 2);
		
		return Math.sqrt(xDiffSquared + yDiffSquared);
	}
	
	/**
	 * Checks if the provided object equals to the prime object of value class Vector2D
	 * @param	other
	 * 			the other object
	 * 
	 * @return	false if the object is a null reference
	 * 			|if(other == null)
	 * 			|then result == false
	 * 
	 * @return 	false if the object is not an instance of Vector2D
	 * 			|if (!(other instanceof Vector2D)
	 * 			|then result == false
	 * 
	 * @return 	true if and only if both vectors are equal
	 * 			|result == this.difference(other).getXComponent() == 0 && this.difference(other).getYComponent() == 0)
	 */
	@Override
	public boolean equals(Object other){
		// check if the object is a null reference
		if(other == null)
			return false;
		
		// check if the object is an instance of Vector2D
		if(!(other instanceof Vector2D))
			return false;
		
		// if the object is instance of Vector2D the other can be cast
		// as an object of value class Vector2D
		Vector2D diffVector = this.difference((Vector2D)other);
		return diffVector.getXComponent() == 0 && diffVector.getYComponent() == 0;
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(Vector2D);
		return result;
	}
	
	public final static Vector2D TEMINATED_POS = new Vector2D(-1E10, -1E10);
	public final static Vector2D TERMINATED_VEL = new Vector2D(0,0);
	
	
	public final static Vector2D ORIGIN = new Vector2D(0,0);
}
