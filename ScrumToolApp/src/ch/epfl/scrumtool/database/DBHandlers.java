/**
 * 
 */
package ch.epfl.scrumtool.database;

/**
 * @author aschneuw
 * 
 */
public final class DBHandlers {
    private final IssueHandler issueHandler;
    private final MainTaskHandler mainTaskHandler;
    private final PlayerHandler playerHandler;
    private final ProjectHandler projectHandler;
    private final SprintHandler sprintHandler;
    private final UserHandler userHandler;

    private DBHandlers(DBHandlers.Builder builder) {
        issueHandler = builder.issueHandlr;
        mainTaskHandler = builder.maintaskHandlr;
        playerHandler = builder.playerHandlr;
        projectHandler = builder.projectHandlr;
        sprintHandler = builder.sprintHandlr;
        userHandler = builder.userHandlr;
    }

    /**
     * @return the issueHandler
     */
    public IssueHandler getIssueHandler() {
        return issueHandler;
    }

    /**
     * @return the mainTaskHandler
     */
    public MainTaskHandler getMainTaskHandler() {
        return mainTaskHandler;
    }

    /**
     * @return the playerHandler
     */
    public PlayerHandler getPlayerHandler() {
        return playerHandler;
    }

    /**
     * @return the projectHandler
     */
    public ProjectHandler getProjectHandler() {
        return projectHandler;
    }

    /**
     * @return the sprintHandler
     */
    public SprintHandler getSprintHandler() {
        return sprintHandler;
    }

    /**
     * @return the userHandler
     */
    public UserHandler getUserHandler() {
        return userHandler;
    }

    /**
     * 
     * @author aschneuw
     * 
     */
    public static class Builder {
        private IssueHandler issueHandlr;
        private MainTaskHandler maintaskHandlr;
        private PlayerHandler playerHandlr;
        private ProjectHandler projectHandlr;
        private SprintHandler sprintHandlr;
        private UserHandler userHandlr;

        /**
         * @return the issueHandler
         */
        public IssueHandler getIssueHandler() {
            return issueHandlr;
        }

        /**
         * @param issueHandlr
         */
        public Builder setIssueHandler(IssueHandler issueHandlr) {
            this.issueHandlr = issueHandlr;
            return this;
        }

        /**
         * @return the maintaskHandler
         */
        public MainTaskHandler getMainTaskHandler() {
            return maintaskHandlr;
        }

        /**
         * @param maintaskHandlr
         */
        public Builder setMaintaskHandler(MainTaskHandler maintaskHandlr) {
            this.maintaskHandlr = maintaskHandlr;
            return this;
        }

        /**
         * @return the playerHandler
         */
        public PlayerHandler getPlayerHandler() {
            return playerHandlr;
        }

        /**
         * @param playerHandlr
         */
        public Builder setPlayerHandler(PlayerHandler playerHandlr) {
            this.playerHandlr = playerHandlr;
            return this;
        }

        /**
         * @return the projectHandler
         */
        public ProjectHandler getProjectHandler() {
            return projectHandlr;
        }

        /**
         * @param projectHandlr
         */
        public Builder setProjectHandler(ProjectHandler projectHandlr) {
            this.projectHandlr = projectHandlr;
            return this;
        }

        /**
         * @return the sprintHandler
         */
        public SprintHandler getSprintHandler() {
            return sprintHandlr;
        }

        /**
         * @param sprintHandlr
         */
        public Builder setSprintHandler(SprintHandler sprintHandlr) {
            this.sprintHandlr = sprintHandlr;
            return this;
        }

        /**
         * @return the userHandler
         */
        public UserHandler getUserHandler() {
            return userHandlr;
        }

        /**
         * @param userHandlr
         */
        public Builder setUserHandler(UserHandler userHandlr) {
            this.userHandlr = userHandlr;
            return this;
        }

        /**
         * Builds the Builder
         * 
         * @return an immutable DBHandlers
         */
        public DBHandlers build() {
            return new DBHandlers(this);
        }
    }
}
