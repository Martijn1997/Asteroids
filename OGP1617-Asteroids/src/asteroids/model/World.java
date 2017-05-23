package asteroids.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import asteroids.part2.CollisionListener;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of world's
 * @author Flor & Martijn
 *
 * @Invar 	The World Objects that reside in the world may not overlap with each other
 * 			| canHaveAsWorldObject(worldObject)
 * 
 * @Invar	The World Objects that reside within the world may not overlap with the boundaries
 * 			| canHaveAsWorldObject(worldObject)
 * 
 * @Invar 	every world object can be found using its position in nearly constant time
 * 			|getEntityAt(position)
 */
public class World {
	
	/**
	 * Constructor for a World
	 * @param 	width
	 * 			the desired width of a world
	 * @param 	height
	 * 			the desired height of a world
	 * 
	 * @effect	the width of the world is set to width
	 * 			|setWidth(width)
	 * 
	 * @effect 	the height of the world is set to height
	 * 			|setHeight(height)
	 */
	public World(double width, double height){
		this.setWidth(width);
		this.setHeight(height);
	}

	/**
	 * Basic getter for the width of the world
	 * @return the width of the world
	 */
	@Basic @Raw
	public double getWidth(){
		return this.width;
	}
	
	/**
	 * Basic setter for the width of the world
	 * @param 	width
	 * 			the width desired for the world
	 * @post	if the width is valid the width of the world is set to the provided value
	 * 			| if isValidBoundary(width)
	 * 			| then new.getWidth() == width
	 * 
	 * @post 	if the width is not valid, the boundary is set to Double.POSITIVE_INFINITY
	 * 			| if !isValidBoundary(width)
	 * 			| then new.getWidth() = Double.POSITIVE_INFINITY
	 */
	@Basic @Raw
	public void setWidth(double width){
		if (isValidWidth(width))
			this.width = width;	
		else 
			this.width = getMaxWidth();
	}
	
	/***
	 * Return if the given width is between zero and the maximum value
	 * 
	 * @see implementation
	 */
	private static boolean isValidWidth(double width){
		return (width <= getMaxWidth() && width >= 0);
	}
	
	/**
	 * variable that stores the width of the world
	 */
	private double width;
	
	/**
	 * @return the maximum width of a world
	 */
	@Immutable @Raw
	public static double getMaxWidth(){
		return MAX_WIDTH;
	}
	
	/**
	 * constant that stores the maximum width of a world
	 */
	public final static double MAX_WIDTH = Double.MAX_VALUE;
	
	/**
	 * Basic getter for the Height of the world
	 * @return the height of the world
	 */
	@Basic @Raw
	public double getHeight(){
		return this.height;
	}
	
	/****
	 * Basic setter for the height of the world
	 * @param 	height
	 * 			the height desired for the world
	 * @post	if the height is valid the height of the world is set to the provided value
	 * 			| if isValidBoundary(height)
	 * 			| then new.getHeight() == height
	 * 
	 * @post 	if the height is not valid, the boundary is set to Double.POSITIVE_INFINITY
	 * 			| if !isValidBoundary(height)
	 * 			| then new.getHeight() = Double.POSITIVE_INFINITY
	 */
	@Basic @Raw
	public void setHeight(double height){
		if (isValidHeight(height))
			this.height = height;	
		else
			this.height = getMaxHeight();
	}
	
	/***
	 * Return if the given height is between zero and the maximum value
	 * 
	 * @see implementation
	 */
	private static boolean isValidHeight(double boundary){
		return (boundary <= getMaxHeight() && boundary >= 0);
	}
	
	/**
	 * variable that stores the height of the world
	 */
	private double height;
	
	/**
	 * @return the max height of the world
	 */
	@Immutable @Raw
	public static double getMaxHeight(){
		return MAX_HEIGHT;
	}
	
	/**
	 * constant that stores the maximum height
	 */
	private final static double MAX_HEIGHT = Double.MAX_VALUE;
	


