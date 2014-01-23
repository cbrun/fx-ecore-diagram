package fr.obeo.ecore.fxdiag.app;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class MoveBehavior {

	private DragContext dragContext;
	private Node host;

	public MoveBehavior(Node host) {
		this.host = host;
	}

	private EventHandler<MouseEvent> mousePressed = new EventHandler<MouseEvent>() {

		public void handle(MouseEvent event) {
			Point2D initialPositionInScene = host.getParent().localToScene(
					host.getLayoutX(), host.getLayoutY());
			dragContext = new DragContext(event.getScreenX(),
					event.getScreenY(), initialPositionInScene);
		}

	};
	private EventHandler<MouseEvent> mouseDragged = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			Point2D newPositionInScene = new Point2D(
					dragContext.initialPosInScene.getX() + event.getScreenX()
							- dragContext.mouseAnchorX,
					dragContext.initialPosInScene.getY() + event.getScreenY()
							- dragContext.mouseAnchorY);
			Point2D newPositionInDiagram = host.getParent().sceneToLocal(
					newPositionInScene);
			if (newPositionInDiagram != null) {
				host.setLayoutX(newPositionInDiagram.getX());
				host.setLayoutY(newPositionInDiagram.getY());
			}

		}
	};

	public void activate() {
		host.setOnMousePressed(mousePressed);
		host.setOnMouseDragged(mouseDragged);
	}

	class DragContext {
		public double mouseAnchorX;
		public double mouseAnchorY;
		public Point2D initialPosInScene;

		public DragContext(double mouseAnchorX, double mouseAnchorY,
				Point2D initialPosInScene) {
			super();
			this.mouseAnchorX = mouseAnchorX;
			this.mouseAnchorY = mouseAnchorY;
			this.initialPosInScene = initialPosInScene;
		}

	};
}
