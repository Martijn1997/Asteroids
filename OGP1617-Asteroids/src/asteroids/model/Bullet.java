package asteroids.model;

public class Bullet extends WorldObject {
	
	/**
	 * Sets the radius of a ship
	 * @post 	the radius is set to radius
	 * 			| new.getRadius() == radius
	 * 
	 * @throws  IllegalArgumentException
	 * 			| !isValidRadius(radius)
	 * 		
	 */
	public void setRadius(double radius) throws IllegalArgumentException{
		if(!isValidRadius(radius))
			throw new IllegalArgumentException();
		else
			super.setRadius(radius);
	}
	
	/**
	 * checks whether the provided radius is valid
	 * @param 	rad
	 * 			the radius
	 * 
	 * @return |result == (radius >= MIN_RADIUS)
	 */
	public static boolean isValidRadius(double radius){
		return radius >= MIN_RADIUS;
	}
	
	public final static double MIN_RADIUS = 1d;
	
	public void setDensity(double density){
		if(isValidDensity(density))
			super.setDensity(density);
		else
			super.setDensity(MINIMUM_DENSITY);
			
	}
	
	public static boolean isValidDensity(double density){
		return density >= MINIMUM_DENSITY;
	}
	
	public final static double MINIMUM_DENSITY = 7.8E12;
	
	public void setMass(double mass){
		if(this.canHaveAsMass(mass))
			super.setMass(mass);
		else
			super.setMass(volumeSphere(this.getRadius())*MINIMUM_DENSITY);
		
	}
	
	public boolean canHaveAsMass(double mass){
		return mass >= volumeSphere(this.getRadius())*this.getDensity();
	}
	
	
}
