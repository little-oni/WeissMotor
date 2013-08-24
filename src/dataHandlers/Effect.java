package dataHandlers;
import auxClasses.*;
public class Effect {
	private Chain<Skill> skills;
	public Effect(){
	}
	public int getIndex(){
		return skills.getIndex();
	}
	public Chain<Skill> getSkills(){
		return this.skills;
	}
}
