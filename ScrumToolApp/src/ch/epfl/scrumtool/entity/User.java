package ch.epfl.scrumtool.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Vincent
 * 
 */
public class User {

    private String mUsername;
    private String mEmail;
    private String mName;
    private long mToken;
    private final Set<Project> mProjects;

    /**
     * @param username
     * @param email
     * @param name
     * @param token
     * @param projects
     */
    public User(long id, String username, String email, String name,
            long token, Set<Project> projects) {
        super();
        this.mUsername = username;
        this.mEmail = email;
        this.mName = name;
        this.mToken = token;
        this.mProjects = new HashSet<Project>(projects);
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return mUsername;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.mUsername = username;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.mEmail = email;
    }

    /**
     * @return the name
     */
    public String getName() {
        return mName;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.mName = name;
    }

    /**
     * @return the token
     */
    public long getToken() {
        return mToken;
    }

    /**
     * @param token
     *            the token to set
     */
    public void setToken(long token) {
        this.mToken = token;
    }

    /**
     * @return the projects
     */
    public Set<Project> getProjects() {
        return mProjects;
    }

    public List<Project> getProjectsSharedWith(User user) {
        // TODO Retrieve from Database + javadoc
        ArrayList<Project> projects = new ArrayList<>();
        projects.add(Entity.COOL_PROJECT);
        projects.add(Entity.SUPER_PROJECT);
        return projects;
    }
}
