package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * 
 * @author Martijn & Flor
 *
 * @Invar	If a bullet belongs to a world, the total velocity of the bullet equals SHOOTING_VELOCITY
 * 			|if(residesinWorld())
 * 			|then WorldObject.getTotalVelocity(getXVelocity(), getYVelocity()) == SHOOTING_VELOCITY
 * 
 * @Invar	the density equals to the minimum density
 * 			|canHaveAsDensity()
 * 
 * @Invar	a bullet cannot be in a world and being loaded on a ship at the same time.
 */
public class Bullet extends WorldObject {
	
	/**
	 * Constructor for a Bullet object
	 * @param	xPos
	 * 			the desired x-position
	 * @param 	yPos
	 * 			the desired y-position
	 * @param 	radius
	 * 			the desired radius
	 * @param 	xVel
	 * 			the desired x-velocity
	 * @param 	yVel
	 * 			the desired y-velocity
	 * @param	density
	 * 			the desired density
	 * 
	 * @effect	construct a bullet with the given position, velocity, radius and density
	 * 			|WorldObject(xPos, yPos, radius, xVel, yVel, density)
	 * 
	 * @throws  IllegalArgumentException
	 * 			thrown if the radius is not valid
	 * 			|!isValidRadius()
	 */
	public Bullet(double xPos, double yPos, double radius, double xVel, double yVel, double density) throws IllegalArgumentException{
		super(xPos, yPos, radius, xVel, yVel, density);	
	}
	
	/**
	 * Default constructor for a bullet
	 * 
	 * @effect	Bullet(0, 0, getMinimumRadius(), 0, 0, 0)
	 */
	public Bullet(){
		this(0,0,MIN_RADIUS,0,0,1);
	}
	
	
	/**
	 * Destructor for a bullet class object
	 * 
	 * @effect		if the bullet has still a bidirectional relation with a ship
	 * 				unload the bullet from the ship
	 * 				|if(getShip().containsBullet(this)
	 * 				|then getShip().unloadBullet(this)
	 * 
	 * @post 		the unidirectional relation between the bullet and the ship is broken
	 * 				|new.getShip() = null
	 */
	@Override
	public void terminate(){
		// if there still exists a bidirectional relation between the bullet and a ship
		// unload the bullet from the ship
		if(this.getShip() != null && this.getShip().containsBullet(this))
			this.getShip().unloadBullet(this);
		this.associatedShip = null;
		super.terminate();
	}
	
	/**
	 * Checks whether the provided radius is valid
	 * @param 	rad
	 * 			the radius
	 * 
	 * @return |result == (radius >= MIN_RADIUS)
	 */
	@Override
	public boolean isValidRadius(double radius){
		return radius >= this.getMinimumRadius();
	}
	
	/**
	 * the minimum radius of a bullet
	 */
	public final static double MIN_RADIUS = 1d;
	
	/**
	 * @return 	the minimum radius of a bullet
	 * 			|result == MIN_RADIUS
	 */
	@Override
	public double getMinimumRadius(){
		return MIN_RADIUS;
	}
	
	/**
	 * Checker for the density
	 * @return 	true if the density is equal to the minimum density
	 * 			| result == density == getMinimumDensity()
	 * @return 	false if the denisty is NaN or the maximum value of type double
	 * 			| result == (!Double.isNaN(density)||!(density != getMinimumDensity()))
	 */
	@Override
	public boolean isValidDensity(double density){
		return density == this.getMinimumDensity()&&!Double.isNaN(density)&&density <= Double.MAX_VALUE;
	}
	
	/**
	 * variable that stores the minimum density of the bullet
	 */
	public final static double MINIMUM_DENSITY = 7.8E12;
	
	/**
	 * Getter for the minimum density of the bullet
	 */
	@Override
	public double getMinimumDensity(){
		return MINIMUM_DENSITY;
	}
	
	// do we want changes to the prime object by the clients?
	// gaat de bullet weldegelijk null teruggeven?
	// maak ook functie om associated ship los te koppelen van bullet (zodat we transitie naar de wereld kunnen maken)
	
