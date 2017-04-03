package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * 
 * @author Martijn & Flor
 *
 * @Invar	If a bullet belongs to a world, the total velocity of the bullet equals SHOOTING_VELOCITY
 * 			|if(getWorld() != null)
 * 			|then WorldObject.getTotalVelocity(getXVelocity(), getYVelocity()) == SHOOTING_VELOCITY
 */
public class Bullet extends WorldObject {
	
	public Bullet(double xPos, double yPos, double radius, double xVel, double yVel, double mass){
		super(xPos, yPos, radius, xVel, yVel, mass);	
	}
	
	public Bullet(){
		this(0,0,MIN_RADIUS,0,0,1);
	}
	
	
	/**
	 * Terminator for a bullet class object
	 * @effect		if the bullet has still a bidirectional relation with a ship
	 * 				unload the bullet from the ship
	 * 				|if(getShip().containsBullet(this)
	 * 				|then getShip().unloadBullet(this)
	 * @post 		the unidirectional relation between the bullet and the ship is broken
	 * 				|new.getShip() = null
	 */
	@Override
	public void terminate(){
		
		// if there still exists a bidirectional relation between the bullet and a ship
		// unload the bullet from the ship
		if(this.getShip().containsBullet(this))
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
		return radius >= MIN_RADIUS;
	}
	
	public final static double MIN_RADIUS = 1d;
	
	/**
	 * @return 	the minimumn radius of a bullet
	 * 			|result == MIN_RADIUS
	 */
	@Override
	public double getMinimumRadius(){
		return MIN_RADIUS;
	}
	
	@Override
	public boolean isValidDensity(double density){
		return density >= MINIMUM_DENSITY&&!Double.isNaN(density)&&density <= Double.MAX_VALUE;
	}
	
	public final static double MINIMUM_DENSITY = 7.8E12;
	
	@Override
	public boolean canHaveAsMass(double mass){
		return mass == calcMinMass()&&!Double.isNaN(mass)&&mass <= Double.MAX_VALUE;
	}
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
	 * @param ship
	 * @effect	first the unidirectional between the bullet and the ship is established
	 * 			|setShip(ship)
	 * @effect	the ship is associated with the bullet (bidirectional relation)
	 * 			|ship.loadBullet(this)
	 * @effect	the bullets position and velocity are synced with the ship
	 * 			|syncBulletVectors();
	 */
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
	 * 			|this.loadedOnShip = false;
	 */
	public void transferToWorld(){
		World shipWorld = this.getShip().getWorld();
		shipWorld.addWorldObject(this);
		this.getShip().unloadBullet(this);
		
	}
	
	public void transferToShip(){
		this.getWorld().removeFromWorld(this);
		this.getShip().loadBullet(this);
			
	}
	
	public boolean isLoadedOnShip(){
		return this.getWorld() == null;
	}
	
	
	/**
	 * Checks if a Bullet is associated with a World or a Ship.
	 * @return true if and only if the Bullet is Already Associated
	 */
	public boolean isAssociated(){
		return (this.getShip()!= null)||(this.getWorld() != null);
	}
	
	protected void syncBulletVectors()throws IllegalStateException{
		if(!isLoadedOnShip())
			throw new IllegalStateException();
		Ship matchedShip = this.getShip();
		this.setXPosition(matchedShip.getXPosition());
		this.setYPosition(matchedShip.getYPosition());
		this.setVelocity(matchedShip.getXVelocity(), matchedShip.getYVelocity());
	}
	
	public boolean canHaveAsWorld(World world){
		return (world!=null) || (world==null && this.getShip()!= null);
		
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
		if(!this.overlap(other))
			throw new IllegalStateException();
		this.terminate();
		other.terminate();
	}
}
