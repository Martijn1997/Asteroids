package asteroids.facade;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import asteroids.model.Asteroid;
import asteroids.model.Bullet;
import asteroids.model.Planetoid;
import asteroids.model.Ship;
import asteroids.model.Vector2D;
import asteroids.model.World;
import asteroids.model.WorldObject;
import asteroids.model.Program;
import asteroids.model.ProgramFactory;
import asteroids.part2.CollisionListener;
import asteroids.part3.programs.IProgramFactory;
import asteroids.util.ModelException;
import exceptions.IllegalAngleException;
import exceptions.BuilderException;

public class Facade implements asteroids.part3.facade.IFacade{
	
	/**
	 * Create a new ship with a default position, velocity, radius and
	 * direction.
	 * 
	 * Result is a unit circle centered on (0, 0) facing right. Its
	 * speed is zero.
	 */
	@Deprecated
	public Ship createShip() throws ModelException{
		Ship newShip = new Ship();
		return newShip;
	}
	
	/**
	 * Create a new non-null ship with the given position, velocity, radius,
	 * direction and mass.
	 * 
	 * The thruster of the new ship is initially inactive. The ship is not
	 * located in a world.
	 */
	public Ship createShip(double x, double y, double xVelocity, double yVelocity, double radius, double direction,

		double mass) throws ModelException{
		try{
			assert((direction <= 2*Math.PI)&&(direction >= 0));
		}catch(AssertionError exc1){
			throw new ModelException(exc1);
		}
		try {
			Ship newShip = new Ship(x, y, direction, radius, xVelocity, yVelocity, mass);
			return newShip;
		} catch(IllegalArgumentException exc){
			throw new ModelException(exc);			
		}

	}
	
	/**
	 * Return the position of ship as an array of length 2, with the
	 * x-coordinate at index 0 and the y-coordinate at index 1.
	 */
	public double[] getShipPosition(Ship ship) throws ModelException{
		return ship.getPosition().getVector2DArray();
	}
	
	/**
	 * Return the velocity of ship as an array of length 2, with the velocity
	 * along the X-axis at index 0 and the velocity along the Y-axis at index 1.
	 */
	public double[] getShipVelocity(Ship ship) throws ModelException{
		return ship.getVelocity().getVector2DArray();
	}
	
