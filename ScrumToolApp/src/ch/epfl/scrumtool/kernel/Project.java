/**
 * 
 */
package kernel;

import java.util.Set;

/**
 * @author Vincent
 * 
 */
public class Project {

    private BackLog backlog;
    private Set<Player> contributors;
    private String name;
    private String description;
    private Player admin;

    /**
     * @param backlog
     * @param contributors
     * @param name
     * @param description
     * @param admin
     */
    public Project(BackLog backlog, Set<Player> contributors, String name,
            String description, Player admin) {
        super();
        this.backlog = backlog;
        this.contributors = contributors;
        this.name = name;
        this.description = description;
        this.admin = admin;
    }

    /**
     * @return the backlog
     */
    public BackLog getBacklog() {
        return backlog;
    }

    /**
     * @param backlog
     *            the backlog to set
     */
    public void setBacklog(BackLog backlog) {
        this.backlog = backlog;
    }

    /**
     * @return the contributors
     */
    public Set<Player> getContributors() {
        return contributors;
    }

    /**
     * @param contributors
     *            the contributors to set
     */
    public void setContributors(Set<Player> contributors) {
        this.contributors = contributors;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the admin
     */
    public Player getAdmin() {
        return admin;
    }

    /**
     * @param admin
     *            the admin to set
     */
    public void setAdmin(Player admin) {
        this.admin = admin;
    }

}
