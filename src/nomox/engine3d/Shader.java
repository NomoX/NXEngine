package nomox.engine3d;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

public class Shader {
	private int program;
	private int vertexShaderID;
	private int fragmentShaderID;
	private final Map<String, Integer> uniforms;

	public Shader() {
		uniforms = new HashMap<>();
		program = GL20.glCreateProgram();
		if (program == 0)
			throw new RuntimeException("glCreateProgram error");
	}
	public Shader(String sourceVertex, String sourceFragment) {
		uniforms = new HashMap<>();
		program = GL20.glCreateProgram();
		if (program == 0)
			throw new RuntimeException("glCreateProgram error");
		
		createVertexShader(sourceVertex);
		createFragmentShader(sourceFragment);
	}
	public void createVertexShader(String source) {
		vertexShaderID = createShader(source, GL20.GL_VERTEX_SHADER);
	}
	public void createFragmentShader(String source) {
		fragmentShaderID = createShader(source, GL20.GL_FRAGMENT_SHADER);
	}
	
	public void createUniform(String name) {
		GL20.glUseProgram(program);
		int uniformLocation = GL20.glGetUniformLocation(program, name);
		if (uniformLocation < 0) {
			System.err.println("Could not find uniform: " + name);
		}
		uniforms.put(name, uniformLocation);
		GL20.glUseProgram(0);
	}
	public void setUniform(String name, Matrix4f value) {
		FloatBuffer fb = BufferUtils.createFloatBuffer(16);
		value.get(fb);
		//fb.flip(); // сыџџџџџџџџ
		GL20.glUniformMatrix4fv(uniforms.get(name), false, fb);
	}
	public void setUniform(String name, int value) {
		GL20.glUniform1i(uniforms.get(name), value);
	}
	public void setUniform(String name, Vector3f value) {
		GL20.glUniform3f(uniforms.get(name), value.x, value.y, value.z);
	}
	
	private int createShader(String source, int shaderType) {
		int shaderID = GL20.glCreateShader(shaderType);
		if (shaderID == 0)
			throw new RuntimeException("glCreateShader error");
		GL20.glShaderSource(shaderID, source);
		GL20.glCompileShader(shaderID);
		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == 0)
			throw new RuntimeException("Error: shader compilation. Code: " + GL20.glGetShaderInfoLog(shaderID, 1024));
		
		GL20.glAttachShader(program, shaderID);
		return shaderID;
	}
	public void link() {
		GL20.glLinkProgram(program);
		if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == 0)
			throw new RuntimeException("Error: shader linking. Code: " + GL20.glGetProgramInfoLog(program, 1024));
		GL20.glValidateProgram(program);
		if (GL20.glGetProgrami(program, GL20.GL_VALIDATE_STATUS) == 0)
			System.err.println("Warning: shader validation. Code: " + GL20.glGetProgramInfoLog(program, 1024));
	}
	public void delete() {
		disable();
		if (program != 0) {
			if (vertexShaderID != 0)
				GL20.glDetachShader(program, vertexShaderID);
			if (fragmentShaderID != 0)
				GL20.glDetachShader(program, fragmentShaderID);
			GL20.glDeleteProgram(program);
		}
	}
	public void enable() {
		GL20.glUseProgram(program);
	}
	public void disable() {
		GL20.glUseProgram(0);
	}
}
