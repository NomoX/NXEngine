package game;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import nomox.engine3d.Display;
import nomox.engine3d.Engine;
import nomox.engine3d.EngineEvents;
import nomox.engine3d.Keyboard;
import nomox.engine3d.RawModel;
import nomox.engine3d.Renderer;
import nomox.engine3d.Resources;
import nomox.engine3d.Shader;
import nomox.engine3d.Texture;
import nomox.engine3d.Transformation;
import nomox.engine3d.component.Camera;
import nomox.engine3d.component.Component;
import nomox.engine3d.component.Group;
import nomox.engine3d.component.Mesh;
import nomox.engine3d.component.Movable;
import nomox.engine3d.component.SkyBox;

public class Game implements EngineEvents {

	private final static float FOV = 60.0f;
	private final static float Z_NEAR = 0.01f;
	private final static float Z_FAR = 1000.0f;
	
	private final static float MOUSE_SENSITIVITY = 0.08f;

	static Engine engine;
	
	Transformation transformation;
	List<Component> components; 
	
	RawModel m;
	
	Renderer renderer;
	Camera camera;
	Shader shader;
	
	Mesh h_maus_hull;
	Mesh h_maus_turret;
	Mesh h_maus_cannon;
	Mesh h_map;
	
	Mesh h_map_0;
	Mesh h_map_1;
	
	Mesh h_world_0;
	
	Group tank;
	
	SkyBox skybox;
	
	Vector3f lightPos;
	Vector3f lightColor;
	
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
		
		shader.createUniform("lightPos");
		shader.createUniform("lightColor");
		
		m = new RawModel();
		m.loadIndicies(indices);
		m.loadVBO(0, positions, 3);
		m.loadVBO(1, uvc, 2);
		
		skybox = new SkyBox(Loader.createMesh("models/skybox.xm", "textures/skybox.xt"));
		
		h_maus_hull = Loader.createMesh("models/maus_hull.xm", "textures/maus_hull.xt");
		h_maus_turret = Loader.createMesh("models/maus_turret.xm", "textures/maus_turret.xt");
		h_maus_cannon = Loader.createMesh("models/maus_cannon.xm", "textures/maus_cannon.xt");
		
		h_maus_hull.setScale(0.1f);
		h_maus_turret.setScale(0.1f);
		h_maus_cannon.setScale(0.1f);

		h_map_0 = Loader.createMesh("models/map_0.xm", "textures/map_0.xt");
		h_map_1 = Loader.createMesh("models/map_1.xm", "textures/map_1.xt");
		h_map = h_map_0;
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
		/*
		components.add(h_maus_hull);
		components.add(h_maus_turret);
		components.add(h_maus_cannon);*/
		
		h_maus_hull.setRotation(new Vector3f(-10.0f, 0, 0));
		tank = new Group(h_maus_hull, h_maus_turret, h_maus_cannon);
		//tank.add(camera); // camera
		components.add(tank);
		//tank.setScale(scale);
		tank.setScale(0.1f);
		camera.setRotation(new Vector3f(0.0f, -90.0f, 0.0f));
		camera.setPosition(new Vector3f(0.0f, 0.4f, 0.0f));
		
		renderer = new Renderer();
		renderer.setCamera(camera);
		renderer.setDisplay(engine.getDisplay());
		renderer.setComponents(components);
		renderer.setShader(shader);
		renderer.setSkybox(skybox);
		
		lightColor = new Vector3f(1.0f, 1.0f, 1.0f);
		lightPos = new Vector3f(1.0f, 1.0f, 1.0f);
		
		renderer.setLightColor(lightColor);
		renderer.setLightPos(lightPos);
		
		skybox.setColor(lightColor);
		
		engine.setRenderer(renderer);
	}
	@Override
	public void destroy() {

	}
	@Override
	public void render() {
		/*
		shader.enable();
		// display width
		Matrix4f projectionMatrix = transformation.getProjectionMatrix((float) Math.toRadians(FOV), engine.getDisplay().height, engine.getDisplay().height, Z_NEAR, Z_FAR);
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
		*/
	}
	/* ÀÕÒÓÍÃ! */
	void moveAround(Movable point, Movable e, Vector3f rotation) {
		e.movePosition(-point.getPosition().x, -point.getPosition().y, -point.getPosition().z); // move one point on the vector into the origin
		e.moveRotation(rotation.x, rotation.y, rotation.z); // rotate everything
		e.movePosition(point.getPosition().x, point.getPosition().y, point.getPosition().z);
	}
	@Override
	public void update(double delta) {
		skybox.getMesh().moveRotation(0, 0.005f, 0); // move skybox
		//skybox.setColor(skybox.getColor().sub(0.001f, 0.001f, 0.001f));
		//System.out.println(delta);
		if (Keyboard.isKeyDown(Keyboard.VK_A)) {
			tank.moveRotation(0, -1.0f, 0); // h_maus_hull
			camera.moveRotation(0, -1.0f, 0);
		}
		if (Keyboard.isKeyDown(Keyboard.VK_D)) {
			tank.moveRotation(0, 1.0f, 0);
			camera.moveRotation(0, 1.0f, 0);
		}

		
		if (Keyboard.isKeyDown(Keyboard.VK_W)) {
			tank.movePosition(-0.01f, 0, 0);
			camera.movePosition(0, 0, -0.01f);
		}
		if (Keyboard.isKeyDown(Keyboard.VK_S)) {
			tank.movePosition(0.01f, 0, 0);
			camera.movePosition(0, 0, 0.01f);
		}
		
		if (Keyboard.isKeyDown(Keyboard.VK_UP)) {
			//camera.moveRotation(-1.0f, 0, 0);
			lightPos = lightPos.sub(0.0f, 0.0f, 0.1f);
		}
		if (Keyboard.isKeyDown(Keyboard.VK_DOWN)) {
			//camera.moveRotation(1.0f, 0, 0);
			lightPos = lightPos.sub(0.0f, 0.0f, -0.1f);
		}
		
		if (Keyboard.isKeyDown(Keyboard.VK_LEFT)) {
			lightPos = lightPos.sub(0.1f, 0.0f, 0.0f);
		}
		if (Keyboard.isKeyDown(Keyboard.VK_RIGHT)) {
			lightPos = lightPos.sub(-0.1f, 0.0f, 0.0f);
		}
		
		if (Keyboard.isKeyDown(Keyboard.VK_PAGE_UP)) {
			lightPos = lightPos.sub(0.0f, -0.1f, 0.0f);
		}
		if (Keyboard.isKeyDown(Keyboard.VK_PAGE_DOWN)) {
			lightPos = lightPos.sub(0.0f, 0.1f, 0.0f);
		}
		// light color
		if (Keyboard.isKeyDown(Keyboard.VK_LBRACKET)) {
			lightColor = lightColor.sub(0.01f, 0.01f, 0.01f);
		}
		if (Keyboard.isKeyDown(Keyboard.VK_RBRACKET)) {
			lightColor = lightColor.add(0.01f, 0.01f, 0.01f);
		}
		// map changing
		if (Keyboard.isKeyDown(Keyboard.VK_1)) {
			components.remove(h_map);
			h_map = h_map_0;
			components.add(h_map);
		}
		if (Keyboard.isKeyDown(Keyboard.VK_2)) {
			components.remove(h_map);
			h_map = h_map_1;
			components.add(h_map);
		}
		
		if (Keyboard.isKeyDown(Keyboard.VK_NUMPAD4)) {
			h_maus_turret.movePosition(1.0f, 0, 0);
		}

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
