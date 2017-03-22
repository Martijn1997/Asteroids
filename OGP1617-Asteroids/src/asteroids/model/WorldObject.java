package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

// to do:
// Make double reference binding from WorldObject to World
// Create Boundry collision method
// adjust the collision function to check wether two colliding objects reside within the same world.
// documentatie voor de massa
public abstract class WorldObject {
	
	
	/**
	 * Initializes an object of the WorldObject class
	 * @param 	xPos
	 * 			the x position of the WorldObject
	 * 
	 * @param	yPos
	 * 			the y position of the WorldObject
	 * 
	 * @param 	orientation
	 * 			the orientation of the WorldObject
	 * 
	 * @param 	radius
	 * 			the Radius of the WorldObject
	 * 
	 * @effect 	xPosition is set to the provided xPos
	 * 			| setXPosition(xPos)
	 * 
	 * @effect 	yPosition is set to the provided yPos
	 * 			| setYPosition(yPos)
	 * 
	 * @effect 	the radius of the WorldObject is set to the given radius
	 * 			| setRadius(radius)
	 * 
	 * @effect 	the velocity is set to the given velocity components xVel and yVel
	 * 			| setVelocity(xVel,yVel)
	 */
	public WorldObject(double xPos, double yPos, double radius, double xVel, double yVel)throws IllegalArgumentException{
		this.setXPosition(xPos);
		this.setYPosition(yPos);
		this.setRadius(radius);
		this.setVelocity(xVel, yVel);
	}
		
	
	/**
	 * default constructor for a WorldObject object
	 * @effect WorldObject(0,0,10,0,0)
	 */
	public WorldObject(){
		this(0d,0d,10d,0,0);
	}
	
	/**
	 * Returns the current x position of the WorldObject
	 */
	@Basic @Raw
	public double getXPosition(){	
		return this.xPosition;
	}
	
	/**
	 * Sets the x position of the WorldObject if the xPos is valid
	 * 
	 * @param 	xPos
	 * 			the new x-position of the WorldObject
	 * 
	 * @post	the new x position is xPos
	 * 			|new.getXPosition() = xPos
	 * 
	 * @throws 	IllegalArgumentexception
	 * 			| !isValidposition(xPos)
	 */
	@Basic @Raw
	public void setXPosition(double xPos) throws IllegalArgumentException{
		if(!isValidPosition(xPos))
			throw new IllegalArgumentException();
		this.xPosition = xPos;
	}
	
	/**
	 * Checks if the Position is a Valid position
	 */
	public static boolean isValidPosition(double Pos){
		return !((Double.isNaN(Pos))||(Pos == Double.POSITIVE_INFINITY)
				||(Pos == Double.NEGATIVE_INFINITY));
	}
	
	private double xPosition;
	
	/**
	 * returns the y position of the WorldObject
	 */
	@Basic @Raw
	public double getYPosition(){	
		return this.yPosition;
	}
	
	
	/**
	 * Sets the y position of the WorldObject if the yPos is valid
	 * @param   yPos
	 * 			the new y position of the WorldObject
	 * 
	 * @post    the new y position is yPos
	 * 			|new.getYPosition() == yPos
	 * 
	 * @throws 	IllegalArgumentException
	 * 			| !isValidPosition(yPos)
	 */
	@Basic @Raw
	public void setYPosition(double yPos) throws IllegalArgumentException{
		if(!isValidPosition(yPos))
			throw new IllegalArgumentException();
		this.yPosition = yPos;
	}
	
	private double yPosition;
	
	
	/**
	 * Returns the x velocity of the WorldObject
	 */
	@Basic @Raw
	public double getXVelocity(){
		
		return this.xVelocity;
	}
	
	/**
	 * Sets the x-velocity to xVel if the supplied value is valid
	 * @param 	xVel
	 * 			the new x velocity
	 * 
	 * @post 	if the supplied value for yVel is valid, the velocity it set to yVel
	 * 			| if isValidVelocity(totalVelocity(yVel, this.getXVelocity))
	 * 			| then new.getXVelocity() = xVel
	 * 			| else new.getXVelocity() = this.getXVelocity()
	 */
	@Basic @Raw
	public void setXVelocity(double xVel){
		
		if(isValidVelocity(totalVelocity(xVel, this.yVelocity)))
			this.xVelocity = xVel;
		
	}
	
