package asteroids.Programs;

import java.util.Set;

import asteroids.model.World;
import asteroids.model.WorldObject;

public class WorldObjectExpression<T> extends LiteralExpression<T> {
	
	public WorldObjectExpression(T value, Statement statement){
		super(value, statement);
	}
	
	@Override
	public boolean isValidValue(T value){
		if(!super.isValidValue(value))
			return false;
		Program program = this.getStatement().getProgram();
		World shipWorld = program.getShip().getWorld();
		Set<WorldObject> worldObjects = shipWorld.getAllWorldObjects();
		//TODO use streams and lambda functions to check if the desired object is in the world
		
		return false;
	}

}
