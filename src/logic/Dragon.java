package logic;

/**
 * Class who creates a new Dragon which is an Element
 * @author Luis
 * @author Teresa
 */
public class Dragon extends Element {

	private boolean dead = false;
	private boolean sleepy = false;
	
	/**
	 * Builder for the Dragon
	 */
	public Dragon() {
		this.setLetter('D');
	}

	/**
	 * Method to check if the dragon is dead
	 * @return
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * Method to change the Dead State of this Dragon
	 * @return
	 */
	public void setDead(boolean dead) {
		this.dead = dead;
	}

	/**
	 * Method to check if the dragon is sleeping
	 * @return
	 */
	public boolean isSleepy() {
		return sleepy;
	}

	/**
	 * Method to change the Sleepy State of this Dragon
	 * @return
	 */
	public void setSleepy(boolean sleepy) {
		this.sleepy = sleepy;
	}

}