	/***
	 * Terminator for a world
	 * @effect 	if there are world objects associated with this world remove it from this world
	 * 			|removeFromWorld(object)
	 * 
	 * @post	the association between the world object and the world is broken
	 * 			|object.getWorld() == null
	 * 
	 * @post	the list of all world objects is empty
	 * 			|new.getAllWorldObjects().isEmpty()
	 * 
	 * @post	the flag that the world is terminated is raised
	 * 			|new.isTerminated() == true
	 */
	@Basic
	public void terminate(){
		this.isTerminated = true;
		Set<WorldObject> allObjects = new HashSet<WorldObject>(worldObjects.values());		
		for (WorldObject  object : allObjects){
			removeFromWorld(object);
		}
	}
	
	/**
	 * checks if a world is terminated
	 * @return	if the world is terminated or not
	 */
	@Basic @Raw
	public boolean isTerminated(){
		return this.isTerminated;
	}
	
	/**
	 * variable that stores the termination flag
	 */
	private boolean isTerminated;
	

	
	/***
	 * Return if two objects overlap taking account of rounding issues
	 * 
	 * @param	worldObject1
	 * @param	worldObject2
	 * 
	 * @return 	true if worldObject1 is the same as worldObject2
	 * 			| result == (worldObject2 == worldObject1)
	 * 
	 * @return 	true if the WorldObjects overlap
	 * 			|distance == worldObject1.getPosition().distanceTo(worldObject2.getPosition())
	 * 			|sumOfRadii == (worldObject1.getRadius() + worldObject2.getRadius())
	 * 			|result == distance <= 0.99*sumOfRadii
	 * 
	 * @throws	IllegalArgumentException
	 * 			| other == 0
	 * 
	 * @throws	ArithmeticException
	 * 			| causedOverflow()
	 */
	public static boolean significantOverlap(WorldObject worldObject1, WorldObject worldObject2) throws IllegalArgumentException, ArithmeticException{
		if(worldObject1 == null || worldObject2 == null)
			throw new IllegalArgumentException();
		if(worldObject1 == worldObject2)
			return true;
		return worldObject1.getPosition().distanceTo(worldObject2.getPosition()) <= 0.99*(worldObject1.getRadius() + worldObject2.getRadius());	
	}
	
	/***
	 * Return if two objects collide taking account of rounding issues
	 * 
	 * @param	worldObject1
	 * @param	worldObject2
	 * 
	 * @return 	false if worldObject1 is the same as worldObject2
	 * 			| result == (worldObject2 == worldObject1)
	 * 
	 * @return 	true if the WorldObjects collide
	 * 			|distance == worldObject1.getPosition().distanceTo(worldObject2.getPosition())
	 * 			|sumOfRadii == (worldObject1.getRadius() + worldObject2.getRadius())
	 * 			|result == 0.99*sumOfRadii <= distance <= 1.01*sumOfRadii
	 */
	public static boolean apparentlyCollide(WorldObject worldObject1, WorldObject worldObject2){
		if(worldObject1 == null || worldObject2 == null)
			throw new IllegalArgumentException();
		if(worldObject1 == worldObject2)
			return false;
		return worldObject1.getPosition().distanceTo(worldObject2.getPosition()) >= 0.99*(worldObject1.getRadius() + worldObject2.getRadius())&&
				worldObject1.getPosition().distanceTo(worldObject2.getPosition()) <= 1.01*(worldObject1.getRadius() + worldObject2.getRadius());
	}
	

