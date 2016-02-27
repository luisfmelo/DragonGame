package logic;

public class Dragon extends Element {

	private boolean dead = false;
	private boolean sleepy = false;
	
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

	public boolean isSleepy() {
		return sleepy;
	}

	public void setSleepy(boolean sleepy) {
		this.sleepy = sleepy;
	}

}
