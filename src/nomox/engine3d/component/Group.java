package nomox.engine3d.component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.joml.Vector3f;

public class Group extends Component {
	List<Movable> components;
	public Group() {
		super();
	}
	public Group(Movable... components) {
		super();
		this.components = new LinkedList<>(Arrays.asList(components));
	}
	public void add(Movable component) {
		components.add(component);
	}
	/*
	public void move() {
		components.forEach(e -> {
			float x = super.getPosition().x;
			float y = super.getPosition().y;
			float z = super.getPosition().z;
			e.setPosition(new Vector3f(x, y, z));
		});
	}*/
	@Override
	public void movePosition(float offsetX, float offsetY, float offsetZ) {
		super.movePosition(offsetX, offsetY, offsetZ);
		components.forEach(e -> {
			e.movePosition(offsetX, offsetY, offsetZ);
		});
	}
	@Override
	public void moveRotation(float offsetX, float offsetY, float offsetZ) {
		/*
		super.moveRotation(offsetX, offsetY, offsetZ);
		components.forEach(e -> {
			e.moveRotation(offsetX, offsetY, offsetZ);
		});
		*/
		super.moveRotation(offsetX, offsetY, offsetZ);
		components.forEach(e -> {
			e.movePosition(-super.getPosition().x, -super.getPosition().y, -super.getPosition().z); // move one point on the vector into the origin
			e.moveRotation(offsetX, offsetY, offsetZ); // rotate everything
			e.movePosition(super.getPosition().x, super.getPosition().y, super.getPosition().z);
		});
	}
	@Override
	public void setPosition(Vector3f position) {
		Vector3f pos = position.sub(super.getPosition());
		super.setPosition(position);
		components.forEach(e -> {
			e.movePosition(pos.x, pos.y, pos.z);
		});
	}
	@Override
	public void setRotation(Vector3f rotation) {
		Vector3f rot = rotation.sub(super.getPosition());
		super.setRotation(rotation);
		components.forEach(e -> {
			e.moveRotation(rot.x, rot.y, rot.z);
		});
	}
	@Override
	public void setScale(float scale) {
		super.setScale(scale);
		components.forEach(e -> {
			e.setScale(scale);
		});
	}
	@Override
	public void draw() {
		components.forEach(e -> {
			if (e instanceof Drawable)
				((Drawable)(e)).draw();
		});
	}
}