	private double xVelocity;
	
	/**
	 * Returns the y velocity of the WorldObject
	 */
	@Basic @Raw
	public double getYVelocity(){
		return this.yVelocity;
	}
	
	/** 
	 * Sets the y-velocity to yVel if the supplied value is valid
	 * @param 	yVel
	 * 			the new y-velocity
	 * 
	 * @post 	if the supplied value for yVel is valid, the velocity it set to yVel
	 * 			| if isValidVelocity(totalVelocity(this.xVelocity, yVel))
	 * 			| then new.getYVelocity() = yVel
	 * 			| else new.getYVelocity() = this.getYVelocity()
	 */
	@Basic @Raw
	public void setYVelocity(double yVel){
		if(isValidVelocity(totalVelocity(this.xVelocity,yVel)))
			this.yVelocity = yVel;
		
	}
	private double yVelocity;
	
	
	
	/**
	 * Sets the velocity to the two provided velocity components, xVel and yVel
	 * @param 	xVel 
	 * 			the new x-velocity component
	 * 
	 * @param 	yVel
	 * 			the new y-velocity component
	 * 
	 * @post 	if the supplied velocity is valid, the velocity is set to the supplied values
	 * 			| if isValidVelocity(totalVelocity(xVel, yVel))
	 * 			| then new.getXVelocity() = xVel&&
	 * 			| 	   new.getYVelocity() = yVel
	 * 			|
	 * 			| else new.getYVelocity() = this.getYVelocity()&&
	 * 			|	   new.getXVelocity() = this.getXVelocity()
	 */
	@Basic @Raw
	public void setVelocity(double xVel, double yVel){
		if (isValidVelocity(totalVelocity(xVel,yVel)))
			this.xVelocity = xVel;
			this.yVelocity = yVel;
	}
	

	/**
	 * @return 	The total velocity of the WorldObject
	 * 			|result == Math.sqrt(Math.pow(xVel,2) + Math.pow(yVel,2))
	 * 
	 * @return 	if the sum causes overflow, return positive infinity
	 * 			|result == Double.POSITIVE_INFINITY
	 */
	protected static double totalVelocity(double xVel, double yVel){
		if (Double.MAX_VALUE - Math.pow(xVel,2) < Math.pow(yVel,2))
			return Double.POSITIVE_INFINITY;
		else
			return Math.sqrt(Math.pow(xVel,2) + Math.pow(yVel,2));
	}
	
	/**
	 * Returns if the given velocity is below the light speed
	 * @return totalVel <= LIGHT_SPEED && totalVel >= 0
	 */
	public static boolean isValidVelocity(double totalVel){
		return (totalVel <= LIGHT_SPEED && totalVel >= 0);
	}

	public static final double LIGHT_SPEED = 300000;

	
	/**
	 * @return 	the radius of the WorldObject
	 */
	@Basic @Raw
	public double getRadius(){
		return radius;
	}
	
	/**
	 * Sets the radius of a WorldObject
	 * @post 	the radius is set to rad
	 * 			| new.getRadius() == rad
	 * 
	 * @throws  IllegalArgumentException
	 * 			| !isValidRadius(rad)
	 * 		
	 */
	@Basic @Immutable @Raw
	public void setRadius(double rad) throws IllegalArgumentException{
			if(! isValidRadius(rad))
				throw new IllegalArgumentException();
			this.radius = rad;
			
	}
	
	/**
	 * checks whether the provided radius is valid
	 * @param 	rad
	 * 			the radius
	 * 
	 * @return |result == (rad >= MIN_RADIUS)
	 */
	public static boolean isValidRadius(double rad){
		return rad > 0;
	}
	
	// the radius will not change once the radius has been set
	private double radius;
	

	
	/**
	 * @return The density of the WorldObject
	 */
	public double getDensity(){
		return this.Density;
	}
	
	/**
	 * Set the mass of the WorldObject to the provided mass
	 * @param 	density
	 * @post	| the object density equals to the provided mass
	 * 			| if(canHaveAsDensity(density))
	 * 			| then new.getDensity() == density
	 * 			| else new.getDensity() == 1
	 */
	public void setDensity(double density){
		if(canHaveAsDensity(density))
			this.Density = density;
		else
			this.Density = 1;
	}
	
