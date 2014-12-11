package ch.epfl.scrumtool.database.google.converters.test;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.database.google.conversion.SprintConverters;
import ch.epfl.scrumtool.entity.Sprint;
import ch.epfl.scrumtool.gui.utils.test.ServerClientEntities;
import ch.epfl.scrumtool.server.scrumtool.model.KeyResponse;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumSprint;

/**
 * 
 * @author aschneuw
 *
 */
public class SprintConvertersTest extends TestCase {
    public void testToSprintNullKey() {
        try {
            ScrumSprint sprint = ServerClientEntities.generateBasicScrumSprint();
            sprint.setKey(null);
            
            SprintConverters.SCRUMSPRINT_TO_SPRINT.convert(sprint);
            fail("NullPointerException for invalid ScrumSprint expected");
        } catch (NullPointerException e) {
            
        }
    }
    
    public void testToSprintNullTitle() {
        try {
            ScrumSprint sprint = ServerClientEntities.generateBasicScrumSprint();
            sprint.setDate(null);
            
            SprintConverters.SCRUMSPRINT_TO_SPRINT.convert(sprint);
            fail("NullPointerException for invalid ScrumSprint expected");
        } catch (NullPointerException e) {
            
        }
    }
    
    public void testToSprintNullDate() {
        try {
            ScrumSprint sprint = ServerClientEntities.generateBasicScrumSprint();
            sprint.setTitle(null);
            
            SprintConverters.SCRUMSPRINT_TO_SPRINT.convert(sprint);
            fail("NullPointerException for invalid ScrumSprint expected");
        } catch (NullPointerException e) {
            
        }
    }
    
    public void testToSprintValid() {
        ScrumSprint sprint = ServerClientEntities.generateBasicScrumSprint();
        Sprint result = SprintConverters.SCRUMSPRINT_TO_SPRINT.convert(sprint);
        assertEquals(ServerClientEntities.generateBasicSprint(), result);
    }
    
    public void testToScrumSprintEmptyKey() {
        Sprint sprint = ServerClientEntities.generateBasicSprint();
        sprint = sprint.getBuilder()
                .setKey("")
                .build();
        ScrumSprint result = SprintConverters.SPRINT_TO_SCRUMSPRINT.convert(sprint);
        ScrumSprint mustResult = ServerClientEntities.generateBasicScrumSprint();
        mustResult.setKey(null);
        
        assertEquals(mustResult.getKey(), result.getKey());
        assertEquals(mustResult.getTitle(), result.getTitle());
        assertEquals(mustResult.getDate(), result.getDate());
    }
    
    public void testToScrumSprint() {
        KeyResponse response = new KeyResponse();
        response.setKey(ServerClientEntities.VALIDKEY);
        Sprint sprint = ServerClientEntities.generateBasicSprint();
        sprint = sprint.getBuilder()
                .setKey("")
                .build();
        InsertResponse<Sprint> insresp = new InsertResponse<Sprint>(sprint, response);
        Sprint mustResult = ServerClientEntities.generateBasicSprint();
        Sprint keySprint = SprintConverters.INSERTRESPONSE_TO_SPRINT.convert(insresp);
        
        assertEquals(mustResult, keySprint);
    }

}
