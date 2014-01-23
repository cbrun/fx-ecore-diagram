package fr.obeo.ecore.fxdiag.app;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.fx.osgi.util.AbstractJFXApplication;

import fr.obeo.ecore.fxdiag.app.ecore.EAttributeNodeBinding;
import fr.obeo.ecore.fxdiag.app.ecore.EClassNodeBinding;
import fr.obeo.ecore.fxdiag.app.ecore.EOperationNodeBinding;
import fr.obeo.ecore.fxdiag.app.ecore.EReferenceEdgeBinding;

public class MainApplication extends AbstractJFXApplication {
	protected void jfxStart(IApplicationContext applicationContext,
			Application jfxApplication, Stage stage) {

		
	
		
		ScrollPane scroller = new ScrollPane();
		scroller.setPrefViewportWidth(800);
		scroller.setPrefViewportHeight(600);
		final Diagram diagRoot = new Diagram();
		diagRoot.setOpacity(0.0);

		Scene scene = new Scene(scroller, 500, 500, Color.WHITE);
		stage.setTitle("JavaFX Scene Graph Demo");
		stage.setScene(scene);
		stage.setWidth(800);
		stage.setHeight(600);		
		stage.show();

		openDiag(scroller, diagRoot);

	}

	private void openDiag(ScrollPane scroller, final Diagram diagRoot) {
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(6);
		dropShadow.setColor(Color.GREY);
		dropShadow.setOffsetX(2);
		dropShadow.setOffsetY(1);

//		
		
		final double SCALE_DELTA = 1.1;
		final StackPane zoomPane = new StackPane();

		zoomPane.getChildren().add(diagRoot);
		zoomPane.setOnScroll(new EventHandler<ScrollEvent>() {
		  @Override public void handle(ScrollEvent event) {
		    event.consume();

		    if (event.getDeltaY() == 0) {
		      return;
		    }

		    double scaleFactor =
		      (event.getDeltaY() > 0)
		        ? SCALE_DELTA
		        : 1/SCALE_DELTA;

		    diagRoot.setScaleX(diagRoot.getScaleX() * scaleFactor);
		    diagRoot.setScaleY(diagRoot.getScaleY() * scaleFactor);
		  }
		});
		scroller.setContent(zoomPane);

		for (EClassifier eClass : EcorePackage.eINSTANCE.getEClassifiers()) {
			if (eClass instanceof EClass) {
				DiagNode n = DiagNode.create();
				Selectable policy = new Selectable(n);
				policy.activate();
				MoveBehavior move = new MoveBehavior(n);
				move.activate();
				n.bind(new EClassNodeBinding(eClass));
				diagRoot.addNode(n);
				for (EReference ref : ((EClass) eClass).getEReferences()) {
					Connection con = Connection.createDefault(diagRoot);
					con.bind(new EReferenceEdgeBinding(ref));
					diagRoot.addConnection(con);
				}

				for (EAttribute attr : ((EClass) eClass).getEAttributes()) {
					NodeListItem item = new NodeListItem();
					item.bind(new EAttributeNodeBinding(attr));
					n.getAttributeCompartment().getChildren().add(item);

				}

				for (EOperation op : ((EClass) eClass).getEOperations()) {
					NodeListItem item = new NodeListItem();
					item.bind(new EOperationNodeBinding(op));
					n.getOperationsCompartment().getChildren().add(item);

				}

			}
		}

		
		
		FadeTransition ft = new FadeTransition(Duration.millis(3000), diagRoot);
		ft.setInterpolator(Interpolator.EASE_BOTH);
		ft.setDelay(Duration.millis(1000));
		ft.setFromValue(0.0);
		ft.setToValue(1);
		ft.play();
	}
}
