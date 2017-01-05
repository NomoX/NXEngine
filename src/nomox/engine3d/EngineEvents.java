package nomox.engine3d;

public interface EngineEvents {
	public void init();
	public void destroy();
	public void render();
	public void update(double delta);
	public void keyPressed(int scancode);
	public void keyReleased(int scancode);
	public void mouseMove(double x, double y);
	public void mouseButtonPressed(int button);
	public void mouseButtonRelesed(int button);
	public void mouseWheelMoved(double xoffset, double yoffset);
}