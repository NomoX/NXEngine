package game;

import nomox.engine3d.Model;
import nomox.engine3d.Resources;
import nomox.engine3d.Texture;
import nomox.engine3d.component.Mesh;

public class Loader {
	public static Mesh createMesh(String modelPath, String texturePath) {
		Model model = new Model();
		model.load(Resources.get(modelPath));
		model.attachTexture(new Texture(Resources.get(texturePath)));
		Mesh mesh = new Mesh(model);
		return mesh;
	}
}
