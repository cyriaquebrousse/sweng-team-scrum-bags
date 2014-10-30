package ch.epfl.scrumtool.database;

public interface DatabaseCallback<A> {
	public void interactionDone(A object);
}
