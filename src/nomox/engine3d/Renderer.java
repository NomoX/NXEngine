package nomox.engine3d;

import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import nomox.engine3d.component.Camera;
import nomox.engine3d.component.Component;
import nomox.engine3d.component.SkyBox;

public class Renderer {
	
	public float FOV = 60.0f;
	public float Z_NEAR = 0.01f;
	public float Z_FAR = 1000.0f;
	
	List<Component> components;
	SkyBox skybox;
	Display display;
	Camera camera;
	Shader shader;
	Shader skyboxShader;
	
	Vector3f lightPos;
	Vector3f lightColor;
	
	Transformation transformation;
	
	public Renderer() {
		transformation = new Transformation();
	}
	
	public void render() {
		
		shader.enable();
		// display width
		//light
		shader.setUniform("lightPos", lightPos);
		shader.setUniform("lightColor", lightColor);
		
		shader.setUniform("textureSampler", 0);
		Matrix4f projectionMatrix = transformation.getProjectionMatrix((float) Math.toRadians(FOV), display.height, display.height, Z_NEAR, Z_FAR);
		shader.setUniform("projectionMatrix", projectionMatrix);
		
		Matrix4f viewMatrix = transformation.getViewMatrix(camera);
		// init projection
		
		for (Component component : components) {
			Matrix4f modelViewMatrix = transformation.getModelViewMatrix(component, viewMatrix);
			shader.setUniform("modelViewMatrix", modelViewMatrix);
			component.draw();
		}
		
		shader.disable();
		
		renderSkybox();
		
	}
	
	private void renderSkybox() {
		skyboxShader.enable();
		
		skyboxShader.setUniform("textureSampler", 0);
		// Update projection Matrix
		Matrix4f projectionMatrix = transformation.getProjectionMatrix((float) Math.toRadians(FOV), getDisplay().height, getDisplay().height, Z_NEAR, Z_FAR);
		skyboxShader.setUniform("projectionMatrix", projectionMatrix);
		Matrix4f viewMatrix = transformation.getViewMatrix(camera);

		viewMatrix.m30(0);
		viewMatrix.m31(0);
		viewMatrix.m32(0);
		
		Matrix4f modelViewMatrix = transformation.getModelViewMatrix(skybox.getMesh(), viewMatrix);
		skyboxShader.setUniform("modelViewMatrix", modelViewMatrix);
		skyboxShader.setUniform("ambientLight", skybox.getColor());


		skybox.getMesh().draw();
		
		skyboxShader.disable();
	}

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	public SkyBox getSkybox() {
		return skybox;
	}

	public void setSkybox(SkyBox skybox) {
		this.skybox = skybox;
		skyboxShader = skybox.getShader();
	}

	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Shader getShader() {
		return shader;
	}

	public void setShader(Shader shader) {
		this.shader = shader;
	}

	public float getFOV() {
		return FOV;
	}

	public void setFOV(float fOV) {
		FOV = fOV;
	}

	public float getZ_NEAR() {
		return Z_NEAR;
	}

	public void setZ_NEAR(float z_NEAR) {
		Z_NEAR = z_NEAR;
	}

	public float getZ_FAR() {
		return Z_FAR;
	}

	public void setZ_FAR(float z_FAR) {
		Z_FAR = z_FAR;
	}

	public Vector3f getLightPos() {
		return lightPos;
	}

	public void setLightPos(Vector3f lightPos) {
		this.lightPos = lightPos;
	}

	public Vector3f getLightColor() {
		return lightColor;
	}

	public void setLightColor(Vector3f lightColor) {
		this.lightColor = lightColor;
	}
	
}