	/**
	 * checks if the mass for an object is a valid mass
	 * @param mass
	 * @return  true if and only if the mass is larger than zero, smaller than Double.MAX_VALUE and does not equal NaN.
	 * 			@see implementation
	 */
	public boolean canHaveAsDensity(double density){
		return density > 0 && density < Double.MAX_VALUE && !Double.isNaN(density);
	}
	
	// mass is variable so no final value
	private double Density;
	
	public double getMass(){
		return this.mass;
	}
	public void setMass(double mass){
		if(canHaveAsMass(mass))
			this.mass = mass;
		else
			this.mass =1;
	}
	
	private double mass;
	
	public boolean canHaveAsMass(double mass){
		return mass > 0&& mass < Double.MAX_VALUE && !Double.isNaN(mass);
	}
	
	protected static double volumeSphere(double radius){
		return 4.0/3.0 * Math.PI*Math.pow(radius,3);
	}
	
	/**
	 * Sets the new position of the WorldObject according to the current speed and given time interval
	 * @param   time
	 * 			the passed time used to move the WorldObject
	 * 
	 * @post 	if the given time is zero, the position of the WorldObject will not change
	 * 			| if(time == 0)
	 * 			| then (new.getXPosition() == getXPosition) &&
	 * 			|	   (new.getYPosition() == getYPosition) 
	 * 
	 * @post	if the given type is nonnegative and not zero the WorldObject is moved
	 * 			| if(time > 0)
	 * 			| then (new.getXPosition() == getXPosition() + getXVelocity()*time)&&
	 * 			|	   (new.getYPosition() == getYPosition() + getYVelocity()*time)
	 * 
	 * @throws	IllegalArgumentException
	 * 			The given value for the time is illegal
	 * 			| ! isValidTime(time)
	 */
	public void move(double time) throws IllegalArgumentException{
		if(!isValidTime(time))
			throw new IllegalArgumentException();
		
		double xPos = this.getXPosition() + time*this.getXVelocity();
		double yPos = this.getYPosition() + time*this.getYVelocity();
		
		this.setXPosition(xPos);
		this.setYPosition(yPos);
		
	}
	
	/**
	 * Check whether the given time is a valid time
	 * @param   time
	 * 			the time to check
	 * 
	 * @return 	True if and only if the time is nonnegative
	 * 			| result == time >= 0
	 */
	public static boolean isValidTime(double time){
		return time>=0;
	}
	
	
	/**
	 * Calculates the distance between the sides of two WorldObjects, if two WorldObjects overlap the distance is negative
	 * if other is the same WorldObject as the prime object, the distance is zero
	 * @param 	other
	 * 			the other WorldObject
	 * 
	 * @return 	the total distance between two WorldObjects, if the other WorldObject is also the prime object, the 
	 * 		   	distance returned is 0
	 * 			| if(other != this)
	 * 			|	then result == distance(this.getXPosition(), this.getYPosition(), 
	 *			| 	other.getXPosition(), other.getYPosition()) - other.getRadius() - this.getRadius()
	 *			| else if (other == this)
	 *			|	then result == 0
	 * @throws 	IllegalArgumentException
	 * 			| other == null
	 * 
	 * @throws 	ArithmeticException
	 * 			is thrown when a calculation caused overflow
	 * 			|causedOverflow()
	 */
	public double getDistanceBetween(WorldObject other) throws IllegalArgumentException, ArithmeticException{
		if(other == this)
			return 0;
		if(other == null)
			throw new IllegalArgumentException();
		double distanceToCenter = distance(this.getXPosition(), this.getYPosition(), 
				other.getXPosition(), other.getYPosition());
		double totalDistance = distanceToCenter - this.getSigma(other);
		
		if(causedOverflow(totalDistance))
			throw new ArithmeticException();
		
		return totalDistance;
	}
	
