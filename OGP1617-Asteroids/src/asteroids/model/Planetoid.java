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
	public void terminate(){
		double radP = this.getRadius();
		World world = this.getWorld();
		if((radP >= 30) && (world != null)){
			double rad = radP/2;
			double xVelP = this.getXVelocity();
			double yVelP = this.getYVelocity();
			double velP = totalVelocity(xVelP, yVelP);
			double totalVelocity = 1.5 * velP;
			Random r = new Random();
			double orientation = 2 * Math.PI * r.nextDouble();
			double xVel1 = totalVelocity * Math.cos(orientation);
			double yVel1 = totalVelocity * Math.sin(orientation);
			double xPos = this.getXPosition();
			double yPos = this.getYPosition();
			double xPos1 = xPos + rad * Math.cos(orientation);
			double yPos1 = yPos + rad * Math.sin(orientation);
			double xPos2 = xPos - rad * Math.cos(orientation);
			double yPos2 = yPos - rad * Math.sin(orientation);
			Asteroid asteroid1 = new Asteroid(xPos1, yPos1, rad, xVel1, yVel1, 0);
			Asteroid asteroid2 = new Asteroid(xPos2, yPos2, rad, -xVel1, -yVel1, 0);
			super.terminate();
			world.addWorldObject(asteroid1);
			world.addWorldObject(asteroid2);
		}
		else{
			super.terminate();
		}
	}
	
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
	
	@Override
	public String toString(){
		return "Planetoid";
	}
	
}
