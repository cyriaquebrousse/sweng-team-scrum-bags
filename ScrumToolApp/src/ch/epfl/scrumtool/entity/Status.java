package ch.epfl.scrumtool.entity;

import ch.epfl.scrumtool.R;

/**
 * @author ketsio
 */
public enum Status {
	READY_FOR_SPRINT(R.color.BlanchedAlmond, "Ready for sprint"),
	IN_SPRINT(R.color.AliceBlue, "In sprint"),
	READY_FOR_ESTIMATION(R.color.Black, "Ready for estimation"),
	FINISHED(R.color.Pink, "Finished");
	
	private int color;
	private String stringValue;
	
	Status(int color, String stringValue) {
	    this.color = color;
	    this.stringValue = stringValue;
	}
	
	/**
	 * @return the color. It is already a R color
	 */
	public int getColor() {
	    return color;
	}
	
	@Override
	public String toString() {
	    return this.stringValue;
	}
}