	/**
	 * calculates the distance between two points in space
	 * @param 	xCoord1
	 * 			The x-coordinate of the first point
	 * 
	 * @param 	yCoord1
	 * 			The y-coordinate of the first point
	 * 
	 * @param 	xCoord2
	 * 			The x-coordinate of the second point
	 * 
	 * @param 	yCoord2
	 * 			The y-coordinate of the second point
	 * 
	 * @return	The distance between the two points
	 * 			| Math.sqrt(Math.pow(xCoord1-xCoord2, 2) + Math.pow(yCoord1-yCoord2, 2))
	 * 
	 * @throws 	ArithMeticException
	 * 			|causedOverflow()
	 */
	@Model
	private static double distance(double xCoord1, double yCoord1, double xCoord2, double yCoord2) throws ArithmeticException{
		double distance = Math.sqrt(Math.pow(xCoord1-xCoord2, 2) + Math.pow(yCoord1-yCoord2, 2));
		
		if(causedOverflow(distance))
			throw new ArithmeticException();
		return distance;	
	}
	
	/**
	 * This function checks whether the two WorldObjects overlap
	 * @param other
	 * 
	 * @return 	true if and only if the WorldObjects overlap or the other WorldObject is the same as the primary object
	 * 			| if (other == this)
	 * 			| then true
	 * 			| else getDistanceBetween(other) <= 0
	 * 
	 * @throws IllegalArgumentException
	 * 			| other == 0
	 * @throws ArithmeticException
	 * 		    throws the exception if a calculation caused overflow
	 * 			| causedOverflow()
	 */
	public boolean overlap(WorldObject other) throws IllegalArgumentException, ArithmeticException{
		if(other == null)
			throw new IllegalArgumentException();
		if(other == this)
			return true;
		return this.getDistanceBetween(other) <= 0;
	}
	
	/**
	 * returns the sum of the radii of two WorldObjects 
	 * @param 	other
	 * 			the other WorldObject
	 * 
	 * @return 	the sum of the radii
	 * 			| result == this.getRadius() + other.getRadius()
	 * 
	 * @throws ArithmeticException
	 * 			throws exception when calculation resulted in an overflow
	 * 			|causedOverflow()
	 */
	public double getSigma(WorldObject other) throws ArithmeticException{
		double sigma = this.getRadius() + other.getRadius();
		
		if(causedOverflow(sigma))
			throw new ArithmeticException();
		return sigma;
	}

	
	/**
	 * Calculates the time it will take for two WorldObjects to collide if they keep their current trajectory
	 * @param	other
	 * 			the other WorldObject
	 * 
	 * @return	return the time of the collision
	 * 			|positions on collision are:
	 * 			|	xPos1 = this.getXPosition() + this.getXVelocity()*time
	 * 			|	yPos1 = this.getYPosition() + this.getYVelocity()*time
	 * 			|	xPos2 = other.getXPosition() + other.getXVelocity()*time
	 * 			|	yPos2 = other.getYPosition() + other.getYVelocity()*time
	 * 			|returns time such that:
	 * 			|	distance(XPos1, YPos1, XPos2, YPos2) == this.getSigma(other)
	 * 
	 * @throws 	IllegalArgumentException
	 * 			| other == null || this.overlap(other)
	 * 
	 * @throws 	ArithmeticException
	 * 			throws the exception if a calculation caused overflow
	 * 			| causedOverflow()
	 */
	public double getTimeToCollision(WorldObject other) throws IllegalArgumentException, ArithmeticException{
		
		if (other == null)
			throw new IllegalArgumentException();
		
		if (this.overlap(other))
			throw new IllegalArgumentException();
		
		double[] deltaR = this.getDeltaR(other);
		double[] deltaV = this.getDeltaV(other);
		
		if(dotProduct2D(deltaV, deltaR) >= 0)
			return Double.POSITIVE_INFINITY;
		
		// check if the calculations for d cause over or underflow
		double d = calculateD(other, deltaR, deltaV);
		
		if (d <= 0)
			return Double.POSITIVE_INFINITY;
		else{
			
			double time = -(dotProduct2D(deltaV, deltaR) + Math.sqrt(d))/dotProduct2D(deltaV,deltaV);
			if(causedOverflow(time))
				throw new ArithmeticException();
			
			return time;
		}
	}	
	

