package asteroids.model;

public class Bullet extends WorldObject {
	
	public Bullet(double xPos, double yPos, double radius, double xVel, double yVel, double density, double mass){
		super(xPos, yPos, radius, xVel, yVel, density, mass);
		
	}
	
	public Bullet(){
		this(0,0,MIN_RADIUS,0,0,MINIMUM_DENSITY,1);
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
	 * @return |@see implementation
	 */
	public Ship getShip(){
		return this.associatedShip;
	}
	
	/**
	 * Associates the bullet with the specified ship if the bullet can be loaded
	 * @param ship
	 * @post	the specified ship is associated with the bullet	
	 * 			|@see implementation
	 * @throws 	IllegalArgumentException
	 */
	public void loadBulletOnShip(Ship ship) throws IllegalArgumentException{
		if((!this.isAssociated())&&(this.canBeLoadedOnShip(ship))){
				this.associatedShip = ship;
				this.getWorld().removeFromWorld(this);
				ship.loadBullet(this);
				this.syncBulletVectors();
		}
		else 
			throw new IllegalArgumentException();
		
	}
	
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
		
		this.getShip().loadBullet(this);
		
	}
	
	public boolean isLoadedOnShip(){
		return this.getWorld() != null;
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
	public final static int SHOOTING_VELOCITY=250;
}