	/**
	 * checker if the world object is within the world boundaries
	 * @param	worldObject
	 * @return	true if the worldObject is located within the world boundaries
	 * 			| result == (0 + worldObject.getRadius() * 0.99< worldObject.getXPosition < getWidth() - worldObject.getRadius() * 0.99 &&
	 * 			| 0 + worldObject.getRadius()*0.99 < worldObject.getYPosition() < getHeight() - worldObject.getRadius()*0.99)
	 * @throws 	IllegalArgumentException
	 * 			thrown if the worldObject is a null reference
	 * 			| this == null
	 */
	public boolean withinBoundary(WorldObject worldObject) throws IllegalArgumentException{	
		if (worldObject == null)
			throw new IllegalArgumentException();
		
		// get the boundaries of the world
		double[] xBoundary = {0, this.getWidth()};
		double[] yBoundary = {0, this.getHeight()};
		
		// set variables for the radius
		double percentage = 0.99;
		double radius = worldObject.getRadius();
		
		// check if the WO lies within the world boundaries
		if(xBoundary[0] < worldObject.getXPosition() - radius*percentage && xBoundary[1] > worldObject.getXPosition() + radius*percentage){
			if(yBoundary[0] < worldObject.getYPosition() - radius*percentage && yBoundary[1] > worldObject.getYPosition() + radius*percentage)
				return true;
		}
		return false;

	}
	
	/**
	 * Checks if the given world object would be within the world boundaries if
	 * the x and y position coordinates would be assigned to it.
	 * @param 	xPos
	 * @param 	yPos
	 * @param 	worldObject
	 * 
	 * @return	true if and only if the world object would be located within the world boundaries
	 * 			| result == (0 + worldObject.getRadius() * 0.99< xPos < getWidth() - worldObject.getRadius() * 0.99 &&
	 * 			| 0 + worldObject.getRadius()*0.99 < yPos < getHeight() - worldObject.getRadius()*0.99)
	 *
	 * @throws 	IllegalArgumentException
	 * 			thrown if the worldObject is a null reference
	 * 			| this == null
	 */
	public boolean withinBoundary(double xPos, double yPos, WorldObject worldObject) throws IllegalArgumentException{
		// get the boundaries of the world
		double[] xBoundary = {0, this.getWidth()};
		double[] yBoundary = {0, this.getHeight()};
		
		// set variables for the radius
		double percentage = 0.99;
		double radius = worldObject.getRadius();
		
		// check if the WO lies within the world boundaries
		if(xBoundary[0] < xPos - radius*percentage && xBoundary[1] > xPos + radius*percentage){
			if(yBoundary[0] < yPos - radius*percentage && yBoundary[1] > yPos + radius*percentage)
				return true;
		}
		return false;
	}

	
	/**
	 * Adds a world object to the world 
	 * @param	worldObject
	 * 
	 * @post	the world object in placed within the world
	 * 			| worldObject.getWorld() == World
	 * 
	 * @post	the world object is added to the list of all world objects
	 * 			|new.getAllWorldObjects.contains(worldObject)
	 * 
	 * @throws 	IllegalArgumentException
	 * 			thrown if the worldObject is not legal
	 * 			|!canHaveAsWorldObject(worldObject)
	 */
	public void addWorldObject(WorldObject worldObject) throws IllegalArgumentException{
		
		// checker if valid object
		if(!this.canHaveAsWorldObject(worldObject))
			throw new IllegalArgumentException();
		
		//get the position of the WO
		Vector2D position = worldObject.getPosition();
		
		//set the WO in the map
		worldObjects.put(position , worldObject);
		worldObject.setWorld(this);
		
	}
	
