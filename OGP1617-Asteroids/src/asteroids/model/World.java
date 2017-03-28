package asteroids.model;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;

public class World {
	

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
		double xPos = worldObject.getXPosition();
		double yPos = worldObject.getYPosition();
		double[] position = {xPos, yPos};
		
		//set the WO in the map
		worldObjects.put(position, worldObject);
		worldObject.setWorld(this);
		
	}
	
	public void evolve(double dt) throws IllegalArgumentException, ArithmeticException{
		if (dt < 0)
			throw new IllegalArgumentException();
		double tc = 0;
		for (double timeLeft = dt; timeLeft > 0; timeLeft -= tc){
			Set<WorldObject> allObjects = new HashSet<WorldObject>(worldObjects.values());
			tc = getNextCollision()[0];
			if (tc <= dt) {
				for (WorldObject i : allObjects){
					i.move(tc);
					//resolve collision
				}
			}else {
				for (WorldObject i : allObjects){
					i.move(dt);
				}
			}
		}
	}
	
	
	
	
	public double[] getNextCollision() throws IllegalArgumentException, ArithmeticException{
		double timeToCollision = Double.POSITIVE_INFINITY;
		double[] collisionPosition = {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};
		WorldObject object = null;
		Set<WorldObject> allObjects = new HashSet<WorldObject>(worldObjects.values());
		Set<WorldObject> copyAllObjects = new HashSet<WorldObject>(worldObjects.values());
		for (WorldObject i : allObjects){
			copyAllObjects.remove(i);
			for (WorldObject j : copyAllObjects){
				if (i.getTimeToCollision(j) < timeToCollision)
					timeToCollision = i.getTimeToCollision(j);
					collisionPosition = i.getCollisionPosition(j);
					object = i;
			//bij nieuwe overloading van gettimetocollision wel ok
			if (i.getTimeToCollision(this) < timeToCollision)
				timeToCollision = i.getTimeToCollision(this);
				collisionPosition = i.getCollisionPosition(this);
				object = i;
			}
		}
		//nog een return statement probleem met double[]
	}

	
	
	
	public boolean canHaveAsWorldObject(WorldObject worldObject){
		return (worldObject != null)&&(worldObject.getWorld() == null);
	}


	private Map<double[],WorldObject> worldObjects = new HashMap<double[],WorldObject>();
}


//Bij WorldObject constructors protected en @Model, ook @Raw?