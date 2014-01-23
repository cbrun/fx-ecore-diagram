package fr.obeo.ecore.fxdiag.app;

public interface ConnectionBinding extends ObjectBinding{
	
	public Object getSource();
	
	public Object getTarget();

	public String label();

}
