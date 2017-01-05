package nomox.engine3d.component;

import nomox.engine3d.Model;

public class Mesh extends Component {
	Model model;
	public Mesh(Model model) {
		super();
		this.model = model;
	}
	public Mesh(Mesh mesh) {
		super();
		this.model = mesh.getModel();
		this.setPosition(mesh.getPosition());
		this.setRotation(mesh.getRotation());
		this.setScale(mesh.getScale());
		this.setName(mesh.getName());
	}
	@Override
	public void draw() {
		model.draw();
	}
	public Model getModel() {
		return model;
	}
}
