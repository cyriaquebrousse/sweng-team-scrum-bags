package ch.epfl.scrumtool.entity;

import ch.epfl.scrumtool.R;

/**
 * @author ketsio
 */
public enum Status {
	READY_FOR_SPRINT(R.color.BlanchedAlmond),
	IN_SPRINT(R.color.AliceBlue),
	READY_FOR_ESTIMATION(R.color.Black),
	FINISHED(R.color.Pink);
	
	private int color;
	
	Status(int color) {
	    this.color = color;
	}
	
	/**
	 * @return the color. It is already a R color
	 */
	public int getColor() {
	    return color;
	}
}
