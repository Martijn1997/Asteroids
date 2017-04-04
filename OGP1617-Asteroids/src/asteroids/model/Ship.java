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

// voorzie nog een documentatie bij de termation

// voorzie documentatie bij de botsing ship-ship

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
	public Ship(double xPos, double yPos, double orientation, double radius, double xVel, double yVel, double mass)throws IllegalArgumentException{
		
		// set the single valued attributes
		super(xPos, yPos, radius, xVel, yVel, mass);
		this.setOrientation(orientation);

//		// load the 15 bullets on the ship
//		for(int index = 0; index < START_BULLETS; index++){
//			Bullet newBullet = new Bullet(xPos, yPos, radius*STANDARD_BULLET_SIZE_RATIO, xVel,yVel, 0);
//			newBullet.loadBulletOnShip(this);
//		}
	}
		
	
	/**
	 * default constructor for a Ship object
	 * @effect Ship(0,0,0,10,0,0)
	 */
	public Ship(){
		this(0d,0d,0d,10d,0,0,0);
	}
	
	/**
	 * Terminates a Ship Object
	 */
	@Override
	public void terminate(){
		
		for(Bullet bullet: this.getLoadedBullets()){
			bullet.terminate();
		}

		super.terminate();
		
		// decide what to to with the bullets that are free flying is space that
		// reference the ship
//		HashSet<Bullet> freeBullets = this.getWorld().getAllBullets();
//		for(Bullet bullet: freeBullets){
//			if(bullet.getShip()=this){
//				bullet.
//			}
//		}
		
		
	}
	
