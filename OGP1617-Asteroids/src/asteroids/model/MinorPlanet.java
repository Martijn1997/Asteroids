package asteroids.model;

public class MinorPlanet extends WorldObject {

	@Override
	public double getMinimumRadius() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isValidRadius(double rad) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getMinimumDensity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canHaveAsMass(double mass) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canHaveAsWorld(World world) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resolveCollision(Ship ship) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resolveCollision(Bullet bullet) {
		// TODO Auto-generated method stub

	}

}
