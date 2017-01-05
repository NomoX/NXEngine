package game;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import nomox.engine3d.Display;
import nomox.engine3d.Engine;
import nomox.engine3d.EngineEvents;
import nomox.engine3d.Keyboard;
import nomox.engine3d.Model;
import nomox.engine3d.RawModel;
import nomox.engine3d.Resources;
import nomox.engine3d.Shader;
import nomox.engine3d.Texture;
import nomox.engine3d.Transformation;
import nomox.engine3d.component.Camera;
import nomox.engine3d.component.Component;
import nomox.engine3d.component.Mesh;

public class Game implements EngineEvents {

	private final static float FOV = 60.0f;
	private final static float Z_NEAR = 0.01f;
	private final static float Z_FAR = 1000.0f;
	
	private final static float MOUSE_SENSITIVITY = 0.08f;

	static Engine engine;
	
	Transformation transformation;
	List<Component> components; 
	
	RawModel m;
	
	Camera camera;
	Shader shader;
	
	Mesh h_maus_hull;
	Mesh h_maus_turret;
	Mesh h_maus_cannon;
	Mesh h_map;
	Mesh h_world_0;
	Texture c_texture;
	float[] positions = new float[] {
			-0.5f, 0.5f, 0.5f,
			-0.5f, -0.5f, 0.5f,
			0.5f, -0.5f, 0.5f,
			0.5f, 0.5f, 0.5f,
			-0.5f, 0.5f, -0.5f,
			0.5f, 0.5f, -0.5f,
			-0.5f, -0.5f, -0.5f,
			0.5f, -0.5f, -0.5f,
		};

	int[] indices = new int[] {
			// Front face
			0, 1, 3, 3, 1, 2,
			// Top Face
			4, 0, 3, 5, 4, 3,
			// Right face
			3, 2, 7, 5, 3, 7,
			// Left face
			0, 1, 6, 4, 0, 6,
			// Bottom face
			6, 1, 2, 7, 6, 2,
			// Back face
			4, 6, 7, 5, 4, 7,
	};
	float[] uvc = new float[] {
		0.0f, 0.0f,
		0.0f, 0.5f,
		0.5f, 0.5f,
		0.5f, 0.0f
	};
	
