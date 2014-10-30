package ch.epfl.scrumtool.entity;

public interface DatabaseCallback<A> {
	public void interactionDone(A object);
}
