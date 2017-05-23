package asteroids.model;

public class Asteroid extends MinorPlanet {

	/**
	 * Initializes an object of the Asteroid class
	 * @param 	xPos
	 * 			the x position of the Asteroid
	 * 
	 * @param	yPos
	 * 			the y position of the Asteroid

	 * @param 	radius
	 * 			the Radius of the Asteroid
	 * 
	 * @param 	xVel
	 * 			the x velocity of the Asteroid
	 * 
	 * @param	yVel
	 * 			the y velocity of the Asteroid
	 * 
	 * @param	mass
	 * 			the mass of the Asteroid
	 * 
	 * @effect	creates a Asteroid with the given values for position, velocity, radius and mass
	 * 			|WorldObject(xPos,yPos,Radius,xVel,yVel,mass)
	 * 
	 */
	public Asteroid(double xPos, double yPos, double radius, double xVel, double yVel, double mass)
			throws IllegalArgumentException {
		super(xPos, yPos, radius, xVel, yVel, mass);
	}
	
	/**
	 * variable that stores the minimum density of the Asteroid
	 */
	public final static double MINIMUM_DENSITY = 2.65E12;
	
	/**
	 * Getter for the minimum density of the Asteroid
	 */
	@Override
	public double getMinimumDensity(){
		return MINIMUM_DENSITY;
	}

	/**
	 * Resolves the collision between an asteroid and a ship
	 * 
	 * @see implementation
	 */
	@Override
	public void resolveCollision(Ship ship) throws IllegalStateException{
		if(!World.apparentlyCollide(this,ship))
			throw new IllegalStateException();
		ship.terminate();
	}	

	/**
	 * Checks whether the provided radius is valid
	 * @param 	rad
	 * 			the radius
	 * 
	 * @return |result == (radius >= MIN_RADIUS)
	 */
	@Override
	public boolean canHaveAsRadius(double radius){
		return radius >= this.getMinimumRadius() && !this.hasInitializedRadius();
	}
	
	@Override
	public String toString(){
		return "Asteroid";
	}
}
