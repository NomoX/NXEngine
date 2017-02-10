package nomox.engine3d.component;

import org.joml.Vector3f;

import nomox.engine3d.Resources;
import nomox.engine3d.Shader;

public class SkyBox {
	private Mesh mesh;
	private Shader skyboxShader;
	private Vector3f color;
	public SkyBox(Mesh mesh) {
		super();
		this.mesh = mesh;
		this.mesh.setPosition(new Vector3f(0, 0, 0));
		this.mesh.setScale(1.0f);
		setColor(new Vector3f(1.0f, 1.0f, 1.0f));
		
		initShader();
	}
	
	private void initShader() {
		skyboxShader = new Shader();
		skyboxShader.createVertexShader(Resources.loadAsString("shaders/skybox/vertex.glsl"));
		skyboxShader.createFragmentShader(Resources.loadAsString("shaders/skybox/fragment.glsl"));
		skyboxShader.link();
		
		skyboxShader.createUniform("projectionMatrix");
		skyboxShader.createUniform("modelViewMatrix");
		skyboxShader.createUniform("textureSampler");
		skyboxShader.createUniform("ambientLight");
	}
	public Shader getShader() {
		return skyboxShader;
	}
	public Mesh getMesh() {
		return mesh;
	}

	public Vector3f getColor() {
		return color;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}
	
}
