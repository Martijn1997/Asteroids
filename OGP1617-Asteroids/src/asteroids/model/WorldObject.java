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
// de overlap functie aanpassen om met de 99% overlap overweg te kunnen

/*
 * Design choices:
 * If a bullet is loaded on a ship, the association between the ship and the bullet is a bidirectional relationship
 * this because the totalMass of the ship is calculated as the mass of the ship itself + the mass of the bullets.
 * If a bullet is shot into the world, the association between the bullet and the Ship becomes an unidirectional relationship
 * where only the bullet knows about the association with the ship
 */
/**
 * A class that represents the World Object
 * @author Martijn & Flor
 * @Invar  	The velocity of a WorldObject is always lower than the LIGHT_SPEED
 * 			|isValidVelocity()
 * 
 * @Invar	The radius will always be larger than or equal to the specified minimum radius
 * 			|isValidRadius()
 * 
 * @Invar   The Density will always be larger than or equal to the specified minimum
 * 			|isValidDensity()
 * 
 * @Invar   The mass will always be larger than or equal to the specified minimum
 * 			|canHaveAsMass()
 */
public abstract class WorldObject {
	
	
	/**
	 * Initializes an object of the WorldObject class
	 * @param 	xPos
	 * 			the x position of the WorldObject
	 * @param	yPos
	 * 			the y position of the WorldObject
	 * @param 	radius
	 * 			the Radius of the WorldObject
	 * @param 	orientation
	 * 			the orientation of the WorldObject
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
	 * 
	 * @effect 	the density is set to the given density
	 * 			| setDensity(density)
	 * 
	 * @effect 	the mass is set to the given mass
	 * 			| setMass(mass)
	 */
	@Model @Raw
	protected WorldObject(double xPos, double yPos, double radius, double xVel, double yVel, double density)throws IllegalArgumentException{
		this.setRadius(radius);
		this.setPosition(xPos, yPos);
		this.setVelocity(xVel, yVel);
		this.setDensity(density);
		this.setMass();
	}
		
	protected static final double EPSILON = 0.0001;
	
	/**
	 * default constructor for a WorldObject object
	 * @effect WorldObject(0,0,10,0,0)
	 */
	protected WorldObject(){
		this(0d,0d,10d,0,0,1);
	}
	
	/**
	 * Terminator for a world object
	 * @effect  removes the world object from the associated world
	 * 			|getWorld().removeFromWorld(this)
	 * 
	 * @post	the association between the World object and the world is broken
	 * 			|new.getWorld() == null
	 * 
	 * @post	the world object position vector is set to a null reference
	 * 			|new.getPosition() == null
	 * 
	 * @post 	the world object velocity vector is set to a null reference
	 * 			|new.getVelocity() == null
	 * 
	 * @post	the flag that the object is terminated is raised
	 * 			|new.isTerminated() == true
	 * 
	 */
	public void terminate(){
		this.terminated = true;
		if(this.getWorld() != null)
			this.getWorld().removeFromWorld(this);
		this.associatedWorld = null;
//		this.position = null;
//		this.velocity = null;
		
	}
	
	/**
	 * checker if a world object is terminated
	 * @return if the world object is terminated or not
	 */
	public boolean isTerminated(){
		return this.terminated;
	}
	
	private boolean terminated = false;
	
	/**
	 * Basic setter for the position
	 * 
	 * @param 	xPos
	 * 			The x position of the WorldObject
	 * @param 	yPos
	 * 			The y position of the WorldObject
	 * 
	 * @post	The position of the WorldObject is set to xPos and yPos for 
	 * 			the x and y position respectively
	 * 			| new.getPosition() == Vector2D(xPos, yPos)
	 * 
	 * @throws	IllegalArgumentException
	 * 			Thrown if the xPos or yPos are not valid positions
	 * 			|!(isValidPosition(xPos)||!isValidPosition(yPos)
	 */
	@Basic @Raw
	public void setPosition(double xPos, double yPos)throws IllegalArgumentException{
		if(!isValidPosition(xPos,yPos))
			throw new IllegalArgumentException();
		this.position = new Vector2D(xPos, yPos);	
	}
	
