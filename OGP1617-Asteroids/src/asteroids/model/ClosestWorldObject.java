package asteroids.model;

import java.util.Set;

public class ClosestWorldObject<T extends WorldObject> extends Expression<T,T> {
	
	public ClosestWorldObject(String className){
		this.setClassName(className);
	}
	
	@Override
	public T evaluate(){
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
		
		World currentWorld = self.getWorld();
			
		Set<WorldObject> worldObjects = currentWorld.getAllWorldObjects();
		T result = 	worldObjects.stream()
					.filter(worldObject -> worldObject instanceof Class.forName(className)) // filter for the right types
					.reduce((a, b) -> self.getDistanceBetween(a) < self.getDistanceBetween(b) && (a != self || b != self)? a : b); // select the smallest distance
		
		
		return result;
	}
	
	public String getClassName(){
		return this.className;
	}
	
	public void setClassName(String name){
		this.className = name;
	}
	
	private String className;
	
}
