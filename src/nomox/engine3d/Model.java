package nomox.engine3d;

import java.io.File;

import nomox.engine3d.xdf.XDFile;
import objloader.ModelData;
import objloader.OBJFileLoader;

public class Model {
	private RawModel rawModel;
	private Texture texture;
	private String name;
	public Model() {
		rawModel = new RawModel();
	}
	public Model(RawModel rawModel) {
		this.rawModel = rawModel;
	}
	public void load(File file) {
		float[] vertices = null;
		float[] normals = null;
		float[] uv = null;
		int[] indices = null;
		
		XDFile xdf = new XDFile(file);
		if (xdf.getBlockType() == XDFile.MODEL_NAME) {
			this.name = xdf.getAsString(XDFile.STATIC_16);
		} else {
			System.err.println("XDF error MODEL_NAME expected");
			return;
		}
		//System.out.println(this.name);
		while (!xdf.eof()) {
			switch (xdf.getBlockType()) {
			case XDFile.MODEL_VERTICES:
				vertices = xdf.getAsFloatArray(xdf.getIntBlockSize());
				break;
			case XDFile.MODEL_NORMAL:
				normals = xdf.getAsFloatArray(xdf.getIntBlockSize());
				break;
			case XDFile.MODEL_UV:
				uv = xdf.getAsFloatArray(xdf.getIntBlockSize());
				break;
			case XDFile.MODEL_INDICES:
				indices = xdf.getAsIntArray(xdf.getIntBlockSize());
				break;
			}
		}
		rawModel.loadIndicies(indices);
		rawModel.loadVBO(0, vertices, 3);
		rawModel.loadVBO(1, uv, 2);
		rawModel.loadVBO(2, normals, 3);
	}
	public void loadOBJ(File file) {
		ModelData data = OBJFileLoader.loadOBJ(file);
		
		rawModel.loadVBO(0, data.getVertices(), 3);
		rawModel.loadVBO(1, data.getTextureCoords(), 2);
		rawModel.loadVBO(2, data.getNormals(), 3);
		rawModel.loadIndicies(data.getIndices());
	}
	public void draw() {
		texture.activate();
		rawModel.draw();
	}
	public void destroy() {
		texture.destroy();
		rawModel.destroy();
	}
	public String getName() {
		return this.name;
	}
	public Texture getTexture() {
		return texture;
	}
	public void attachTexture(Texture texture) {
		this.texture = texture;
	}
}
