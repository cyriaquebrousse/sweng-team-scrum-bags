package ch.epfl.scrumtool.gui.components;

import java.util.Set;

import ch.epfl.scrumtool.entity.PlayerInterface;
import ch.epfl.scrumtool.entity.ProjectInterface;
import ch.epfl.scrumtool.entity.TaskInterface;
import ch.epfl.scrumtool.entity.UserInterface;

/**
 * Only for demo purposes !
 */
@Deprecated
public class DummyProject implements ProjectInterface {
    private String name;
    private String desc;
    private int changesCount;

    public DummyProject(String name, String desc, int changesCount) {
        this.name = name;
        this.desc = desc;
        this.changesCount = changesCount;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public int getChangesCount(UserInterface user) {
        return changesCount;
    }

	@Override
	public Set<TaskInterface> getBacklog() {
		return null;
	}

	@Override
	public Set<PlayerInterface> getPlayers() {
		return null;
	}
}