	/**
	 * Calculates the d value for the collision time calculation
	 * @param 	other
	 * 			the other WorldObject
	 * 
	 * @param 	deltaR
	 * 			the vector that points from the center of the first WorldObject to the other WorldObject
	 * 
	 * @param 	deltaV
	 * 			the vector of the difference in speed of both WorldObjects
	 * 
	 * @return 	the value d used in the collision time method
	 * 			| result == Math.pow(dotProduct2D(deltaV, deltaR),2) - 
	 *			| (dotProduct2D(deltaR, deltaR) - Math.pow(this.getSigma(other), 2))*dotProduct2D(deltaV, deltaV)
	 *
	 * @throws 	ArithmeticException
	 * 			throws the exception if a calculation caused overflow
	 * 			| causedOverflow()
	 * 
	 * @throws 	IllegalArgumentexception
	 * 			|other == null
	 */
	private double calculateD(WorldObject other, double[] deltaR, double[] deltaV) throws ArithmeticException, IllegalArgumentException{
		
		if(other == null)
			throw new IllegalArgumentException();
		
		double result = Math.pow(dotProduct2D(deltaV, deltaR),2) - 
				(dotProduct2D(deltaR, deltaR) - Math.pow(this.getSigma(other), 2))*dotProduct2D(deltaV, deltaV);
		
		if(causedOverflow(result))
			throw new ArithmeticException();
		
		return result;
	}
	
	/**
	 * Calculates the position of a collision
	 * @param 	other
	 * 			the other WorldObject
	 * 
	 * @return  the position of the collision
	 * @return	return the time of the collision
	 * 			|positions on collision are:
	 * 			|	xPos1 = this.getXPosition() + this.getXVelocity()*time
	 * 			|	yPos1 = this.getYPosition() + this.getYVelocity()*time
	 * 			|	xPos2 = other.getXPosition() + other.getXVelocity()*time
	 * 			|	yPos2 = other.getYPosition() + other.getYVelocity()*time
	 * 			|calculates time such that:
	 * 			|	distance(XPos1, YPos1, XPos2, YPos2) == this.getSigma(other)
	 *          |
	 * 			|using the calculated time determine xCol, yCol the coordinates
	 * 			|of the collision such that:
	 * 			|   distance(xPos1, yPos1, xCol, yCol) == this.getRadius() && 
	 * 			|   distance(xPos2,yPos2, xCol, yCol) == other.getRadius()
	 * 
	 * @throws 	ArithmeticException
	 * 			throws the exception if a calculation caused overflow
	 * 			|causedOverflow()
	 * 
	 * @throws 	IllegalArgumentException
	 * 			| other == null
	 */
	public double[] getCollisionPosition(WorldObject other)throws ArithmeticException, IllegalArgumentException{
		
		if(other == null)
			throw new IllegalArgumentException();
		
		double time = this.getTimeToCollision(other);
		
		if(time == Double.POSITIVE_INFINITY){
			return null;
		}
		
		double[] thisWorldObjectPos = {this.getXPosition() + this.getXVelocity()*time, this.getYPosition() + this.getYVelocity()*time};
		double[] otherWorldObjectPos = {other.getXPosition() + other.getXVelocity()*time, other.getYPosition() + other.getYVelocity()*time};
		
		if(causedOverflow(thisWorldObjectPos[0])||causedOverflow(thisWorldObjectPos[1])||
				causedOverflow(otherWorldObjectPos[0])||causedOverflow(otherWorldObjectPos[1]))
			throw new ArithmeticException();
		
		double radiusRatio = this.getRadius()/(this.getSigma(other));
		
		if(causedOverflow(radiusRatio))
			throw new ArithmeticException();
		
		
		double[] collisionPos = {(otherWorldObjectPos[0] - thisWorldObjectPos[0])*radiusRatio + thisWorldObjectPos[0], 
				(otherWorldObjectPos[1] - thisWorldObjectPos[1])*radiusRatio + thisWorldObjectPos[1]};

		if(causedOverflow(collisionPos[0])||causedOverflow(collisionPos[1]))
			throw new ArithmeticException();
		
		return collisionPos;
	}
	

	
	/**
	 * Calculates the vector that connects the centers of the
	 * two WorldObjects
	 * @param 	other
	 * 			the other WorldObject
	 * @return 	the vector that connects the two centers of the WorldObject
	 * 			| result == { other.getXPosition() - this.getXPosition(), 
	 * 			|             other.getYPosition() - this.getYPosition()}
	 * 
	 * @throws  IllegalArgumentException
	 * 			throws the exception whether a subtraction causes overflow
	 * 			| causedOverflow() 
	 * 
	 * @throws 	NullPointerException
	 * 			throws the exception if the supplied other is a null reference
	 * 			| other == null
	 */
	