	public static void main(String[] args) {
		engine = new Engine(new Display(800, 600, "Muus"));
		engine.setEngineEvents(new Game());
		engine.init();
	}
	@Override
	public void init() {
		components = new ArrayList<Component>();
		transformation = new Transformation();
		
		camera = new Camera();
		
		shader = new Shader();
		shader.createVertexShader(Resources.loadAsString("shaders/vertex.glsl"));
		shader.createFragmentShader(Resources.loadAsString("shaders/fragment.glsl"));
		shader.link();
		//shader.createUniform("projectionMatrix");
		//shader.createUniform("modelViewMatrix");
		shader.createUniform("textureSampler");
		shader.createUniform("projectionMatrix");
		shader.createUniform("modelViewMatrix");
		
		m = new RawModel();
		m.loadIndicies(indices);
		m.loadVBO(0, positions, 3);
		m.loadVBO(1, uvc, 2);
		
		h_maus_hull = Loader.createMesh("models/maus_hull.xm", "textures/maus_hull.xt");
		h_maus_turret = Loader.createMesh("models/maus_turret.xm", "textures/maus_turret.xt");
		h_maus_cannon = Loader.createMesh("models/maus_cannon.xm", "textures/maus_cannon.xt");
		
		h_maus_hull.setScale(0.1f);
		h_maus_turret.setScale(0.1f);
		h_maus_cannon.setScale(0.1f);

		h_map = Loader.createMesh("models/map.xm", "textures/map.xt");
		h_map.setScale(1.0f);
		
		h_world_0 = Loader.createMesh("models/world_0.xm", "textures/world_0.xt");
		h_world_0.setScale(0.2f);
		h_world_0.setPosition(new Vector3f(4.0f, 0, -2.0f));
		Mesh h_world_1 = new Mesh(h_world_0);
		Mesh h_world_2 = new Mesh(h_world_0);
		Mesh h_world_3 = new Mesh(h_world_0);
		Mesh h_world_4 = new Mesh(h_world_0);
		
		h_world_1.setPosition(new Vector3f(4.0f, 0, 1.0f));
		h_world_2.setPosition(new Vector3f(-1.0f, 0, -2.0f));
		h_world_3.setPosition(new Vector3f(-3.0f, 0, -2.0f));
		h_world_4.setPosition(new Vector3f(-2.0f, 0, 2.0f));
		
		components.add(h_world_0);
		components.add(h_world_1);
		components.add(h_world_2);
		components.add(h_world_3);
		components.add(h_world_4);
		components.add(h_map);
		components.add(h_maus_hull);
		components.add(h_maus_turret);
		components.add(h_maus_cannon);
		
		camera.setRotation(new Vector3f(60.0f, 0.0f, 0.0f));
		camera.setPosition(new Vector3f(0, 2.0f, 0));
	}
	@Override
	public void destroy() {

	}
	@Override
	public void render() {
		shader.enable();
		
		Matrix4f projectionMatrix = transformation.getProjectionMatrix((float) Math.toRadians(FOV), engine.getDisplay().height, engine.getDisplay().width, Z_NEAR, Z_FAR);
		shader.setUniform("projectionMatrix", projectionMatrix);
		
		Matrix4f viewMatrix = transformation.getViewMatrix(camera);
		// init projection
		shader.setUniform("textureSampler", 0);
		for (Component component : components) {
			Matrix4f modelViewMatrix = transformation.getModelViewMatrix(component, viewMatrix);
			shader.setUniform("modelViewMatrix", modelViewMatrix);
			component.draw();
		}
		
		shader.disable();
	}
	@Override
	public void update(double delta) {
		//System.out.println(delta);
		if (Keyboard.isKeyDown(Keyboard.VK_LEFT))
			h_maus_hull.moveRotation(0, -1.0f, 0);
		if (Keyboard.isKeyDown(Keyboard.VK_RIGHT))
			h_maus_hull.moveRotation(0, 1.0f, 0);
		if (Keyboard.isKeyDown(Keyboard.VK_UP))
			h_maus_hull.moveRotation(-1.0f, 0, 0);
		if (Keyboard.isKeyDown(Keyboard.VK_DOWN))
			h_maus_hull.moveRotation(1.0f, 0, 0);
		
		if (Keyboard.isKeyDown(Keyboard.VK_A))
			camera.movePosition(-0.1f, 0, 0);
		if (Keyboard.isKeyDown(Keyboard.VK_D))
			camera.movePosition(0.1f, 0, 0);
		
		if (Keyboard.isKeyDown(Keyboard.VK_W))
			camera.movePosition(0, 0, -0.1f);
		if (Keyboard.isKeyDown(Keyboard.VK_S))
			camera.movePosition(0, 0, 0.1f);
	}
	@Override
	public void keyPressed(int scancode) {
		//System.out.println(scancode);
	}
	@Override
	public void keyReleased(int scancode) {
		//System.out.println(scancode);
		if (scancode == Keyboard.VK_ESCAPE) {
			engine.close();
		}
	}
	@Override
	public void mouseMove(double x, double y) {
		//System.out.println("x = " + x + " y = " + y);
		//camera.moveRotation((float)x * MOUSE_SENSITIVITY, (float)y * MOUSE_SENSITIVITY , 0);
	}
	@Override
	public void mouseButtonPressed(int button) {
		//System.out.println("mb pressed " + button);
	}
	@Override
	public void mouseButtonRelesed(int button) {
		
	}
	@Override
	public void mouseWheelMoved(double xoffset, double yoffset) {
		//System.out.println("xoffset = " + xoffset + " yoffset = " + yoffset);
		/*
		if (yoffset > 0)
			cube.setScale(cube.getScale()+0.001f);
		if (yoffset < 0)
			cube.setScale(cube.getScale()-0.001f);*/
		if (yoffset > 0)
			camera.movePosition(0, -0.1f, 0);
		if (yoffset < 0)
			camera.movePosition(0, 0.1f, 0);
	}

}
