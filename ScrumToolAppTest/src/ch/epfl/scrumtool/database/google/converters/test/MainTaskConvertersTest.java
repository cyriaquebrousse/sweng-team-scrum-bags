package ch.epfl.scrumtool.database.google.converters.test;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.conversion.MainTaskConverters;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumMainTask;
import ch.epfl.scrumtool.test.TestConstants;

public class MainTaskConvertersTest extends TestCase {
    
    public void testToMainTaskNullKey() {
        try {
            ScrumMainTask mainTask = TestConstants.generateBasicScrumMainTask();
            mainTask.setKey(null);
            
            MainTaskConverters.SCRUMMAINTASK_TO_MAINTASK.convert(mainTask);
            fail("NullPointerException for invalid ScrumMainTask expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    public void testToMainTaskInvalidKey() {
        try {
            ScrumMainTask mainTask = TestConstants.generateBasicScrumMainTask();
            mainTask.setName(null);
            MainTaskConverters.SCRUMMAINTASK_TO_MAINTASK.convert(mainTask);
            fail("NullPointerException for invalid ScrumMainTask expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
        
    
    public void testToMainTaskNullName() {
        try {
            ScrumMainTask mainTask = TestConstants.generateBasicScrumMainTask();
            mainTask.setName(null);
            
            MainTaskConverters.SCRUMMAINTASK_TO_MAINTASK.convert(mainTask);
            fail("NullPointerException for invalid ScrumMainTask expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
        
    public void testToMainTaskNullDescription() {
        try {
            ScrumMainTask mainTask = TestConstants.generateBasicScrumMainTask();
            mainTask.setDescription(null);
            
            MainTaskConverters.SCRUMMAINTASK_TO_MAINTASK.convert(mainTask);
            fail("NullPointerException for invalid ScrumMainTask expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
        
    }
    
    public void testToMainTaskNullPrio() {
        try {
            ScrumMainTask mainTask = TestConstants.generateBasicScrumMainTask();
            mainTask.setPriority(null);
            
            MainTaskConverters.SCRUMMAINTASK_TO_MAINTASK.convert(mainTask);
            fail("NullPointerException for invalid ScrumMainTask expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    public void testToMainTaskNullStatus() {
        try {
            ScrumMainTask mainTask = TestConstants.generateBasicScrumMainTask();
            mainTask.setStatus(null);
            
            MainTaskConverters.SCRUMMAINTASK_TO_MAINTASK.convert(mainTask);
            fail("NullPointerException for invalid ScrumMainTask expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    public void testToMainTaskValid() {
            ScrumMainTask mainTask = TestConstants.generateBasicScrumMainTask();
            MainTask result = MainTaskConverters.SCRUMMAINTASK_TO_MAINTASK.convert(mainTask);
            assertEquals(result, TestConstants.generateBasicMainTask());
    }
    
    public void testToScrumMainTaskEmptyKey() {
        MainTask mainTask = TestConstants.generateBasicMainTask();
        ScrumMainTask mustResult = TestConstants.generateBasicScrumMainTask();
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
            fail("NullPointerException for invalid ScrumMainTask expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    public void testToScrumMainTaskValid() {
        MainTask mainTask = TestConstants.generateBasicMainTask();
        ScrumMainTask mustResult = TestConstants.generateBasicScrumMainTask();
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
