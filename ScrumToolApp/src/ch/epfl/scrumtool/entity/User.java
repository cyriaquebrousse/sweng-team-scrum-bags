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
    public User(String username, String email, String name, long token,
            Set<Project> projects) {
        super();
        if (username == null || email == null || name == null
                || projects == null) {
            throw new NullPointerException("User.Constructor");
        }
        this.mUsername = username;
        this.mEmail = email;
        this.mName = name;
        this.mToken = token;
        this.mProjects = new HashSet<Project>();
        for (Project p : projects) {
            mProjects.add(new Project(p));
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
        return mUsername;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        if (username != null) {
            this.mUsername = username;
        }
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
        if (email != null) {
            // TODO check email is valid email format?
            this.mEmail = email;
        }
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
        if (name != null) {
            this.mName = name;
        }
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
        HashSet<Project> tmp = new HashSet<Project>();
        for (Project p : mProjects) {
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
            this.mProjects.add(new Project(project));
        }
    }

    /**
     * @param project
     *            the project to remove
     */
    public void removeProject(Project project) {
        if (project != null) {
            this.mProjects.remove(project);
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
}
