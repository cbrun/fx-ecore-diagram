package fr.obeo.ecore.fxdiag.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class DiagNode extends StackPane {

	private VBox innerChilds;
	private VBox attributeCompartment;
	private VBox operationCompartment;

	private Text label;
	private Rectangle shape;

	private Anchors anchors;
	private DiagNodeBinding binding = new DiagNodeBinding() {

		@Override
		public Image icon() {
			return null;
		}

		@Override
		public String label() {
			return "Some Name";
		}

		@Override
		public Object getRepresentedObject() {
			return DiagNodeBinding.NULL;
		}
	};
	private ImageView icon;

	public DiagNode() {
		setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		shape = new Rectangle();
		shape.setFill(defaultFill.getFill());
		shape.setArcWidth(8);
		shape.setArcHeight(8);
		shape.setStroke(Color.BLACK);

		HBox titleBox = new HBox();

		label = new Text();
		label.setTextOrigin(VPos.TOP);
		icon = new ImageView(binding.icon());

		titleBox.setAlignment(Pos.CENTER);

		titleBox.getChildren().addAll(icon, label);
		innerChilds = new VBox();
		innerChilds.setAlignment(Pos.TOP_CENTER);

		getChildren().add(shape);
		setMargin(shape, new Insets(0));

		getChildren().add(innerChilds);
		attributeCompartment = new VBox();
		attributeCompartment.setAlignment(Pos.CENTER);
		operationCompartment = new VBox();

		innerChilds.getChildren().add(titleBox);
		innerChilds.getChildren().add(new Separator());
		innerChilds.getChildren().add(attributeCompartment);
		innerChilds.getChildren().add(new Separator());
		innerChilds.getChildren().add(operationCompartment);

		innerChilds.setMargin(titleBox, new Insets(8, 8, 8, 8));
		innerChilds.setMargin(attributeCompartment, new Insets(5, 10, 5, 10));
		innerChilds.setMargin(operationCompartment, new Insets(5, 10, 5, 10));

		anchors = new RectangleAnchors(this);
		setCacheHint(CacheHint.QUALITY);
		setCache(true);
	}

	public void bind(DiagNodeBinding binding) {
		this.binding = binding;
		this.label.setText(binding.label());
		this.icon.setImage(binding.icon());
	}

	public DiagNodeBinding getBinding() {
		return binding;
	}

	public VBox getAttributeCompartment() {
		return attributeCompartment;
	}

	public VBox getOperationsCompartment() {
		return operationCompartment;
	}

	public Node getPrimaryShape() {
		return shape;
	}

	public void setLabel(String val) {
		label.setText(val);
	}

	@Override
	protected void layoutChildren() {
		this.shape.setWidth(getWidth());
		this.shape.setHeight(getHeight());
		super.layoutChildren();

	}

	private static Paint backgroundPaint = new LinearGradient(0, 0, 1, 1, true,
			CycleMethod.NO_CYCLE, new Stop[] { new Stop(0, Color.WHITE),
					new Stop(1, DiagColors.NODE_BACKGROUND) });

	public static final BackgroundFill defaultFill = new BackgroundFill(
			backgroundPaint, null, null);

	public static DiagNode create() {
		DiagNode node = new DiagNode();
		node.setLabel("Some Name");

//		InnerShadow inShadow = new InnerShadow();
//		inShadow.setRadius(3);
//		DropShadow dropShadow = new DropShadow();
//		dropShadow.setRadius(3);
//		dropShadow.setColor(Color.GREY);
//		dropShadow.setInput(inShadow);
//		dropShadow.setOffsetX(2);
//		dropShadow.setOffsetY(1);
//		node.setEffect(dropShadow);

		return node;
	}

	public Anchors getAnchors() {
		return anchors;
	}

}