	/**
	 * checker if a WorldObject can be set in the world
	 * 
	 * @param 	worldObject
	 * 			the WorldObject that will be placed in the world
	 * 
	 * @return	false if the worldObject is a null reference
	 * 			|result == (worldObject == null)
	 * 
	 * @return  false if the worldObject is already part of another world
	 * 			|result == (worldObject.getWorld != null)
	 * 
	 * @return  false if the worldObject does overlap with the world boundaries
	 * 			|result == (withinBoundary(worldObject))
	 * 
	 * @return 	true if the world object has no significant overlap with another object in the world
	 * 			| WorldObjectSet == getAllWorldObjects()
	 * 			| for all objects in WorldObjectSet if not significantOverlap(object, worldObject)
	 * 			| then return true
	 */
	private boolean canHaveAsWorldObject(WorldObject worldObject){
		// first check if the world object is a null reference
		if((worldObject != null)&&(worldObject.getWorld() == null)&&this.withinBoundary(worldObject)){
			HashSet<WorldObject> WorldObjectsInWorld = this.getAllWorldObjects();
			// check if another world object is within overlapping radius
			for(WorldObject other: WorldObjectsInWorld){
				if(significantOverlap(worldObject, other)){
					return false;
				}	
			}
			// return true if no such object can be found	
			return true;
		}
		
		else 
			return false;
		
	}
	
	/***
	 * removes an world object out the world
	 * 
	 * @post	the world object doesn't have a world
	 * 			|worldObject.getWorld() == null
	 * 			
	 * @post	the world object is removed from the list of all world objects
	 * 			|!new.getAllWorldObjects.contains(worldObject)
	 * 
	 * @param	worldObject
	 * @throws	IllegalArgumentException
	 * 			|worldObject.getWorld() != this
	 */
	public void removeFromWorld(WorldObject worldObject) throws IllegalArgumentException{
		if (worldObject == null)
			throw new IllegalArgumentException();
		if (worldObject.getWorld() != this)
			throw new IllegalArgumentException();
		Vector2D position = worldObject.getPosition();
		this.getWorldObjectMap().remove(position);
		worldObject.setWorldToNull();
	}
	
	/***
	 * Returns the object, if any, at the given position
	 * @param position
	 * @see implementation
	 */
	public WorldObject getEntityAt(Vector2D position){
		WorldObject entity = (WorldObject)worldObjects.get(position);
		return entity;
	}

	/**
	 * Returns the position of the world object where object collides with
	 * @param 	object
	 * @return	if the object collides with another world object return the position of the other world object
	 * 			|for worldObject in getAllWorldObjects() if within radius(worldObject, object) result == true
	 * 
	 * @return 	if the world object doesn't collide with another entity return null
	 */
	public Vector2D getPositionCollisionPartner(WorldObject object){
		HashSet<WorldObject> allWorldObjects = this.getAllWorldObjects();
		for(WorldObject worldObject: allWorldObjects){
			if(withinRadius(worldObject, object))
				return worldObject.getPosition();
		}
		return null;
	}
	
	/**
	 * Checks if the two objects are within radius of each other
	 * @param object1
	 * @param object2
	 * @return	true if and only if the two objects are within each others radius
	 * 			|result == object1.getPosition().distanceTo(object2.getPosition()) <= object1.getRadius() + object2.getRadius()
	 * 			
	 */
	private static boolean withinRadius(WorldObject object1, WorldObject object2){
		double radiusRatio = 1.001;
		return object1.getPosition().distanceTo(object2.getPosition()) <= (object1.getRadius() + object2.getRadius())*radiusRatio;
	}
	
	/**
	 * returns all the positions of the WorldObjects placed in the world
	 * 
	 * @see implementation
	 */
	public HashSet<Vector2D> getAllWorldObjectPositions(){
		return new HashSet<Vector2D>(worldObjects.keySet());
	}

	/**
	 * returns all the world objects currently in the world
	 * 
	 * @see implementation
	 */
	public HashSet<WorldObject> getAllWorldObjects(){
		HashSet<WorldObject> allObjects = new HashSet<WorldObject>(worldObjects.values());
		return allObjects;
	}
	
