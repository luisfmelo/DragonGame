package logic;

/**
 * Class who creates a new Sword which is an Element
 * @author Luis
 * @author Teresa
 */
public class Sword extends Element {
	private boolean visible = true;
	
	public Sword() {
		this.setLetter('E');
	}
	
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
