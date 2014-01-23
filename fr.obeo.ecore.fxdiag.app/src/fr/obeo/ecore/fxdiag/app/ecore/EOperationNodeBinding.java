package fr.obeo.ecore.fxdiag.app.ecore;

import javafx.scene.image.Image;

import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

import fr.obeo.ecore.fxdiag.app.DiagNode;
import fr.obeo.ecore.fxdiag.app.DiagNodeBinding;

public class EOperationNodeBinding implements DiagNodeBinding {

	
	private EOperation eOperation;
	private Function<EParameter, String> toName = new Function<EParameter, String>() {

		@Override
		public String apply(EParameter input) {
			return input.getName() + ":" + input.getEType().getName();
		}
	};

	public EOperationNodeBinding(EOperation eOperation) {
		this.eOperation = eOperation;
	}

	@Override
	public Image icon() {
		return new Image(getClass().getResourceAsStream("EOperation.gif"));
	}

	@Override
	public String label() {
		String result = eOperation.getName();
		result += "("
				+ Joiner.on(",").join(
						Iterables.transform(this.eOperation.getEParameters(),
								toName)) + ")";
		if (eOperation.getEType() != null) {
			result += ":" + eOperation.getEType().getName();
		}
		return result;
	}

	@Override
	public Object getRepresentedObject() {
		return eOperation;
	}

}