	public <T extends WorldObject> Set<T> getAll(String className){
		Set<WorldObject> allObjects = new HashSet<WorldObject>(this.getWorldObjectMap().values());
		
		Class<?> currentClass;
		
		try{
			currentClass = Class.forName(className);
		}catch(ClassNotFoundException exc){
			throw new IllegalArgumentException("Class not found");
		}
		
		Set<WorldObject> result = allObjects.stream()
			    .filter(worldObject -> currentClass.isInstance(worldObject)).collect(Collectors.toSet());
		
		return (Set<T>)result;
	}
	
	
	/**
	 * returns all the ships currently in the world
	 * 
	 * @see implementation
	 */
	public HashSet<Ship> getAllShips(){
		Set<WorldObject> allObjects = new HashSet<WorldObject>(worldObjects.values());
		HashSet<Ship> allShips = new HashSet<Ship>();
		Ship ship = null;
		for (WorldObject i : allObjects){
			if (i instanceof Ship) {
				ship = (Ship) i;
				allShips.add(ship);
			}
		}
		return allShips;
	}
	
	/**
	 * returns all the bullets currently in the world
	 * 
	 * @see implementation
	 */
	public HashSet<Bullet> getAllBullets(){
		Set<WorldObject> allObjects = new HashSet<WorldObject>(worldObjects.values());
		HashSet<Bullet> allBullets = new HashSet<Bullet>();
		Bullet bullet = null;
		for (WorldObject i : allObjects){
			if (i instanceof Bullet) {
				bullet =(Bullet) i;
				allBullets.add(bullet);
			}
		}
		return allBullets;
	}
	
	public HashSet<Asteroid> getAllAsteroids(){
		Set<WorldObject> allObjects = new HashSet<WorldObject>(worldObjects.values());
		HashSet<Asteroid> allAsteroids = new HashSet<Asteroid>();
		Asteroid asteroid = null;
		for (WorldObject i : allObjects){
			if (i instanceof Asteroid) {
				asteroid =(Asteroid) i;
				allAsteroids.add(asteroid);
			}
		}
		return allAsteroids;
	}
	
	public HashSet<Planetoid> getAllPlanetoids(){
		Set<WorldObject> allObjects = new HashSet<WorldObject>(worldObjects.values());
		HashSet<Planetoid> allPlanetoids = new HashSet<Planetoid>();
		Planetoid planetoid = null;
		for (WorldObject i : allObjects){
			if (i instanceof Planetoid) {
				planetoid =(Planetoid) i;
				allPlanetoids.add(planetoid);
			}
		}
		return allPlanetoids;
	}
	
	
	/**
	 * returns the HashMap containing all the world objects and their position in the world
	 * 
	 * @see implementation
	 */
	@Model @Basic
	private Map<Vector2D, WorldObject> getWorldObjectMap(){
		return this.worldObjects;
	}
	
	/**
	 * Evolve the world for a specified amount of time
	 * @param	deltaT
	 * @param	collisionListener
	 * 
	 * @post	advance all the objects in the world and resolve the collisions
	 * 			|@see implementation
	 * 
	 * @throws	IllegalArgumentException
	 * 			|deltaT <= 0
	 * @throws	IllegalStateException
	 */
	public void evolve(double deltaT, CollisionListener collisionListener)throws IllegalArgumentException, IllegalStateException{
		if(deltaT <= 0)
			throw new IllegalArgumentException();
		
		double[] collision = this.getNextCollision();
		if (collision == null){
			advanceTime(deltaT);
			return;
		}
		double collisionTime = collision[0];
		Vector2D collisionPos = new Vector2D(collision[1], collision[2]);
		
		if(collisionTime<= deltaT){
			advanceTime(collisionTime);
			resolveCollision(collisionPos, collisionListener);
			evolve(deltaT - collisionTime, collisionListener);		
		}
		
		else{
			advanceTime(deltaT);
			return;
		}
	}

