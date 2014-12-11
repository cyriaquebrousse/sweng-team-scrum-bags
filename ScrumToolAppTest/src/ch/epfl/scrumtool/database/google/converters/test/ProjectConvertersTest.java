package ch.epfl.scrumtool.database.google.converters.test;

import junit.framework.TestCase;
import ch.epfl.scrumtool.database.google.containers.InsertResponse;
import ch.epfl.scrumtool.database.google.conversion.ProjectConverters;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.gui.utils.test.ServerClientEntities;
import ch.epfl.scrumtool.server.scrumtool.model.KeyResponse;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumProject;
/**
 * 
 * @author aschneuw
 *
 */
public class ProjectConvertersTest extends TestCase {
    
    public void testNullKey() {
        try {
            ScrumProject project = ServerClientEntities.generateBasicScrumProject();
            project.setKey(null);
            
            ProjectConverters.SCRUMPROJECT_TO_PROJECT.convert(project);
            fail("NullPointerException for invalid ScrumProject expected");
        } catch (NullPointerException e) {
            
        }
    }
    
    public void testNullDescription() {
        try {
            ScrumProject project = ServerClientEntities.generateBasicScrumProject();
            project.setDescription(null);
            
            ProjectConverters.SCRUMPROJECT_TO_PROJECT.convert(project);
            fail("NullPointerException for invalid ScrumProject expected");
        } catch (NullPointerException e) {
            
        }
    }
    
    public void testNullName() {
        try {
            ScrumProject project = ServerClientEntities.generateBasicScrumProject();
            project.setName(null);
            
            ProjectConverters.SCRUMPROJECT_TO_PROJECT.convert(project);
            fail("NullPointerException for invalid ScrumProject expected");
        } catch (NullPointerException e) {
            
        }
    }
    
    public void testValidProject() {
        ScrumProject project = ServerClientEntities.generateBasicScrumProject();
        Project result = ProjectConverters.SCRUMPROJECT_TO_PROJECT.convert(project);
        assertEquals(ServerClientEntities.generateBasicProject(), result);
    }
    
    public void testToScrumProjectEmptyKey() {
        Project project = ServerClientEntities.generateBasicProject();
        project = project
                .getBuilder()
                .setKey("")
                .build();
        ScrumProject result = ProjectConverters.PROJECT_TO_SCRUMPROJECT.convert(project);
        ScrumProject mustResult = ServerClientEntities.generateBasicScrumProject();
        mustResult.setKey(null);
        assertEquals(mustResult.getKey(), result.getKey());
        assertEquals(mustResult.getName(), result.getName());
        assertEquals(mustResult.getDescription(), result.getName());
    }
    
    public void testToScrumProjectValid() {
        Project project = ServerClientEntities.generateBasicProject();
        ScrumProject result = ProjectConverters.PROJECT_TO_SCRUMPROJECT.convert(project);
        ScrumProject mustResult = ServerClientEntities.generateBasicScrumProject();
        assertEquals(mustResult.getKey(), result.getKey());
        assertEquals(mustResult.getName(), result.getName());
        assertEquals(mustResult.getDescription(), result.getName());
    }
    
    public void testInsertResponse() {
        KeyResponse response = new KeyResponse();
        response.setKey(ServerClientEntities.VALIDKEY);
        Project project = ServerClientEntities.generateBasicProject();
        project = project.getBuilder()
                .setKey("")
                .build();

        InsertResponse<Project> insresp = new InsertResponse<Project>(project, response);
        
        Project mustResult = ServerClientEntities.generateBasicProject();

        Project keyProject = ProjectConverters.INSERTRESPONE_TO_PROJECT.convert(insresp);
        assertEquals(mustResult, keyProject);
    }
}
