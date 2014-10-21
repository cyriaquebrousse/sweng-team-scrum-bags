/**
 * 
 */
package kernel;

/**
 * @author Vincent
 * 
 */
public class User {
    private String username;
    private String email;
    private String name;
    private int token;

    /**
     * @param username
     * @param email
     * @param name
     * @param token
     */
    public User(String username, String email, String name, int token) {
        super();
        this.username = username;
        this.email = email;
        this.name = name;
        this.token = token;
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
        this.username = username;
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
        this.email = email;
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
     * @return the token
     */
    public int getToken() {
        return token;
    }

    /**
     * @param token
     *            the token to set
     */
    public void setToken(int token) {
        this.token = token;
    }

}
