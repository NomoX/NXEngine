package nomox.engine3d;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Engine {
	private static GLFWErrorCallback errorCallback;
	private static long windowID;
	private Display display;
	private EngineEvents engineEvents;
	
	private double lastFrame;
	
	public Engine(Display display) {
		this.display = display;
	}
	public void setEngineEvents(EngineEvents engineEvents) {
		this.engineEvents = engineEvents;
	}
	public void init() {
		// init error callback	
		errorCallback = GLFWErrorCallback.createPrint(System.err);
		glfwSetErrorCallback(errorCallback);
		// init GLFW
		boolean result = glfwInit();
		if (!result)
		throw new IllegalStateException("GLFW init failed!");
		// create window
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		
		windowID = glfwCreateWindow(getDisplay().width, getDisplay().height, getDisplay().title, MemoryUtil.NULL, MemoryUtil.NULL);
		if (windowID == MemoryUtil.NULL)
		throw new IllegalStateException("Failed to create window!");	
		
		// set callbacks
		// keyboard listener
		glfwSetKeyCallback(windowID, (window, key, scancode, action, mods) -> {
			if (action == GLFW_PRESS) {
				Keyboard.currentKey = key;
				engineEvents.keyPressed(key);
			}
			if (action == GLFW_RELEASE) {
				Keyboard.currentKey = -1;
				engineEvents.keyReleased(key);
			}
			/*
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true);*/
		});
		// mouse position
		glfwSetCursorPosCallback(windowID, (window, x, y) -> {
			engineEvents.mouseMove(x, y);
		});
		// mouse button
		glfwSetMouseButtonCallback(windowID, (window, button, action, mods) -> {
			if (action == GLFW_PRESS)
				engineEvents.mouseButtonPressed(button);
			if (action == GLFW_RELEASE)
				engineEvents.mouseButtonRelesed(button);
		});
		// scroll 
		glfwSetScrollCallback(windowID, (window, xoffset, yoffset) -> {
			engineEvents.mouseWheelMoved(xoffset, yoffset);
		});
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		// center of screen	
		glfwSetWindowPos(windowID, (vidmode.width() - getDisplay().width) / 2, (vidmode.height() - getDisplay().height) / 2);
				
		glfwMakeContextCurrent(windowID);
		glfwSwapInterval(1);
		glfwShowWindow(windowID);
				
		GL.createCapabilities();

		
		GL11.glFrontFace(GL_CW);
		glEnable(GL_DOUBLEBUFFER);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glDepthFunc(GL_LESS);
		
		engineEvents.init();
		getDelta();
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f); 
		while (!glfwWindowShouldClose(windowID)) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear sceen
			//render
			double delta = getDelta();
			engineEvents.update(delta);
			engineEvents.render();
			
            glfwSwapBuffers(windowID);
			glfwPollEvents();
		}
		glfwDestroyWindow(windowID);
		glfwTerminate();
		engineEvents.destroy();
	}
	public void close() {
		glfwSetWindowShouldClose(windowID, true);
	}
	public Display getDisplay() {
		return display;
	}
	
	//
	public double getDelta() {
		double time = Util.getTime();
		double delta = (time - lastFrame);
		lastFrame = time;
		return delta;
	}
}
