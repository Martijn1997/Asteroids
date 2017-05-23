package asteroids.model;

import asteroids.part3.programs.SourceLocation;
import exceptions.BreakException;

public class BreakStatement extends NormalStatement{
	public BreakStatement(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	public void executeStatement(){
			throw new BreakException();

	}

}