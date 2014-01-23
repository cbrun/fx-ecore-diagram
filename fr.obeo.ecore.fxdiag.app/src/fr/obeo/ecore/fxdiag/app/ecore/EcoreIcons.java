package fr.obeo.ecore.fxdiag.app.ecore;

import javafx.scene.image.Image;

public class EcoreIcons {

	public static EcoreIcons INSTANCE = new EcoreIcons();

	public Image ECLASS = new Image(getClass()
			.getResourceAsStream("EClass.gif"));
	public Image EOPERATION = new Image(getClass().getResourceAsStream(
			"EOperation.gif"));
	public Image EATTRIBUTE = new Image(getClass().getResourceAsStream(
			"EAttribute.gif"));
}
