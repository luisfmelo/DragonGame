package logic;

/**
 * Class who creates a new Sword which is an {@link logic.Element}
 * @author Luis
 * @author Teresa
 */
public class Sword extends Element {
	private boolean visible = true;
	
	/**
	 * Builder for the Sword
	 */
	public Sword() {
		this.setLetter('E');
	}
	
	/**
	 * Method to check if the sword is visible
	 * @return
	 */
	public boolean isVisible() {
		return visible;
	}
	
	/**
	 * Method to change the Visibility State of this Sword
	 * @return
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
