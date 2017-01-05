package nomox.engine3d;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Resources {
	public static String loadAsString(String path) {
		try {
			File file = new File(path);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			fileReader.close();
			return stringBuffer.toString();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	public static File get(String path) {
		return new File(path);
	}
}
