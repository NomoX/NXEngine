package nomox.engine3d.component;

public abstract class Component extends Movable implements Drawable {
	private String name;
	
	public Component() {
		super();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
