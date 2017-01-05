package nomox.engine3d;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;

import java.io.File;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import nomox.engine3d.xdf.XDFile;

public class Texture {
	private int textureID;
	private int width, height;
	private String name;
	public Texture() {
		textureID = GL11.glGenTextures();
		// color = texture(textureSampler, UV).rgb;
	}
	public Texture(File file) {
		textureID = GL11.glGenTextures();
		load(file);
	}
	public void load(File file) {
		ByteBuffer pixels = getTexture(file);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, getWidth(), getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
		GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR); // GL_NEAREST
		GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR); // GL_LINEAR
		
		GL11.glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		
		generateMipmap();
	}
	public void generateMipmap() {
		GL30.glGenerateMipmap(GL_TEXTURE_2D);
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
	private ByteBuffer getTexture(File file) {
		XDFile xdf = new XDFile(file);
		if (xdf.getBlockType() == XDFile.TEXTURE_NAME) {
			this.name = xdf.getAsString(XDFile.STATIC_16);
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
		return Util.toBytreBuffer(buffer);
	}
	public void destroy() {
		GL11.glDeleteTextures(textureID);
	}
	protected void activate() {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
	}
}
