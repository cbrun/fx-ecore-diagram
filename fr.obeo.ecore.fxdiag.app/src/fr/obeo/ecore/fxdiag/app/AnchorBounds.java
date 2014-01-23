package fr.obeo.ecore.fxdiag.app;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;

public class AnchorBounds {

	private Bounds bounds;

	public AnchorBounds(Bounds bnd) {
		this.bounds = bnd;
	}

	public Point2D getCenter() {
		return new Point2D(getTopLeftCorner().getX() + bounds.getWidth() / 2,
				getTopLeftCorner().getY() + bounds.getHeight() / 2);
	}

	public Point2D getTopCenter() {
		return new Point2D(bounds.getMinX() + bounds.getWidth() / 2,
				bounds.getMinY());

	}

	public Point2D getRightCenter() {
		return new Point2D(bounds.getMaxX(), bounds.getMinY()
				+ bounds.getHeight() / 2);
	}

	public Point2D getLeftCenter() {
		return new Point2D(bounds.getMinX(), bounds.getMinY()
				+ bounds.getHeight() / 2);
	}

	public Point2D getBottomCenter() {
		return new Point2D(bounds.getMinX() + bounds.getWidth() / 2,
				bounds.getMaxY());

	}

	public Point2D getTopLeftCorner() {
		return new Point2D(bounds.getMinX(), bounds.getMinY());

	}

	public Bounds getBounds() {
		return bounds;
	}

	public boolean above(AnchorBounds other) {
		return bounds.getMinY() < other.getBounds().getMinY();
	}

	public boolean under(AnchorBounds other) {
		return bounds.getMaxY() > other.getBounds().getMaxY()
				+ other.getBounds().getHeight();
	}

	public boolean leftOf(AnchorBounds other) {
		return bounds.getMinX() < other.getBounds().getMaxX();
	}

	public boolean rightOf(AnchorBounds other) {
		return bounds.getMaxX() > other.getBounds().getMinX()
				+ other.getBounds().getWidth();
	}

	public static Bounds localToDiagramBounds(Node any) {
		return getDiagramBounds(any.getParent(), any.getBoundsInLocal());

	}
	
	public static Point2D localToDiagramPoint(Node any,Point2D local) {
		return getDiagramPoints(any.getParent(), local);

	}

	private static Point2D getDiagramPoints(Node parent, Point2D boundsInLocal) {
		if (parent instanceof Diagram) {
			return parent.localToParent(boundsInLocal);
		}
		return getDiagramPoints(parent.getParent(), parent.localToParent(boundsInLocal));
	}
	
	private static Bounds getDiagramBounds(Node parent, Bounds boundsInLocal) {
		if (parent instanceof Diagram) {
			return parent.localToParent(boundsInLocal);
		}
		return getDiagramBounds(parent.getParent(), parent.localToParent(boundsInLocal));
	}
}