	/**
	 * Basic getter for the position
	 * @return the position of the WorldObject
	 */
	@Basic @Raw
	public Vector2D getPosition(){
		return this.position;
	}
	
	/**
	 * Returns the current x position of the WorldObject
	 */
	@Basic @Raw
	public double getXPosition(){	
		return this.position.getXComponent();
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
		if(!isValidPosition(xPos, this.getYPosition()))
			throw new IllegalArgumentException();
		this.position = new Vector2D(xPos,this.getYPosition());
	}
	
	/**
	 * Checks if the Position is a Valid position
	 * @return 	returns true if the the world object is not associated with a world
	 * 			and the position is not NaN
	 * 			|if(this.getWorld() == null && !isNan(xPos) && !isNaN(yPos))
	 * 			|then result == true
	 * @return 	returns true if the world object lies within the associated world bounds
	 * 			|if(this.getWorld() != null)
	 * 			|then @see implementation
	 */
	@Basic @Raw
	public boolean isValidPosition(double xPos,double yPos){
		if(Double.isNaN(xPos) || Double.isNaN(yPos))
			return false;
		if(this.getWorld() == null)
			return true;
		World thisWorld = this.getWorld();
//		if(thisWorld.getWidth()< xPos || thisWorld.getHeight()< yPos 
//				|| 0 > xPos || 0 > yPos)
//			return false;
//		else
			return true;
	}
	
