package fr.obeo.ecore.fxdiag.app.ecore;

import org.eclipse.emf.ecore.EReference;

import fr.obeo.ecore.fxdiag.app.ConnectionBinding;

public class EReferenceEdgeBinding implements ConnectionBinding{

	private EReference ref;

	public EReferenceEdgeBinding(EReference ref) {
		this.ref = ref;
	}

	@Override
	public Object getRepresentedObject() {
		return this.ref;
	}

	@Override
	public Object getSource() {
		return this.ref.getEContainingClass();
	}

	@Override
	public Object getTarget() {
		return this.ref.getEType();
	}

	@Override
	public String label() {
		return this.ref.getName();
	}

}
