package ch.epfl.scrumtool.database.google.converters.test;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.conversion.MainTaskConverters;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.exception.InconsistentDataException;
import ch.epfl.scrumtool.gui.utils.test.ServerClientEntities;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumMainTask;

/**
 * 
 * @author aschneuw
 *
 */
public class MainTaskConvertersTest extends TestCase {
    
    public void testToMainTaskNullKey() {
        try {
            ScrumMainTask mainTask = ServerClientEntities.generateBasicScrumMainTask();
            mainTask.setKey(null);
            
            MainTaskConverters.SCRUMMAINTASK_TO_MAINTASK.convert(mainTask);
            fail("InconsistentDataException for invalid ScrumMainTask expected");
        } catch (InconsistentDataException e) {
            
        }
    }
    
    public void testToMainTaskInvalidKey() {
        try {
            ScrumMainTask mainTask = ServerClientEntities.generateBasicScrumMainTask();
            mainTask.setName(null);
            MainTaskConverters.SCRUMMAINTASK_TO_MAINTASK.convert(mainTask);
            fail("InconsistentDataException for invalid ScrumMainTask expected");
        } catch (InconsistentDataException e) {
            
        }
    }
    
    public void testToMainTaskNullName() {
        try {
            ScrumMainTask mainTask = ServerClientEntities.generateBasicScrumMainTask();
            mainTask.setName(null);
            
            MainTaskConverters.SCRUMMAINTASK_TO_MAINTASK.convert(mainTask);
            fail("InconsistentDataException for invalid ScrumMainTask expected");
        } catch (InconsistentDataException e) {
            
        }
    }
        
    public void testToMainTaskNullDescription() {
        try {
            ScrumMainTask mainTask = ServerClientEntities.generateBasicScrumMainTask();
            mainTask.setDescription(null);
            
            MainTaskConverters.SCRUMMAINTASK_TO_MAINTASK.convert(mainTask);
            fail("InconsistentDataException for invalid ScrumMainTask expected");
        } catch (InconsistentDataException e) {
            
        }
    }
    
    public void testToMainTaskNullPrio() {
        try {
            ScrumMainTask mainTask = ServerClientEntities.generateBasicScrumMainTask();
            mainTask.setPriority(null);
            
            MainTaskConverters.SCRUMMAINTASK_TO_MAINTASK.convert(mainTask);
            fail("InconsistentDataException for invalid ScrumMainTask expected");
        } catch (InconsistentDataException e) {
            
        }
    }
    
    public void testToMainTaskNullStatus() {
        try {
            ScrumMainTask mainTask = ServerClientEntities.generateBasicScrumMainTask();
            mainTask.setStatus(null);
            
            MainTaskConverters.SCRUMMAINTASK_TO_MAINTASK.convert(mainTask);
            fail("InconsistentDataException for invalid ScrumMainTask expected");
        } catch (InconsistentDataException e) {
            
        }
    }
    
    public void testToMainTaskValid() {
        ScrumMainTask mainTask = ServerClientEntities.generateBasicScrumMainTask();
        MainTask result = MainTaskConverters.SCRUMMAINTASK_TO_MAINTASK.convert(mainTask);
        assertEquals(result, ServerClientEntities.generateBasicMainTask());
    }
    
    public void testToScrumMainTaskEmptyKey() {
        MainTask mainTask = ServerClientEntities.generateBasicMainTask();
        ScrumMainTask mustResult = ServerClientEntities.generateBasicScrumMainTask();
        mustResult.setKey(null);
        mainTask = mainTask.getBuilder()
                .setKey("")
                .build();
        ScrumMainTask result = MainTaskConverters.MAINTASK_TO_SCRUMMAINTASK.convert(mainTask);
        assertEquals(mustResult.getKey(), result.getKey());
    }
    
    public void testToScrumMainTaskNull() {
        try {
            
            MainTaskConverters.SCRUMMAINTASK_TO_MAINTASK.convert(null);
            fail("NullPointerexception for invalid ScrumMainTask expected");
        } catch (NullPointerException e) {
            
        }
    }
    
    public void testToScrumMainTaskValid() {
        MainTask mainTask = ServerClientEntities.generateBasicMainTask();
        ScrumMainTask mustResult = ServerClientEntities.generateBasicScrumMainTask();
        ScrumMainTask result = MainTaskConverters.MAINTASK_TO_SCRUMMAINTASK.convert(mainTask);
        assertEquals(mustResult.getKey(), result.getKey());
        assertEquals(mustResult.getDescription(), result.getDescription());
        assertEquals(mustResult.getName(), result.getName());
        assertEquals(mustResult.getPriority(), result.getPriority());
        assertEquals(mustResult.getStatus(), result.getStatus());
        
        //the counters are computed on the server
        assertEquals(null, result.getTimeFinished());
        assertEquals(null, result.getTotalIssues());
        assertEquals(null, result.getTotalTime());
        assertEquals(null, result.getIssues());
    }
}