package Rogue;

public class Effect implements Cloneable{

	protected int duration;
	
	public boolean isDone(){
		return duration <1;
	}
	
	public Effect(int duration){
		this.duration = duration;
	}
	
	public void update(Creature creature){
		duration--;
	}
	
	public void start(Creature creature){
		
	}
	
	public void end(Creature creature){
		
	}
	
	public Effect(Effect other){
		this.duration = other.duration;
	}
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) { //should never happen
			throw new InternalError(e.toString());
		}
	}
}
