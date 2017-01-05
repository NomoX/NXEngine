package nomox.engine3d.component;

import org.joml.Vector3f;

public abstract class Component {
	private String name;
	private Vector3f position;
	private Vector3f rotation;
	private float scale;
	public Component() {
		position = new Vector3f(0,0,0);
		rotation = new Vector3f(0,0,0);
		scale = 1.0f;
	}
	public Vector3f getPosition() {
		return position;
	}
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	public Vector3f getRotation() {
		return rotation;
	}
	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}
	public float getScale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}
	public void moveRotation(float offsetX, float offsetY, float offsetZ) {
		rotation.x += offsetX;
		rotation.y += offsetY;
		rotation.z += offsetZ;
	}
	public abstract void draw();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
