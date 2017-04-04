package asteroids.model;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;

public class World {
	

	public World(double width, double height){
		this.setWidth(width);
		this.setHeight(height);
	}

	@Basic @Raw
	public double getWidth(){
		return this.width;
	}
	
	
	@Basic @Raw
	public void setWidth(double width){
		if (isValidBoundary(width))
			this.width = width;	
	}
	
	private double width;
	
	
	@Basic @Raw
	public double getHeight(){
		return this.height;
	}
	
	@Basic @Raw
	public void setHeight(double height){
		if (isValidBoundary(height))
			this.height = height;	
	}
	
	private double height;

	
	public void terminate(){
		if (!isTerminated()) {
			Set<WorldObject> allObjects = new HashSet<WorldObject>(worldObjects.values());		
			for (WorldObject i : allObjects){
				removeFromWorld(i);
			}
		}
	}
	
	@Basic @Raw
	public boolean isTerminated(){
		return this.isTerminated;
	}
	
	private boolean isTerminated;
	
	
	public static boolean isValidBoundary(double boundary){
		return (boundary <= Double.MAX_VALUE && boundary >= 0);
	}
	
	public static boolean significantOverlap(WorldObject worldObject1, WorldObject worldObject2) throws IllegalArgumentException, ArithmeticException{
		if(worldObject1 == null || worldObject2 == null)
			throw new IllegalArgumentException();
		if(worldObject1 == worldObject2)
			return true;
		return worldObject1.getDistanceBetween(worldObject2) <= 0.99*(worldObject1.getRadius() + worldObject2.getRadius());
	}
	


	public boolean withinBoundary(WorldObject worldObject) throws IllegalArgumentException{	
		if (worldObject == null)
			throw new IllegalArgumentException();

		if ((worldObject.getXPosition() + worldObject.getRadius())*0.99 >= this.getWidth())
				return false;
		return (!((worldObject.getYPosition() + worldObject.getRadius())*0.99 >= this.getHeight()));
	}
	

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
	
	public void resolveCollisionObjects(WorldObject object1, WorldObject object2){
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
		Set<WorldObject> allObjects = new HashSet<WorldObject>(worldObjects.values());
		Set<WorldObject> copyAllObjects = new HashSet<WorldObject>(worldObjects.values());
		for (WorldObject i : allObjects){
			copyAllObjects.remove(i);
			if (i.getTimeToCollision(this) < timeToCollision)
				timeToCollision = i.getTimeToCollision(this);
				collisionXPosition = i.getCollisionPosition(this)[0];
				collisionYPosition = i.getCollisionPosition(this)[1];
			for (WorldObject j : copyAllObjects){
				if (i.getTimeToCollision(j) < timeToCollision)
					timeToCollision = i.getTimeToCollision(j);
					collisionXPosition = i.getCollisionPosition(j)[0];
					collisionYPosition = i.getCollisionPosition(j)[1];
			}
		}
		double[] nextCollision = {timeToCollision, collisionXPosition, collisionYPosition};
		return nextCollision;
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

	public boolean canHaveAsWorldObject(WorldObject worldObject){
		return (worldObject != null)&&(worldObject.getWorld() == null);
	}


	private Map<Vector2D,WorldObject> worldObjects = new HashMap<Vector2D,WorldObject>();
}


//Bij WorldObject constructors protected en @Model, ook @Raw?
//moet bij within boundry ook geen rekening gehouden worden met de x en y as zelf?
