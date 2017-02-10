package nomox.engine3d;

import org.lwjgl.opengl.*;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class RawModel {
	//private int atributesCount = 0;
	private int vao;
	private List<Integer> vboList;
	private int vboi;
	private int indices_size;
	
	public RawModel() {
		vboList = new ArrayList<Integer>();
		vao = GL30.glGenVertexArrays();
	}
	public void loadVBO(int atribute, float[] data, int size) {
		int vbo = GL15.glGenBuffers();
		GL30.glBindVertexArray(vao);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		//GL15.glBufferData(GL15.GL_ARRAY_BUFFER, Util.toFloatBuffer(data), GL15.GL_STATIC_DRAW);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(atribute, size, GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
		vboList.add(vbo);
	}
	/*
	public void loadVBO(int atribute, int[] data) {
		//storeAtribute(0, data);
		int vbo = GL15.glGenBuffers();
		GL30.glBindVertexArray(vao);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, toIntBuffer(data), GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(atribute, 3, GL_INT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
		vboList.add(vbo);
	}*/
	public void loadIndicies(int[] indices) {
		vboi = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboi);
		//GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.toIntBuffer(indices), GL15.GL_STATIC_DRAW);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		indices_size = indices.length;
	}
	public void destroy() {
		//GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(vao);
		for (int i : vboList)
			GL15.glDeleteBuffers(i);
	}
	public int getVao() {
		return vao;
	}
	public void draw() {
		GL30.glBindVertexArray(vao);
		GL20.glEnableVertexAttribArray(0); // врубаєм модельку
		GL20.glEnableVertexAttribArray(1); // врубаєм текстурки
		GL20.glEnableVertexAttribArray(2); // врубаєм нормалі
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboi);

		GL11.glDrawElements(GL_TRIANGLES, indices_size, GL_UNSIGNED_INT, 0); // Т-34 нє прабіл сук

		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
}
