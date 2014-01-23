package fr.obeo.ecore.fxdiag.app;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

import com.google.common.base.Optional;
import com.google.common.collect.Iterators;

public class Connection extends Parent {

	private static final int DIST_TO_ANCHOR = 50;

	private Group shapeGroup;

	private Optional<DiagNode> source;

	private Optional<DiagNode> target;

	private Text label;

	private ConnectionBinding binding = new ConnectionBinding() {

		@Override
		public Object getRepresentedObject() {
			return ObjectBinding.NULL;
		}

		@Override
		public Object getTarget() {
			return ObjectBinding.NULL;
		}

		@Override
		public Object getSource() {
			return ObjectBinding.NULL;
		}

		@Override
		public String label() {
			return "";
		}
	};

	private Diagram diagram;

	public void bind(ConnectionBinding bind) {
		this.binding = bind;
		this.label.setText(bind.label());
		attachNodes();
	}

	public Connection(Diagram diag) {
		this.shapeGroup = new Group();
		Path curve = new Path();
		this.shapeGroup.getChildren().add(curve);
		this.diagram = diag;
		this.label = new Text();
		this.shapeGroup.getChildren().add(label);
		this.getChildren().add(shapeGroup);
	}

	public void reRoute() {
		if (!this.target.isPresent() || !this.source.isPresent()) {
			/*
			 * let's try to rebind
			 */
			attachNodes();
		}

		if (this.target.isPresent() && this.source.isPresent()) {
			DiagNode actualSource = this.source.get();
			DiagNode actualTarget = this.target.get();
			Point2D start = getAnchorPosition(actualSource, actualTarget);
			Point2D end = getAnchorPosition(actualTarget, actualSource);

			// FIXME quite dangerous
			Path path = Iterators.filter(
					this.shapeGroup.getChildren().iterator(), Path.class)
					.next();
			path.getElements().clear();
			MoveTo moveToStart = new MoveTo(start.getX(), start.getY());

			CubicCurveTo lineToEnd = new CubicCurveTo();
			Point2D sourceControl = getSourceControl(actualSource,
					actualTarget, DIST_TO_ANCHOR);
			Point2D targetControl = getSourceControl(actualTarget,
					actualSource, DIST_TO_ANCHOR);
			lineToEnd.setX(end.getX());
			lineToEnd.setControlX1(sourceControl.getX());
			lineToEnd.setControlY1(sourceControl.getY());
			lineToEnd.setControlX2(targetControl.getX());
			lineToEnd.setControlY2(targetControl.getY());
			lineToEnd.setY(end.getY());
			path.getElements().addAll(moveToStart, lineToEnd);
			path.setStrokeWidth(1);
			path.setStrokeType(StrokeType.INSIDE);
			path.setSmooth(true);
			// path.setStroke(Color.DARKGRAY);

			Point2D labelPosition = getSourceControl(actualSource,
					actualTarget, 20);
			this.label.setLayoutX(labelPosition.getX());
			this.label.setLayoutY(labelPosition.getY());
		}

	}

	private void attachNodes() {
		this.source = diagram.findByRepresentedObject(binding.getSource());
		this.target = diagram.findByRepresentedObject(binding.getTarget());

		// TODO Auto-generated method stub

	}

	private Point2D getSourceControl(DiagNode anchored, DiagNode linked,
			int delta) {
		AnchorBounds anchoredBounds = new AnchorBounds(
				AnchorBounds.localToDiagramBounds(anchored.getPrimaryShape()));
		AnchorBounds linkedBounds = new AnchorBounds(
				AnchorBounds.localToDiagramBounds(linked.getPrimaryShape()));

		Point2D anchorPosition = getAnchorPosition(anchored, linked);

		if (linkedBounds.above(anchoredBounds)) {
			return new Point2D(anchorPosition.getX(), anchorPosition.getY()
					- delta);
		}
		if (linkedBounds.under(anchoredBounds)) {
			return new Point2D(anchorPosition.getX(), anchorPosition.getY()
					+ delta);
		}
		if (linkedBounds.leftOf(anchoredBounds)) {
			return new Point2D(anchorPosition.getX() - delta,
					anchorPosition.getY());
		}
		if (linkedBounds.rightOf(anchoredBounds)) {
			return new Point2D(anchorPosition.getX() + delta,
					anchorPosition.getY());
		}
		// in doubt use the middle point.
		return anchoredBounds.getCenter();
	}

	private Point2D getNewAnchorPosition(DiagNode anchored, DiagNode linked) {
		AnchorBounds anchoredBounds = new AnchorBounds(anchored
				.getPrimaryShape().getBoundsInParent());
		AnchorBounds linkedBounds = new AnchorBounds(linked.getPrimaryShape()
				.getBoundsInParent());
		Point2D anchorPositionInLocal = anchoredBounds.getCenter();
		if (linkedBounds.above(anchoredBounds)) {
			anchorPositionInLocal = anchoredBounds.getTopCenter();
		}
		if (linkedBounds.under(anchoredBounds)) {
			anchorPositionInLocal = anchoredBounds.getBottomCenter();
		}
		if (linkedBounds.leftOf(anchoredBounds)) {
			anchorPositionInLocal = anchoredBounds.getLeftCenter();
		}
		if (linkedBounds.rightOf(anchoredBounds)) {
			anchorPositionInLocal = anchoredBounds.getRightCenter();
		}
		// in doubt use the middle point.
		return AnchorBounds
				.localToDiagramPoint(anchored, anchorPositionInLocal);
	}

	private Point2D getAnchorPosition(DiagNode anchored, DiagNode linked) {
		AnchorBounds anchoredBounds = new AnchorBounds(
				AnchorBounds.localToDiagramBounds(anchored.getPrimaryShape()));
		AnchorBounds linkedBounds = new AnchorBounds(
				AnchorBounds.localToDiagramBounds(linked.getPrimaryShape()));
		if (linkedBounds.above(anchoredBounds)) {
			return anchoredBounds.getTopCenter();
		}
		if (linkedBounds.under(anchoredBounds)) {
			return anchoredBounds.getBottomCenter();
		}
		if (linkedBounds.leftOf(anchoredBounds)) {
			return anchoredBounds.getLeftCenter();
		}
		if (linkedBounds.rightOf(anchoredBounds)) {
			return anchoredBounds.getRightCenter();
		}
		// in doubt use the middle point.
		return anchoredBounds.getCenter();
	}

	@Override
	protected void layoutChildren() {
		reRoute();
		super.layoutChildren();

	}

	public static Connection createDefault(Diagram diag) {
		Connection result = new Connection(diag);
//		 DropShadow dropShadow = new DropShadow();
//		 dropShadow.setRadius(6);
//		 dropShadow.setColor(Color.GREY);
//		 dropShadow.setOffsetX(2);
//		 dropShadow.setOffsetY(1);
//		 result.setEffect(dropShadow);
		return result;
	}

}
