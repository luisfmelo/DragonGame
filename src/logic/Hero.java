package logic;

/**
 * Class who creates a new Hero which is an {@link logic.Element}
 * @author Luis
 * @author Teresa
 */
public class Hero extends Element{
	private boolean armed = false;
	private boolean dead = false;
	
	/**
	 * Builder for the Hero
	 */
	public Hero() {
		this.setLetter('H');
	}

	/**
	 * Method to check if the Hero is Armed
	 * @return
	 */
	public boolean isArmed() {
		return armed;
	}
	
	/**
	 * Method to change the Arm or disarm the Hero
	 * @return
	 */
	public void setArmed(boolean armed) {
		this.armed = armed;
	}
	
	/**
	 * Method to check if the Hero is dead
	 * @return
	 */
	public boolean isDead() {
		return dead;
	}
	
	/**
	 * Method to change the Dead State of this Hero
	 * @return
	 */
	public void setDead(boolean dead) {
		this.dead = dead;
	};
}
