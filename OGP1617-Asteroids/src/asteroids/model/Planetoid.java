package asteroids.model;

import java.util.Random;

public class Planetoid extends MinorPlanet {
	
	public Planetoid(double xPos, double yPos, double radius, double xVel, double yVel, double totalTraveledDistance, double mass)
			throws IllegalArgumentException {
		super(xPos, yPos, radius, xVel, yVel, mass);
		this.setOriginalRadius(radius);
		this.setTotalTraveledDistance(totalTraveledDistance);
	}

	public double getTotalTraveledDistance(){
		return this.totalTraveledDistance;
	}
	
	public void setTotalTraveledDistance(double distance){
		if (distance >= 0)
			this.totalTraveledDistance = distance;
		else
			this.totalTraveledDistance = 0;
		double radius = this.getOriginalRadius();
		double newRadius = radius - totalTraveledDistance*(0.0001/100);
		if (newRadius < 5)
			this.terminate();
		else
			this.setRadius(newRadius);
	}
		
	private double totalTraveledDistance;
	
	/**
	 * variable that stores the minimum density of the bullet
	 */
	public final static double MINIMUM_DENSITY = 0.917E12;
	
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
		transport(ship);
	}

	private void transport(Ship ship) {
		World world = ship.getWorld();
		double radius = ship.getRadius();
		double minValue = radius;
		double xMax = world.getWidth() - radius;
		double yMax = world.getHeight() - radius;
		Random rX = new Random();
		Random rY = new Random();
		double newX = minValue + (xMax - minValue) * rX.nextDouble();
		double newY = minValue + (yMax - minValue) * rY.nextDouble();
		ship.setPosition(newX, newY);
		if (!world.checkTransport(ship))
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
		return radius >= this.getMinimumRadius();
	}
	
	public double getOriginalRadius(){
		return originalRadius;
	}
	
	public void setOriginalRadius(double radius) throws IllegalArgumentException{
		if(! this.canHaveAsRadius(radius))
			throw new IllegalArgumentException();
		this.originalRadius = radius;
	}
	
	private double originalRadius;
	
	@Override
	public void move(double time){
		if(!isValidTime(time))
			throw new IllegalArgumentException();
		
		Vector2D oldPosVector = this.getPosition();
		Vector2D newPosVector = this.getPosition().vectorSum(this.getVelocity().rescale(time));
		
		this.setPosition(newPosVector.getXComponent(), newPosVector.getYComponent());
		double oldDistance = this.getTotalTraveledDistance();
		double newDistance = oldDistance + oldPosVector.distanceTo(newPosVector);
		this.setTotalTraveledDistance(newDistance);
	}
}
