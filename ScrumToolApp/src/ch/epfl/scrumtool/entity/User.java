package ch.epfl.scrumtool.entity;

import java.util.HashSet;
import java.util.Set;

import com.google.api.client.util.Key;

/**
 * @author vincent, aschneuw, zenhaeus
 * 
 */
public class User implements DatabaseInteraction<User> {
    private final String mEmail;
    private final String mName;
    private final Set<Key> mProjectKeys;

    /**
     * @param email
     * @param name
     * @param projects
     */
    public User(String email, String name, Set<Key> projectKeys) {
        if (email == null || name == null || projectKeys == null) {
            throw new NullPointerException("User.Constructor");
        }
        
        this.mEmail = email;
        this.mName = name;
        this.mProjectKeys = new HashSet<Key>(projectKeys);
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return mEmail;
    }


    /**
     * @return the name
     */
    public String getName() {
        return mName;
    }
    
    /**
     * @return the projects
     */
    public Set<Key> getProjectsKeys() {
        return new HashSet<Key>(mProjectKeys);
    }
    
    public static class Builder {
    	private String mEmail;
        private String mName;
        private Set<Key> mProjectKeys;
    	
        
        Builder() {
        	this.mProjectKeys = new HashSet<Key>();
        }
        
        Builder(User otherUser) {
        	this.mEmail = otherUser.mEmail;
        	this.mName = otherUser.mName;
        	this.mProjectKeys = new HashSet<Key>(otherUser.mProjectKeys);
        }
        
        /**
         * @return the email
         */
        public String getEmail() {
            return mEmail;
        }
        

        public void setEmail(String email) {
            mEmail = email;
        }


        /**
         * @return the name
         */
        public String getName() {
            return mName;
        }
        
        public void setName(String name) {
        	mName = name;
        }
        
        /**
         * @return the projects
         */
        public Set<Key> getProjectsKeys() {
            return new HashSet<Key>(mProjectKeys);
        }
        
        public void addProjectKey(Key k) {
        	mProjectKeys.add(k);
        }
        
        public void removeProjectKey(Key k) {
        	mProjectKeys.remove(k);
        }
        
        public User build() {
        	return new User(this.mEmail, this.mName, this.mProjectKeys);
        }
    	
    }

	/* (non-Javadoc)
	 * @see ch.epfl.scrumtool.entity.DatabaseInteraction#updateDatabase(java.lang.Object, ch.epfl.scrumtool.entity.DatabaseHandler)
	 */
	@Override
	public void updateDatabase(User reference, DatabaseHandler<User> handler) {
		handler.update(reference);
	}

	/* (non-Javadoc)
	 * @see ch.epfl.scrumtool.entity.DatabaseInteraction#deleteFromDatabase(ch.epfl.scrumtool.entity.DatabaseHandler)
	 */
	@Override
	public void deleteFromDatabase(DatabaseHandler<User> handler) {
		handler.remove(this);
	}
    

}
