package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

// To do
// documentatie voor mass & density functies

/**
 * 
 * @author Martijn Sauwens & Flor Theuns
 * 
 * @Invar The ship cannot go faster than the speed of light
 * 			|isValidVelocity(TotalVelocity(getXVelocity(), getYVelocity))
 *
 * @Invar The radius of the ship is always lager than 10km
 * 			|isValidRadius(radius)
 * @Invar The orientation of the ship is always an angle between 0 and 2*Math.PI
 * 			|isValidOrientation(angle)
 */
public class Ship extends WorldObject{
	
	
	/**
	 * Initializes an object of the Ship class
	 * @param 	xPos
	 * 			the x position of the Ship
	 * 
	 * @param	yPos
	 * 			the y position of the Ship
	 * 
	 * @param 	orientation
	 * 			the orientation of the Ship
	 * 
	 * @param 	radius
	 * 			the Radius of the Ship
	 * 
	 * @effect 	xPosition is set to the provided xPos
	 * 			| setXPosition(xPos)
	 * 
	 * @effect 	yPosition is set to the provided yPos
	 * 			| setYPosition(yPos)
	 * 
	 * @effect 	the orientation of the ship is set to the given orientation
	 * 			| setOrientation(orientation)
	 * 
	 * @effect 	the radius of the ship is set to the given radius
	 * 			| setRadius(radius)
	 * 
	 * @effect 	the velocity is set to the given velocity components xVel and yVel
	 * 			| setVelocity(xVel,yVel)
	 */
	public Ship(double xPos, double yPos, double orientation, double radius, double xVel, double yVel)throws IllegalArgumentException{
		super(xPos, yPos, 10, xVel, yVel);
		this.setOrientation(orientation);
		this.setRadius(radius);
	}
		
	
	/**
	 * default constructor for a Ship object
	 * @effect Ship(0,0,0,10,0,0)
	 */
	public Ship(){
		this(0d,0d,0d,10d,0,0);
	}

	/**
	 * Returns the orientation of the Ship
	 */
	@Basic
	public double getOrientation(){
		return this.orientation;
	}
	
	/**
	 * Basic setter for the orientation of the ship
	 * 
	 * @pre 	the provided angle is between 0 and Math.PI * 2
	 * 			| isValidOrientation(angle)
	 * 
	 * @post 	the orientation is equal to the given angle
	 * 			| new.getOrientation() == angle
	 */
	@Basic
	public void setOrientation(double angle){
		assert(isValidOrientation(angle));
		this.orientation = angle;
	}
	
	/**
	 * returns if the given angle is between 0 and 2PI
	 */
	public static boolean isValidOrientation(double angle){
		return (angle <= 2*Math.PI)&&(angle >= 0);
	}
	private double orientation;
	

	
	/**
	 * Sets the radius of a ship
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
			super.setRadius(rad);
	}
	
	/**
	 * checks whether the provided radius is valid
	 * @param 	rad
	 * 			the radius
	 * 
	 * @return |result == (rad >= MIN_RADIUS)
	 */
	public static boolean isValidRadius(double rad){
		return rad >= MIN_RADIUS;
	}
	
	// the radius will not change once the radius has been set
	
	public final static double MIN_RADIUS = 10;
	
	public void setShipMass(double mass){
		if(canHaveAsMass(mass))
			super.setMass(mass);
		else
			super.setMass(volumeSphere(this.getRadius())*super.getDensity());
	}
	
	// eventueel nog een functie om de density te zetten
	public void setDensity(double density){
		if(density > MINIMUM_DENSITY)
			super.setDensity(density);
		else
			super.setDensity(MINIMUM_DENSITY);

	}
	
	public final static double MINIMUM_DENSITY = 1.42e12;
	

	public boolean canHaveAsMass(double mass){
		return mass >= volumeSphere(this.getRadius());
	}
	
	
	private boolean thrustStatus;
	
	/**
	 * Sets the new position of the ship according to the current speed and given time interval
	 * @param   time
	 * 			the passed time used to move the ship
	 * 
	 * @post 	if the given time is zero, the position of the ship will not change
	 * 			| if(time == 0)
	 * 			| then (new.getXPosition() == getXPosition) &&
	 * 			|	   (new.getYPosition() == getYPosition) 
	 * 
	 * @post	if the given type is nonnegative and not zero the ship is moved
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
	 * Turns the ship for a given angle
	 * @param 	angle
	 * 			The angle the ship has to turn
	 * 
	 * @Pre 	The sum of the angle and the old angle of the ship is nonnegative and smaller than 2PI
	 * 			| (angle + getOrientation()) <= 2*Math.PI && (angle + getOrientation() >= 0)
	 * 
	 * @post 	The new orientation is the sum of the angle and the old orientation
	 * 			| new.getOrientation() == angle + getOrientation()
	 */
	public void turn(double angle){
		double newAngle = angle + this.getOrientation();
		assert( isValidOrientation(newAngle) );
		this.setOrientation(newAngle);		
	}
	
	public void thrustOn(){
		this.thrustStatus = true;
	}
	
	public void thrustOff(){
		this.thrustStatus = false;
	}
	
	public boolean getThrusterStatus(){
		return this.thrustStatus;
	}
	
	public double getThrustForce(){
		return this.thrustForce;
	}
	
	/**
	 * changes the velocity of the ship, according to the current orientation and the given acceleration
	 * @param 	acc
	 * 			the acceleration of the ship
	 * 
	 * @post	if the new total velocity is below light speed the new velocity is the sum
	 * 			of the old velocity plus the acceleration, if the new velocity is greater then the speed
	 * 			of light, the total velocity stays the same but the vector components are scaled accordingly
	 * 			if an overflow occured, the old values are kept for the velocity
	 * 			|@see implementation
	 */
	public void thrust(double deltaTime){
		
		if(this.getThrusterStatus()){
			
		}
			double acceleration = this.getThrustForce()/this.getMass();
			
			double newXVel = this.getXVelocity() + acceleration * Math.cos(this.getOrientation())*deltaTime;
			double newYVel = this.getYVelocity() + acceleration * Math.sin(this.getOrientation())*deltaTime;
			
			if (!WorldObject.causedOverflow(newXVel) && !WorldObject.causedOverflow(newYVel) ){
				double newTotalVel = WorldObject.totalVelocity(newXVel, newYVel);
							
				if(!isValidVelocity(newTotalVel)){
					double rescaleConstant = newTotalVel/(LIGHT_SPEED);
					newXVel /= rescaleConstant;
					newYVel /= rescaleConstant;
				}
				
				this.setVelocity(newXVel, newYVel);
			
		}
	}
		
//		if(acc < 0)
//			acc = 0;
//		
//		if(acc > Double.MAX_VALUE - this.getXVelocity() ||acc > Double.MAX_VALUE - this.getYVelocity())
//			acc = 0;
//		
//		double newXVel = this.getXVelocity() + acc * Math.cos(this.getOrientation());
//		double newYVel = this.getYVelocity() + acc * Math.sin(this.getOrientation());
//		double newTotalVel = WorldObject.totalVelocity(newXVel, newYVel);
//		
//		if(!isValidVelocity(newTotalVel)){
//			double rescaleConstant = newTotalVel/(LIGHT_SPEED);
//			newXVel /= rescaleConstant;
//			newYVel /= rescaleConstant;
//		}
//		
//		this.setVelocity(newXVel, newYVel);

	
	private final double thrustForce = 1.1E21;
}