package nomox.engine3d.xdf;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class XDFile {
	/* File structure
	 * signature 6 bytes XGraph
	 * version 1 byte
	 * reserved 3 bytes
	 * datablock [B_TYPE]
	 */
	public final static byte XDF_VER = 0x01;
	
	public final static byte EOF = 0x00;
	
	public final static byte STATIC_2 = 2;
	public final static byte STATIC_4 = 4;
	public final static byte STATIC_8 = 8;
	public final static byte STATIC_16 = 16;
	public final static byte STATIC_32 = 32;
	public final static byte STATIC_64 = 64;
	
	public final static byte D_2 = 2;
	public final static byte D_4 = 4;
	
	public final static byte TEXTURE_NAME = 0x20; // STATIC_16
	public final static byte TEXTURE_RGB = 0x21; // D_32
	public final static byte TEXTURE_RGBA = 0x22; // D_32
	public final static byte TEXTURE_SIZE = 0x23; // STATIC_4
	
	public final static byte MODEL_NAME = 0x10;  // STATIC_16
	public final static byte MODEL_VERTICES = 0x11; // D_32
	public final static byte MODEL_NORMAL = 0x12; // D_32
	public final static byte MODEL_UV = 0x13; // D_32
	public final static byte MODEL_INDICES = 0x14; // D_32
	public final static byte MODEL_USETEX = 0x15;  // STATIC_16

	private byte version;
	private BufferedInputStream bs;

	public XDFile(File file) {
		try {
			bs = new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String signature = new String(getAsByteArray(6)); // signature
		if (!signature.equals("XGraph"))
			System.err.println("XDF: file corrupt");
		
		version = getAsByteArray(1)[0]; // version
		if (version != XDF_VER)
			System.err.println("XDF: version mismatch");
		getAsByteArray(3); // reserver
	}
	public boolean eof() {
		try {
			return bs.available() <= 0;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	public void close() {
		try {
			bs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public byte getBlockType() {
		return getAsByteArray(1)[0]; // block type
	}
	
	public float[] getAsFloatArray(int size) {
		byte[] bytes = getAsByteArray(size);
		int dataSize = bytes.length/4;
		float[] data = new float[dataSize];
		for (int i = 0; i < dataSize; i++) {
			int d = (bytes[4*i] & 0xFF) 
		            | ((bytes[4*i+1] & 0xFF) << 8) 
		            | ((bytes[4*i+2] & 0xFF) << 16) 
		            | ((bytes[4*i+3] & 0xFF) << 24);
			data[i] = Float.intBitsToFloat(d);
		}
		return data;	
	}
	public int[] getAsIntArray(int size) {
		byte[] bytes = getAsByteArray(size);
		int dataSize = bytes.length/4;
		int[] data = new int[dataSize];
		for (int i = 0; i < dataSize; i++) {
			data[i] = (bytes[4*i] & 0xFF) 
		            | ((bytes[4*i+1] & 0xFF) << 8) 
		            | ((bytes[4*i+2] & 0xFF) << 16) 
		            | ((bytes[4*i+3] & 0xFF) << 24);
		}
		return data;	
	}
	public byte[] getAsByteArray(int size) {
		byte[] data = new byte[size];
		try {
			bs.read(data, 0, size);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	public String getAsString(int size) {
		return new String(getAsByteArray(size));
	}
	public int getIntBlockSize() {
		return XDFUtil.byteArrayToInt(getAsByteArray(D_4));
	}
	public int getShortBlockSize() {
		return XDFUtil.byteArrayToShort(getAsByteArray(D_2));
	}
}
