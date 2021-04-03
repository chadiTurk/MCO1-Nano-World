package mco1;

public class Alien extends Entity {
	private String front;
	
	public Alien(){
		this.symbol = "A";
		this.front = ">";
	}
	
	@Override
	public String toString() {
		return this.symbol + this.front;
	}
}
