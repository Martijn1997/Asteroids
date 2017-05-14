package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;


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
 * 
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
 * 
 * @Invar	If a World object is associated with a world, it shall never go outside it's boundary
 * 			|isValidPosition();
 * 
 * @Invar	worldObject will always have a valid world associated
 * 			|canHaveAsWorld()
 * 
 */
public abstract class WorldObject {
	
	
	/**
	 * Constructor of a WorldObject
	 * @param 	xPos
	 * 			the desired x-position of the WorldObject
	 * 
	 * @param 	yPos
	 * 			the desired y-position of the WorldObject
	 * 
	 * @param 	radius
	 * 			the desired radius of the WorldObject
	 * 
	 * @param	xVel
	 * 			the desired x-velocity of the WorldObject
	 * 
	 * @param 	yVel
	 * 			the desired y-velocity of the WorldObject
	 * 
	 * @param 	density
	 * 			the desired density of the WorldObject
	 * 
	 * @effect  the position of the World object is set to the provided param xPos and yPos
	 * 			| setPosition(xPos,yPos)
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
	 * @throws 	IllegalArgumentException
	 * 			thrown if the provided radius isn't effective
	 * 			| set
	 */
	@Model @Raw
	protected WorldObject(double xPos, double yPos, double radius, double xVel, double yVel, double mass)throws IllegalArgumentException{
		// radius needs to be set first for the density to work
		this.setRadius(radius);
		this.setPosition(xPos, yPos);
		this.setVelocity(xVel, yVel);
		this.setMass(mass);
	}
	
	
	/**
	 * default constructor for a WorldObject object
	 * @effect WorldObject(0,0,10,0,0,0)
	 */
	protected WorldObject(){
		this(0d,0d,10d,0,0,0);
	}
	
	/**
	 * Terminator for a world object
	 * @effect  if the object is associated with a world remove it from the associated world
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
	 */
	@Basic
	public void terminate(){
		this.terminated = true;

		if(this.getWorld() != null){
			this.getWorld().removeFromWorld(this);
		}
		this.position = null;
		this.velocity = null;
		
		this.associatedWorld = null;
	}
	
	
	/**
	 * checks if a world object is terminated
	 * @return if the world object is terminated or not
	 */
	@Basic
	public boolean isTerminated(){
		return this.terminated;
	}
	
