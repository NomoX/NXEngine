package nomox.engine3d;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class Util {
	public static double getTime() {
		return (double)System.nanoTime()/(double)1000000000L;
	}
	public static FloatBuffer toFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.clear();
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	public static IntBuffer toIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.clear();
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	public static ByteBuffer toBytreBuffer(byte[] data) {
		ByteBuffer buffer = BufferUtils.createByteBuffer(data.length);
		buffer.clear();
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	public static void reverseFloatArray(float[] data) {
		for(int i = 0; i < data.length / 2; i++)
		{
		    float temp = data[i];
		    data[i] = data[data.length - i - 1];
		    data[data.length - i - 1] = temp;
		}

	}
	public static void reverseIntArray(int[] data) {
		for(int i = 0; i < data.length / 2; i++)
		{
		    int temp = data[i];
		    data[i] = data[data.length - i - 1];
		    data[data.length - i - 1] = temp;
		}

	}
}
