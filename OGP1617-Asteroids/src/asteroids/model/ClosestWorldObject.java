package asteroids.model;


import java.util.NoSuchElementException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import asteroids.part3.programs.SourceLocation;

public class ClosestWorldObject<T extends WorldObject> extends Expression<T,T> {
	
	public ClosestWorldObject(String className, SourceLocation sourceLocation){
		super(sourceLocation);
		this.className = className;
	}
	

	@SuppressWarnings("unchecked")
	//TODO modify the reduction with unity parameter

	@Override
	public T evaluate() throws IllegalStateException{
		final Ship self;
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

		final Class<?> currentClass;
		
		try {
			currentClass = Class.forName(this.getClassName());
		} catch (ClassNotFoundException e){
			throw new IllegalArgumentException("No such class found");
		}
		
		T returnValue;
		Optional<WorldObject> result2;
		
		Set<WorldObject> worldObjects = currentWorld.getAllWorldObjects();
		worldObjects.remove(self);
		

		//first count the amount of objects that statisfy the conditions
		Set<WorldObject> result1 = worldObjects.stream()
				    .filter(worldObject -> currentClass.isInstance(worldObject)).collect(Collectors.toSet());// filter for the right types	
		
		
		// case 1 hij is leeg de set
		if(result1.size()==0){
			return null;
		}
		else if(result1.size()==1){
			return (T) result1;
			
		//	
		}else{
			result2= result1.stream().reduce((WorldObject a, WorldObject b) 
					-> self.getDistanceBetween(a) <= self.getDistanceBetween(b) ? a : b);
		}
							
		try{
			returnValue = (T) result2.get();
		}catch (NoSuchElementException exc ){
			return null;
		}
		return returnValue;


		
//		T result = 	worldObjects.stream()
//				.filter(worldObject -> worldObject instanceof Class.forName(className)) // filter for the right types
//				.reduce((a, b) -> self.getDistanceBetween(a) < self.getDistanceBetween(b) && (a != self || b != self)? a : b); // select the smallest distance
		
//		return result;
	}
	
	public String getClassName(){
		return this.className;
	}
	
	
	private final String className;
	
	public String toString(){
		return className;
	}
	
}
