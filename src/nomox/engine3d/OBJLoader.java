package nomox.engine3d;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import nomox.engine3d.util.Vector2f;
import nomox.engine3d.util.Vector3f;

public class OBJLoader {
	// спжна
	float[] vertices = null;
	float[] normals = null;
	float[] uv = null;
	int[] indices = null;
	public OBJLoader(File file) {
		FileReader fr = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
		List<Vector3f> verticesList = new ArrayList<Vector3f>();
		List<Vector3f> normalsList = new ArrayList<Vector3f>();
		List<Vector2f> uvList = new ArrayList<Vector2f>();
		List<Integer> indicesList = new ArrayList<Integer>();
		
		String line;
		try {
			while (true) {
				line = br.readLine();
				String[] current = line.split(" ");
				if (line.startsWith("v ")) {
					Vector3f vertex = new Vector3f(Float.parseFloat(current[1]), Float.parseFloat(current[2]), Float.parseFloat(current[3]));
					verticesList.add(vertex);
				} else if (line.startsWith("vn ")) {
					Vector3f normal = new Vector3f(Float.parseFloat(current[1]), Float.parseFloat(current[2]), Float.parseFloat(current[3]));
					normalsList.add(normal);
				} else if (line.startsWith("vt ")) {
					Vector2f texture = new Vector2f(Float.parseFloat(current[1]), Float.parseFloat(current[2]));
					uvList.add(texture);
				} else if (line.startsWith("f ")) {
					 uv = new float[verticesList.size()*2];
					 normals = new float[verticesList.size()*3];
					 break;
				}
			}
			while (line != null) {
				if (!line.startsWith("f ")) {
					line = br.readLine();
					continue;
				}
				String[] current = line.split(" ");
				String[] vertex1 = current[1].split("/");
				String[] vertex2 = current[2].split("/");
				String[] vertex3 = current[3].split("/");
				
				processVertex(vertex1, indicesList, uvList, normalsList);
				processVertex(vertex2, indicesList, uvList, normalsList);
				processVertex(vertex3, indicesList, uvList, normalsList);
				line = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		vertices = new float[verticesList.size()*3];
		indices = new int[indicesList.size()];
		
		int vertexPointer = 0;
		for (Vector3f vertex : verticesList) {
			vertices[vertexPointer++] = vertex.z;
			vertices[vertexPointer++] = vertex.y;
			vertices[vertexPointer++] = vertex.x;
		}
		for (int i = 0; i<indicesList.size(); i++) {
			indices[i] = indicesList.get(i);
		}
	}
	private void processVertex(String[] vertexData, List<Integer> indicesList, List<Vector2f> uvList, List<Vector3f> normalsList) {
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indicesList.add(currentVertexPointer);
		Vector2f currentTex = uvList.get(Integer.parseInt(vertexData[1]) - 1);
		uv[currentVertexPointer*2] = 1.0f - currentTex.y;
		uv[currentVertexPointer*2+1] = currentTex.x; // 1.0f - currentTex.y;
		Vector3f currentNorm = normalsList.get(Integer.parseInt(vertexData[2]) - 1);
		normals[currentVertexPointer*3] = currentNorm.z;
		normals[currentVertexPointer*3+1] = currentNorm.y;
		normals[currentVertexPointer*3+2] = currentNorm.x;
	}
	public float[] getVertices() {
		return vertices;
	}
	public float[] getNormals() {
		return normals;
	}
	public float[] getUV() {
		return uv;
	}
	public int[] getIndices() {
		return indices;
	}
}