	/**
	 * advances time for a given time interval
	 * @param 	time
	 * 			the time that needs to be advanced
	 * 
	 * @effect	all the ships in the world are advanced time-seconds 
	 * 			| for ship in getAllShips() do ship.move(time) && ship.thrust(time) && updatePosition((old ship).getPosition(), ship)
	 * 
	 * @effect	all the Planetoid in the world are advanced time-seconds
	 * 			| for bullet in getAllasteroid() do bullet.move(time)&&updatePosition((old bullet).getPosition(), bullet)
	 */
	public void advanceTime(double time){
		HashSet<Ship> worldShips = this.getAllShips();
		HashSet<Bullet> worldBullets = this.getAllBullets();
		HashSet<Planetoid> worldPlanetoids = this.getAllPlanetoids();
		HashSet<Asteroid> worldAsteroids = this.getAllAsteroids();
		// move all the ships
		for(Ship ship: worldShips){
			Vector2D oldPosition = ship.getPosition();
			ship.move(time);
			ship.thrust(time);
			this.updatePosition(oldPosition, ship);

		}
		
		// move all the bullets
		for(Bullet bullet: worldBullets){
			Vector2D oldPosition = bullet.getPosition();
			bullet.move(time);
			//update the position in the world
			this.updatePosition(oldPosition, bullet);
		}
		
		// move all the planetoids
		for(Planetoid planetoid: worldPlanetoids){
			Vector2D oldPosition = planetoid.getPosition();
			planetoid.move(time);
			//update the position in the world
			this.updatePosition(oldPosition, planetoid);
		}
		
		// move all the asteroids
		for(Asteroid asteroid: worldAsteroids){
			Vector2D oldPosition = asteroid.getPosition();
			asteroid.move(time);
			//update the position in the world
			this.updatePosition(oldPosition, asteroid);
		}
	}
	
	/**
	 * Updates the position of the world object associated with the old position
	 * @param 	oldPosition
	 * 			the old position of the ship
	 * 
	 * @param 	object
	 * 			the object that needs to be updated
	 * 
	 * @post	the world object associated with oldPosition is now moved to newPosition
	 * 			|@see implementation
	 * 
	 * @throws	IllegalArgumentException
	 * 			thrown if and only if there is no worldObject at the oldPosition
	 * 			|getWorldObjectMap().get(oldPosition) == null
	 */
	public void updatePosition(Vector2D oldPosition, WorldObject object)throws IllegalArgumentException{
		if(object == null)
			throw new IllegalArgumentException();
		this.getWorldObjectMap().remove(oldPosition);
		this.getWorldObjectMap().put(object.getPosition(), object);
		
	}
	
	/***
	 * resolve the collision as collision with boundary or with an other object
	 * @param collisionPos
	 * @param collisionListener
	 * 
	 * @effect	if the collision is between a world object and the  world boundary resolve as a
	 * 			worldObject - boundary collision
	 * 			| resolveBoundaryCollision(collisionPos, collisionListener
	 * 
	 * @effect 	if the collision is between two world objects resolve as
	 * 			a world object-world object collision
	 * 			| resolveObjectCollision(collisionPos, collisionListener)
	 */
	private void resolveCollision(Vector2D collisionPos,CollisionListener collisionListener){
		if(WorldObject.doubleEquals(collisionPos.getXComponent(),0)||WorldObject.doubleEquals(collisionPos.getXComponent(),this.getWidth())
				||WorldObject.doubleEquals(collisionPos.getYComponent(),0) || WorldObject.doubleEquals(collisionPos.getYComponent(),this.getHeight())){
			this.resolveBoundaryCollision(collisionPos, collisionListener);
		}
		
		else{
			this.resolveObjectCollision(collisionPos, collisionListener);
		}

	}

	
	/**
	 * resolves the collisions between world objects
	 * @param 	collisionPos
	 * 			the collision position
	 * @param 	collisionListener
	 * 			interface objects from collision listener
	 * @effect	the collision between the two objects is resolved
	 * 			| determine the colliding objects as follows and with collObjList the list
	 * 			| that contains all the collision partners (2)
	 * 			| for object in getAllWorldObjects if 
	 * 			| 		object.getPosition().distanceTo(collisionPos) == object.getradius then collObjList.add(object)
	 * 			| then identify the collision and resolve them
	 * 			| collisionIdentifier(collisionPos, collisionListener, collisionCandidates)
	 * 
	 */
	private void resolveObjectCollision(Vector2D collisionPos, CollisionListener collisionListener){

		HashSet<WorldObject> worldObjects = new HashSet<WorldObject>(this.getAllWorldObjects());
		
		ArrayList<WorldObject> collisionCandidates = new ArrayList<WorldObject>();
		for(WorldObject object: worldObjects){
			//Radius of a planetoid changes
			if(object instanceof Planetoid){
				if(WorldObject.doubleEqualsLessAccurate(object.getPosition().distanceTo(collisionPos), object.getRadius())){
					collisionCandidates.add(object);
				}
			}
			else if(WorldObject.doubleEquals(object.getPosition().distanceTo(collisionPos), object.getRadius())){
					collisionCandidates.add(object);
			}
		}
		collisionIdentifier(collisionPos, collisionListener, collisionCandidates);

	}
	
