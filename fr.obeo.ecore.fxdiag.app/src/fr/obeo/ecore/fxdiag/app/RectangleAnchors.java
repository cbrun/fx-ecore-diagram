package fr.obeo.ecore.fxdiag.app;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;

public class RectangleAnchors implements Anchors {

	public static double EPSILON = 1e-9;

	private DiagNode host;

	public RectangleAnchors(DiagNode diagNode) {
		this.host = diagNode;
	}

	@Override
	public Anchor getAnchor(double x, double y) {
		return null;
	}

	private void oldCode(double x, double y) {
		Bounds boundsInRootDiagram = localToRootDiagram(host,
				host.getLayoutBounds());
		double centerX = 0.5 * (boundsInRootDiagram.getMinX() + boundsInRootDiagram
				.getMaxX());
		double centerY = 0.5 * (boundsInRootDiagram.getMinY() + boundsInRootDiagram
				.getMaxY());
		NearestPointFinder finder = new NearestPointFinder(x, y);
		if (Math.abs(centerY - y) > EPSILON) {
			double xTop = getXIntersection(boundsInRootDiagram.getMinY(),
					centerX, centerY, x, y);
			if (xTop >= boundsInRootDiagram.getMinX()
					&& xTop <= boundsInRootDiagram.getMaxX())
				;
			finder.addCandidate(xTop, boundsInRootDiagram.getMinY());
			double xBottom = getXIntersection(boundsInRootDiagram.getMaxY(),
					centerX, centerY, x, y);
			if (xBottom >= boundsInRootDiagram.getMinX()
					&& xBottom <= boundsInRootDiagram.getMaxX())
				finder.addCandidate(xBottom, boundsInRootDiagram.getMaxY());
		}
		if (Math.abs(centerX - x) > EPSILON) {
			double yLeft = getYIntersection(boundsInRootDiagram.getMinX(),
					centerX, centerY, x, y);
			if (yLeft >= boundsInRootDiagram.getMinY()
					&& yLeft <= boundsInRootDiagram.getMaxY())
				finder.addCandidate(boundsInRootDiagram.getMinX(), yLeft);
			double yRight = getYIntersection(boundsInRootDiagram.getMaxX(),
					centerX, centerY, x, y);
			if (yRight >= boundsInRootDiagram.getMinY()
					&& yRight <= boundsInRootDiagram.getMaxY())
				finder.addCandidate(boundsInRootDiagram.getMaxX(), yRight);
		}
//		return finder.getCurrentNearest();
	}

	public static Bounds localToRootDiagram(Node node, Bounds bounds) {
		if (node instanceof Diagram)
			return bounds;
		return localToRootDiagram(node.getParent(), node.localToParent(bounds));
	}

	private double getYIntersection(double xIntersection, double centerX,
			double centerY, double x, double y) {
		double t = (xIntersection - centerX) / (x - centerX);
		return (y - centerY) * t + centerY;
	}

	private double getXIntersection(double yIntersection, double centerX,
			double centerY, double x, double y) {
		double t = (yIntersection - centerY) / (y - centerY);
		return (x - centerX) * t + centerX;
	}
}

class NearestPointFinder {

	private double refX;
	private double refY;

	private double currentDistanceSquared = Double.MAX_VALUE;
	private Point2D currentNearest;

	public NearestPointFinder(double refX, double refY) {
		this.refX = refX;
		this.refY = refY;
	}

	public Point2D getCurrentNearest() {
		return currentNearest;
	}

	public void addCandidate(Point2D point) {
		double distanceSquared = (point.getX() - refX) * (point.getX() - refX)
				+ (point.getY() - refY) * (point.getY() - refY);
		if (distanceSquared < currentDistanceSquared) {
			currentDistanceSquared = distanceSquared;
			currentNearest = point;
		}
	}

	public void addCandidate(double px, double py) {
		double distanceSquared = (px - refX) * (px - refX) + (py - refY)
				* (py - refY);
		if (distanceSquared < currentDistanceSquared) {
			currentDistanceSquared = distanceSquared;
			currentNearest = new Point2D(px, py);
		}
	}
}
