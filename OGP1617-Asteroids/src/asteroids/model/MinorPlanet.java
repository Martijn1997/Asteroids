package asteroids.model;

public abstract class MinorPlanet extends WorldObject {
	
	protected MinorPlanet(double xPos, double yPos, double radius, double xVel, double yVel, double mass) throws IllegalArgumentException{
		super(xPos, yPos, radius, xVel, yVel, mass);
	}
	
	/**
	 * the minimum radius of a minor planet
	 */
	public final static double MIN_RADIUS = 5;
	
	/**
	 * @return 	the minimum radius of a minor planet
	 * 			|result == MIN_RADIUS
	 */
	@Override
	public double getMinimumRadius(){
		return MIN_RADIUS;
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
	 * Getter for the minimum density of the minor planet
	 */
	@Override
	public abstract double getMinimumDensity();

	@Override
	public boolean canHaveAsMass(double mass) {
		return this.calcMinMass()==mass;
	}

	@Override
	public boolean canHaveAsWorld(World world) {
		if(world != null&&!residesInWorld()&&!this.isTerminated()){
			return true;
		}else if (this.isTerminated() && world == null){
			return true;
		}else if (this.getWorld().isTerminated() && world == null){
			return true;
		}else
			return false;
	}

	@Override
	public void resolveCollision(WorldObject other) {
		if (other instanceof Ship)
			this.resolveCollision((Ship) other);
		if (other instanceof Bullet)
			this.resolveCollision((Bullet) other);
		if (other instanceof MinorPlanet)
			this.resolveCollision((MinorPlanet) other);
	}
	
	public void resolveCollision(MinorPlanet other) throws IllegalStateException{
		if(!World.apparentlyCollide(this, other)){
			throw new IllegalStateException();
		}
		
		//prepare all the variables
		Vector2D deltaR = this.getPosition().difference(other.getPosition());
		Vector2D deltaV = this.getVelocity().difference(other.getVelocity());
		double massMP1 = this.getMass();
		double massMP2 = other.getMass();
		double sigma = this.getSigma(other);
		
		// run the calculations provided
		double energy = ((2*massMP1*massMP2*deltaR.dotProduct(deltaV))/(sigma*(massMP1+massMP2)));
		Vector2D energyVector = this.getPosition().difference(other.getPosition()).rescale(energy/sigma);
		
		// set the velocities
		this.setVelocity(this.getXVelocity()-energyVector.getXComponent()/massMP1, this.getYVelocity()-energyVector.getYComponent()/massMP1);
		other.setVelocity(other.getXVelocity() + energyVector.getXComponent()/massMP2, other.getYVelocity() + energyVector.getYComponent()/massMP2);
		
		// safety margins on the bounce
//		Vector2D object1Pos = this.getPosition();
//		Vector2D object2Pos = other.getPosition();
//		this.move(1E-10);
//		other.move(1E-10);
//		this.getWorld().updatePosition(object1Pos, this);
//		this.getWorld().updatePosition(object2Pos, other);
	}
	
	public void resolveCollision(Bullet bullet) throws IllegalStateException, IllegalArgumentException{
		if(!World.apparentlyCollide(this,bullet))
			throw new IllegalStateException();
		
		if(bullet == null)
			throw new IllegalArgumentException();
		
		bullet.terminate();
		this.terminate();
	}
	
	public abstract void resolveCollision(Ship other);

}