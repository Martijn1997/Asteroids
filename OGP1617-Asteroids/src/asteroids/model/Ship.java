package asteroids.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;

// To do
// documentatie voor mass & density functies

// Algemene info over de klasse schip:
// De associatie bullet-Ship wordt enkel aangemaakt door bullet, de methodes
// die het schip binden aan de bullet is protected en kan dus niet zomaar gebruikt worden
// dit is gedaan om de association integrity te behouden

// denk ook nog eens na over als je de density aanpast of de massa ook aangepast moet worden om de invariant
// niet te schenden (dus als je een nieuwe density zet dat je massa nog geldig blijft)

// moet het schip nog van de bullet weten als deze geschoten is?
// wat als we het schip de bullet doen schieten is er dan apparte 'klasse' van afgeschoten kogels?

/**
 * 
 * @author Martijn Sauwens & Flor Theuns
 * 
 * @Invar 	The ship cannot go faster than the speed of light
 * 			|isValidVelocity(TotalVelocity(getXVelocity(), getYVelocity))
 *
 * @Invar 	The radius of the ship is always lager than 10km
 * 			|isValidRadius(radius)
 * 
 * @Invar 	The orientation of the ship is always an angle between 0 and 2*Math.PI
 * 			|isValidOrientation(angle)
 * 
 * @Invar	The Density of the ship is always larger than or equalt to the MINIMUM_DENISTY
 * 			|isValidDensity()
 * 
 * @Invar 	The mass of the ship is larger than or equal to the volume of a sphere with the radius of the ship multiplied
 * 			with the density of the ship
 * 			|WorldObject.volumeSphere(this.getRadius()) * this.getDensity()
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
	public Ship(double xPos, double yPos, double orientation, double radius, double xVel, double yVel, double density, double mass)throws IllegalArgumentException{
		
		// set the single valued attributes
		super(xPos, yPos, radius, xVel, yVel, density, mass);
		this.setOrientation(orientation);

		// load the 15 bullets on the ship
		for(int index = 0; index < START_BULLETS; index++){
			Bullet newBullet = new Bullet(xPos, yPos, radius*STANDARD_BULLET_SIZE_RATIO, xVel,yVel, 0, 0);
			newBullet.loadBulletOnShip(this);
		}
	}
		
	
	/**
	 * default constructor for a Ship object
	 * @effect Ship(0,0,0,10,0,0)
	 */
	public Ship(){
		this(0d,0d,0d,10d,0,0,0,0);
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
	 * checks whether the provided radius is valid
	 * @param 	rad
	 * 			the radius
	 * 
	 * @return |result == (rad >= MIN_RADIUS)
	 */
	@Override
	public boolean isValidRadius(double rad){
		return rad >= MIN_RADIUS;
	}
	
	// the radius will not change once the radius has been set
	
	public final static double MIN_RADIUS = 10;
	
	@Override
	public boolean isValidDensity(double density){
		return density >= MINIMUM_DENSITY&& density <= Double.MAX_VALUE && !Double.isNaN(density);
	}
	
	public final static double MINIMUM_DENSITY = 1.42e12;
	
	@Override
	public boolean canHaveAsMass(double mass){
		// causes overflow if the radius is to big or the density is to high
		return mass >= this.calcMinMass()&&!Double.isNaN(mass)&&mass <= Double.MAX_VALUE;
	}
	
	@Override
	public double getMinimumDensity(){
		return MINIMUM_DENSITY;
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
	
	// ga ook na of de bullet niet geassocieerd is met een wereld (gaat pas nadat er functie voor is bij Bullet
	protected void loadBullet(Bullet bullet) throws IllegalArgumentException{
		if(bullet.getShip()==this){
			loadedBullets.add(bullet);
			double newMass = this.getMass() + bullet.getMass();
			this.setMass(newMass);
		}
		else
			throw new IllegalArgumentException();
	}
	
	/**
	 * Loads an arbitrary amount of bullets onto the ship
	 * @param 	bullets
	 * 			the bullets to be loaded onto the ship
	 * @post	the bullets are loaded onto the ship
	 * 			|new.getLoadedBullets == getLoadedBullets.addAll(bullets)
	 * 
	 * @throws 	IllegalArgumentException if the argument contains a bullet that is a null reference
	 * 			or when a bullet in the argument already is associated with as ship
	 * 			|bullets.contains(null)||bullets[i].isAssociated()
	 */
	public void loadBullets(Bullet...bullets) throws IllegalArgumentException{
		Set<Bullet> bulletSet = new HashSet<Bullet>(Arrays.asList(bullets));
		if(bulletSet.contains(null))
			throw new IllegalArgumentException();
		
		// check if the bullet isn't already associated with another ship or world
		for(Bullet bullet : bulletSet){ // look for solution to get rid of the double for lus
			if(bullet.isAssociated())
				throw new IllegalArgumentException();		
		}
		
		// load the bullet on the ship
		for(Bullet bullet : bulletSet){
			bullet.loadBulletOnShip(this);
		}
		
	}
	
	/**
	 * unloads the specified bullet
	 * @param bullet
	 * @post	The association between the ship and the bullet is broken 
	 * @throws 	IllegalArgumentException
	 * 			|bullet == null || !containsBullet(bullet)
	 */
	private void unloadBullet(Bullet bullet) throws IllegalArgumentException{
		if(bullet == null)
			throw new IllegalArgumentException();
		if(!this.containsBullet(bullet))
			throw new IllegalArgumentException();
		this.getLoadedBullets().remove(bullet);
		bullet.transferToWorld();
		
	}
	
	/**
	 * A getter for the loaded bullets
	 * @return 	the loaded bullets
	 * 			|result == this.loadedBullets()
	 */
	@Model @Basic
	private Set<Bullet> getLoadedBullets(){
		// return the set itself (not a clone) the method is used to manipulate the amount of bullets on the ship
		return this.loadedBullets;
	}
	
	/**
	 * Checks if a ship has the specified bullet associated with itself
	 * @param bullet
	 * @return	Whether the bullet is associated with the ship
	 * 			|result == this.getLoadedBullets().contains(bullet)
	 */
	public boolean containsBullet(Bullet bullet){
		return this.getLoadedBullets().contains(bullet);
	}
	
	public Bullet selectBullet(){
		for(Bullet bullet : this.getLoadedBullets())
		{
		        return bullet;
		}
		return null;
	}
	
	// voeg nog checkers toe om na te gaan of de kogel al niet overlapt met andere schepen of de rand van de wereld
	/**
	 * Fires the Bullet into space
	 * @param 	bullet
	 * 
	 * @effect	The bullet is removed from the arsenal of the ship and the bullet is cast into the world
	 * 			|unloadBullet()
	 * 
	 * @post  	The fired bullet is positioned next to the ship (on the relative y-axis at a distance equal to the total radii)
	 * 			|(new bullet).getXPosition()== this.getXPosition() - Math.sin(this.getOrientation())*(this.getRadius()+bullet.getRadius())
	 *			|(new bullet).getYPosition()==this.getYPosition() + Math.cos(this.getOrientation())*(this.getRadius()+bullet.getRadius())
	 *
	 * @post	The fired bullet has a standard velocity (depending on bullet.SHOOTING_VELOCITY) in the same direction as the ship is facing
	 * 			|(new bullet).getXVelocity() == Math.cos(this.getOrientation())*Bullet.SHOOTING_VELOCITY;
	 *			|(new bullet).getYVelocity() == Math.sin(this.getOrientation())*Bullet.SHOOTING_VELOCITY;
	 */
	public void fireBullet(){
		
		// select a bullet from the magazine
		Bullet bullet = selectBullet();
		
		//unload the bullet
		this.unloadBullet(bullet);
		
		// set the position of the bullet.
		double nextToShipX = this.getXPosition() - Math.sin(this.getOrientation())*(this.getRadius()+bullet.getRadius());
		double nextToShipY = this.getYPosition() + Math.cos(this.getOrientation())*(this.getRadius()+bullet.getRadius());
		
		//if(causedOverflow(nextToShipX)||causedOverflow(nextToShipY))
		//	throw new ArithmeticException();
		
		bullet.setXPosition(nextToShipX);
		bullet.setYPosition(nextToShipY);
		
		// set the velocity of the bullet. Can only cause overflow
		double bulletXVelocity = Math.cos(this.getOrientation())*Bullet.SHOOTING_VELOCITY;
		double bulletYVelocity = Math.sin(this.getOrientation())*Bullet.SHOOTING_VELOCITY;
		
		//if(causedOverflow(totalVelocity(bulletXVelocity, bulletYVelocity)))
		//	throw new ArithmeticException();
		bullet.setVelocity(bulletXVelocity, bulletYVelocity);	
	}
	
	/**
	 * The set of the bullets carried by a ship
	 */
	private Set<Bullet> loadedBullets = new HashSet<Bullet>();
	
	/**
	 * the amount of bullets that is loaded on the ship upon creation of the ship
	 */
	private final static int START_BULLETS = 15;
	
	/**
	 * the size ratio between the bullets and the ship
	 */
	public final static double STANDARD_BULLET_SIZE_RATIO = 0.10;
}