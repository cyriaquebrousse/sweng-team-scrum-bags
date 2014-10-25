package ch.epfl.scrumtool.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ketsio
 */
public class Sprint {
    private Date mDeadLine;
    private Set<Issue> mIssues;

    /**
     * @param mDeadLine
     * @param mIssues
     */
    public Sprint(Date deadLine, Set<Issue> issues) {
        super();
        this.mDeadLine = deadLine;
        this.mIssues = new HashSet<Issue>(issues);
    }

    /**
     * @return the mDeadLine
     */
    public Date getDeadLine() {
        return mDeadLine;
    }

    /**
     * @param mDeadLine
     *            the mDeadLine to set
     */
    public void setDeadLine(Date mDeadLine) {
        this.mDeadLine = mDeadLine;
    }

    /**
     * @return the mIssues
     */
    public Set<Issue> getIssues() {
        return mIssues;
    }

    /**
     * @param mIssues
     *            the mIssues to set
     */
    public void setIssues(Set<Issue> mIssues) {
        this.mIssues = mIssues;
    }

}