	/**
	 * Return the radius of ship.
	 */
	public double getShipRadius(Ship ship) throws ModelException{
		try {
			return ship.getRadius();
		}
		catch (IllegalArgumentException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Return the orientation of ship (in radians).
	 */
	public double getShipOrientation(Ship ship) throws ModelException{
		return ship.getOrientation();
	}
	
	/**
	 * Update ship's position, assuming it moves dt
	 * seconds at its current velocity.
	 */
	@Deprecated
	public void move(Ship ship, double dt) throws ModelException{
		try {
			ship.move(dt);
		}
		catch (IllegalArgumentException exc){
			throw new ModelException(exc);			
		}
	}
	
	/**
	 * Update ship's velocity based on its current velocity, its
	 * direction and the given amount.
	 */
	public void thrust(Ship ship, double amount) throws ModelException{
		ship.thrust(amount);		
	}

	/**
	 * Update the direction of ship by adding angle
	 * (in radians) to its current direction. angle may be
	 * negative.
	 */
	public void turn(Ship ship, double angle) throws ModelException{
		double newAngle = angle + ship.getOrientation();
		if (!(newAngle <= 2*Math.PI)&&(newAngle >= 0))
			throw new ModelException("null");
		else
			ship.turn(angle);
	}

	/**
	 * Return the distance between ship1 and ship2.
	 * 
	 * The absolute value of the result of this method is the minimum distance
	 * either ship should move such that both ships are adjacent. Note that the
	 * result must be negative if the ships overlap. The distance between a ship
	 * and itself is 0.
	 */
	public double getDistanceBetween(Ship ship1, Ship ship2) throws ModelException{
		try { 
			return ship1.getDistanceBetween(ship2);
		}
		catch (ArithmeticException exc1) {
			throw new ModelException(exc1);
		}
		catch (IllegalArgumentException exc2) {
			throw new ModelException(exc2);
		}
	}

	/**
	 * Check whether ship1 and ship2 overlap. A ship
	 * always overlaps with itself.
	 */
	public boolean overlap(Ship ship1, Ship ship2) throws ModelException{
		try { 
			return ship1.overlap(ship2);
		}
		catch (IllegalArgumentException exc1) {
			throw new ModelException(exc1);
		}
		catch (ArithmeticException exc2) {
			throw new ModelException(exc2);
		}
	}

	/**
	 * Return the number of seconds until the first collision between
	 * ship1 and ship2, or Double.POSITIVE_INFINITY if
	 * they never collide. A ship never collides with itself.
	 */
	public double getTimeToCollision(Ship ship1, Ship ship2) throws ModelException{
		try {
			return ship1.getTimeToCollision(ship2);
		}
		catch (IllegalArgumentException exc1) {
			throw new ModelException(exc1);
		}
		catch (ArithmeticException exc2) {
			throw new ModelException(exc2);
		}
	}

	/**
	 * Return the first position where ship1 and ship2
	 * collide, or null if they never collide. A ship never
	 * collides with itself.
	 * 
	 * The result of this method is either null or an array of length 2, where
	 * the element at index 0 represents the x-coordinate and the element at
	 * index 1 represents the y-coordinate.
	 */
	public double[] getCollisionPosition(Ship ship1, Ship ship2) throws ModelException{
		try {
			return ship1.getCollisionPosition(ship2);
		}
		catch (IllegalArgumentException exc1) {
			throw new ModelException(exc1);
		}
		catch (ArithmeticException exc2) {
			throw new ModelException(exc2);
		}
	}
	

	/**
	 * Terminate <code>ship</code>.
	 */
	public void terminateShip(Ship ship) throws ModelException{
		ship.terminate();
	}
	
	/**
	 * Check whether <code>ship</code> is terminated.
	 */
	public boolean isTerminatedShip(Ship ship) throws ModelException{
		return ship.isTerminated();
	}
	
	/**
	 * Return the total mass of <code>ship</code> (i.e., including bullets
	 * loaded onto the ship).
	 */
	public double getShipMass(Ship ship) throws ModelException{
		return ship.getMass();
	}
	
	/**
	 * Return the world of <code>ship</code>.
	 */
	public World getShipWorld(Ship ship) throws ModelException{
		return ship.getWorld();
	}
	
	/**
	 * Return whether <code>ship</code>'s thruster is active.
	 */
	public boolean isShipThrusterActive(Ship ship) throws ModelException{
		return ship.getThrusterStatus();
	}
	
	/**
	 * Enables or disables <code>ship</code>'s thruster depending on the value
	 * of the parameter <code>active</code>.
	 */
	public void setThrusterActive(Ship ship, boolean active) throws ModelException{
		if(active){
			ship.thrustOn();
		}else{
			ship.thrustOff();
		}
	}
	
	/**
	 * Return the acceleration of <code>ship</code>.
	 */
	public double getShipAcceleration(Ship ship) throws ModelException{
		return ship.getAcceleration();
	}
	
	/**
	 * Create a new non-null bullet with the given position, velocity and
	 * radius,
	 * 
	 * The bullet is not located in a world nor loaded on a ship.
	 */
	public Bullet createBullet(double x, double y, double xVelocity, double yVelocity, double radius)
			throws ModelException{
		Bullet bullet = new Bullet(x, y, radius, xVelocity, yVelocity, 0);
		return bullet;
	}
	
	/**
	 * Terminate <code>bullet</code>.
	 */
	public void terminateBullet(Bullet bullet) throws ModelException{
		bullet.terminate();
	}
	
	/**
	 * Check whether <code>bullet</code> is terminated.
	 */
	public boolean isTerminatedBullet(Bullet bullet) throws ModelException{
		return bullet.isTerminated();
	}
	
	/**
	 * Return the position of <code>ship</code> as an array containing the
	 * x-coordinate, followed by the y-coordinate.
	 */
	public double[] getBulletPosition(Bullet bullet) throws ModelException{
		return bullet.getPosition().getVector2DArray();
	}
	
	/**
	 * Return the velocity of <code>ship</code> as an array containing the
	 * velocity along the X-axis, followed by the velocity along the Y-axis.
	 */
	public double[] getBulletVelocity(Bullet bullet) throws ModelException{
		return bullet.getVelocity().getVector2DArray();
	}
	
	/**
	 * Return the radius of <code>bullet</code>.
	 */
	public double getBulletRadius(Bullet bullet) throws ModelException{
		return bullet.getRadius();
	}
	
	/**
	 * Return the mass of <code>bullet</code>.
	 */
	public double getBulletMass(Bullet bullet) throws ModelException{
		return bullet.getMass();
	}
	
	/**
	 * Return the world in which <code>bullet</code> is positioned.
	 * 
	 * This method must return null if a bullet is not positioned in a world, or
	 * if it is positioned on a ship.
	 */
	public World getBulletWorld(Bullet bullet) throws ModelException{
		return bullet.getWorld();
	}
	
	/**
	 * Return the ship in which <code>bullet</code> is positioned.
	 * 
	 * This method must return null if a bullet is not positioned on a ship.
	 */
	public Ship getBulletShip(Bullet bullet) throws ModelException{
		if (bullet.getLoadedOnShip())
			return bullet.getShip();
		else
			return null;
	}
	
	/**
	 * Return the ship that fired <code>bullet</code>.
	 */
	public Ship getBulletSource(Bullet bullet) throws ModelException{
		return bullet.getShip();
	}
	
	/**
	 * Return the set of all bullets loaded on <code>ship</code>.
	 * 
	 * For students working alone, this method may return null.
	 */
	public Set<? extends Bullet> getBulletsOnShip(Ship ship) throws ModelException{
		return ship.getBulletSet();
	}
	
	/**
	 * Return the number of bullets loaded on <code>ship</code>.
	 */
	public int getNbBulletsOnShip(Ship ship) throws ModelException{
		return ship.getTotalAmountOfBullets();

	}
	
	/**
	 * Load <code>bullet</code> on <code>ship</code>.
	 */
	public void loadBulletOnShip(Ship ship, Bullet bullet) throws ModelException{
		try {
			ship.loadBullets(bullet);
		}
		catch (IllegalArgumentException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Load <code>bullet</code> on <code>ship</code>.
	 * 
	 * For students working alone, this method must not do anything.
	 */
	public void loadBulletsOnShip(Ship ship, Collection<Bullet> bullets) throws ModelException{
		Bullet[] bulletArray = bullets.toArray(new Bullet[bullets.size()]);
		try {
			ship.loadBullets(bulletArray);
		}
		catch (IllegalArgumentException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Remove <code>bullet</code> from <code>ship</code>.
	 */
	public void removeBulletFromShip(Ship ship, Bullet bullet) throws ModelException{
		try{
		ship.removeBulletFromShip(bullet);
		}catch (IllegalArgumentException exc ){
			throw new ModelException(exc);
		}
	}
	
	/**
	 * <code>ship</code> fires a bullet.
	 */
	public void fireBullet(Ship ship) throws ModelException{
		ship.fireBullet();
	}
	
	
	
	
	/**
	 * Create a new world with the given <code>width</code> and
	 * <code>height</code>.
	 */
	public World createWorld(double width, double height) throws ModelException{
		World newWorld = new World(width, height);
		return newWorld;
	}

	/**
	 * Terminate <code>world</code>.
	 */
	public void terminateWorld(World world) throws ModelException{
		world.terminate();
	}

	/**
	 * Check whether <code>world</code> is terminated.
	 */
	public boolean isTerminatedWorld(World world) throws ModelException{
		return world.isTerminated();
	}

	/**
	 * Return the size of <code>world</code> as an array containing the width,
	 * followed by the height.
	 */
	public double[] getWorldSize(World world) throws ModelException{
		double[] worldSize = {world.getWidth(), world.getHeight()};
		return worldSize;
	}

	/**
	 * Return all ships located within <code>world</code>.
	 */
	public Set<? extends Ship> getWorldShips(World world) throws ModelException{
		Set<Ship> allShips = new HashSet<Ship>(world.getAllShips());
		return allShips;
	}

	/**
	 * Return all bullets located in <code>world</code>.
	 */
	public Set<? extends Bullet> getWorldBullets(World world) throws ModelException{
		Set<Bullet> allBullets = new HashSet<Bullet>(world.getAllBullets());
		return allBullets;
	}

	/**
	 * Add <code>ship</code> to <code>world</code>.
	 */
	public void addShipToWorld(World world, Ship ship) throws ModelException{
		try{
			world.addWorldObject(ship);
		}catch (IllegalArgumentException exc) {
			throw new ModelException(exc);
		}
	}

	/**
	 * Remove <code>ship</code> from <code>world</code>.
	 */
	public void removeShipFromWorld(World world, Ship ship) throws ModelException{
		try{
			world.removeFromWorld(ship);
		}catch (IllegalArgumentException exc) {
			throw new ModelException(exc);
		}
	}

	/**
	 * Add <code>bullet</code> to <code>world</code>.
	 */
	public void addBulletToWorld(World world, Bullet bullet) throws ModelException{
		try{
			world.addWorldObject(bullet);
		}catch (IllegalArgumentException exc) {
			throw new ModelException(exc);
		}
	}

	/**
	 * Remove <code>ship</code> from <code>world</code>.
	 */
	public void removeBulletFromWorld(World world, Bullet bullet) throws ModelException{
		try{
			world.removeFromWorld(bullet);
		}catch (IllegalArgumentException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Return the shortest time in which the given entity will collide with the
	 * boundaries of its world.
	 */
	public double getTimeCollisionBoundary(Object object) throws ModelException{
		try{
		return ((WorldObject) object).getTimeToCollision(((WorldObject) object).getWorld());
		} catch (IllegalArgumentException exc){
			throw new ModelException(exc);
		}
	}

	/**
	 * Return the first position at which the given entity will collide with the
	 * boundaries of its world.
	 */
	public double[] getPositionCollisionBoundary(Object object) throws ModelException{
		return ((WorldObject) object).getCollisionPosition(((WorldObject) object).getWorld());
	}

	/**
	 * Return the shortest time in which the first entity will collide with the
	 * second entity.
	 */
	public double getTimeCollisionEntity(Object object1, Object object2) throws ModelException{
		try { 
			return ((WorldObject) object1).getTimeToCollision((WorldObject) object2);
		}
		catch (ArithmeticException exc1) {
			throw new ModelException(exc1);
		}
		catch (IllegalArgumentException exc2) {
			throw new ModelException(exc2);
		}
	}

	/**
	 * Return the first position at which the first entity will collide with the
	 * second entity.
	 */
	public double[] getPositionCollisionEntity(Object object1, Object object2) throws ModelException{
		try { 
			return ((WorldObject) object1).getCollisionPosition((WorldObject) object2);
		}
		catch (ArithmeticException exc1) {
			throw new ModelException(exc1);
		}
		catch (IllegalArgumentException exc2) {
			throw new ModelException(exc2);
		}
	}

	/**
	 * Return the time that must pass before a boundary collision or an entity
	 * collision will take place in the given world. Positive Infinity is
	 * returned if no collision will occur.
	 */
	public double getTimeNextCollision(World world) throws ModelException{

		try { 
			return world.getNextCollision()[0];
		}catch (ArithmeticException exc1) {
			throw new ModelException(exc1);
		}catch (IllegalArgumentException exc2) {
			throw new ModelException(exc2);
		}
	}

	/**
	 * Return the position of the first boundary collision or entity collision
	 * that will take place in the given world. Null is returned if no collision
	 * will occur.
	 */
	public double[] getPositionNextCollision(World world) throws ModelException{

		try { 
			double[] nextColl= world.getNextCollision();
		  return new double[] {nextColl[1], nextColl[2]};
		}catch (NullPointerException exc) {
			return null;
		}catch (ArithmeticException exc1) {
			throw new ModelException(exc1);
		}catch (IllegalArgumentException exc2) {
			throw new ModelException(exc2);
		}
	}

	/**
	 * Advance <code>world</code> by <code>dt<code> seconds. 
	 * 
	 * To enable explosions within the UI, notify <code>collisionListener</code>
	 * whenever an entity collides with a boundary or another entity during this
	 * method. <code>collisionListener</code> may be null. If
	 * <code>collisionListener</code> is <code>null</code>, do not call its
	 * notify methods.
	 */
	public void evolve(World world, double dt, CollisionListener collisionListener) throws ModelException{

		try{
		world.evolve(dt, collisionListener);
		} catch (IllegalArgumentException exc){
			throw new ModelException(exc);
		} catch (IllegalStateException exc){
			throw new ModelException(exc);

		}
	}

	/**
	 * Return the entity at the given <code>position</code> in the given
	 * <code>world</code>.
	 */
	public WorldObject getEntityAt(World world, double x, double y) throws ModelException{
		Vector2D position = new Vector2D(x, y);
		return world.getEntityAt(position);
	}

	/**
	 * Return a set of all the entities in the given world.
	 */
	public Set<? extends WorldObject> getEntities(World world) throws ModelException{
		return world.getAllWorldObjects();
	}

	@Override
	public int getNbStudentsInTeam() {
		return 2;
	}

	@Override
	public Set<? extends Asteroid> getWorldAsteroids(World world) throws ModelException {
		Set<Asteroid> allAsteroids = new HashSet<Asteroid>(world.getAllAsteroids());
		return allAsteroids;
	}

	@Override
	public void addAsteroidToWorld(World world, Asteroid asteroid) throws ModelException {
		try{
			world.addWorldObject(asteroid);
		}catch (IllegalArgumentException exc) {
			throw new ModelException(exc);
		}	
	}

	@Override
	public void removeAsteroidFromWorld(World world, Asteroid asteroid) throws ModelException {
		try{
			world.removeFromWorld(asteroid);
		}catch (IllegalArgumentException exc) {
			throw new ModelException(exc);
		}		
	}

	@Override
	public Set<? extends Planetoid> getWorldPlanetoids(World world) throws ModelException {
		Set<Planetoid> allPlanetoids = new HashSet<Planetoid>(world.getAllPlanetoids());
		return allPlanetoids;
	}

	@Override
	public void addPlanetoidToWorld(World world, Planetoid planetoid) throws ModelException {
		try{
			world.addWorldObject(planetoid);
		}catch (IllegalArgumentException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public void removePlanetoidFromWorld(World world, Planetoid planetoid) throws ModelException {
		try{
			world.removeFromWorld(planetoid);
		}catch (IllegalArgumentException exc) {
			throw new ModelException(exc);
		}	
	}

	@Override
	public Asteroid createAsteroid(double x, double y, double xVelocity, double yVelocity, double radius)
			throws ModelException {
		Asteroid asteroid = new Asteroid(x, y, radius, xVelocity, yVelocity, 0);
		return asteroid;
	}

	@Override
	public void terminateAsteroid(Asteroid asteroid) throws ModelException {
		asteroid.terminate();
	}

	@Override
	public boolean isTerminatedAsteroid(Asteroid asteroid) throws ModelException {
		return asteroid.isTerminated();
	}

	@Override
	public double[] getAsteroidPosition(Asteroid asteroid) throws ModelException {
		return asteroid.getPosition().getVector2DArray();
	}

	@Override
	public double[] getAsteroidVelocity(Asteroid asteroid) throws ModelException {
		return asteroid.getVelocity().getVector2DArray();
	}

	@Override
	public double getAsteroidRadius(Asteroid asteroid) throws ModelException {
		return asteroid.getRadius();
	}

	@Override
	public double getAsteroidMass(Asteroid asteroid) throws ModelException {
		return asteroid.getMass();
	}

	@Override
	public World getAsteroidWorld(Asteroid asteroid) throws ModelException {
		return asteroid.getWorld();
	}

	@Override
	public Planetoid createPlanetoid(double x, double y, double xVelocity, double yVelocity, double radius,
			double totalTraveledDistance) throws ModelException {
		Planetoid planetoid = new Planetoid(x, y, radius, xVelocity, yVelocity, totalTraveledDistance, 0);
		return planetoid;
	}

	@Override
	public void terminatePlanetoid(Planetoid planetoid) throws ModelException {
		planetoid.terminate();		
	}

	@Override
	public boolean isTerminatedPlanetoid(Planetoid planetoid) throws ModelException {
		return planetoid.isTerminated();
	}

	@Override
	public double[] getPlanetoidPosition(Planetoid planetoid) throws ModelException {
		return planetoid.getPosition().getVector2DArray();
	}

	@Override
	public double[] getPlanetoidVelocity(Planetoid planetoid) throws ModelException {
		return planetoid.getVelocity().getVector2DArray();
	}

	@Override
	public double getPlanetoidRadius(Planetoid planetoid) throws ModelException {
		return planetoid.getRadius();
	}

	@Override
	public double getPlanetoidMass(Planetoid planetoid) throws ModelException {
		return planetoid.getMass();
	}

	@Override
	public double getPlanetoidTotalTraveledDistance(Planetoid planetoid) throws ModelException {
		return planetoid.getTotalTraveledDistance();
	}

	@Override
	public World getPlanetoidWorld(Planetoid planetoid) throws ModelException {
		return planetoid.getWorld();
	}

	@Override
	public Program getShipProgram(Ship ship) throws ModelException {
		return ship.getProgram();
	}

	@Override
	public void loadProgramOnShip(Ship ship, Program program) throws ModelException {
		program.setShip(ship);
	}

	@Override
	public List<Object> executeProgram(Ship ship, double dt) throws ModelException {
		try{
			return ship.getProgram().excecuteProgram(dt);
		}catch(IllegalArgumentException exc){
			throw new ModelException(exc);
		}catch(IllegalStateException exc){
			throw new ModelException(exc);
		}catch(IllegalAngleException exc){
			throw new ModelException(exc);
		}catch(IndexOutOfBoundsException exc){
			throw new ModelException(exc);
		}catch(BuilderException exc){
			throw new ModelException(exc);
		}
	}

	@Override
	public IProgramFactory<?, ?, ?, ? extends Program> createProgramFactory() throws ModelException {
		try{
		return new ProgramFactory();
		}catch (ClassCastException exc){
			throw new ModelException(exc);
		}
	}
	
}