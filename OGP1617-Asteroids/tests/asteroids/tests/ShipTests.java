package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.Ship;

public class ShipTests {
	
	private static final double EPSILON = 0.0001;
	private static final double EPSILON2 = 1;
	
	private static Ship ship1, ship2, ship3_100_0, ship4_0_0, ship5, ship6, ship7, ship8;
	
	/**
	 * Set up a mutable test fixture.
	 * 
	 * @post	
	 */
	@Before
	public void setUpMutableFixture() {
		ship1 = new Ship(10, 10, 0, 10, 10, -10);
		ship2 = new Ship(10, 10, Math.PI/6, 10, Math.sqrt(2)*300000/2, Math.sqrt(2)*300000/2);
		ship3_100_0 = new Ship(100,0,0,10,0,0);
		ship4_0_0 = new Ship();
		ship5 = new Ship(-20,0,0,10,1,0);
		ship6 = new Ship(20,0,0,10,-1,0);
		ship7 = new Ship(-400,-400,0,10,Math.sqrt(2)/2,Math.sqrt(2)/2);
		ship8 = new Ship(-545.6854249492433, 0, 0, 10, 1,0);
	}
	
	@Test
	public final void extendedConstructor_SingleCase() throws IllegalArgumentException{
		Ship newShip = new Ship(10, 10, 0, 10, 10, 10);
		assertEquals(10, newShip.getXPosition(), EPSILON);
		assertEquals(10, newShip.getYPosition(), EPSILON);
		assertEquals(0, newShip.getOrientation(), EPSILON);
		assertEquals(10, newShip.getRadius(), EPSILON);
		assertEquals(10, newShip.getXVelocity(), EPSILON);
		assertEquals(10, newShip.getYVelocity(), EPSILON);
	}
		
