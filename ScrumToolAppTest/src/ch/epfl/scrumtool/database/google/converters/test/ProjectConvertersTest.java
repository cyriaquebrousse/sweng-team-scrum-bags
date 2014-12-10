package ch.epfl.scrumtool.database.google.converters.test;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.database.google.conversion.ProjectConverters;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.server.scrumtool.model.KeyResponse;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumProject;
import ch.epfl.scrumtool.test.TestConstants;

public class ProjectConvertersTest extends TestCase {
    
    public void testNullKey() {
        try {
            ScrumProject project = TestConstants.generateBasicScrumProject();
            project.setKey(null);
            
            ProjectConverters.SCRUMPROJECT_TO_PROJECT.convert(project);
            fail("NullPointerException for invalid ScrumProject expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    public void testNullDescription() {
        try {
            ScrumProject project = TestConstants.generateBasicScrumProject();
            project.setDescription(null);
            
            ProjectConverters.SCRUMPROJECT_TO_PROJECT.convert(project);
            fail("NullPointerException for invalid ScrumProject expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    public void testNullName() {
        try {
            ScrumProject project = TestConstants.generateBasicScrumProject();
            project.setName(null);
            
            ProjectConverters.SCRUMPROJECT_TO_PROJECT.convert(project);
            fail("NullPointerException for invalid ScrumProject expected");
        } catch (NullPointerException e) {
            
        } catch (Exception e) {
            fail("NullPointerException expected");
        }
    }
    
    public void testValidProject() {
        ScrumProject project = TestConstants.generateBasicScrumProject();
        Project result = ProjectConverters.SCRUMPROJECT_TO_PROJECT.convert(project);
        assertEquals(TestConstants.generateBasicProject(), result);
    }
    
    public void testToScrumProjectEmptyKey() {
        Project project = TestConstants.generateBasicProject();
        project = project
                .getBuilder()
                .setKey("")
                .build();
        ScrumProject result = ProjectConverters.PROJECT_TO_SCRUMPROJECT.convert(project);
        ScrumProject mustResult = TestConstants.generateBasicScrumProject();
        mustResult.setKey(null);
        assertEquals(mustResult.getKey(), result.getKey());
        assertEquals(mustResult.getName(), result.getName());
        assertEquals(mustResult.getDescription(), result.getName());
    }
    
    public void testToScrumProjectValid() {
        Project project = TestConstants.generateBasicProject();
        ScrumProject result = ProjectConverters.PROJECT_TO_SCRUMPROJECT.convert(project);
        ScrumProject mustResult = TestConstants.generateBasicScrumProject();
        assertEquals(mustResult.getKey(), result.getKey());
        assertEquals(mustResult.getName(), result.getName());
        assertEquals(mustResult.getDescription(), result.getName());
    }
    
    public void testInsertResponse() {
        KeyResponse response = new KeyResponse();
        response.setKey(TestConstants.VALIDKEY);
        Project Project = TestConstants.generateBasicProject();
        Project = Project.getBuilder()
                .setKey("")
                .build();

        InsertResponse<Project> insresp = new InsertResponse<Project>(Project, response);
        
        Project mustResult = TestConstants.generateBasicProject();

        Project keyProject = ProjectConverters.INSERTRESPONE_TO_PROJECT.convert(insresp);
        assertEquals(mustResult, keyProject);
    }
}
