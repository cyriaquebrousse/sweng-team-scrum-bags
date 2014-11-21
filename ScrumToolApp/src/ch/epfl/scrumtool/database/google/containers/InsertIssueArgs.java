package ch.epfl.scrumtool.database.google.containers;

import ch.epfl.scrumtool.entity.Issue;

/**
 * Arguments for Issue insertion operation
 * 
 * @author vincent
 *
 */
public class InsertIssueArgs {
    private String key;
    private Issue issue;
    
    public InsertIssueArgs(final Issue issue, final String key) {
        if (issue == null || key == null) {
            throw new NullPointerException();
        }
        this.issue = issue;
        this.key = key;
    }
    
    public String getKey() {
        return key;
    }
    
    public Issue getIssue() {
        return issue;
    }
}
