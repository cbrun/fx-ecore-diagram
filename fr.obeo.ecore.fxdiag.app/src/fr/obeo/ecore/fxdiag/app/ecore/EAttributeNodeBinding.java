package fr.obeo.ecore.fxdiag.app.ecore;

import javafx.scene.image.Image;

import org.eclipse.emf.ecore.EAttribute;

import fr.obeo.ecore.fxdiag.app.DiagNodeBinding;

public class EAttributeNodeBinding implements DiagNodeBinding {

	private EAttribute eAttr;

	public EAttributeNodeBinding(EAttribute eAttribute) {
		this.eAttr = eAttribute;
	}

	@Override
	public Image icon() {
		return EcoreIcons.INSTANCE.EATTRIBUTE;
	}

	@Override
	public String label() {
		return eAttr.getName() + " : " + eAttr.getEType().getName();
	}

	@Override
	public Object getRepresentedObject() {
		return eAttr;
	}

}
