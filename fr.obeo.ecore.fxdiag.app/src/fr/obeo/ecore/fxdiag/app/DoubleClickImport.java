package fr.obeo.ecore.fxdiag.app;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class DoubleClickImport implements EventHandler<MouseEvent> {

	private Diagram diag;
	
	private DiagNode host;
	

	public  DoubleClickImport(Diagram diag, DiagNode host) {
		this.diag = diag;
		this.host = host;
	}

	@Override
	public void handle(MouseEvent event) {
		if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
				
			
		}

	}
	
	

}
