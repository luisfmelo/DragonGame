package logic;

public class Hero extends Element{
	private boolean armed = false;

	public Hero() {
		// TODO Auto-generated constructor stub
		this.setLetter('H');
	}

	public boolean isArmed() {
		return armed;
	}

	public void setArmed(boolean armed) {
		this.armed = armed;
	}
	
	public void receiveCommand(){
		
	};
	

}