	/**
	 * Identifies and resolves collision between two world objects
	 * @param 	collisionPos
	 * 			the position of the collision
	 * @param 	collisionListener
	 * 			the CollisionListener interface object
	 * @param 	collisionCandidates
	 * 			a list of size 2
	 * 
	 * @effect	if the collision is between two ships resolve as ship - ship collision
	 * 			|((Ship)collisionCandidates.get(0)).resolve((Ship)collisionCandidates.get(1))
	 * 
	 * @effect 	if the collision is between a ship and a bullet resolve as ship-bullet collision
	 * 			| ((Ship)collisionCandidates.get(0)).resolve((Ship)collisionCandidates.get(1))
	 * 
	 * @throws 	IllegalStateException
	 * 			thrown if the size of collisionCandidates is not equal to two
	 * 			| collisionCandidates.size() != 2
	 * 
	 * @throws 	IllegalArgumentException
	 */
	protected void collisionIdentifier(Vector2D collisionPos, CollisionListener collisionListener,
			ArrayList<WorldObject> collisionCandidates) throws IllegalStateException, IllegalArgumentException {
			if(collisionCandidates.size() !=2){
				throw new IllegalStateException();
			}
			
			WorldObject object1 = collisionCandidates.get(0);
			WorldObject object2 = collisionCandidates.get(1);
			
			if(apparentlyCollide(object1, object2)){
				//try catch for usage in tests
				try{
					collisionListener.objectCollision(object1, object2, collisionPos.getXComponent(), collisionPos.getYComponent());
				}catch(NullPointerException exc){
				}
				
				object1.resolveCollision(object2);
				
//				// check if object1 is a ship and object2 a bullet
//				if(object1 instanceof Ship && object2 instanceof Bullet){		
//					((Ship)object1).resolveCollision(((Bullet)object2));
//					
//				// check if object1 is a ship and object2 a ship
//				} else if( object1 instanceof Ship && object2 instanceof Ship ){
//					
//					((Ship)object1).resolveCollision(((Ship)object2));
//					
//				// we now know that object1 is a bullet so check if object 2 is a ship
//				} else if( object2 instanceof Ship){
//					
//					((Ship)object2).resolveCollision(((Bullet)object1));
//				// only option left is that object 1 is a bullet and object 2 is a bullet
//				} else{
//					((Bullet)object1).resolveCollision(((Bullet)object2));
//				}
//				
			}
	}
	
