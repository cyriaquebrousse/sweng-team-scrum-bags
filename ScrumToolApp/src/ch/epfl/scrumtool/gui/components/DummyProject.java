package ch.epfl.scrumtool.gui.components;

/**
 * Only for demo purposes !
 */
@Deprecated
public class DummyProject {
	private String name;
	private String desc;
	private int changesCount;
	
	public DummyProject(String name, String desc, int changesCount) {
		this.name = name;
		this.desc = desc;
		this.changesCount = changesCount;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public int getChangesCount() {
		return changesCount;
	}
}