package fr.obeo.ecore.fxdiag.app;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class NodeListItem extends HBox {

	private Text label;
	private ImageView icon;

	private DiagNodeBinding binding = new DiagNodeBinding() {

		@Override
		public String label() {
			return "name : EString";
		}

		@Override
		public Image icon() {
			return null;
		}

		@Override
		public Object getRepresentedObject() {
			return DiagNodeBinding.NULL;
		}
	};

	public NodeListItem() {
		label = new Text();
		icon = new ImageView();
		label.setPickOnBounds(true);
		label.setTextOrigin(VPos.CENTER);
		label.setFont(Font.font(12));
		setAlignment(Pos.BOTTOM_LEFT);
		getChildren().addAll(icon, label);
		updateFromModel();
		setCache(true);
		setCacheHint(CacheHint.QUALITY);
	}

	private void updateFromModel() {
		label.setText(binding.label());
		icon.setImage(binding.icon());
	}

	public void bind(DiagNodeBinding binding) {
		this.binding = binding;
		updateFromModel();
	}
}
