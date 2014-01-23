package fr.obeo.ecore.fxdiag.app;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class Selectable {

	private Node host;
	private InnerShadow mouseOverEffect;
	private DropShadow selectionEffect;
	private Effect originalEffect;

	public EventHandler<MouseEvent> onMouseEntered = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			originalEffect = host.getEffect();
			host.getScene().setCursor(Cursor.HAND);
			host.setEffect(mouseOverEffect);
		}
	};

	public EventHandler<MouseEvent> onMouseExit = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			host.setEffect(originalEffect);
			host.getScene().setCursor(Cursor.DEFAULT);
			originalEffect = null;

		}
	};

	public EventHandler<MouseEvent> onMouseClic = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			if (event.getClickCount() == 1
					&& event.getButton() == MouseButton.PRIMARY) {

			}
			// originalEffect = host.getEffect();
			// host.setEffect(selectionEffect);
		}
	};

	public Selectable(Node host) {
		this.host = host;
		this.selectionEffect = new DropShadow();
		this.selectionEffect.setRadius(6);
		this.selectionEffect.setOffsetX(2);
		this.selectionEffect.setOffsetY(1);
		this.mouseOverEffect = new InnerShadow();
		this.mouseOverEffect.setRadius(14);
	}

	public void activate() {
		this.host.setOnMouseEntered(onMouseEntered);
		this.host.setOnMouseExited(onMouseExit);
		this.host.setOnMouseClicked(onMouseClic);
	}

}
