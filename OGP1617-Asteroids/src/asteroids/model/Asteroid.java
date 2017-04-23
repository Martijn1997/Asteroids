package asteroids.model;

public class Asteroid extends MinorPlanet {

	@Override
	public void resolveCollision(Ship ship) throws IllegalStateException{
		if(!World.apparentlyCollide(this,ship))
			throw new IllegalStateException();
		ship.terminate();
	}
	

}
