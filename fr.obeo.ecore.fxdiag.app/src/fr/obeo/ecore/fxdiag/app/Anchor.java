package fr.obeo.ecore.fxdiag.app;

import java.util.Collection;

import javafx.scene.Group;
import javafx.scene.shape.Circle;

import com.google.common.collect.Sets;

public class Anchor extends Group {

	private Circle shape ;
	
	private Collection<Connection> connectedTo = Sets.newLinkedHashSet();
	
	public Anchor() {
		shape = new Circle();
		shape.setRadius(5);
		this.getChildren().add(shape);
	}
	
	
	
}