	/**
	 * returns the y position of the WorldObject
	 */
	@Basic @Raw
	public double getYPosition(){	
		return this.position.getYComponent();
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
	 * 			| !isValidPosition(getXPosition(), yPos)
	 */
	@Basic @Raw
	public void setYPosition(double yPos) throws IllegalArgumentException{
		if(!isValidPosition(this.getXPosition(), yPos))
			throw new IllegalArgumentException();
		this.position = new Vector2D(this.getXPosition(),yPos);
	}
	
	private Vector2D position;
	
	
	/**
	 * Basic getter for the velocity vector
	 */
	public Vector2D getVelocity(){
		return this.velocity;
	}
	
	/**
	 * Returns the x velocity of the WorldObject
	 */
	@Basic @Raw
	public double getXVelocity(){
		return this.velocity.getXComponent();
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
	@Basic @Raw @Deprecated
	public void setXVelocity(double xVel){
		
		if(isValidTotalVelocity(totalVelocity(xVel, this.getYVelocity())))
			this.velocity = new Vector2D(xVel, this.getYVelocity());
		
	}
	
	
	/**
	 * Returns the y velocity of the WorldObject
	 */
	@Basic @Raw
	public double getYVelocity(){
		return this.velocity.getYComponent();
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
	@Basic @Raw @Deprecated
	public void setYVelocity(double yVel){
		if(isValidTotalVelocity(totalVelocity(this.getXVelocity(),yVel)))
			this.velocity = new Vector2D(this.getXVelocity(), yVel);
		
	}
	
	
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
	 * 
	 * @post 	if the supplied total velocity is larger than the speed of light rescale the velocity
	 * 			| if !isValidVelocity(totalVelocity(xVel,yVel))
	 * 			| then 	new.getXVelocity() == xVel* LIGHT_SPEED/totalVelocity(xVel,yVel)
	 * 			|		new.getYVelocity() == yVel* LIGHT_SPEED/totalVelocity(xVel,yVel)
	 */
	@Basic @Raw
	public void setVelocity(double xVel, double yVel){
		double totalVelocity = totalVelocity(xVel, yVel);
		if (isValidTotalVelocity(totalVelocity)){
			this.velocity = new Vector2D(xVel, yVel);
		} 
		else {
			double rescaleConstant = totalVelocity/(LIGHT_SPEED);
			xVel /= rescaleConstant;
			yVel /= rescaleConstant;
			
			this.velocity = new Vector2D(xVel, yVel);
		}
	}
	
	private Vector2D velocity;

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
	public static boolean isValidTotalVelocity(double totalVel){
		return (totalVel <= LIGHT_SPEED&&totalVel >= 0);
	}
	
	/**
	 * constant: light speed
	 */
	public static final double LIGHT_SPEED = 300000;

	
	/**
	 * @return 	the radius of the WorldObject
	 */
	@Basic @Raw @Immutable
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
	 * @throws  IllegalStateException
	 * 			| !hasUninitializedRadius()
	 */
	@Basic @Immutable @Raw
	public void setRadius(double rad) throws IllegalArgumentException{
			if(! this.isValidRadius(rad))
				throw new IllegalArgumentException();
			if(! this.hasUnInitalizedRadius())
				throw new IllegalStateException();
			
			this.radius = rad;
			
	}
	
	/**
	 * checks if the radius is initialized
	 * @return 	true if and only if the radius is uninitialized
	 * 			| result == this.getRadius() == 0;
	 */
	@Model @Raw
	private boolean hasUnInitalizedRadius(){
		return  this.getRadius() == 0;
	}
	
	public abstract double getMinimumRadius();
	
	/**
	 * checks whether the provided radius is valid
	 * @param 	rad
	 * 			the radius
	 * 
	 * @return |result == (rad >= MIN_RADIUS)
	 */
	public abstract boolean isValidRadius(double rad);
	
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
	 * @post	the object density equals to the provided density
	 * 			| if(canHaveAsDensity(density))
	 * 			| then new.getDensity() == density
	 * @post	if the density is not valid set the density to the minimum density
	 * 			| if(!canHaveAsDensity(density)
	 * 			| then new.getDensity() == getMinimumDensity()
	 */
	@Raw @Basic
	public void setDensity(double density){
		if(this.isValidDensity(density))
			this.Density = density;
		else
			this.Density = this.getMinimumDensity();
		this.setMass();
	}
	
	/**
	 * checker to check if the density is valid
	 */
	public abstract boolean isValidDensity(double density);
	
	public abstract double getMinimumDensity();
	
	// mass is variable so no final value
	private double Density;
	
	public double getMass(){
		return this.mass;
	}
	
	/**
	 * sets the mass of a world object
	 * @effect 	the mass is set according to the density and the radius of the world object
	 * 		 	|new.getMass == calcMass();
	 * 
	 * @effect 	if the radius of the world object is not initialized the radius is set to the minimal radius
	 * 			|if(hasUninitializedRadius())
	 * 			|then 	setRadius(getMinRadius);
	 * 			|		new.getMass == calcMass;
	 */
	@Raw @Basic
	public void setMass(){
		
		if(!this.hasUnInitalizedRadius()){
			this.mass = this.calcMass();
		}else{
			this.setRadius(this.getMinimumRadius());
			this.mass = this.calcMass();
		}
			
//		if(this.canHaveAsMass(mass))
//			this.mass = mass;
//		else{
//			if(this.isValidDensity(this.getDensity())&&this.isValidRadius(this.getRadius())){
//				this.mass = calcMinMass();
//			}
//			else
//				this.mass = volumeSphere(this.getMinimumRadius())*this.getMinimumDensity();
//		}
	}
	
	/**
	 * The mass of a World Object
	 */
	private double mass;
	
	
	/**
	 * calculates the volume of a sphere with the given radius
	 * @param radius
	 * @return 	the volume of a sphere with the given radius
	 * 			| result == 4/3*Math.PI*Math.pow(radius,3)
	 */
	public static double volumeSphere(double radius){
		return 4.0/3.0 * Math.PI*Math.pow(radius,3);
	}
	
	/**
	 * calculate the minimum mass of the given object
	 * @return	the minimum mass of an object
	 * 			| result == volumeSphere(getRadius()) * getDensity()
	 */
	@Model
	protected double calcMass(){
		return volumeSphere(this.getRadius())*this.getDensity();
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
		
		Vector2D newPosVector = this.getPosition().vectorSum(this.getVelocity().rescale(time));
		
//		double xPos = this.getXPosition() + time*this.getXVelocity();
//		double yPos = this.getYPosition() + time*this.getYVelocity();
		
		this.setPosition(newPosVector.getXComponent(), newPosVector.getYComponent());
		
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
	 * @return 	the distance between the borders of two world objects
	 * 			|result == getPosition().distanceTo(other.getPosition()) - getSigma(other)
	 * 
	 * @return  zero if the other object is the same as the prime object
	 * 			|if(other == this)
	 * 			|then result == 0
	 * 
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
		double distanceToCenter = this.getPosition().distanceTo(other.getPosition());//distance(this.getXPosition(), this.getYPosition(), 
				//other.getXPosition(), other.getYPosition());
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
	@Model @Deprecated
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
	 * @return 	true if the other WorldObject is the same as the primary object
	 * 			| result == (other == this)
	 * 
	 * @return 	true if  the WorldObjects overlap
	 * 			|result == getDistanceBetween(other) <= 0
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
		
		Vector2D deltaR = this.getPosition().difference(other.getPosition());
		Vector2D deltaV = this.getVelocity().difference(other.getVelocity());
		
//		double[] deltaR = this.getDeltaR(other);
//		double[] deltaV = this.getDeltaV(other);
//		dotProduct2D(deltaV, deltaR) >= 0
		
		if(deltaV.dotProduct(deltaR)>=0)
			return Double.POSITIVE_INFINITY;
		
		// check if the calculations for d cause over or underflow
		double d = calculateD(other, deltaR, deltaV);
		
		if (d <= 0)
			return Double.POSITIVE_INFINITY;
		else{
			
			double time = -(deltaV.dotProduct(deltaR) + Math.sqrt(d))/deltaV.dotProduct(deltaV);
			if(causedOverflow(time))
				throw new ArithmeticException();
			
			return time;
		}
	}	
	
	public double getTimeToCollision(World world){
		if(world == null)
			return Double.POSITIVE_INFINITY;
		
		double xTime = Double.POSITIVE_INFINITY;
		double yTime = Double.POSITIVE_INFINITY;
		
		// first calculate the time needed to hit the x boundary
		if(Math.signum(this.getXVelocity())>0 && this.getXVelocity()>0){
			// the boundary is on the right
			xTime = Math.abs(world.getWidth() - (this.getXPosition()+this.getRadius()))/this.getXVelocity();
		}
		else if(this.getXVelocity()<0){
			// boundary on the left
			xTime =  (this.getXPosition()-this.getRadius())/Math.abs(this.getXVelocity());
		}
		// calculate the time needed to hit the y boundary
		if(Math.signum(this.getYVelocity())>0 &&this.getYVelocity()>0){
			// boundary on top
			yTime = Math.abs(world.getHeight()- (this.getYPosition()+this.getRadius()))/this.getYVelocity();
		}
		else if(this.getYVelocity()<0){
			// boundary on bottom
			yTime = (this.getYPosition()-this.getRadius())/Math.abs(this.getYVelocity());
		}
		
		return Math.min(xTime, yTime);
	}
		
//		if(other == null)
//			return Double.POSITIVE_INFINITY;
//		
//		// calculate the collision time for the x velocity and the y velocity seperatly
//		double xPosWO = this.getXPosition();
//		double yPosWO = this.getYPosition();
//		double xVelWO = this.getXVelocity();
//		double yVelWO = this.getYVelocity();
//		double RadiusWO = this.getRadius();
//		
//		// initialize the time components
//		double xTime;
//		double yTime;
//		
//		// calculate the time needed to get to the boundary using the xComponent
//		// first case: right boundary
//		if(Math.signum(xVelWO)>0){
//			xTime = this.calculateLinearCollisionTime(xPosWO + RadiusWO, other.getWidth(), xVelWO);
//		}
//		// case left boundary
//		else
//		{
//			xTime = this.calculateLinearCollisionTime(xPosWO + RadiusWO, 0, xVelWO);
//		}
//		
//		// calculate the time needed to get to the boundary using the yComponent
//		// first case: collision with the top boundary
//		if(Math.signum(yVelWO)>0){
//			yTime = this.calculateLinearCollisionTime(yPosWO + RadiusWO, other.getHeight(), yVelWO);
//		}
//		// case bottom boundary
//		else
//		{
//			yTime = this.calculateLinearCollisionTime(yPosWO + RadiusWO, 0, yVelWO);
//		}
//		
//		//Select the shortest time
//		if(xTime>=yTime)
//			return xTime;
//		else
//			return yTime;	

	
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
		
		Vector2D thisWorldObjectPos = this.getPosition().vectorSum(this.getVelocity().rescale(time));
		Vector2D otherWorldObjectPos = other.getPosition().vectorSum(other.getVelocity().rescale(time));
		
		if(causedOverflow(thisWorldObjectPos.getXComponent())||causedOverflow(thisWorldObjectPos.getYComponent())||
				causedOverflow(otherWorldObjectPos.getXComponent())||causedOverflow(otherWorldObjectPos.getYComponent()))
			throw new ArithmeticException();
		
		double radiusRatio = this.getRadius()/(this.getSigma(other));
		
		if(causedOverflow(radiusRatio))
			throw new ArithmeticException();
		
		Vector2D collisionPos = (otherWorldObjectPos.difference(thisWorldObjectPos).rescale(radiusRatio)).vectorSum(thisWorldObjectPos);
		
//		double[] collisionPos = {(otherWorldObjectPos[0] - thisWorldObjectPos[0])*radiusRatio + thisWorldObjectPos[0], 
//				(otherWorldObjectPos[1] - thisWorldObjectPos[1])*radiusRatio + thisWorldObjectPos[1]};

		if(causedOverflow(collisionPos.getXComponent())||causedOverflow(collisionPos.getYComponent()))
			throw new ArithmeticException();
		
		return collisionPos.getVector2DArray();
	}
	
	
	
	public double[] getCollisionPosition(World world){
		
		double time = this.getTimeToCollision(world);
		
		Vector2D center = this.getPosition().vectorSum(this.getVelocity().rescale(time));
		
		// initialize all the possible collisions
		Vector2D[] possibleCollisions = {new Vector2D(world.getWidth(), center.getYComponent()), new Vector2D(0, center.getYComponent()),
				new Vector2D(center.getXComponent(),world.getHeight()), new Vector2D(center.getXComponent(), 0)};
		
		//check if an collision happens
		for(Vector2D collision: possibleCollisions){
			if(doubleEquals(center.distanceTo(collision),this.getRadius())){
				return collision.getVector2DArray();
			}
		}
		
		return new double [] {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};
			
		}
//		double collisionTime = this.getTimeToCollision(world);
//		if(collisionTime == Double.POSITIVE_INFINITY)
//			return null;
//		
//		double WOXPosAtCollision = this.getXPosition() + this.getXVelocity()*collisionTime;
//		double WOYPosAtCollision = this.getYPosition() + this.getYVelocity()*collisionTime;
//		double WORadius = this.getRadius();
//		
//		double xCollision;
//		double yCollision;
//		
//		// check if the ship collides with the right boundary of the world
//		if(0<=(WOXPosAtCollision+WORadius)-world.getWidth()&&(WOXPosAtCollision+WORadius)-world.getWidth()<EPSILON){
//			xCollision = WOXPosAtCollision + WORadius;
//			yCollision = WOYPosAtCollision;
//			
//		// check if the ship collides with the left boundary of the world
//		}else if(0<= WOXPosAtCollision-WORadius && WOXPosAtCollision-WORadius < EPSILON){
//			xCollision = WOXPosAtCollision - WORadius;
//			yCollision = WOYPosAtCollision;
//		// check if the ship collides with the bottom boundary of the world
//		}else if(0<= WOYPosAtCollision-WORadius && WOYPosAtCollision-WORadius < EPSILON){
//			xCollision = WOXPosAtCollision;
//			yCollision = WOYPosAtCollision - WORadius;
//		}
//		// in all other cases the ship collides with the top of the world
//		else{
//			xCollision = WOXPosAtCollision;
//			yCollision = WOYPosAtCollision + WORadius;
//		}
//		
//		// create the return array
//		double[] collisionPosition = {xCollision, yCollision};
//		
//		return collisionPosition;
//	
//	public double calculateLinearCollisionTime(double pos1,double pos2,double objVel){
//		return (pos1-pos2)/(objVel);
//	}
//	

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
	private double calculateD(WorldObject other, Vector2D deltaR, Vector2D deltaV) throws ArithmeticException, IllegalArgumentException{
		
		if(other == null)
			throw new IllegalArgumentException();
		
		double result = Math.pow(deltaV.dotProduct(deltaR),2) - 
				(deltaR.dotProduct(deltaR) - Math.pow(this.getSigma(other), 2))*deltaV.dotProduct(deltaV);
		
		if(causedOverflow(result))
			throw new ArithmeticException();
		
		return result;
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
	@Deprecated
	protected double[] getDeltaR(WorldObject other) throws IllegalArgumentException, ArithmeticException{
		
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
	@Deprecated
	protected double[] getDeltaV(WorldObject other) throws ArithmeticException, IllegalArgumentException{
		
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
	@Deprecated
	protected static double dotProduct2D(double[] vector1, double[] vector2) throws IllegalArgumentException, ArithmeticException{
		
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
	
	/**
	 * Return the associated world (no clone)
	 * @return the associated world
	 * 			|this.associatedWorld
	 */
	public World getWorld(){
		return this.associatedWorld;
	}
	
	/**
	 * Sets the world wherein the worldObject resides
	 * @param 	world
	 * 	
	 * @Post	The WorldObject is set into the world
	 * 			| new.getWorld() == world
	 * 
	 * @throws 	IllegalArgumentException
	 * 			thrown is the worldObject cannot have world as World
	 * 			|!canHaveAsWorld(world)
	 */
	public void setWorld(World world) throws IllegalArgumentException{
		if(!canHaveAsWorld(world))
			throw new IllegalArgumentException();
		this.associatedWorld = world;
		
	}
	
	public abstract boolean canHaveAsWorld(World world);
	
	public abstract void resolveCollision(Ship ship);
	
	public abstract void resolveCollision(Bullet bullet);
	
	/**
	 * Resolve the collision between a ship and a world boundary
	 * 
	 * @param 	world
	 * 			the world where the ship collides with
	 * 
	 * @post 
	 * 
	 * @throws 	IllegalArgumentException
	 * 			thrown if the provided world is a null reference or the WorldObject isn't located in the world
	 * 			| world == null || this.getWorld() != world
	 * 			
	 * @throws 	IllegalStateException
	 * 			thrown if the WorldObject isn't colliding with a boundary
	 * 			| {this.getXPosition(), this.getYPosition} != this.getCollisionPosition(world)
	 */
	public void resolveCollision(World world)throws IllegalArgumentException, IllegalStateException{
		
		if(world==null)
			throw new IllegalArgumentException();
		
		if(this.getWorld() != world)
			throw new IllegalArgumentException();
		
		double[] collisionPosition = this.getCollisionPosition(world);
		
//		if(!(doubleEquals(collisionPosition[0], this.getXPosition())&&doubleEquals(collisionPosition[0], this.getYPosition())))
//			throw new IllegalStateException();
		
		double worldWidth = world.getWidth();
		
		double[] currentVelocity = {this.getXVelocity(), this.getYVelocity()};
		
	
		if(doubleEquals(collisionPosition[0], worldWidth)||doubleEquals(collisionPosition[0],0))
			if(doubleEquals(collisionPosition[0],collisionPosition[1]))
				this.setVelocity(-currentVelocity[0],-currentVelocity[1]);
			else
				this.setVelocity(-currentVelocity[0], currentVelocity[1]);
		else
			this.setVelocity(currentVelocity[0], -currentVelocity[1]);	
	}
	
	private World associatedWorld;
	
	public static boolean doubleEquals(double a, double b){
		return a-EPSILON <= b && a+EPSILON >= b;
	}

}
