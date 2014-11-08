/**
 * 
 */
package ch.epfl.scrumtool.database;

/**
 * @author aschneuw
 *
 */
public final class DBHandlers {
    private final IssueHandler issueH;
    private final MainTaskHandler mainTaskH;
    private final PlayerHandler playerH;
    private final ProjectHandler projectH;
    private final SprintHandler sprintH;
    private final UserHandler userH;
    
    private DBHandlers(DBHandlers.Builder builder) {
        issueH = builder.iH;
        mainTaskH = builder.mH;
        playerH = builder.playerH;
        projectH = builder.projectH;
        sprintH = builder.sprintH;
        userH = builder.uH;
    }
    
    /**
     * @return the issueH
     */
    public IssueHandler getIssueHandler() {
        return issueH;
    }

    /**
     * @return the mainTaskH
     */
    public MainTaskHandler getMainTaskHandler() {
        return mainTaskH;
    }

    /**
     * @return the playerH
     */
    public PlayerHandler getPlayerHandler() {
        return playerH;
    }

    /**
     * @return the projectH
     */
    public ProjectHandler getProjectHandler() {
        return projectH;
    }

    /**
     * @return the sprintH
     */
    public SprintHandler getSprintHandler() {
        return sprintH;
    }


    /**
     * @return the userH
     */
    public UserHandler getUserHandler() {
        return userH;
    }

/**
 * 
 * @author aschneuw
 *
 */
    public static class Builder {
        private IssueHandler iH;
        private MainTaskHandler mH;
        private PlayerHandler playerH;
        private ProjectHandler projectH;
        private SprintHandler sprintH;
        private UserHandler uH;
        /**
         * @return the iH
         */
        public IssueHandler getIssueHandler() {
            return iH;
        }
        /**
         * @param iH the iH to set
         */
        public void setIssueHandler(IssueHandler iH) {
            this.iH = iH;
        }
        /**
         * @return the mH
         */
        public MainTaskHandler getMainTaskHandler() {
            return mH;
        }
        /**
         * @param mH the mH to set
         */
        public void setMaintaskHandler(MainTaskHandler mH) {
            this.mH = mH;
        }
        /**
         * @return the playerH
         */
        public PlayerHandler getPlayerHandler() {
            return playerH;
        }
        /**
         * @param playerH the playerH to set
         */
        public void setPlayerHandler(PlayerHandler playerH) {
            this.playerH = playerH;
        }
        /**
         * @return the projectH
         */
        public ProjectHandler getProjectHandler() {
            return projectH;
        }
        /**
         * @param projectH the projectH to set
         */
        public void setProjectHandler(ProjectHandler projectH) {
            this.projectH = projectH;
        }
        /**
         * @return the sprintH
         */
        public SprintHandler getSprintHandler() {
            return sprintH;
        }
        /**
         * @param sprintH the sprintH to set
         */
        public void setSprintHandler(SprintHandler sprintH) {
            this.sprintH = sprintH;
        }
        /**
         * @return the uH
         */
        public UserHandler getUserHandler() {
            return uH;
        }
        /**
         * @param uH the uH to set
         */
        public void setUserHandler(UserHandler uH) {
            this.uH = uH;
        }
        
        public DBHandlers build() {
            return new DBHandlers(this);
        }
    }
}
