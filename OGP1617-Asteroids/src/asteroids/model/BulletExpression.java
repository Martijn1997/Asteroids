package asteroids.model;

import java.util.Set;
import java.util.stream.Collectors;

import asteroids.part3.programs.SourceLocation;

public class BulletExpression extends Expression<Bullet, Bullet>{
	
	public BulletExpression(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	/**
	 * returns one of the own fired bullets if any,
	 * if no fired bullets exist within the world, return null
	 */
	public Bullet evaluate(){
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
		Set<Bullet> bullets = world.getAllBullets();
		Set<Bullet> ownBullets =  bullets.stream().filter(bullet -> self == bullet.getShip()).collect(Collectors.toSet());
		
		
		for(Bullet bullet: ownBullets){
			return bullet;
		}
		
		return null;
		
	}
	
	public String toString(){
		return "Bullet";
	}
}