	private double[] getDeltaR(WorldObject other) throws IllegalArgumentException, ArithmeticException{
		
		if(other == null)
			throw new IllegalArgumentException();
		
		// Acquire the coordinates of the first WorldObject
		double x1 = this.getXPosition();
		double y1 = this.getYPosition();
		
		//  Acquire the coordinates of the other WorldObject
		double x2 = other.getXPosition();
		double y2 = other.getYPosition();
		
		double[] deltaR = {x2-x1, y2-y1};
		
		if(causedOverflow(deltaR[0])||causedOverflow(deltaR[1]))
			throw new ArithmeticException();
			
		return deltaR;
		
	}

	
	/**
	 * Calculates the difference in speed vectors of the two WorldObjects
	 * @param 	other
	 * 			the other WorldObject
	 * 
	 * @return 	the difference in speed vectors of the two WorldObjects in an array
	 * 			|result == {other.getXVelocity() - this.getXVelocity(),
	 * 						other.getYVelocity() - this.getYvelocity()}
	 * 
	 * @throws 	NullPointerException
	 * 			| other == null
	 * 
	 * @throws 	IllegalArgumentException
	 * 			| causedOverflow()
	 */
	private double[] getDeltaV(WorldObject other) throws ArithmeticException, IllegalArgumentException{
		
		if(other == null)
			throw new IllegalArgumentException();
		
		// Acquire the velocity of the first WorldObject
		double vx1 = this.getXVelocity();
		double vy1 = this.getYVelocity();
		
		// Acquire the velocity of the other WorldObject
		double vx2 = other.getXVelocity();
		double vy2 = other.getYVelocity();
		
		double[] deltaV = {vx2-vx1, vy2-vy1};
		
		if(causedOverflow(deltaV[0])||causedOverflow(deltaV[1]))
			throw new ArithmeticException();
		
		return deltaV;
		
	}
	
	/**
	 * Calculates the dot product of two 2D vectors
	 * @param 	vector1
	 * 			the first 2D vector in array format
	 * 
	 * @param 	vector2
	 * 			the second 2D vector in array format
	 * 
	 * @return	the dot product of the two supplied 2D vectors
	 *			| result == vector1[0]*vector2[0] + vector1[1]*vector2[1]
	 *
	 * @throws 	IllegalArgumentException
	 * 			| vector1.length == 0 || vector2.length == 0||vector1 == null || vector2 == null 
	 * 
	 * @throws 	ArithmeticException
	 * 			| causedOverflow()
	 */
	private static double dotProduct2D(double[] vector1, double[] vector2) throws IllegalArgumentException, ArithmeticException{
		
		// check if null reference
		if(vector1 == null || vector2 == null)
			throw new IllegalArgumentException();
		
		// check if empty arrays
		if( vector1.length == 0 || vector2.length == 0)
			throw new IllegalArgumentException();
		// extract the x parts of the vectors
		double x1 = vector1[0];
		double x2 = vector2[0];
		
		// extract the y parts of the vectors
		double y1 = vector1[1];
		double y2 = vector2[1];
		
		// do the actual dot product
		double xPart = x1*x2;
		double yPart = y1*y2;
		
		double sum = xPart + yPart;
		if(causedOverflow(sum))
			throw new ArithmeticException();
		
		return sum;
	}
	
	/**
	 * checks if a calculation caused overflow
	 * @param a
	 * 
	 * @return 	true if and only if the result of a calculation was overflow
	 * 			| result == (a==Double.POSITIVE_INFINITY)||(a==Double.NEGATIVE_INFINITY)||(a == Double.NaN)
	 */
	public static boolean causedOverflow(double a){
		return ((Double.isInfinite(a))||(Double.isNaN(a)));
		
	}
	
	// before implementing this method, create a function to set a WorldObject into a world
	public double boundryCollision(){
		return 1d;
	}



}
