package asteroids.model;
import java.util.ArrayList;
import java.util.Collection;
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
			removeFromWorld(object);
		}
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
		return worldObject1.getPosition().distanceTo(worldObject2.getPosition()) < 0.99*(worldObject1.getRadius() + worldObject2.getRadius());
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
		if(xBoundary[0] < worldObject.getXPosition() - radius*percentage && xBoundary[1] > worldObject.getXPosition() + radius*percentage)
			return true;
		if(yBoundary[0] < worldObject.getYPosition() - radius*percentage && yBoundary[1] > worldObject.getYPosition() + radius*percentage)
			return true;
		
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
				if(World.significantOverlap(worldObject, other)){
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
//		Set<WorldObject> allObjects = new HashSet<WorldObject>(worldObjects.values());
		HashSet<Ship> allShips = new HashSet<Ship>();
		Ship ship = null;
		for (WorldObject i : worldObjects.values()){
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
//		Set<WorldObject> allObjects = new HashSet<WorldObject>(worldObjects.values());
		HashSet<Bullet> allBullets = new HashSet<Bullet>();
		Bullet bullet = null;
		for (WorldObject i : worldObjects.values()){
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
	
	
//	public void evolve(double dt) throws IllegalArgumentException, ArithmeticException{
//		if (dt < 0)
//			throw new IllegalArgumentException();
//		double tc = 0;
//		Vector2D oldPos = null;
//		Vector2D newPos = null;
//		for (double timeLeft = dt; timeLeft > 0; timeLeft -= tc){
//			Set<WorldObject> allObjects = new HashSet<WorldObject>(worldObjects.values());
//			tc = getTimeNextCollision();
//			if (tc <= dt) {
//				for (WorldObject i : allObjects){
//					oldPos = i.getPosition();
//					i.move(tc);
//					newPos = i.getPosition();
//					worldObjects.put(newPos, worldObjects.remove(oldPos));
//					if (i instanceof Ship)
//						((Ship) i).thrust(tc);
//				}
//				WorldObject object1 = getObjectsNextCollision()[0];
//				WorldObject object2 = getObjectsNextCollision()[1];
//				if (object2 == null)
//					object1.resolveCollision(this);
//				else
//					resolveCollisionObjects(object1, object2);
//			}else {
//				for (WorldObject i : allObjects){
//					oldPos = i.getPosition();
//					i.move(dt);
//					newPos = i.getPosition();
//					worldObjects.put(newPos, worldObjects.remove(oldPos));
//					if (i instanceof Ship)
//						((Ship) i).thrust(tc);
//				}
//			}
//		}
//	}
//	
	public void evolve(double deltaT, CollisionListener collisionListener)throws IllegalArgumentException{
		if(deltaT <= 0)
			throw new IllegalArgumentException();
		
		double[] collision = this.getNextCollision();
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
	private void advanceTime(double time){
		HashSet<Ship> worldShips = this.getAllShips();
		HashSet<Bullet> worldBullets = this.getAllBullets();
		// move all the ships
		for(Ship ship: worldShips){
			Vector2D oldPosition = ship.getPosition();
//			System.out.println("before");
//			System.out.println(ship.getXVelocity());
			ship.move(time);
			// if the thuster is enables adjust the velocity
			if(ship.getThrusterStatus()){;
				ship.thrust(time);
			}
			//update the position in the world
//			System.out.println("after");
//			System.out.println(ship.getXVelocity());
			this.updatePosition(oldPosition, ship.getPosition(), ship);
		}
		
		// move all the bullets
		for(Bullet bullet: worldBullets){
			Vector2D oldPosition = bullet.getPosition();
			bullet.move(time);
			//update the position in the world
			this.updatePosition(oldPosition, bullet.getPosition(), bullet);
		}
	}
	
	/**
	 * Updates the position of the world object associated with the old position
	 * @param 	oldPosition
	 * 			the old position of the ship
	 * @param 	newPosition
	 * 			the new position of the ship
	 * @post	the world object associated with oldPosition is now moved to newPosition
	 * 			|@see implementation
	 * @throws	IllegalArgumentException
	 * 			thrown if and only if there is no worldObject at the oldPosition
	 * 			|getWorldObjectMap().get(oldPosition) == null
	 */
	public void updatePosition(Vector2D oldPosition, Vector2D newPosition, WorldObject object)throws IllegalArgumentException{
		if(object == null)
			throw new IllegalArgumentException();
		this.getWorldObjectMap().remove(oldPosition);
		this.getWorldObjectMap().put(newPosition, object);
		
	}
	
	private void resolveCollision(Vector2D collisionPos,CollisionListener collisionListener){
		
		HashSet<WorldObject> worldObjects = new HashSet<WorldObject>(this.getWorldObjectMap().values());
		
		// check if the collision was with a boundary of the world
		if(collisionPos.getXComponent() == 0||collisionPos.getXComponent()==this.getWidth()||collisionPos.getYComponent() == 0 || collisionPos.getYComponent()== this.getHeight()){
			//look for all the positions that have the X component in common with the collision
			
			for(WorldObject object: worldObjects){
				if(WorldObject.doubleEquals(object.getPosition().distanceTo(collisionPos),object.getRadius())){
					collisionListener.boundaryCollision(object, collisionPos.getXComponent(),collisionPos.getYComponent());
					object.resolveCollision(this);
				}
			}
		}

		
		else{
			
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
							System.out.println("ships colliding");
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
	}
	
	
	private void resolveCollisionObjects(WorldObject object1, WorldObject object2){
		Ship ship1 = null;
		Ship ship2 = null;
		Bullet bullet1 = null;
		Bullet bullet2 = null;
		if (object1 instanceof Ship) {
			ship1 = (Ship) object1;
			if (object2 instanceof Ship){
				ship2 = (Ship) object2;
				ship2.resolveCollision(ship1);
			}
			else
				bullet2 = (Bullet) object2;
				bullet2.resolveCollision(ship1);
		}
		else
			bullet1 =(Bullet) object1;
			if (object2 instanceof Ship){
				ship2 = (Ship) object2;
				ship2.resolveCollision(bullet1);
			}
			else
				bullet2 = (Bullet) object2;
				bullet2.resolveCollision(bullet1);
	}

	public double getTimeNextCollision() throws IllegalArgumentException, ArithmeticException{
		return getNextCollision()[0];
	}
	
	public double[] getPosNextCollision() throws IllegalArgumentException, ArithmeticException{
		double[] posNextCollision = {getNextCollision()[1], getNextCollision()[2]};
		return posNextCollision;
	}
	
	
	public WorldObject[] getObjectsNextCollision() throws IllegalArgumentException, ArithmeticException{
		double timeToCollision = Double.POSITIVE_INFINITY;
		WorldObject object1 = null;
		WorldObject object2 = null;
		Set<WorldObject> allObjects = new HashSet<WorldObject>(worldObjects.values());
		Set<WorldObject> copyAllObjects = new HashSet<WorldObject>(worldObjects.values());
		for (WorldObject i : allObjects){
			copyAllObjects.remove(i);
			if (i.getTimeToCollision(this) < timeToCollision)
				timeToCollision = i.getTimeToCollision(this);
				object1 = i;
				object2 = null;
			for (WorldObject j : copyAllObjects){
				if (i.getTimeToCollision(j) < timeToCollision)
					timeToCollision = i.getTimeToCollision(j);
					object1 = i;
					object2 = j;
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
		HashSet<WorldObject> checkedElem = new HashSet<WorldObject>();
		// start the outer for loop, iterating over all the objects in the set
		for (WorldObject object1 : worldObjects.values()){
		
			checkedElem.add(object1);
			// check if the object collides with the sides of the world
			double collWorldTime = object1.getTimeToCollision(this);
			if (collWorldTime < timeToCollision)
				timeToCollision = collWorldTime;
				collisionPosition = object1.getCollisionPosition(this);
			// second for loop check if the object collides with another object in space
			for (WorldObject object2 : worldObjects.values()){
				if(!checkedElem.contains(object2)){
				double collWOTime = object1.getTimeToCollision(object2);
				if (object1.getTimeToCollision(object2) < timeToCollision)
					timeToCollision = collWOTime;
					collisionPosition = object1.getCollisionPosition(object2);
				}
			}
		}
		return new double[] {timeToCollision, collisionPosition[0], collisionPosition[1]};
	}
	
	

	//worldObjects.remove(worldObject) werkt niet want worldObject is de Value en niet de key
	public void removeFromWorld(WorldObject worldObject) throws IllegalArgumentException{
		if (worldObject.getWorld() == null)
			throw new IllegalArgumentException();
		Vector2D position = worldObject.getPosition();
		worldObjects.remove(position);
		worldObject.setWorld(null);
	}
	
	public WorldObject getEntityAt(Vector2D position){
		WorldObject entity = worldObjects.get(position);
		return entity;
	}



	private Map<Vector2D,WorldObject> worldObjects = new HashMap<Vector2D,WorldObject>();
}


//Bij WorldObject constructors protected en @Model, ook @Raw?
//moet bij within boundry ook geen rekening gehouden worden met de x en y as zelf?
