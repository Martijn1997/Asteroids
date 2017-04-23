package asteroids.model;

import java.util.Random;

public class Planetoid extends MinorPlanet {
	
	@Override
	public void resolveCollision(Ship ship) throws IllegalStateException{
		if(!World.apparentlyCollide(this,ship))
			throw new IllegalStateException();
		
		transport(ship);
	}

	private void transport(Ship ship) {
		World world = ship.getWorld();
		double radius = ship.getRadius();
		double minValue = radius;
		double xMax = world.getWidth() - radius;
		double yMax = world.getHeight() - radius;
		Random rX = new Random();
		Random rY = new Random();
		double newX = minValue + (xMax - minValue) * rX.nextDouble();
		double newY = minValue + (yMax - minValue) * rY.nextDouble();
		ship.setPosition(newX, newY);
		if (!world.checkTransport(ship))
			ship.terminate();
	}
	

}
