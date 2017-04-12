package asteroids.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import asteroids.part2.CollisionListener;
import be.kuleuven.cs.som.annotate.*;

public class World {
	
	public World(double width, double height){
		this.setHeight(height);
		this.setWidth(width);
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
	 * @Post	if the width is valid the width of the world is set to the provided value
	 * 			| if isValidBoundary(width)
	 * 			| then new.getWidth() == width
	 * 
	 * @Post 	if the width is not valid, the boundary is set to Double.POSITIVE_INFINITY
	 * 			| if !isValidBoundary(width)
	 * 			| then new.getWidth() = Double.POSITIVE_INFINITY
	 */
	@Basic @Raw
	public void setWidth(double width){
		if (isValidBoundary(width))
			this.width = width;	
		else 
			this.width = Double.POSITIVE_INFINITY;
	}
	
	private double width;
	
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
	 * 			the width desired for the world
	 * @Post	if the height is valid the height of the world is set to the provided value
	 * 			| if isValidBoundary(width)
	 * 			| then new.getHeight() == height
	 * 
	 * @Post 	if the height is not valid, the boundary is set to Double.POSITIVE_INFINITY
	 * 			| if !isValidBoundary(height)
	 * 			| then new.getHeight() = Double.POSITIVE_INFINITY
	 */
	@Basic @Raw
	public void setHeight(double height){
		if (isValidBoundary(height))
			this.height = height;	
		else
			this.height = Double.POSITIVE_INFINITY;
	}
	
	private double height;

	// add functionality to set the references to this world to null in the associated WO
	public void terminate(){
		this.isTerminated = true;
		Set<WorldObject> allObjects = new HashSet<WorldObject>(worldObjects.values());		
		for (WorldObject  object : allObjects){
			object.setWorld(null);
		}
		worldObjects.clear();
	}
	
	@Basic @Raw
	public boolean isTerminated(){
		return this.isTerminated;
	}
	
	private boolean isTerminated;
	
	
	private static boolean isValidBoundary(double boundary){
		return (boundary <= Double.MAX_VALUE && boundary >= 0);
	}
	
	public static boolean significantOverlap(WorldObject worldObject1, WorldObject worldObject2) throws IllegalArgumentException, ArithmeticException{
		if(worldObject1 == null || worldObject2 == null)
			throw new IllegalArgumentException();
		if(worldObject1 == worldObject2)
			return true;
		return worldObject1.getPosition().distanceTo(worldObject2.getPosition()) >= 0.99*(worldObject1.getRadius() + worldObject2.getRadius())&&
				worldObject1.getPosition().distanceTo(worldObject2.getPosition()) < 1.01*(worldObject1.getRadius() + worldObject2.getRadius());
	}
	

	/**
	 * checker if the world object is within the world boundaries
	 * @param worldObject
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
	 * Adds a world object to the world 
	 * @Post	the world object in placed within the world
	 * 			| worldObject.getWorld() == World
	 * 
	 * @param worldObject
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
	 * @true 	true if the world object has no significant overlap with another object in the world
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
				if(withinRadius(worldObject, other)){
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
	 * Returns the position of the world object where object collides with
	 * @param 	object
	 * @return	if the object collides with another world object return the position of the other world object
	 * 
	 * 			|for worldObject in getAllWorldObjects() if withinradius(worldObject, object) result == true
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
		return object1.getPosition().distanceTo(object2.getPosition()) <= object1.getRadius() + object2.getRadius();
	}
	
	/**
	 * @return all the positions of the WorldObjects placed in the world
	 */
	public HashSet<Vector2D> getAllWorldObjectPositions(){
		return new HashSet<Vector2D>(worldObjects.keySet());
	}

	/**
	 * @return all the world objects currently in the world
	 */
	public HashSet<WorldObject> getAllWorldObjects(){
		HashSet<WorldObject> allObjects = new HashSet<WorldObject>(worldObjects.values());
		return allObjects;
	}
	
	/**
	 * @return all the ships currently in the world
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
	 * All the bullets currently in the world
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
	
	/**
	 * @return the HashMap containing all the world objects and their position in the world
	 */
	@Model @Basic
	private Map<Vector2D, WorldObject> getWorldObjectMap(){
		return this.worldObjects;
	}
	
	/**
	 * Evolve the world for a specified amount of time
	 * @param deltaT
	 * @param collisionListener
	 * @throws IllegalArgumentException
	 */
	public void evolve(double deltaT, CollisionListener collisionListener)throws IllegalArgumentException{
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
			// recursive call of the function
			evolve(deltaT - collisionTime, collisionListener);		
		}
		
		else{
			advanceTime(deltaT);
			return;
		}
	}
//		
//		while(collisionTime <= deltaT){
//			
//			advanceTime(collisionTime);
//			resolveCollision(collisionPos);
//			deltaT -= collisionTime;
//					
//			collision = this.getNextCollision();
//			collisionTime = collision[0];
//			collisionPos = new Vector2D(collision[1], collision[2]);
//			
//		}
//
//		advanceTime(deltaT);
//	}
//		

	
	/**
	 * advances time for a given time interval
	 * @param 	time
	 * 			the time that needs to be advanced
	 * @post	all the positions and velocities of the world objects are done
	 * 			|@see implementation
	 */
	public void advanceTime(double time){
		HashSet<Ship> worldShips = this.getAllShips();
		HashSet<Bullet> worldBullets = this.getAllBullets();
		// move all the ships
		for(Ship ship: worldShips){
			Vector2D oldPosition = ship.getPosition();

			ship.move(time);
			
			if (ship.getThrusterStatus()) {
			//	System.out.println("Ship mass difference: " + Double.toString(ship.getMass() /*+ ship.getTotalMass()*/));
			//	System.out.println("Ship totalmass: " + Double.toString(ship.getMass()));
			}
			ship.thrust(time);

			//this.updatePosition(oldPosition, ship.getPosition(), ship);
			this.updatePosition(oldPosition, ship);

		}
		
		// move all the bullets
		for(Bullet bullet: worldBullets){
			Vector2D oldPosition = bullet.getPosition();
			bullet.move(time);
			//update the position in the world
			this.updatePosition(oldPosition, bullet);
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
	
	
	private void resolveCollision(Vector2D collisionPos,CollisionListener collisionListener){		
		// check if the collision was with a boundary of the world
		if(collisionPos.getXComponent() == 0||collisionPos.getXComponent()==this.getWidth()||collisionPos.getYComponent() == 0 || collisionPos.getYComponent()== this.getHeight()){
			this.resolveBoundaryCollision(collisionPos, collisionListener);
		}

		
		else{
			this.resolveObjectCollision(collisionPos, collisionListener);
		}

	}
	
	private void resolveObjectCollision(Vector2D collisionPos, CollisionListener collisionListener){

		HashSet<WorldObject> worldObjects = new HashSet<WorldObject>(this.getWorldObjectMap().values());
		
		ArrayList<WorldObject> collisionCandidates = new ArrayList<WorldObject>();
		// look where the object collides
		for(WorldObject object: worldObjects){
			// look if the current object overlaps with the given position, if so, add it to the candidate list
			if(WorldObject.doubleEquals(object.getPosition().distanceTo(collisionPos), object.getRadius()))
					collisionCandidates.add(object);
			
		}
		
		// check if the objects indeed overlap
		for(int index1 = 0; index1 < collisionCandidates.size(); index1++){
			WorldObject object1 = collisionCandidates.get(index1);
			for(int index2 = index1 + 1; index2 < collisionCandidates.size(); index2++ ){
				WorldObject object2 = collisionCandidates.get(index2);
				if(WorldObject.doubleEquals(object1.getPosition().distanceTo(object2.getPosition()), object1.getRadius()+object2.getRadius())){
					collisionListener.objectCollision(object1, object2, collisionPos.getXComponent(), collisionPos.getYComponent());
					// check if object1 is a ship and object2 a bullet
					if(object1 instanceof Ship && object2 instanceof Bullet){		
						((Ship)object1).resolveCollision(((Bullet)object2));
						
					// check if object1 is a ship and object2 a ship
					} else if( object1 instanceof Ship && object2 instanceof Ship ){
						
						((Ship)object1).resolveCollision(((Ship)object2));
						
						
		
					// we now know that object1 is a bullet so check if object 2 is a ship
					} else if( object2 instanceof Ship){
						((Ship)object2).resolveCollision(((Bullet)object1));
					// only option left is that object 1 is a bullet and object 2 is a bullet
					} else{
						((Bullet)object1).resolveCollision(((Bullet)object2));
					}
					
					return;
				}
			}
			
		}
	}
	
	private void resolveBoundaryCollision(Vector2D collisionPos, CollisionListener collisionListener){
		
		HashSet<WorldObject> worldObjects = new HashSet<WorldObject>(this.getWorldObjectMap().values());
		
		for(WorldObject object: worldObjects){
			if(WorldObject.doubleEquals(object.getPosition().distanceTo(collisionPos),object.getRadius())){
				collisionListener.boundaryCollision(object, collisionPos.getXComponent(),collisionPos.getYComponent());
				object.resolveCollision(this);
			}
		}
	}
	
	
	public WorldObject[] getObjectsNextCollision() throws IllegalArgumentException, ArithmeticException{
		double timeToCollision = Double.POSITIVE_INFINITY;
		WorldObject object1 = null;
		WorldObject object2 = null;
		Set<WorldObject> allObjects = new HashSet<WorldObject>(worldObjects.values());
		Set<WorldObject> copyAllObjects = new HashSet<WorldObject>(worldObjects.values());
		for (WorldObject i : allObjects){
			copyAllObjects.remove(i);
			if (i.getTimeToCollision(this) < timeToCollision){
				timeToCollision = i.getTimeToCollision(this);
				object1 = i;
				object2 = null;
			}
			for (WorldObject j : copyAllObjects){
				if (i.getTimeToCollision(j) < timeToCollision){
					timeToCollision = i.getTimeToCollision(j);
					object1 = i;
					object2 = j;
				}
			}
		}
		WorldObject[] objectsNextCollision = {object1, object2};
		return objectsNextCollision;
	}
	
	public double[] getNextCollision() throws IllegalArgumentException, ArithmeticException{
		double timeToCollision = Double.POSITIVE_INFINITY;
		double collisionXPosition = Double.POSITIVE_INFINITY;
		double collisionYPosition = Double.POSITIVE_INFINITY;
		double[] collisionPosition = {collisionXPosition,collisionYPosition};
		double collWOTime;
		HashSet<WorldObject> unCheckedElem = this.getAllWorldObjects();
		// start the outer for loop, iterating over all the objects in the set
		for (WorldObject object1 : this.getAllWorldObjects()){
			unCheckedElem.remove(object1);
			
			// check if the object collides with the sides of the world
			double collWorldTime = object1.getTimeToCollision(this);
			if (collWorldTime < timeToCollision){
				timeToCollision = collWorldTime;
				collisionPosition = object1.getCollisionPosition(this);
			}

			// second for loop check if the object collides with another object in space
			for (WorldObject object2 : unCheckedElem){
				try{
					collWOTime = object1.getTimeToCollision(object2);
				}	
				catch (IllegalArgumentException exc){
					collWOTime = Double.POSITIVE_INFINITY;
				}
				if (object1.getTimeToCollision(object2) < timeToCollision){
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
	
	

	//worldObjects.remove(worldObject) werkt niet want worldObject is de Value en niet de key
	public void removeFromWorld(WorldObject worldObject) throws IllegalArgumentException{
		if (worldObject.getWorld() == null)
			throw new IllegalArgumentException();
		Vector2D position = worldObject.getPosition();
		worldObjects.remove(position);
//		worldObject.setWorldToNull();
	}
	
	public WorldObject getEntityAt(Vector2D position){
		WorldObject entity = worldObjects.get(position);
		return entity;
	}



	private Map<Vector2D,WorldObject> worldObjects = new HashMap<Vector2D,WorldObject>();
}