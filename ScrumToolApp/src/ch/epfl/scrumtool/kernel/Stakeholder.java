/**
 * 
 */
package ch.epfl.scrumtool.kernel;

/**
 * @author Vincent
 *
 */
public class Stakeholder extends Player {

    /**
     * @param aUser
     * @param aRole
     */
    public Stakeholder(User aUser) {
        super(aUser, Role.STAKEHOLDER);
    }

}