	/**
	 * returns the associated ship of the bullet, if there is no such ship, return null
	 */
	public Ship getShip(){
		return this.associatedShip;
	}
	
	
	/**
	 * Associates the bullet with the specified ship if the bullet can be loaded 
	 * (basic relationship setter)
	 * 
	 * @param ship
	 * @effect	first the unidirectional between the bullet and the ship is established
	 * 			|setShip(ship)
	 * @effect	the ship is associated with the bullet (bidirectional relation)
	 * 			|ship.loadBullet(this)
	 * @effect	the bullets position and velocity are synced with the ship
	 * 			|syncBulletVectors();
	 */
	@Basic
	public void loadBulletOnShip(Ship ship) throws IllegalArgumentException{
				this.setShip(ship);
				ship.loadBullet(this);
				this.syncBulletVectors();
	}
	
	/**
	 * sets up an unidirectional relation between the bullet and the ship
	 * @param 	ship
	 * 			the ship that needs to be associated
	 * 
	 * @post	The bullet is associated with the ship in an unidirectional way
	 * 			| new.getShip() == ship
	 * 
	 * @throws  IllegalArgumentException
	 * 			thrown if the bullet is already associated or the bullet cannot be loaded on the ship
	 * 			| isAssociated()||!canBeLoadedOnShip()
	 */
	@Basic @Model
	private void setShip(Ship ship){
		if(this.isAssociated()||! this.canBeLoadedOnShip(ship))
			throw new IllegalArgumentException();
		this.associatedShip = ship;
	}
	
	/**
	 * Sets the associated ship to null if and only if the ship is terminated
	 * @Post	if the ship is terminated set the associated ship to null
	 * 			|if(getShip().isTerminated())
	 * 			|then (new)getShip() == null;
	 */
	public void setShipToNull(){
		if(this.getShip().isTerminated())
			this.associatedShip = null;
	}
	
	
	/**
	 * checker if the bullet can be loaded on the given ship
	 * @param 	ship
	 * 			the ship that is checked
	 * @return  true if and only if the ship isn't a null reference and the radius of the bullet
	 * 			is smaller than the ships radius
	 * 			|@see implementation
	 */
	public boolean canBeLoadedOnShip(Ship ship){
		return (ship!=null) && (this.getRadius() < ship.getRadius());
	}
	
	
	/**
	 * Basic setter for the loadedOnShip variable
	 * @post 	the bullet is transfered to the World
	 * 			|new.loadedOnShip() == false;
	 * 
	 * @effect	transfer the bullet to the same world as the ship
	 * 			|new.getWorld() == getShip().getWorld().addWorldObject(this)
	 * 
	 * @effect	unload the bullet from the ship
	 * 			|getShip().unLoadBullet(this)
	 */
	@Model
	protected void transferToWorld(){
		// select the current world of the ship
		World shipWorld = this.getShip().getWorld();
		shipWorld.addWorldObject(this);
		// unload the bullet
		this.getShip().unloadBullet(this);
	}
	
	protected void transferToShip(){
		this.getWorld().removeFromWorld(this);
		this.getShip().loadBullet(this);
	}
	
	/**
	 * checks if the bullet is loaded on a ship
	 * @return
	 */
	public boolean getLoadedOnShip(){
		return loadedOnShip;
	}
	
	
	public void setLoadedOnShip(boolean value){
		this.loadedOnShip = value;
	}
	
	private boolean loadedOnShip = false;
	
	/**
	 * Checks if a Bullet is associated with a World or a Ship.
	 * @return true if and only if the Bullet is Already Associated
	 */
	public boolean isAssociated(){
		return (this.getShip()!= null)||(this.getWorld() != null);
	}
	
	/**
	 * synchronize the position and the velocity of the bullet with the associated ship
	 * @Post	the velocity of the bullet is equal to the velocity of the associated ship
	 * 			| new.getVelocity() == this.getShip().getVelocity()
	 * @Post	the position of the bullet is equal to the position of the associated ship
	 * 			| new.getPosition() == this.getShip().getVelocity()
	 * @throws 	IllegalStateException
	 */
	protected void syncBulletVectors()throws IllegalStateException{
		if(!getLoadedOnShip())
			throw new IllegalStateException();
		Ship matchedShip = this.getShip();
		this.setPosition(matchedShip.getXPosition(), matchedShip.getYPosition());
//		this.setXPosition(matchedShip.getXPosition());
//		this.setYPosition(matchedShip.getYPosition());
		this.setVelocity(matchedShip.getXVelocity(), matchedShip.getYVelocity());
	}
	
