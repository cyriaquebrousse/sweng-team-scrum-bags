/**
 * 
 */
package ch.epfl.scrumtool.database;

import ch.epfl.scrumtool.util.Preconditions;

/**
 * This is a container class for the different database handlers.
 * 
 * @author aschneuw
 * 
 */
public final class DatabaseHandlers {
    private final IssueHandler issueHandler;
    private final MainTaskHandler mainTaskHandler;
    private final PlayerHandler playerHandler;
    private final ProjectHandler projectHandler;
    private final SprintHandler sprintHandler;
    private final UserHandler userHandler;

    private DatabaseHandlers(DatabaseHandlers.Builder builder) {
        Preconditions.throwIfNull("All DatabaseHandlers need to be defined",
                builder.issueHandler,
                builder.mainTaskHandler,
                builder.playerHandler,
                builder.projectHandler,
                builder.sprintHandler,
                builder.userHandler);
        issueHandler = builder.issueHandler;
        mainTaskHandler = builder.mainTaskHandler;
        playerHandler = builder.playerHandler;
        projectHandler = builder.projectHandler;
        sprintHandler = builder.sprintHandler;
        userHandler = builder.userHandler;
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
     * DatabaseHandlers builder class
     * 
     * @author aschneuw
     * 
     */
    public static class Builder {
        private IssueHandler issueHandler;
        private MainTaskHandler mainTaskHandler;
        private PlayerHandler playerHandler;
        private ProjectHandler projectHandler;
        private SprintHandler sprintHandler;
        private UserHandler userHandler;

        /**
         * @return the issueHandler
         */
        public IssueHandler getIssueHandler() {
            return issueHandler;
        }

        /**
         * @param issueHandlr
         */
        public Builder setIssueHandler(IssueHandler issueHandlr) {
            this.issueHandler = issueHandlr;
            return this;
        }

        /**
         * @return the maintaskHandler
         */
        public MainTaskHandler getMainTaskHandler() {
            return mainTaskHandler;
        }

        /**
         * @param maintaskHandlr
         */
        public Builder setMaintaskHandler(MainTaskHandler maintaskHandlr) {
            this.mainTaskHandler = maintaskHandlr;
            return this;
        }

        /**
         * @return the playerHandler
         */
        public PlayerHandler getPlayerHandler() {
            return playerHandler;
        }

        /**
         * @param playerHandlr
         */
        public Builder setPlayerHandler(PlayerHandler playerHandlr) {
            this.playerHandler = playerHandlr;
            return this;
        }

        /**
         * @return the projectHandler
         */
        public ProjectHandler getProjectHandler() {
            return projectHandler;
        }

        /**
         * @param projectHandlr
         */
        public Builder setProjectHandler(ProjectHandler projectHandlr) {
            this.projectHandler = projectHandlr;
            return this;
        }

        /**
         * @return the sprintHandler
         */
        public SprintHandler getSprintHandler() {
            return sprintHandler;
        }

        /**
         * @param sprintHandlr
         */
        public Builder setSprintHandler(SprintHandler sprintHandlr) {
            this.sprintHandler = sprintHandlr;
            return this;
        }

        /**
         * @return the userHandler
         */
        public UserHandler getUserHandler() {
            return userHandler;
        }

        /**
         * @param userHandlr
         */
        public Builder setUserHandler(UserHandler userHandlr) {
            this.userHandler = userHandlr;
            return this;
        }

        /**
         * Builds the Builder
         * 
         * @return an immutable DBHandlers
         */
        public DatabaseHandlers build() {
            return new DatabaseHandlers(this);
        }
    }
}