	/**
	 * flag for a terminated object
	 */
	private boolean terminated = false;
	
	
	/**
	 * Basic setter for the position
	 * 
	 * @param 	xPos
	 * 			The desired x-position of the WorldObject
	 * 
	 * @param 	yPos
	 * 			The desired y-position of the WorldObject
	 * 
	 * @post	The position of the WorldObject is set to xPos and yPos for 
	 * 			the x and y position respectively
	 * 			| new.getPosition() == Vector2D(xPos, yPos)
	 * 
	 * @throws	IllegalArgumentException
	 * 			Thrown if the xPos or yPos are not valid positions
	 * 			|!isValidPos(xPos, yPos)
	 */
	@Basic @Raw
	public void setPosition(double xPos, double yPos)throws IllegalArgumentException{
		if(!canHaveAsPosition(xPos,yPos)){
			throw new IllegalArgumentException();
		}
		Vector2D oldPosition = this.getPosition();
		this.position = new Vector2D(xPos, yPos);
		if(this.residesInWorld()){
			this.getWorld().updatePosition(oldPosition, this);
		}
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
	 * 			|new.getXPosition() == xPos
	 * 
	 * @throws 	IllegalArgumentexception
	 * 			| !isValidposition(xPos, getYposition())
	 */
	@Basic @Raw @Deprecated
	public void setXPosition(double xPos) throws IllegalArgumentException{
		if(!canHaveAsPosition(xPos, this.getYPosition()))
			throw new IllegalArgumentException();
		this.position = new Vector2D(xPos,this.getYPosition());
	}
	
	/**
	 * Checks if the provided Position is a Valid position
	 * 
	 * @return 	returns true if the the world object is not associated with a world
	 * 			and the position is not NaN
	 * 			|if(!this.residesInWorld() && !isNan(xPos) && !isNaN(yPos)&&
	 * 			|		!Double.isInfinite(xPos)&&!Double.isInfinite(yPos))
	 * 			|then result == true
	 * 
	 * @effect 	returns true if the world object lies within the associated world bounds
	 * 			|if(this.residesInWorld)
	 * 			|then result == this.getWorld().withinBoundary(this)
	 */
	@Basic @Raw
	public boolean canHaveAsPosition(double xPos,double yPos){
		if(Double.isNaN(xPos) || Double.isNaN(yPos))
			return false;
		if(!this.residesInWorld())
			return true;
		return this.getWorld().withinBoundary(xPos, yPos, this);
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
	@Basic @Raw @Deprecated
	public void setYPosition(double yPos) throws IllegalArgumentException{
		if(!canHaveAsPosition(this.getXPosition(), yPos))
			throw new IllegalArgumentException();
		this.position = new Vector2D(this.getXPosition(),yPos);
	}
	
	
	/**
	 * The Vector2D object that stores the position
	 */
	private Vector2D position;
	
	
	/**
	 * Basic getter for the velocity vector
	 */
	@Basic @Raw
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
	 * @effect	set the velocity to xVel for the x velocity
	 * 			|setVelocity(xVel, getYVelocity())
	 */
	@Basic @Raw @Deprecated
	public void setXVelocity(double xVel){	
		this.setVelocity(xVel, this.getYVelocity());
		
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
	 * @effect	set the velocity to yVel for the y velocity
	 * 			|setVelocity(getXVelocity(), yVel )
	 */
	@Basic @Raw @Deprecated
	public void setYVelocity(double yVel){
		this.setVelocity(this.getXVelocity(), yVel);	
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
	 * @post 	if the supplied total velocity is larger than the getSpeedLimit() rescale the velocity
	 * 			| if !isValidVelocity(totalVelocity(xVel,yVel))
	 * 			| then 	new.getXVelocity() == xVel* getSpeedLimit()/Math.abs(totalVelocity(xVel,yVel))&&
	 * 			|		new.getYVelocity() == yVel* getSpeedLimit/Math.abs(totalVelocity(xVel,yVel))
	 */
	@Basic @Raw
	public void setVelocity(double xVel, double yVel){
		if(Double.isNaN(xVel) || Double.isNaN(yVel)){
			this.velocity = new Vector2D(0, 0);
			return;}
		double totalVelocity = totalVelocity(xVel, yVel);
		if (isValidTotalVelocity(totalVelocity)){
			this.velocity = new Vector2D(xVel, yVel);
		} 
		else {
			// if the velocity is not valid re-scale the velocity
			double rescaleConstant = Math.abs(totalVelocity)/(getSpeedLimit());
			xVel /= rescaleConstant;
			yVel /= rescaleConstant;
			
			this.velocity = new Vector2D(xVel, yVel);
		}
	}
	
	/**
	 * Vector2D objects that stores the velocity of the WorldObject
	 */
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
	@Basic
	public static boolean isValidTotalVelocity(double totalVel){
		return (totalVel <= getSpeedLimit()&&totalVel >= 0);
	}
	
	/**
	 * 
	 */
	public static double getSpeedLimit(){
		return LIGHT_SPEED;
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
	 * @post 	the radius is set to rad only if the radius is uninitialized
	 * 			| new.getRadius() == rad
	 * 
	 * @throws  IllegalArgumentException
	 * 			| !isValidRadius(rad)
	 * 
	 * @throws  IllegalStateException
	 * 			| hasInitializedRadius()
	 */
	@Basic @Immutable @Raw
	public void setRadius(double radius) throws IllegalArgumentException, IllegalStateException{
			if(! this.canHaveAsRadius(radius))
				throw new IllegalArgumentException();
			
			this.radius = radius;
			this.initializedRadius= true;
	}
	
	/**
	 * checks if the radius is initialized
	 * @return 	true if and only if the radius is initialized
	 * 			| result == this.initializedRadius;
	 */
	@Model @Raw
	protected boolean hasInitializedRadius(){
		return this.initializedRadius;
	}
	
	/**
	 * flag for the initialized radius
	 */
	private boolean initializedRadius = false;
	
	/**
	 * @return the minimum radius of the world object
	 */
	@Immutable
	public abstract double getMinimumRadius();
	
	/**
	 * checks if the provided radius is Valid
	 */
	public abstract boolean canHaveAsRadius(double rad);
	
	/**
	 * The variable that stores the radius of the WorldObject
	 */
	private double radius;
	
	
//	/**
//	 * @return The density of the WorldObject
//	 */
//	public double getDensity(){
//		return this.Density;
//	}
//	
//	/**
//	 * Set the mass of the WorldObject to the provided mass
//	 * @param 	density
//	 * 			the desired density of the ship
//	 * 
//	 * @post	the object density equals to the provided density
//	 * 			| if(isValidDensity(density))
//	 * 			| then new.getDensity() == density
//	 * 
//	 * @post	if the density is not valid set the density to the minimum density
//	 * 			| if(!isValidDensity(density)
//	 * 			| then new.getDensity() == getMinimumDensity()
//	 * 
//	 * @effect 	set the mass with the provided density
//	 * 			| setMass()
//	 */
//	@Raw @Basic
//	public void setDensity(double density){
//		if(this.isValidDensity(density))
//			this.Density = density;
//		else
//			this.Density = this.getMinimumDensity();
//		this.setMass();
//	}
	
//	/**
//	 * checker to check if the density is valid
//	 */
//	@Basic
//	public abstract boolean isValidDensity(double density);
	
	/**
	 * @return the minimum value for the density
	 */
	@Basic
	public abstract double getMinimumDensity();
	
//	/**
//	 * variable that stores the density of the World Object
//	 */
//	private double Density;
	
	/**
	 * Basic getter for the mass
	 * @return the mass of the world Object
	 */
	@Basic @Raw
	public double getMass(){
		return this.mass;
	}
	
//	/**
//	 * sets the mass of a world object
//	 * @effect 	the mass is set according to the density and the radius of the world object
//	 * 		 	|new.getMass == calcMass();
//	 * 
//	 * @effect 	if the radius of the world object is not initialized the radius is set to the minimal radius
//	 * 			|if(!hasInitializedRadius())
//	 * 			|then 	setRadius(getMinRadius)&&
//	 * 			|		new.getMass == calcMass
//	 */
//	@Raw @Basic
//	public void setMass(){
//		
//		if(this.hasInitializedRadius()){
//			this.mass = this.calcMinMass();
//		}else{
//			this.setRadius(this.getMinimumRadius());
//			this.mass = this.calcMinMass();
//		}
//	}
	
	
	//TODO fix the overdrive speed
	/**
	 * basic setter for the mass
	 * @param	mass
	 * 			the desired mass
	 * @return	if the mass is valid set the mass and the radius is initialized
	 * 			|if(canHaveAsMass(mass)&&hasInitializedRadius())
	 * 			|then new.getMass() = mass
	 * 
	 * @return	if the radius is initialized and the mass is not valid
	 * 			set the mass to the minimum mass
	 * 			|if(!canHaveAsMass(mass)
	 * 			|then  new.getMass() == calcMinMass()
	 * 
	 * @return	if the radius is not initialized, set the mass to the minimum
	 * 			mass with the radius of the worldObject to the minimum radius
	 * 			|if(!hasInitializedRadius())
	 * 			|then setRadius(getmMinimumRadius())&&
	 * 			|	  new.getMass() = calcMinMass()
	 */
	@Basic @Raw
	public void setMass(double mass){
		if(this.hasInitializedRadius()){
			if(canHaveAsMass(mass)){
				this.mass = mass;
			} else{
				this.mass = calcMinMass();
			}
		} else{
			this.setRadius(getMinimumRadius());
			this.mass = calcMinMass();
		}
	}
	
	/**
	 * checker if the given mass is a valid mass
	 */
	public abstract boolean canHaveAsMass(double mass);
	
	/**
	 * variable that stores the mass of a WorldObject
	 */
	private double mass;
	
	
	/**
	 * calculates the volume of a sphere with the given radius
	 * @param 	radius
	 * 			the radius of the sphere
	 * @return 	the volume of a sphere with the given radius
	 * 			| result == 4/3*Math.PI*Math.pow(radius,3)
	 */
	public static double volumeSphere(double radius){
		return 4.0/3.0 * Math.PI*Math.pow(radius,3);
	}
	
	
	/**
	 * calculates the minimum mass of the given object
	 * @return	the minimum mass of a WorldObject
	 * 			| result == volumeSphere(getRadius()) * getDensity()
	 */
	@Model
	protected double calcMinMass(){
		return volumeSphere(this.getRadius())*this.getMinimumDensity();
	}
	
	
	/**
	 * Sets the new position of the WorldObject according to the current speed and given time interval
	 * @param   time
	 * 			the passed time used to move the WorldObject 
	 * 
	 * @effect	calculates and sets the new position of the WorldObject
	 * 			|result == getPosition().vectorSum(getVelocity().rescale(time))
	 * 
	 * @throws	IllegalArgumentException
	 * 			The given value for the time is illegal
	 * 			| ! isValidTime(time)
	 */
	public void move(double time) throws IllegalArgumentException{
		if(!isValidTime(time))
			throw new IllegalArgumentException();
		
		Vector2D newPosVector = this.getPosition().vectorSum(this.getVelocity().rescale(time));
		
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
	@Basic @Raw
	public static boolean isValidTime(double time){
		return time>=0;
	}
	
	
	/**
	 * Calculates the distance between the radii of two WorldObjects, if two WorldObjects overlap the distance is negative
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
	 * @return 	true if the WorldObjects overlap
	 * 			|result == getDistanceBetween(other) <= 0
	 * 
	 * @throws 	IllegalArgumentException
	 * 			| other == 0
	 * 
	 * @throws 	ArithmeticException
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
	 * @throws 	ArithmeticException
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
	 * @return	if the WorldObjects never collide return positive infinity
	 * 
	 * @return	if the WorldObjects are not in the same world return positive infinity
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
		
		// if(World.significantOverlap(this, other))
		if (this.overlap(other))
			throw new IllegalArgumentException();
		
		if(this.getWorld() != other.getWorld())
			return Double.POSITIVE_INFINITY;
		
		Vector2D deltaR = this.getPosition().difference(other.getPosition());
		Vector2D deltaV = this.getVelocity().difference(other.getVelocity());
		
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
	
	
	/**
	 * returns the time to a collision between a world object and the boundary of the world.
	 * @param 	world
	 * 			the world where the World object collides with
	 * 
	 * @return 	the time between a collision of a world object with the boundary of the world
	 * 			the position of the collision with the world is:
	 * 				position(time) = getPosition().vectorSum(getVelocity().rescale(time))
	 * 			determine xTime and yTime such that
	 * 				position(xTime).getXComponent() == (world.getWidth() - getRadius() || getRadius)
	 * 				position(yTime).getYComponent() == (world.getHeight() - getRadius()|| getRadius)
	 * 			return the minimum time
	 * 				result == Math.min(xTime,yTime)
	 * 
	 * @return	if the world is null return positive infinity
	 * 			| if(world == null)
	 * 			| then result == Double.POSITIVE_INFINITY
	 * 
	 * @return  if the world is not the same world as the current world 
	 * 			the World Object is positioned in return  infinity
	 * 			| if(getWorld() != world)
	 * 			| then result == Double.POSITIVE_INFINITY
	 */
	public double getTimeToCollision(World world){
		if(world == null)
			return Double.POSITIVE_INFINITY;
		if(world != this.getWorld())
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

	
	/**
	 * Calculates the position of a collision
	 * @param 	other
	 * 			the other WorldObject
	 * 
	 * @return  the position of the collision
	 * @return	return the time of the collision
	 * 			| collisionTime == getTimeToCollsion(other)
	 * 			| using the calculated time determine xCol, yCol the coordinates
	 * 			| of the collision such that:
	 * 			|   distance(xPos1, yPos1, xCol, yCol) == this.getRadius() && 
	 * 			|   distance(xPos2,yPos2, xCol, yCol) == other.getRadius()
	 * 			| with xPos1 and yPos1 the position coordinates of the prime object at collisionTime
	 * 			| and xPos2 and yPos2 the position coordinates of the other object at collisionTime
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

		if(causedOverflow(collisionPos.getXComponent())||causedOverflow(collisionPos.getYComponent()))
			throw new ArithmeticException();
		
		return collisionPos.getVector2DArray();
	}
	
	
	/**
	 * determine the collision position between a WorldObject and the boundary with the world
	 * @param 	world
	 * 			the world to calculate the collisionPosition in
	 * @return	the position of the collision between a WorldObject and the world boundary
	 * 			| calculate the time to the collision
	 * 			|	time == getTimeToCollisionWorld(world)
	 * 			| determine the position of the WorldObject:
	 * 			|	center == getPosition().vectorSum(getVelocity().rescale(time))
	 * 			|
	 * 			| And with possibleCollisions the set of all possible collisions
	 * 			|  	possibleCollisions = {(world.getWidth(), center.getYComponent()), (0, center.getYComponent()),
				|						(center.getXComponent(),world.getHeight()), (center.getXComponent(), 0)}
	 * 			| now determine the coordinates where the world and the WorldObject touch
	 * 			| for collision in possibleCollsions if center.distanceTo(collision) == getRadius() ==> result == collision
	 */		
	public double[] getCollisionPosition(World world){
		
		double time = this.getTimeToCollision(world);
		if(time == Double.POSITIVE_INFINITY)
			return null;
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
		
		// added to prevent that the compiler starts complaining
		return new double [] {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};		
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
	 * 			| result == Math.pow(deltaV.dotProduct(deltaR),2) - 
				|(deltaR.dotProduct(deltaR) - Math.pow(this.getSigma(other), 2))*deltaV.dotProduct(deltaV);
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
	 * 			| result == (Double.isInfinite(a)||Double.isNaN(a))
	 */
	public static boolean causedOverflow(double a){
		return ((Double.isInfinite(a))||(Double.isNaN(a)));
		
	}
	
	/**
	 * Return the associated world (no clone)
	 * @return the associated world
	 * 			|this.associatedWorld
	 */
	@Basic
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
	@Basic
	public void setWorld(World world) throws IllegalArgumentException{
		if(!canHaveAsWorld(world))
			throw new IllegalArgumentException();
		this.associatedWorld = world;
	}
	
	public boolean residesInWorld(){
		return this.getWorld()!=null;
	}
	
	/**
	 * Sets the associated world of WorldObject to null
	 * #### !!!WARNING FOR USE IN REMOVE FROM WORLD ONLY!!! ####
	 */
	protected void setWorldToNull(){
		this.associatedWorld = null;
	}
	/**
	 * variable that stores the associated World
	 */
	private World associatedWorld;
	
	
	/**
	 * checks if the provided world is a valid world
	 * @param 	world
	 * 			the desired world for the association between
	 * 			the world and the WorldObject
	 */
	public abstract boolean canHaveAsWorld(World world);
	
	
	public abstract void resolveCollision(WorldObject other);
	
	
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
	 * 			| this.getXPosition(), this.getYPosition} != this.getCollisionPosition(world)
	 */
	public void resolveCollision(World world)throws IllegalArgumentException, IllegalStateException{
		
		if(world==null)
			throw new IllegalArgumentException();
		
		if(this.getWorld() != world)
			throw new IllegalArgumentException();
		
		double[] collisionPosition = this.getCollisionPosition(world);	
		double worldWidth = world.getWidth();
		double worldHeigth = world.getHeight();
		double radius = this.getRadius();
		double[] currentVelocity = this.getVelocity().getVector2DArray();
		
		//check if it collides with both
		if(doubleEquals(collisionPosition[0], 0) && 
				(doubleEquals(collisionPosition[1], 0 + radius) || doubleEquals(collisionPosition[1], worldHeigth - radius))){
			this.setVelocity(-currentVelocity[0], -currentVelocity[1]);
		}
		else if(doubleEquals(collisionPosition[0], worldWidth) && 
				(doubleEquals(collisionPosition[1], 0 + radius) || doubleEquals(collisionPosition[1], worldHeigth - radius))){
			this.setVelocity(-currentVelocity[0], -currentVelocity[1]);
		}
		else if(doubleEquals(collisionPosition[1], worldHeigth) && 
				(doubleEquals(collisionPosition[0], 0 + radius) || doubleEquals(collisionPosition[0], worldWidth - radius))){
			this.setVelocity(-currentVelocity[0], -currentVelocity[1]);
		}
		else if(doubleEquals(collisionPosition[1], 0) && 
				(doubleEquals(collisionPosition[0], 0 + radius) || doubleEquals(collisionPosition[0], worldWidth - radius))){
			this.setVelocity(-currentVelocity[0], -currentVelocity[1]);
		}
		//check if the collision is with one of the x-boundaries
		else if(doubleEquals(collisionPosition[0], worldWidth)||doubleEquals(collisionPosition[0],0)){
			this.setVelocity(-currentVelocity[0], currentVelocity[1]);
		}
		//else it collides with the y-boundary
		else{
			this.setVelocity(currentVelocity[0], -currentVelocity[1]);	
		}
	}
	
	
	/**
	 * error margin used in calculations with vectors
	 */
	protected static final double EPSILON = 1E-10;
	
	/**
	 * equals used for double precision floating point numbers
	 * @return if a and b are equal to eachother within acceptable margins return true
	 */
	public static boolean doubleEquals(double a, double b){
		return a-EPSILON <= b && a+EPSILON >= b;
	}
	

	/**		
	 * error margin used in calculations with vectors		
	 */		
	protected static final double EPSILON2 = 1E-3;		
			
	/**		
	 * equals used for double precision floating point numbers		
	 * @return if a and b are equal to eachother within acceptable margins return true		
	 */		
	public static boolean doubleEqualsLessAccurate(double a, double b){		
		return a-EPSILON2 <= b && a+EPSILON2 >= b;		

	}
	
	@Override
	public String toString(){
		return "WorldObject";
	}

}
