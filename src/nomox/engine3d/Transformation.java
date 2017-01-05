package nomox.engine3d;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import nomox.engine3d.component.Camera;
import nomox.engine3d.component.Component;

public class Transformation {
	private final Matrix4f projectionMatrix;
	private final Matrix4f viewMatrix;
	private final Matrix4f modelViewMatrix;
	
	public Transformation() {
		projectionMatrix = new Matrix4f();
		viewMatrix = new Matrix4f();
		modelViewMatrix = new Matrix4f();
	}
	public final Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
		float aspectRatio = width / height;
		projectionMatrix.identity();
		projectionMatrix.perspective(fov, aspectRatio, zNear, zFar);
		return projectionMatrix;
	}
	/*
	public Matrix4f getWorldMatrix(Vector3f offset, Vector3f rotation, float scale) {
		Matrix4f world = new Matrix4f();
		world.identity().translate(offset).
		rotateX((float)Math.toRadians(rotation.x)).
		rotateY((float)Math.toRadians(rotation.y)).
		rotateZ((float)Math.toRadians(rotation.z)).
		scale(scale);
		return world;
	}*/

	public Matrix4f getViewMatrix(Camera camera) {
		Vector3f cameraPos = camera.getPosition();
		Vector3f rotation = camera.getRotation();
		viewMatrix.identity();
		viewMatrix.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
			.rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
		viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		return viewMatrix;
	}
	public Matrix4f getModelViewMatrix(Component component, Matrix4f viewMatrix) {
		Vector3f rotation = component.getRotation();
		modelViewMatrix.identity().translate(component.getPosition())
			.rotateX((float)Math.toRadians(-rotation.x))
			.rotateY((float)Math.toRadians(-rotation.y))
			.rotateZ((float)Math.toRadians(-rotation.z))
			.scale(component.getScale());
		
		Matrix4f viewCurr = new Matrix4f(viewMatrix);
		return viewCurr.mul(modelViewMatrix);
	}
}
