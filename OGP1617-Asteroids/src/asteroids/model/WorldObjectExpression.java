package asteroids.model;

import java.util.Set;

public class WorldObjectExpression<T extends WorldObject> extends LiteralExpression<T> {
	
	public WorldObjectExpression(T value, Statement statement){
		super(value, statement);
	}
	
	/**
	 * also checks if the said value T is in the world
	 */
	@Override
	public boolean isValidValue(T value){
		if(!super.isValidValue(value))
			return false;
		Program program = this.getStatement().getProgram();
		World shipWorld = program.getShip().getWorld();
		Set<WorldObject> worldObjects = shipWorld.getAllWorldObjects();
		return worldObjects.contains(value);
	}

}
