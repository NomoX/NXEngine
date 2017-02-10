package nomox.engine3d;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;

import java.io.File;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

import nomox.engine3d.xdf.XDFile;

public class CubeMap {
	private int textureID;
	private int width, height;
	private String name;
	
	public CubeMap() {
		textureID = GL11.glGenTextures();
	}
	public CubeMap(File file) {
		textureID = GL11.glGenTextures();
		load(file);
	}
	public void load(File file) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, textureID);
		ByteBuffer[] buffers = getCubeMapTexture(file);
		for (int i = 0; i < buffers.length; i++) {
			GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL11.GL_RGBA,
					getWidth(), getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffers[i]);
		}
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
	}
	
	private ByteBuffer[] getCubeMapTexture(File file) {
		ByteBuffer[] buffers = new ByteBuffer[6];
		for (int i = 0; i < 6; i++) {
			XDFile xdf = new XDFile(file);
			if (xdf.getBlockType() == XDFile.TEXTURE_NAME) {
				this.setName(xdf.getAsString(XDFile.STATIC_16));
			} else {
				System.err.println("XDF error TEXTURE_NAME expected");
				return null;
			}
			if (xdf.getBlockType() == XDFile.TEXTURE_SIZE) {
				this.width = xdf.getShortBlockSize();
				this.height = xdf.getShortBlockSize();
			} else {
				System.err.println("XDF error TEXTURE_SIZE expected");
				return null;
			}
			byte[] buffer;
			if (xdf.getBlockType() == XDFile.TEXTURE_RGBA) {
				buffer = xdf.getAsByteArray(xdf.getIntBlockSize());
			} else {
				System.err.println("XDF error TEXTURE_RGBA expected");
				return null;
			}
			buffers[i] = Util.toBytreBuffer(buffer);
		}
		return buffers;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getTexture() {
		return textureID;
	}
}
