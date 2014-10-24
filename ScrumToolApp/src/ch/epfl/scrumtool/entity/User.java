package ch.epfl.scrumtool.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Vincent
 */
public final class User implements UserInterface {

    private long token;
    private String name;
    private String username;
    private String email;
    private final Set<ProjectInterface> projects;

    /**
     * @param username
     * @param email
     * @param name
     * @param token
     */
    public User(long token, String name, String username, String email,
            Set<ProjectInterface> projects) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.name = name;
        this.projects = projects;
    }

    @Override
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public long getId() {
        return token;
    }

    /**
     * @param token
     */
    public void setToken(long token) {
        this.token = token;
    }

    @Override
    public Set<ProjectInterface> getProjects() {
        return projects;
    }

    @Override
    public List<ProjectInterface> getProjectsSharedWith(UserInterface user) {
        // TODO Retrieve from Database
        ArrayList<ProjectInterface> projects = new ArrayList<>();
        projects.add(new DummyProject("projet 1", "desc", 3));
        projects.add(new DummyProject("projet 2", "desc 2", 0));
        return projects; 
    }

}
