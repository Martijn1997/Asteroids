package asteroids.model;

import exceptions.BreakException;

public class BreakStatement extends NormalStatement{
	public BreakStatement(){
		super();
	}
	
	public void executeStatement(){
			throw new BreakException();

	}

}