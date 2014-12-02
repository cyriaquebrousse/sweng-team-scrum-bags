package ch.epfl.scrumtool.database.google.containers;

import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.util.Preconditions;

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
        Preconditions.throwIfNull("InsertIssueArgs container must have valid wrapped objects", issue, key); 
        Preconditions.throwIfEmptyString("Key must be nonemtpy", key);
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