//	/**
//	 * the amount of bullets that is loaded on the ship upon creation of the ship
//	 */
//	private final static int START_BULLETS = 15;
//	
	/**
	 * the size ratio between the bullets and the ship
	 */
	public final static double STANDARD_BULLET_SIZE_RATIO = 0.10;

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
	
	/**
	 * @return 	the minimum radius of a ship
	 * 			|result == MIN_RADIUS
	 */
	@Override
	public double getMinimumRadius(){
		return MIN_RADIUS;
	}
	
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
	
	/**
	 * Calculates the total mass of the ship (bullets + ship mass)
	 * @return	the mass of the ship + the mass of all the loaded bullets
	 * 			|result == getMass() + sum([bullet.getMass() for bullet in getLoadedBullets()])
	 */
	public double getTotalMass(){
		double totalMass = this.getMass();
		for(Bullet bullet: this.getLoadedBullets()){
			totalMass += bullet.getMass();
		}
		return totalMass;
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
	@Override
	public void move(double time) throws IllegalArgumentException{
		super.move(time);
		for(Bullet bullet: this.getLoadedBullets()){
			bullet.syncBulletVectors();
		}	
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
	 * @param 	deltaTime
	 * 			the time that passes used to calculate the new velocity of the ship
	 * 
	 * @post	if the thruster status is false or the supplied time isn't valid no changes to the velocity are made
	 * 			|if(!getThrusterStatus()||!isValidTime(deltaTime)
	 * 			|then 	new.getXVelocity == getXVelocity()
	 * 			|		new.getYVelocity == getYVelocity()
	 * 
	 * @post 	if the calculation of the totalVelocity caused overflow no velocity is adjusted
	 * 			|@see Implementation
	 * 
	 * @effect	The new speed is set and rescaled if necessary
	 * 			|setVelocity(getXVelocity() + acceleration * Math.cos(getOrientation())*deltaTime,
	 * 			|				getYVelocity() + acceleration * Math.sin(getOrientation())*deltaTime)
	 * 
	 */
	public void thrust(double deltaTime){
		
		if(this.getThrusterStatus()&&isValidTime(deltaTime)){
			
			// calculate the acceleration
			double acceleration = this.getThrustForce()/this.getTotalMass();
			
			// calculate the new values for the velocity
			double newXVel = this.getXVelocity() + acceleration * Math.cos(this.getOrientation())*deltaTime;
			double newYVel = this.getYVelocity() + acceleration * Math.sin(this.getOrientation())*deltaTime;
			
			double totalVelocity = totalVelocity(newXVel, newYVel);
			
			// check if the totalVelocity is an overflow
			if(!causedOverflow(totalVelocity))
				// set the velocity to the new Values
				this.setVelocity(newXVel, newYVel);		
			}
	}
	
	private final double thrustForce = 1.1E21;
	
	/**
	 * Loads the provided bullet onto the ship
	 * @param bullet
	 * 
	 * @post	The bullet is associated with the ship
	 * 			| new.getLoadedBullets() == getLoadedBullets().add(bullet)
	 * 
	 * @post	The mass of the ship equals the mass of the ship (inclusive other loaded bullets)
	 * 			plus the mass of the newly loaded bullet
	 * 			|new.getMass() == getMass() + bullet.getMass()
	 * 
	 * @throws IllegalArgumentException
	 * 			If the bullet doens't reference the ship throw the exception
	 * 			|bullet.getShip()!=this
	 */
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
	 * @effect	all the bullets are loaded on the ship
	 * 			|for bullet in bullets do 
	 * 			|	this.loadBullet(bullet)
	 * 			|end_for
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
	protected void unloadBullet(Bullet bullet) throws IllegalArgumentException{
		if(bullet == null)
			throw new IllegalArgumentException();
		if(!this.containsBullet(bullet))
			throw new IllegalArgumentException();
		
		// remove the bullet and transfer the bullet to the world
		this.getLoadedBullets().remove(bullet);
		
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
	 * Getter for the loaded bullets creates a copy of the loaded bullets
	 * @return 	a cloned set of the associated bullets
	 * 			|result == new HashSet<Bullet>(getLoadedBullets())
	 */
	public Set<Bullet> getBulletSet(){
		return new HashSet<Bullet>(this.getLoadedBullets());
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
	
	/**
	 * A getter for the total amount of bullets
	 * @return 	the total amount of bullets
	 * 			|result == getLoadedBullets().size();
	 */
	public int getTotalAmountOfBullets(){
		return this.getLoadedBullets().size();
	}
	
	/**
	 * Selects a bullet from the set of loaded bullets
	 * @return 	returns a bullet from the set of bullets
	 * 			|@see implemenation
	 */
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
		
		// chose what to do if there is no bullet loaded
		if(bullet == null)
			return;
		
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
		//throw new ArithmeticException();
		bullet.setVelocity(bulletXVelocity, bulletYVelocity);	

		//unload the bullet, needs to be at the end because the bullet coordinates need to be different from the
		//coordinates of the ship
		bullet.transferToWorld();
	}
	
	/**
	 * Checker for adding a ship to a world
	 * @return  true if and only if the world is not a null reference and the ship isn't already in a world.
	 * 			|result == (world != null &&this.getWorld()==null)
	 */
	public boolean canHaveAsWorld(World world){
		return (world != null &&this.getWorld()==null);
	}
	
	/**
	 * The set of the bullets carried by a ship
	 */
	private Set<Bullet> loadedBullets = new HashSet<Bullet>();
	
	
	/**
	 * Resolve the collision between a ship and a bullet
	 * @param 	bullet
	 * 			The bullet that collides with the ship
	 * 
	 * @post	if the bullet is associated with the ship, transfer the bullet to the ship
	 * 			else the ship and the bullet are destroyed
	 * 			|@see Implementation
	 * 
	 * @throws	IllegalStateException
	 * 			thrown if the bullet and the ship don't overlap
	 * 			|!this.overlap(bullet)
	 * @throws 	IllegalArgumentException
	 * 			thrown if the bullet is a null reference
	 * 			| bullet == null
	 */
	@Override
	public void resolveCollision(Bullet bullet)throws IllegalStateException, IllegalArgumentException{
		if(!this.overlap(bullet))
			throw new IllegalStateException();
		if(bullet == null)
			throw new IllegalArgumentException();
		if(bullet.getShip() == this){
			bullet.transferToShip();
		}else
			bullet.terminate();
			this.terminate();
	}
	
	
	@Override
	public void resolveCollision(Ship other){
		
		//prepare all the varriables
		Vector2D deltaR = this.getPosition().difference(other.getPosition());
		Vector2D deltaV = this.getVelocity().difference(other.getVelocity());
		double massShip1 = this.getMass();
		double massShip2 = other.getMass();
		double sigma = this.getSigma(other);
		
		// run the calculations provided
		double energy = (2*massShip1*massShip2*deltaR.dotProduct(deltaV))/(sigma*(massShip1+massShip2));
		double xEnergy = energy*(this.getXPosition()-other.getXPosition())/sigma;
		double yEnergy = energy*(this.getYPosition()-other.getYPosition())/sigma;
		
		// set the velocities
		this.setVelocity(this.getXVelocity()+xEnergy/massShip1, this.getYVelocity()+yEnergy/massShip1);
		other.setVelocity(other.getXVelocity() - xEnergy/massShip2, other.getYVelocity() - yEnergy/massShip2);
		
		
	}

}