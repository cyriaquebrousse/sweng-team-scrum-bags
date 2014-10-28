package ch.epfl.scrumtool.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Vincent
 * 
 */
public final class User {

    private String username;
    private String email;
    private String name;
    private long token;
    private final Set<Project> projects;

    /**
     * @param username
     * @param email
     * @param name
     * @param token
     * @param projects
     */
    public User(String username, String email, String name, long token,
            Set<Project> projects) {
        super();
        if (username == null || email == null || name == null
                || projects == null) {
            throw new NullPointerException("User.Constructor");
        }
        this.username = username;
        this.email = email;
        this.name = name;
        this.token = token;
        this.projects = new HashSet<Project>();
        for (Project p : projects) {
            this.projects.add(new Project(p));
        }
    }

    /**
     * @param aUser
     */
    public User(User aUser) {
        this(aUser.getUsername(), aUser.getEmail(), aUser.getName(), aUser
                .getToken(), aUser.getProjects());
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        if (username != null) {
            this.username = username;
        }
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        if (email != null) {
            // TODO check email is valid email format?
            this.email = email;
        }
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
        if (name != null) {
            this.name = name;
        }
    }

    /**
     * @return the token
     */
    public long getToken() {
        return token;
    }

    /**
     * @param token
     *            the token to set
     */
    public void setToken(long token) {
        this.token = token;
    }

    /**
     * @return the projects
     */
    public Set<Project> getProjects() {
        HashSet<Project> tmp = new HashSet<Project>();
        for (Project p : projects) {
            tmp.add(new Project(p));
        }
        return tmp;
    }

    /**
     * @param project
     *            the project to ad
     */
    public void addProject(Project project) {
        if (project != null) {
            this.projects.add(new Project(project));
        }
    }

    /**
     * @param project
     *            the project to remove
     */
    public void removeProject(Project project) {
        if (project != null) {
            this.projects.remove(project);
        }
    }

    /**
     * @param user
     * @return
     */
    public List<Project> getProjectsSharedWith(User user) {
        // TODO Retrieve from Database + javadoc
        ArrayList<Project> projects = new ArrayList<>();
        projects.add(Entity.COOL_PROJECT);
        projects.add(Entity.SUPER_PROJECT);
        return projects;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User other = (User) o;
        if (!other.getEmail().equals(this.getEmail())) {
            return false;
        }
        if (!other.getName().equals(this.getName())) {
            return false;
        }
        if (!other.getProjects().equals(this.getProjects())) {
            return false;
        }
        if (!other.getUsername().equals(this.getUsername())) {
            return false;
        }
        if (other.getToken() != this.getToken()) {
            return false;
        }
        return true;
    }
}
