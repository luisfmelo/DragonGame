package logic;

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