	/**
	 * checker for the association between bullet and world
	 * @param 	world
	 * 			the world that needs to be associated with the prime object
	 * @return	true if the world is not a null reference and the bullet is currently not in a world
	 * 			| result == (world != null&&getWorld() == null &&!isTerminated())
	 * 
	 * @return  true if the world is a null reference and the bullet is associated with a ship
	 * 			| result == (world==null && getShip()!= null&&!isTerminated())
	 * 
	 * @return 	true if the bullet is terminated and the provided world is a null reference
	 * 			| result == ( world==null && isTerminated())
	 * @return 	true if the current world is terminated and the provided world is a null reference
	 * 			| result == ( world ==null && this.getWorld().isTerminated())
	 */
	public boolean canHaveAsWorld(World world){
		// the world is not a null reference and the current bullet is not associated with another world
		if(world != null&&!residesInWorld()&&!this.isTerminated())
			return true;
		// the world is null but the bullet is associated with as ship --> case a bullet is loaded onto the
		// ship to resolve a collision
		else if(world==null && !this.getLoadedOnShip()&&!this.isTerminated())
			return true;
		// can have as world null if the bullet is terminated
		else if(this.isTerminated()&&world==null)
			return true;
		// can have a null reference for the world if the current world is terminated
		else if( world==null && this.getWorld().isTerminated())
			return true;
		else
			return false;
		
	}
	
	/**
	 * the associated ship of the bullet
	 */
	private Ship associatedShip;
	
	
	/**
	 * the total velocity of a bullet upon shooting.
	 */
	public final static double SHOOTING_VELOCITY=250;
	
	/**
	 * Resolves the collision between a bullet and a Ship
	 * 
	 * @effect  resolves the collision between a ship and a bullet
	 * 			|ship.resolveCollision(this)
	 */
	@Override
	public void resolveCollision(Ship ship){
		ship.resolveCollision(this);
	}
	
	
	/**
	 * Resolves the collision between a bullet and a world
	 * 
	 * @post  	the bounce count is incremented by 1
	 * 			|incrementBounceCount();
	 * 
	 * @post	if the bounce count is larger than or equal to MAX_BOUNCES
	 * 			the bullet will be terminated
	 * 			|if(getBounceCount() >= MAX_BOUNCES)
	 * 			|then terminate()
	 * 
	 */
	@Override
	public void resolveCollision(World world){
		super.resolveCollision(world);
		this.incementBounceCount();
		if(this.getBounceCount()>=MAX_BOUNCES)
			this.terminate();
		
	}
	
	/**
	 * Basic getter for the bounce count variable
	 * @return	the number of bounces
	 */
	@Basic
	public int getBounceCount(){
		return this.bounceCount;
	}
	
	/**
	 * increments the bounce count of the bullet
	 * @post 	the bounce count is incremented by one
	 * 			|new.getBounceCount() ==  getBounceCount() + 1
	 */
	@Basic
	public void incementBounceCount(){
		this.bounceCount++;
	}
	
	private int bounceCount = 0;
	
	public final int MAX_BOUNCES = 3;
	
	
	/**
	 * Resolves the collision between a bullet and another bullet
	 * @param 	other
	 * 			the other bullet involved in the collision
	 * 
	 * @post	both bullets are terminated
	 * 			| terminate()
	 * 			| other.terminate()
	 * 
	 * @throws 	IllegalArgumentException
	 * 			thrown if other is a null reference
	 * 			| other == null
	 * 
	 * @throws 	IllegalStateException
	 * 			thrown if the bullets don't collide
	 * 			|!overlap(other)
	 */
	@Override
	public void resolveCollision(Bullet other){
		if(other == null)
			throw new IllegalArgumentException();
		//if(!this.overlap(other))
		//	throw new IllegalStateException();
		this.terminate();
		other.terminate();
	}

}
