package ch.epfl.scrumtool.database;

public interface Callback<A> {
	public void interactionDone(A object);
}