	@Test(expected = IllegalArgumentException.class)
	public final void constructShip_XIsNan() throws IllegalArgumentException{
		new Ship(Double.NaN, 10, 0, -10, 10, 10);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void constructShip_NegRadius() throws IllegalArgumentException{
		new Ship(10, 10, 0, -10, 10, 10);
	}

	@Test(expected = AssertionError.class)
	public final void constructShip_NegOrientation() throws IllegalArgumentException{
		new Ship(10, 10, -Math.PI, 10, 10, 10);
	}
	
	@Test
	public final void defaultConstructor_SingleCase() {
		Ship newShip = new Ship();
		assertEquals(0, newShip.getXPosition(), EPSILON);
		assertEquals(0, newShip.getYPosition(), EPSILON);
		assertEquals(0, newShip.getOrientation(), EPSILON);
		assertEquals(10, newShip.getRadius(), EPSILON);
		assertEquals(0, newShip.getXVelocity(), EPSILON);
		assertEquals(0, newShip.getYVelocity(), EPSILON);
	}
	
	@Test
	public final void isValidPosition_TrueCase() {
		assertTrue(Ship.isValidPosition(100));
	}
	
	@Test
	public final void isValidPosition_FalseCase1() {
		assertFalse(Ship.isValidPosition(Double.POSITIVE_INFINITY));
	}
	
	@Test
	public final void isValidPosition_FalseCase2() {
		assertFalse(Ship.isValidPosition(Double.NaN));
	}
	
	@Test
	public final void isValidVelocity_TrueCase() {
		assertTrue(Ship.isValidVelocity(100));
	}
	
	@Test
	public final void isValidVelocity_NegativeVelocity() {
		assertFalse(Ship.isValidVelocity(-100));
	}
	
	@Test
	public final void isValidVelocity_ExceedingLightSpeed() {
		assertFalse(Ship.isValidVelocity(300001));
	}

	@Test
	public final void isValidRadius_TrueCase() {
		assertTrue(Ship.isValidRadius(100));
	}
	
	@Test
	public final void isValidRadius_NegRadius() {
		assertFalse(Ship.isValidRadius(-10));
	}
	
	@Test
	public final void isValidTime_TrueCase() {
		assertTrue(Ship.isValidTime(100));
	}
	
	@Test
	public final void isValidTime_FalseCase() {
		assertFalse(Ship.isValidTime(-100));
	}
	
	@Test
	public final void isValidOrientation_TrueCase() {
		assertTrue(Ship.isValidOrientation(Math.PI));
	}
	
	@Test
	public final void isValidOrientation_FalseCase() {
		assertFalse(Ship.isValidOrientation(3*Math.PI));
	}
	
	@Test
	public final void move_LegalCase() throws IllegalArgumentException{
		ship1.move(2);
		double xPos = ship1.getXPosition();
		double yPos = ship1.getYPosition();
		assertEquals(30, xPos, EPSILON);
		assertEquals(-10, yPos, EPSILON);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void move_IllegalCase() throws IllegalArgumentException{
		ship1.move(-1);
	}
	
	@Test
	public final void turn_LegalCase() {
		ship1.turn(Math.PI);
		double angle = ship1.getOrientation();
		assertEquals(Math.PI, angle, EPSILON);
	}
	
	@Test (expected = AssertionError.class)
	public final void turn_IllegalCase() {
		ship1.turn(3*Math.PI);
	}
	
	@Test
	public final void thrust_NegAcceleration() {
		ship1.thrust(-3);
		double xVel = ship1.getXVelocity();
		double yVel = ship1.getYVelocity();
		assertEquals(10, xVel, EPSILON);
		assertEquals(-10, yVel, EPSILON);
	}
	
	@Test
	public final void thrust_UnderLightSpeed() {
		ship1.thrust(3);
		double xVel = ship1.getXVelocity();
		double yVel = ship1.getYVelocity();
		assertEquals(13, xVel, EPSILON);
		assertEquals(-10, yVel, EPSILON);
	}
	
	@Test
	public final void thrust_AboveLightSpeed() {
		ship2.thrust(100000);
		double xVel = ship2.getXVelocity();
		double yVel = ship2.getYVelocity();
		assertEquals(225496, xVel, EPSILON2);
		assertEquals(197867, yVel, EPSILON2);
	}
	
	@Test
	public final void distanceBetween_normal(){
		double distance = ship3_100_0.getDistanceBetween(ship4_0_0);
		assertEquals(80, distance, EPSILON);
	}
	
	@Test  (expected = ArithmeticException.class)
	public final void distanceBetween_overflow(){
		ship3_100_0.setYPosition(Double.MAX_VALUE);
		ship4_0_0.setYPosition(-Double.MAX_VALUE);
		ship3_100_0.getDistanceBetween(ship4_0_0);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void distanceBetween_null(){
		Ship nullShip = null;
		ship1.getDistanceBetween(nullShip);
	}
	
	@Test
	public final void distanceBetween_this(){
		double distance = ship1.getDistanceBetween(ship1);
		assertEquals(0, distance, EPSILON);
	}
	
	@Test
	public final void overlap_overlap(){
		Ship shipX = new Ship(100,10,0,10,0,0);
		assertTrue(shipX.overlap(ship3_100_0));
	}
	
	@Test
	public final void overlap_this(){
		assertTrue(ship1.overlap(ship1));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void overlap_illegalArg(){
		Ship nullShip = null;
		ship1.overlap(nullShip);
	}
	
	@Test (expected = ArithmeticException.class)
	public final void overlap_overflow(){
		ship1.setYPosition(Double.MAX_VALUE);
		ship2.setYPosition(-Double.MAX_VALUE);
		ship1.overlap(ship2);
	}
	
	@Test
	public final void getTimeToCollision_noCollisionTest_Vnonzero(){
		Ship antiVelShip1 = new Ship(-100,-100,0,10,-ship1.getXVelocity(), -ship1.getYVelocity());
		assertEquals(Double.POSITIVE_INFINITY, ship1.getTimeToCollision(antiVelShip1), EPSILON);
	}
	
	@Test
	public final void getTimeToCollision_Vzero(){
		assertEquals(Double.POSITIVE_INFINITY, ship3_100_0.getTimeToCollision(ship4_0_0), EPSILON);
	}
	
	@Test
	public final void getTimeToCollision_headOn(){
		double time = ship5.getTimeToCollision(ship6);
		double distance = ship5.getDistanceBetween(ship6);
		// because the relative speed of ship5 and ship6 is 2
		// the time it takes for ship 5 to reach ship6  equals the distance/2
		assertEquals(distance/2, time, EPSILON);
	}
	
	@Test
	public final void getTimeToCollision_origin(){
		double time = ship7.getTimeToCollision(ship8);
		assertEquals(545.6854249492433, time, EPSILON);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void getTimeToCollison_self(){
		ship7.getTimeToCollision(ship7);
	}
	
	@Test (expected = ArithmeticException.class)
	public final void getTimeToCollision_oveflow_allMax(){
		Ship ship5 = new Ship(Double.MAX_VALUE, Double.MAX_VALUE, 0, 10, Double.MAX_VALUE, Double.MAX_VALUE);
		ship5.getTimeToCollision(ship1);
	}
	
	@Test (expected = ArithmeticException.class)
	public final void getTimeToCollision_oveflow_velMax(){
		Ship ship5 = new Ship(1000, 1000, 0, 10, Double.MAX_VALUE, Double.MAX_VALUE);
		ship5.getTimeToCollision(ship1);
	}
	
	@Test
	public final void getCollisionPosition_headOn(){
		double[] collision = ship6.getCollisionPosition(ship5);
		double[] pos = {0,0};
		assertEquals(pos[0],collision[0], EPSILON);
		assertEquals(pos[1],collision[1], EPSILON);
	}
	
	@Test
	public final void getCollisionPosition_noCollision(){
		double[] collision = ship4_0_0.getCollisionPosition(ship3_100_0);
		assertTrue(null == collision);
	}
}
