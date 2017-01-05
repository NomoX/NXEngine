package nomox.engine3d.xdf;

public class XDFUtil {
	public static int byteArrayToInt(byte[] bytes) {
		if (bytes.length != XDFile.D_4) {
			System.err.println("XDF 4 bytes expected");
			return 0;
		}
		int res = (bytes[0] & 0xFF) 
	            | ((bytes[1] & 0xFF) << 8) 
	            | ((bytes[2] & 0xFF) << 16) 
	            | ((bytes[3] & 0xFF) << 24);
		return res;
	}
	public static short byteArrayToShort(byte[] bytes) {
		if (bytes.length != XDFile.D_2) {
			System.err.println("XDF 2 bytes expected");
			return 0;
		}
		short res = (short)((bytes[0] & 0xFF) | ((bytes[1] & 0xFF) << 8));
		return res;
	}
	public static byte[] intToByteArray(int i) {
		byte[] bytes = new byte[XDFile.D_4];
		int j = 0;
		bytes[j++] = (byte)(i & 0xFF);
		bytes[j++] = (byte)((i >> 8) & 0xFF);
		bytes[j++] = (byte)((i >> 16) & 0xFF);
		bytes[j++] = (byte)((i >> 24) & 0xFF);
		return bytes;
	}
	public static byte[] shortToByteArray(short i) {
		byte[] bytes = new byte[XDFile.D_2];
		int j = 0;
		bytes[j++] = (byte)(i & 0xFF);
		bytes[j++] = (byte)((i >> 8) & 0xFF);
		return bytes;
	}
}
