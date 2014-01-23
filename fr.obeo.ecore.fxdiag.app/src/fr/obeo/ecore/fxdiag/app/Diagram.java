package fr.obeo.ecore.fxdiag.app;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Region;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;

public class Diagram extends Region {

	// private ObservableList<DiagConnection> connections = FXCollections
	// .observableArrayList();
	
	

	private Group nodeLayer = new Group();

	private Group edgeLayer = new Group();

	public Diagram() {
		getChildren().add(edgeLayer);
		getChildren().add(nodeLayer);
	}

	@Override
	protected void layoutChildren() {
		super.layoutChildren();
		// /*
		// * we might want to re-route the connections here. Not sure if that's
		// the best place as it tends to be called quite often.
		// */
		for (Connection edge : Iterables.filter(edgeLayer.getChildren(),
				Connection.class)) {
			edge.requestLayout();
		}
	}
	

	public void addNode(Node n) {
		nodeLayer.getChildren().add(n);
	}

	public Optional<DiagNode> findByRepresentedObject(Object representedObject) {
		for (DiagNode children : Iterables.filter(nodeLayer.getChildren(),
				DiagNode.class)) {
			if (children.getBinding().getRepresentedObject() == representedObject)
				return Optional.of(children);
		}
		return Optional.absent();
	}

	public void addConnection(Connection con) {
		edgeLayer.getChildren().add(con);
	}

}