	/**
	 * resolves a collision at the boundary of a world
	 * @param 	collisionPos
	 * 			the position of the collision
	 * @param 	collisionListener
	 * 			collisionListener interface object
	 * 
	 * @effect	resolve the collision between the world object and the world boundary
	 * 			| determine "object" as the object that collides with the boundary at collisionpos
	 * 			|	object.getPosition().distanceTo(collisionPos) == object.getRadius()
	 * 			| resolve the WorldObject-Boundary collision
	 * 			|  	object.resolveCollision(this)
	 */
	private void resolveBoundaryCollision(Vector2D collisionPos, CollisionListener collisionListener){
		
		HashSet<WorldObject> worldObjects = new HashSet<WorldObject>(this.getWorldObjectMap().values());
		
		for(WorldObject object: worldObjects){
			if(WorldObject.doubleEquals(object.getPosition().distanceTo(collisionPos),object.getRadius())){
				try{
					collisionListener.boundaryCollision(object, collisionPos.getXComponent(),collisionPos.getYComponent());
				}catch(NullPointerException exc){
				}
				object.resolveCollision(this);
			}
		}
	}
	
	
	/***
	 * returns the time and position of the next collision
	 * @return	if the next collision is between two objects
	 * 			return [time, collisionPositionX, collisionPositionY] such that
	 * 			object1.getTimeToCollision(object2) <= objectI.getTimeToCollision(objectJ)
	 * 			with objectI and objectJ any of the objects in the world
	 * 			time == object1.getTimeToCollision(object2)
	 * 			collisionPositionX == object1.getCollisionPosition(object2)[0]
	 * 			collisionPositionY == object1.getCollisionPosition(object2)[1]
	 * 
	 * @return	if the next collision is between an object and the world
	 * 			return [time, collisionPositionX, collisionPositionY] such that
	 * 			object1.getTimeToCollision(this) <= objectI.getTimeToCollision(this)
	 * 			with objectI any of the objects in the world.
	 * 			time == object1.getTimeToCollision(this)
	 * 			collisionPositionX == object1.getCollisionPosition(this)[0]
	 * 			collisionPositionY == object1.getCollisionPosition(this)[1]
	 * 
	 * @throws IllegalArgumentException
	 * @throws ArithmeticException
	 */
	public double[] getNextCollision() throws IllegalArgumentException, ArithmeticException{
		double timeToCollision = Double.POSITIVE_INFINITY;
		double collisionXPosition = Double.POSITIVE_INFINITY;
		double collisionYPosition = Double.POSITIVE_INFINITY;
		double[] collisionPosition = {collisionXPosition,collisionYPosition};
		double collWOTime;
		HashSet<WorldObject> unCheckedElem = this.getAllWorldObjects();
		for (WorldObject object1 : this.getAllWorldObjects()){
			unCheckedElem.remove(object1);
			
			double collWorldTime = object1.getTimeToCollision(this);
			if (collWorldTime < timeToCollision){
				timeToCollision = collWorldTime;
				collisionPosition = object1.getCollisionPosition(this);
			}

			for (WorldObject object2 : unCheckedElem){
				try{
					collWOTime = object1.getTimeToCollision(object2);
				}	
				catch (IllegalArgumentException exc){
					collWOTime = Double.POSITIVE_INFINITY;
				}
				if (collWOTime < timeToCollision){
					timeToCollision = collWOTime;
					collisionPosition = object1.getCollisionPosition(object2);
					}

			}
		}
		if(timeToCollision == Double.POSITIVE_INFINITY){
			return null;
		}else
			return new double[] {timeToCollision, collisionPosition[0], collisionPosition[1]};
	}
	
	public boolean checkTransport(Ship ship){
		// first check if the ship is a null reference
				if((ship != null)&&this.withinBoundary(ship)){
					HashSet<WorldObject> WorldObjectsInWorld = this.getAllWorldObjects();
					// check if another world object is within overlapping radius
					for(WorldObject other: WorldObjectsInWorld){
						if((significantOverlap(ship, other))&&(other != ship)){
							return false;
						}	
					}
					// return true if no such object can be found	
					return true;
				}
				else 
					return false;			
	}

	/**
	 * variable that stores the world objects in the world
	 */
	private Map<Vector2D,WorldObject> worldObjects = new HashMap<Vector2D,WorldObject>();
}