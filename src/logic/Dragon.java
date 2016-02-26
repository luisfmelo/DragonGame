package logic;

public class Dragon extends Element {

	private boolean dead = false;
	
	public Dragon() {
		// TODO Auto-generated constructor stub
		this.setLetter('D');
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

}
