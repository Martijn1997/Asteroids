package asteroids.model;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

	
	public HashSet<WorldObject> getAllWorldObjects(){
		HashSet<WorldObject> allObjects = new HashSet<WorldObject>(worldObjects.values());
		return allObjects;
	}
	
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
	
	public void evolve(double dt) throws IllegalArgumentException, ArithmeticException{
		if (dt < 0)
			throw new IllegalArgumentException();
		double tc = 0;
		Vector2D oldPos = null;
		Vector2D newPos = null;
		for (double timeLeft = dt; timeLeft > 0; timeLeft -= tc){
			Set<WorldObject> allObjects = new HashSet<WorldObject>(worldObjects.values());
			tc = getTimeNextCollision();
			if (tc <= dt) {
				for (WorldObject i : allObjects){
					oldPos = i.getPosition();
					i.move(tc);
					newPos = i.getPosition();
					worldObjects.put(newPos, worldObjects.remove(oldPos));
					if (i instanceof Ship)
						((Ship) i).thrust(tc);
				}
				WorldObject object1 = getObjectsNextCollision()[0];
				WorldObject object2 = getObjectsNextCollision()[1];
				if (object2 == null)
					object1.resolveCollision(this);
				else
					resolveCollisionObjects(object1, object2);
			}else {
				for (WorldObject i : allObjects){
					oldPos = i.getPosition();
					i.move(dt);
					newPos = i.getPosition();
					worldObjects.put(newPos, worldObjects.remove(oldPos));
					if (i instanceof Ship)
						((Ship) i).thrust(tc);
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
			else{
				bullet2 = (Bullet) object2;
				bullet2.resolveCollision(ship1);
			}
		}
		else{
			bullet1 =(Bullet) object1;
			if (object2 instanceof Ship){
				ship2 = (Ship) object2;
				ship2.resolveCollision(bullet1);
			}
			else{
				bullet2 = (Bullet) object2;
				bullet2.resolveCollision(bullet1);
			}
		}
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
		Set<WorldObject> allObjects = new HashSet<WorldObject>(worldObjects.values());
		Set<WorldObject> copyAllObjects = new HashSet<WorldObject>(worldObjects.values());
		for (WorldObject object1 : allObjects){
			copyAllObjects.remove(object1);
			double collWorldTime = object1.getTimeToCollision(this);
			if (collWorldTime < timeToCollision){
				timeToCollision = collWorldTime;
				collisionPosition = object1.getCollisionPosition(this);
			}
			for (WorldObject object2 : copyAllObjects){
				double collWOTime = object1.getTimeToCollision(object2);
				if (collWOTime < timeToCollision){
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
//		worldObject.setWorldToNull();
	}
	
	public WorldObject getEntityAt(Vector2D position){
		WorldObject entity = worldObjects.get(position);
		return entity;
	}



	private Map<Vector2D,WorldObject> worldObjects = new HashMap<Vector2D,WorldObject>();
}


//Bij WorldObject constructors protected en @Model, ook @Raw?
//moet bij within boundry ook geen rekening gehouden worden met de x en y as zelf?
