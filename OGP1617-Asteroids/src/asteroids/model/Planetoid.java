package asteroids.model;

import java.util.Random;

public class Planetoid extends MinorPlanet {
	
	public Planetoid(double xPos, double yPos, double radius, double xVel, double yVel, double totalTraveledDistance, double mass)
			throws IllegalArgumentException {
		super(xPos, yPos, radius, xVel, yVel, mass);
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
	
//	/**
//	 * @return 	the radius of the WorldObject
//	 */
//	@Basic @Raw @Override
//	public double getRadius(){
//		return radius;
//	}
//	
//	/**
//	 * The variable that stores the radius of the WorldObject
//	 */
//	private double radius;
//	
//	@Override
//	public void setRadius(double radius) throws IllegalArgumentException{
//		if(! this.isValidRadius(radius))
//			throw new IllegalArgumentException();
//		
//		this.radius = radius;
//	}
	
	@Override
	public void move(double time){
		if(!isValidTime(time))
			throw new IllegalArgumentException();
		
		Vector2D oldPosVector = this.getPosition();
		Vector2D newPosVector = this.getPosition().vectorSum(this.getVelocity().rescale(time));
		
		this.setPosition(newPosVector.getXComponent(), newPosVector.getYComponent());
		double oldDistance = this.getTotalTraveledDistance();
		double newDistance = oldDistance + oldPosVector.distanceTo(newPosVector);
		double radius = this.getRadius();
		double newRadius = radius - newDistance*(0.0001/100);
		if (newRadius < 5)
			this.terminate();
	}
}
