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

//TODO removebullet from ship

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
 * @Invar	The Density of the ship is always larger than or equals to the MINIMUM_DENISTY
 * 			|isValidDensity()
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
	 * @effect	creates a ship with the given values for position, velocity, radius and density
	 * 			|WorldObject(xPos,yPos,Radius,xVel,yVel,density)
	 * 
	 * @effect 	the orientation of the ship is set to the given orientation
	 * 			| setOrientation(orientation)
	 */
	public Ship(double xPos, double yPos, double orientation, double radius, double xVel, double yVel, double mass)throws IllegalArgumentException{
		
		super(xPos, yPos, radius, xVel, yVel, mass);
		this.setOrientation(orientation);
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
	 * @effect 	unload all the bullets on the ship and terminates them
	 * 			|new.getLoadedBullets().size() == 0
	 * 			|for bullet in old.getLoadedBullets() bullet.terminate()
	 * 
	 * @effect	the thruster of the ship is turned off
	 * 			|thrustOff()
	 * 
	 * @effect	terminate the ship
	 * 			| WorldObject.terminate()
	 * 
	 * @effect 	the reference between the free flying bullets in the world
	 * 			and the ship is broken
	 * 			| for bullet in old.getWorld().getAllBullets() 
	 * 			|		if(bullet.getShip == this) then bullet.setShipToNull()
	 */
	@Override
	public void terminate(){
		
		// terminate all the loaded bullets
		if(this.getLoadedBullets()!= null){
			HashSet<Bullet> copyLoaded = new HashSet<Bullet>(this.getLoadedBullets());
			for(Bullet bullet: copyLoaded){
				bullet.terminate();
			}
		}	
		this.thrustOff();
		World shipWorld = this.getWorld();

		//invoke the top level terminator to raise the termination flag
		super.terminate();
		
		for(Bullet bullet: shipWorld.getAllBullets()){
			if(bullet.getShip() == this){
				// function only works if termination flag is raised
				bullet.setShipToNull();
			}
		}
	}

	
	/**
	 * the size ratio between the bullets and the ship
	 */
	public final static double STANDARD_BULLET_SIZE_RATIO = 0.10;
	
	
	 /**
	  * set the position of the ship to the provided values
	  * @param 	xPos
	  * 		the desired x-position of the ship
	  * 
	  * @param	yPos
	  * 		the desired y-position of the ship
	  * 
	  * @effect set the velocity of the ship
	  * 		|super.setPosition(xPos,yPos)
	  * 
	  * @effect synchronize the loaded bullets with the ship
	  * 		| for bullet in getLoadedBullets() do bullet.syncBulletVectors();
	  */
	@Override @Basic @Raw
	public void setPosition(double xPos, double yPos){
		super.setPosition(xPos, yPos);
		if(this.getLoadedBullets()!= null){
			for(Bullet bullet : this.getLoadedBullets()){
				bullet.syncBulletVectors();
			}
		}
	}
	
	
	 /**
	  * set the velocity of the ship to the provided values
	  * @param	xVel
	  * 		the desired x-velocity
	  * 
	  * @param  yVel
	  * 		the desired y-velocity
	  * 
	  * @effect super.setVelocity(xVel, yVel)
	  * 
	  * @effect synchronize the loaded bullets with the ship
	  * 		| for bullet in getLoadedBullets() do bullet.syncBulletVectors();
	  */
	@Override @Basic @Raw
	public void setVelocity(double xVel, double yVel){
		super.setVelocity(xVel, yVel);
		if(this.getLoadedBullets() != null){
			for(Bullet bullet : this.getLoadedBullets()){
				bullet.syncBulletVectors();
			}
		}
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
	 * @Pre 	the provided angle is between 0 and Math.PI * 2
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
	 * returns true if and only if the given angle is between 0 and 2PI
	 */
	public static boolean isValidOrientation(double angle){
		return (angle <= 2*Math.PI)&&(angle >= 0);
	}
	
	/**
	 * Variable that stores the orientation of the ship
	 */
	private double orientation;
	
	
	/**
	 * checks whether the provided radius is valid
	 * @param 	rad
	 * 			the radius
	 * 
	 * @return |result == (rad >= getMinimumRadius)
	 */
	@Override
	public boolean isValidRadius(double rad){
		return rad >= this.getMinimumRadius();
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
	
//	/**
//	 * Checks if the density if valid
//	 * @return 	true if the density is greater than the minimum density
//	 * 			|result == density >= getMinimumDensity()
//	 * @return  false if the provided density is NaN
//	 * 			|result == !Double.isNan(density)
//	 */
//	@Override
//	public boolean isValidDensity(double density){
//		return density >= this.getMinimumDensity() && !Double.isNaN(density);
//	}
	
	/**
	 * Variable that stores the minimum density of the ship
	 */
	public final static double MINIMUM_DENSITY = 1.42E12;
	
	/**
	 * returns the minimum density of the ship
	 */
	@Override
	public double getMinimumDensity(){
		return MINIMUM_DENSITY;
	}
	
	/**
	 * checks if the mass is valid
	 * @return 	return true if and only if the mass is larger than the minimum mass
	 * 			| if(mass>=calcMinMass())
	 * 			| then result == true
	 */
	@Override
	public boolean canHaveAsMass(double mass){
		if(mass>=calcMinMass())
			return true;
		else{
			return false;
		}
	}
	
	/**
	 * Calculates the total mass of the ship (bullets + ship mass)
	 * @return	the mass of the ship + the mass of all the loaded bullets
	 * 			|result == sum(for bullet in getLoadedBullets() do bullet.getMass())
	 */
	public double getTotalMass(){
		double totalMass = this.getMass();
		// if the bullet arsenal is empty or uninitialized the method returns the mass of the ship
		if(this.getLoadedBullets() == null || this.getLoadedBullets().size() == 0){
			return this.getMass();
		}

		for(Bullet bullet: this.getLoadedBullets()){
			totalMass += bullet.getMass();
		}
		return totalMass;
	}

	/**
	 * variable that stores the trusterstus of the ship
	 */
	private boolean thrustStatus;
	
	/**
	 * Sets the new position of the ship according to the current speed and given time interval
	 * @param   time
	 * 			the passed time used to move the ship
	 * 
	 * @effect 	moves the ship for a given amount of time
	 * 			| WorldObject.move(time)
	 * 
	 * @throws	IllegalArgumentException
	 * 			The given value for the time is illegal
	 * 			| ! isValidTime(time)
	 */
	@Override
	public void move(double time) throws IllegalArgumentException{
		super.move(time);
		
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
	
	/**
	 * Basic setter for the thruster status
	 * @post 	the thruster status is set to true
	 * 			|new.getThrusterStatus() == true
	 */
	@Basic @Raw
	public void thrustOn(){
		this.thrustStatus = true;
	}
	
	/**
	 * Basic setter for the thruster status
	 * @post	the thruster status is set to false
	 * 			| new.getThrusterStatus() == false
	 */
	@Basic @Raw
	public void thrustOff(){
		this.thrustStatus = false;
	}
	
	/**
	 * Basic getter for the thruster status
	 * @return the ThrusterStatus
	 */
	@Basic @Raw
	public boolean getThrusterStatus(){
		return this.thrustStatus;
	}
	
	/**
	 * basic getter for the thrust force of the ship
	 * @return	the thrust force of the ship
	 */
	@Basic @Raw
	public double getThrustForce(){
		return this.thrustForce;
	}
	
	/**
	 * calculates the acceleration of the ship
	 * @return	the acceleration of the ship
	 * 			|result == getThrustforce/getMass();
	 */
	public double getAcceleration(){
		if (this.getThrusterStatus())
			return  this.getThrustForce()/this.getTotalMass();
		else
			return 0;
	}
	
	/**
	 * changes the velocity of the ship, according to the current orientation and the given acceleration
	 * @param 	deltaTime
	 * 			the time that passes used to calculate the new velocity of the ship
	 * 
	 * @post	if the thruster status is false or the supplied time isn't valid no changes to the velocity are made
	 * 			|if(!getThrusterStatus()||!isValidTime(deltaTime)
	 * 			|then new.getVelocity() == getVelocity()
	 * 
	 * @post 	if the calculation of the totalVelocity caused overflow no velocity is adjusted
	 * 			|if(causedOverflow())
	 * 			|then new.getVelocity() == getVelocity()
	 * 
	 * @effect	The new speed is set and rescaled if necessary
	 * 			|setVelocity(getXVelocity() + acceleration * Math.cos(getOrientation())*deltaTime,
	 * 			|				getYVelocity() + acceleration * Math.sin(getOrientation())*deltaTime)
	 * 
	 */
	public void thrust(double deltaTime){
		
		if(this.getThrusterStatus()&&isValidTime(deltaTime)){
			
			// calculate the acceleration
			double acceleration = this.getAcceleration();
			double newXVel = this.getXVelocity() + acceleration * Math.cos(this.getOrientation())*deltaTime;
			double newYVel = this.getYVelocity() + acceleration * Math.sin(this.getOrientation())*deltaTime;
			
			
			double totalVelocity = totalVelocity(newXVel, newYVel);
			
			// check if the totalVelocity is an overflow
			if(!causedOverflow(totalVelocity)){
				// set the velocity to the new Values
				
				this.setVelocity(newXVel, newYVel);		

			}
		}
	}
	
	
	/**
	 * variable that stores the thrust force of the ship
	 */
	private final double thrustForce = 1.1E18;
	
	
	/**
	 * Loads the provided bullet onto the ship
	 * @param bullet
	 * 
	 * @post	The bullet is associated with the ship
	 * 			| new.getLoadedBullets() == getLoadedBullets().add(bullet)
	 * 
	 * @effect	raises flag in the bullet object that it is loaded on the ship
	 * 			| bullet.setLoadedOnShip(true)
	 * 
	 * @throws 	IllegalArgumentException
	 * 			If the bullet doens't reference the ship throw the exception
	 * 			|bullet.getShip()!=this
	 */
	@Model
	protected void loadBullet(Bullet bullet) throws IllegalArgumentException{
		if(bullet.getShip()==this&&bullet!=null){
			loadedBullets.add(bullet);
			bullet.setLoadedOnShip(true);
		}
		else
			throw new IllegalArgumentException();
	}
	

	/**
	 * Loads an arbitrary amount of bullets onto the ship 
	 * if a bullet is ineffective the bullet is not loaded
	 * @param 	bullets
	 * 			the bullets to be loaded onto the ship
	 * 
	 * @effect	all the effective bullets are loaded on the ship
	 * 			|for bullet in bullets do bullet.LoadBulletonShip(this)
	 * 
	 * @throws 	IllegalArgumentException if the argument contains a bullet that is a null reference
	 * 			or when a bullet in the argument already is associated with as ship
	 * 			|bullets.contains(null)||bullets[i].isAssociated()
	 */
	public void loadBullets(Bullet...bullets) throws IllegalArgumentException{
		Set<Bullet> bulletSet = new HashSet<Bullet>(Arrays.asList(bullets));
		if(bulletSet.contains(null))
			throw new IllegalArgumentException();
		
		boolean invalidBullets = false;
		// check if the bullet isn't already associated with another ship or world
		for(Bullet bullet : bulletSet){
			
			try{
				bullet.loadBulletOnShip(this);
			}catch(IllegalArgumentException exc){
				invalidBullets = true;
			}
			
		}
		if(invalidBullets == true)
			throw new IllegalArgumentException();
		
	}
	
	
	/**
	 * unloads the specified bullet
	 * @param 	bullet
	 * @post	The association between the ship and the bullet is broken
	 * 			|getLoadedBullets.remove(bullet)
	 * 
	 * @effect	The loaded flag of the unloaded bullet is set to false
	 * 			|bullet.setLoadedOnShip(false)
	 * 
	 * @throws 	IllegalArgumentException
	 * 			|bullet == null || !containsBullet(bullet)
	 */
	public void removeBulletFromShip(Bullet bullet) throws IllegalArgumentException{
		if(bullet == null)
			throw new IllegalArgumentException();
		if(!this.containsBullet(bullet))
			throw new IllegalArgumentException();
		
		this.getLoadedBullets().remove(bullet);
		bullet.setLoadedOnShip(false);
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
	 * Getter for the loaded bullets, creates a copy of the loaded bullets
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
	

	/**
	 * Fires the Bullet into space
	 * @param 	bullet
	 * 
	 * @effect	The bullet is removed from the arsenal of the ship and the bullet is cast into the world
	 * 			|transferToWorld()
	 * 
	 * @post  	The fired bullet is positioned next to the ship (on the relative y-axis at a distance equal to the total radii)
	 * 			|(new bullet).getXPosition()== getXPosition() - Math.sin(getOrientation())*(getRadius()+bullet.getRadius())
	 *			|(new bullet).getYPosition()==getYPosition() + Math.cos(getOrientation())*(getRadius()+bullet.getRadius())
	 *
	 * @post	The fired bullet has a standard velocity (depending on bullet.SHOOTING_VELOCITY) in the same direction as the ship is facing
	 * 			|(new bullet).getXVelocity() == Math.cos(getOrientation())*Bullet.SHOOTING_VELOCITY;
	 *			|(new bullet).getYVelocity() == Math.sin(getOrientation())*Bullet.SHOOTING_VELOCITY;
	 *
	 *@effect	if the bullet overlaps with another object in the world or the world boundary itself
	 *			resolve the collision
	 *			|resolveBulletCrash(bullet)
	 */
	public void fireBullet(){
		
		// select a bullet from the magazine
		Bullet bullet = selectBullet();
		
		// chose what to do if there is no bullet loaded
		if(bullet == null)
			return;
		
		if (this.getWorld() == null)
			return;
		
		// set the position of the bullet.
		double nextToShipX = this.getXPosition() + Math.cos(this.getOrientation())*(this.getRadius()+bullet.getRadius()*BULLET_OFFSET);
		double nextToShipY = this.getYPosition() + Math.sin(this.getOrientation())*(this.getRadius()+bullet.getRadius()*BULLET_OFFSET);
		
		// set the bullet next to the ship
		bullet.setPosition(nextToShipX, nextToShipY);
		
		// set the velocity of the bullet
		double bulletXVelocity = Math.cos(this.getOrientation())*Bullet.SHOOTING_VELOCITY;
		double bulletYVelocity = Math.sin(this.getOrientation())*Bullet.SHOOTING_VELOCITY;

		bullet.setVelocity(bulletXVelocity, bulletYVelocity);	

		//unload the bullet, needs to be at the end because the bullet coordinates need to be different from the
		//coordinates of the ship
		try{
			bullet.transferToWorld();
		} catch(IllegalArgumentException exc){
			this.resolveBulletCrash(bullet);				
		}
	}


	/**
	 * resolves a bullet crash upon firing a bullet from the ship
	 * 
	 * @param 	bullet
	 * 
	 * @effect	if the bullet collides with a world boundary terminate the bullet
	 * 			|bullet.terminate()
	 * 
	 * @effect 	if the bullet crashes with another bullet resolve as bullet bullet collision
	 * 			|bullet.resolveCollision((Bullet)getEntityAt(getWorld.().getCollisionPartner()))
	 * 
	 * @effect 	if the bullet collides with a ship resolve as bullet ship collision
	 * 			|bullet.resolveCollision((Ship)getEntityAt(getWorld.().getCollisionPartner())
	 * 
	 * @effect 	if the bullet is inside another entity (greater overlap than significant)
	 * 			|
	 */
	private void resolveBulletCrash(Bullet bullet) {
		Vector2D otherPos = this.getWorld().getPositionCollisionPartner(bullet);
		// if the otherPos is a null reference this means that no such collision partner
		// could be found, thus the bullet has crashed with the world boundary
		if(otherPos == null){
			bullet.terminate();
		}
		else{
			WorldObject other = this.getWorld().getEntityAt(otherPos);
			try {
				// if the collision partner is a bullet resolve as a bullet bullet collision
				if(other instanceof Bullet){
					bullet.resolveCollision((Bullet)other);
				// otherwise the collision partner is a Ship
				}else
					bullet.resolveCollision((Ship)other);
			//if there is no significant overlap between the bullet and the other entity
			//means that they are positioned within each other, terminate both
			} catch (IllegalStateException exc) {
				bullet.terminate();
				other.terminate();
			}
		}
	}

	/**
	 * variable that stores the offset of a bullet from the ship
	 */
	public final static double BULLET_OFFSET = 1.1;
	
	/**
	 * Checker for adding a ship to a world
	 * @return  true if the world is not a null reference and the ship isn't already in a world.
	 * 			|result == ((world != null &&this.getWorld()==null)
	 * @return 	true if the Ship is terminated and the provided world is a null reference
	 * 			|result == (this.isTerminated() && world == null))
	 * @return  true if the world is a null reference and the current associated world is terminated
	 */
	public boolean canHaveAsWorld(World world){
		// if the ship is not terminated and has no associated world and
		// the provided world is not null
		if ((world != null &&!this.residesInWorld()&&!this.isTerminated())){
			return true;
		// if the ship is terminated the world is null the world can be set to null
		}else if (this.isTerminated() && world == null){
			return true;
		// if the current associated world of the ship is terminated the world can be set to null
		}else if (this.getWorld().isTerminated() && world == null){
			return true;
		// in all other cases return false
		}else 
			return false;
	}
	
	/**
	 * The variable that stores the set of bullets that is contained within the ship
	 */
	private Set<Bullet> loadedBullets = new HashSet<Bullet>();
	
	@Override
	public void resolveCollision(WorldObject other){
		if (other instanceof Ship)
			this.resolveCollision((Ship) other);
		if (other instanceof Bullet)
			this.resolveCollision((Bullet) other);
		else
			other.resolveCollision(this);
	}
	
	/**
	 * Resolve the collision between a ship and a bullet
	 * @param 	bullet
	 * 			The bullet that collides with the ship
	 * 
	 * @effect	if the bullet is associated with the ship, transfer the bullet to the ship
	 * 			| if bullet.getShip() == this
	 * 			| then bullet.transferToShip()
	 * 
	 * @throws	IllegalStateException
	 * 			thrown if the bullet and the ship don't overlap
	 * 			|!World.apparentlyCollide(this, bullet)
	 * 
	 * @throws 	IllegalArgumentException
	 * 			thrown if the bullet is a null reference
	 * 			| bullet == null
	 */
	public void resolveCollision(Bullet bullet)throws IllegalStateException, IllegalArgumentException{
		if(!World.apparentlyCollide(this,bullet))
			throw new IllegalStateException();
		
		if(bullet == null)
			throw new IllegalArgumentException();
		
		if(bullet.getShip() == this){
			bullet.loadBulletOnShip(this);
		}else{
			bullet.terminate();
			this.terminate();
		}
	}
	
	/**
	 * resolves the collision between a ship and another ship
	 * @post	the collision is resolved and the velocity of both ships is set accordingly
	 * 		 	|@see implementation
	 * @throws  IllegalStateException
	 * 			thrown if the provided ship and the prime object don't collide
	 * 			|World.apparentlyCollide(this,other)
	 */
	public void resolveCollision(Ship other) throws IllegalStateException{
		
		if(!World.apparentlyCollide(this, other)){
			throw new IllegalStateException();
		}
		
		//prepare all the variables
		Vector2D deltaR = this.getPosition().difference(other.getPosition());
		Vector2D deltaV = this.getVelocity().difference(other.getVelocity());
		double massShip1 = this.getMass();
		double massShip2 = other.getMass();
		double sigma = this.getSigma(other);
		
		// run the calculations provided
		double energy = ((2*massShip1*massShip2*deltaR.dotProduct(deltaV))/(sigma*(massShip1+massShip2)));
		Vector2D energyVector = this.getPosition().difference(other.getPosition()).rescale(energy/sigma);
		
		// set the velocities
		this.setVelocity(this.getXVelocity()-energyVector.getXComponent()/massShip1, this.getYVelocity()-energyVector.getYComponent()/massShip1);
		other.setVelocity(other.getXVelocity() + energyVector.getXComponent()/massShip2, other.getYVelocity() + energyVector.getYComponent()/massShip2);
		
		// safety margins on the bounce
//		Vector2D object1Pos = this.getPosition();
//		Vector2D object2Pos = other.getPosition();
//		this.move(1E-10);
//		other.move(1E-10);
//		this.getWorld().updatePosition(object1Pos, this);
//		this.getWorld().updatePosition(object2Pos, other);
	}

}