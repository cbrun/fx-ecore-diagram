package fr.obeo.ecore.fxdiag.app.ecore;

import javafx.scene.image.Image;

import org.eclipse.emf.ecore.EClassifier;

import fr.obeo.ecore.fxdiag.app.DiagNodeBinding;

public class EClassNodeBinding implements DiagNodeBinding {

	private EClassifier eclass;

	public EClassNodeBinding(EClassifier clazz) {
		this.eclass = clazz;
	}

	@Override
	public Image icon() {
		return EcoreIcons.INSTANCE.ECLASS;
	}

	@Override
	public String label() {
		return eclass.getName();
	}

	@Override
	public Object getRepresentedObject() {
		return eclass;
	}

}
