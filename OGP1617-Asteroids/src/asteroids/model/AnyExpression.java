package asteroids.model;

import java.util.Set;

import asteroids.part3.programs.SourceLocation;

public class AnyExpression extends Expression<WorldObject,WorldObject>{
	
	public AnyExpression(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	
	public WorldObject evaluate(){
		Ship self;
		if(this.getStatement().getProgram() != null){
			self = this.getStatement().getProgram().getShip();
		}else if(this.getStatement() instanceof NormalStatement){
			if(((NormalStatement) this.getStatement()).getFunction() != null){
				self = ((NormalStatement) this.getStatement()).getFunction().getProgram().getShip();
			}else{
				throw new IllegalStateException();
			}
		}else{
			throw new IllegalStateException();
		}
		
		World world = self.getWorld();
		Set<WorldObject> worldObjects = world.getAllWorldObjects();
		for(WorldObject worldObject: worldObjects){
			return worldObject;
		}
		
		return null;
	}
	
	public String toString(){
		return "Any";
	}
	
}
