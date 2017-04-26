package asteroids.model;

public class Asteroid extends MinorPlanet {

	public Asteroid(double xPos, double yPos, double radius, double xVel, double yVel, double mass)
			throws IllegalArgumentException {
		super(xPos, yPos, radius, xVel, yVel, mass);
	}
	
	/**
	 * variable that stores the minimum density of the bullet
	 */
	public final static double MINIMUM_DENSITY = 2.65E12;
	
	/**
	 * Getter for the minimum density of the bullet
	 */
	@Override
	public double getMinimumDensity(){
		return MINIMUM_DENSITY;
	}

	@Override
	public void resolveCollision(Ship ship) throws IllegalStateException{
		if(!World.apparentlyCollide(this,ship))
			throw new IllegalStateException();
		ship.terminate();
	}	

}
