package asteroids.model;

import java.util.Set;

public class ClosestWorldObject<T extends WorldObject> extends Expression<T,T> {
	
	public ClosestWorldObject(String className){
		this.setClassName(className);
	}
	
	@Override
	public T evaluate() throws IllegalStateException{
//		Ship self;
//		if(this.getStatement().getProgram() != null){
//			self = this.getStatement().getProgram().getShip();
//		}else if(this.getStatement() instanceof NormalStatement){
//			if(((NormalStatement) this.getStatement()).getFunction() != null){
//				self = ((NormalStatement) this.getStatement()).getFunction().getProgram().getShip();
//			}else{
//				throw new IllegalStateException();
//			}
//		}else{
//			throw new IllegalStateException();
//		}
//		
//		World currentWorld = self.getWorld();
//			
//		Set<WorldObject> worldObjects = currentWorld.getAllWorldObjects();
//		T result = 	worldObjects.stream()
//					.filter(worldObject -> ((WorldObject)worldObject) instanceof Class.forName(className)) // filter for the right types
//					.reduce((WorldObject a, WorldObject b) -> self.getDistanceBetween(a) <= self.getDistanceBetween(b) && (a != self || b != self)? a : b); // select the smallest distance
//
//		return (T)result;
		
		return null;
		}
	
	public String getClassName(){
		return this.className;
	}
	
	public void setClassName(String name){
		this.className = name;
	}
	
	private String className;
	
}
