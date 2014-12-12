package ch.epfl.scrumtool.gui.utils.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.server.scrumtool.Scrumtool;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumMainTask;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumProject;
import ch.epfl.scrumtool.server.scrumtool.model.CollectionResponseScrumSprint;
import ch.epfl.scrumtool.server.scrumtool.model.KeyResponse;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumIssue;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumMainTask;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumPlayer;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumProject;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumSprint;
import ch.epfl.scrumtool.server.scrumtool.model.ScrumUser;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

/**
 * 
 * @author aschneuw
 * Mock Scrumtool AppEngine server that return default values
 *
 */
public class MockScrumTool extends Scrumtool {

    public MockScrumTool() {
        super(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
    }
    
    @Override
    public AddPlayerToProject addPlayerToProject(String projectKey,
            String userKey, String role) throws IOException {
        AddPlayerToProject req = new AddPlayerToProject(projectKey, userKey, role) {
            @Override
            public KeyResponse execute() throws IOException {
                return ServerClientEntities.generateKeyResponse();
            }
        };
        return req;
    }

    @Override
    public InsertIssueInSprint insertIssueInSprint(String issueKey,
            String sprintKey) throws IOException {
        return new InsertIssueInSprint(issueKey, sprintKey){
            @Override
            public Void execute() throws IOException {
                return null;
            }
        };
    }
    
    @Override
    public InsertScrumIssue insertScrumIssue(String mainTaskKey,
            ScrumIssue content) throws IOException {
        return new InsertScrumIssue(mainTaskKey, content){
            @Override
            public KeyResponse execute() throws IOException {
                return ServerClientEntities.generateKeyResponse();
            }
        };
    }
    
    @Override
    public InsertScrumMainTask insertScrumMainTask(String projectKey,
            ScrumMainTask content) throws IOException {
        return new InsertScrumMainTask(projectKey, content) {
            @Override
            public KeyResponse execute() throws IOException {
                return ServerClientEntities.generateKeyResponse();
            }  
        };
    }
    
    @Override
    public InsertScrumProject insertScrumProject(ScrumProject content)
            throws IOException {
        return new InsertScrumProject(content) {
            @Override
            public KeyResponse execute() throws IOException {
                return ServerClientEntities.generateKeyResponse();
            } 
        };
    }
    
    @Override
    public InsertScrumSprint insertScrumSprint(String projectKey,
            ScrumSprint content) throws IOException {
        return new InsertScrumSprint(projectKey, content) {
            @Override
            public KeyResponse execute() throws IOException {
                return ServerClientEntities.generateKeyResponse();
            }
        };
    }
    
    @Override
    public LoadInvitedPlayers loadInvitedPlayers() throws IOException {
        return new LoadInvitedPlayers(){
            @Override
            public CollectionResponseScrumPlayer execute() throws IOException {
                return ServerClientEntities.generateCollectionScrumPlayer();
            }
        };
    }
    
    @Override
    public LoadIssuesByMainTask loadIssuesByMainTask(String mainTaskKey)
            throws IOException {
        return new LoadIssuesByMainTask(mainTaskKey){
            @Override
            public CollectionResponseScrumIssue execute() throws IOException {
                return ServerClientEntities.generateCollectionScrumIssue();
            }
        };
    }
    
    @Override
    public LoadIssuesBySprint loadIssuesBySprint(String sprintKey)
            throws IOException {
        return new LoadIssuesBySprint(sprintKey){
            @Override
            public CollectionResponseScrumIssue execute() throws IOException {
                return ServerClientEntities.generateCollectionScrumIssue();
            }
        };
    }
    
    @Override
    public LoadIssuesForUser loadIssuesForUser(String userKey)
            throws IOException {
        return new LoadIssuesForUser(userKey) {
            @Override
            public CollectionResponseScrumIssue execute() throws IOException {
                ScrumIssue issue = ServerClientEntities.generateBasicScrumIssue();
                issue.setMainTask(ServerClientEntities.generateBasicScrumMainTask());
                issue.getMainTask().setProject(ServerClientEntities.generateBasicScrumProject());
                
                ScrumIssue issue2 = ServerClientEntities.generateBasicScrumIssue();
                issue2.setMainTask(ServerClientEntities.generateBasicScrumMainTask());
                issue2.getMainTask().setProject(ServerClientEntities.generateBasicScrumProject());
                
                List<ScrumIssue> list = new ArrayList<ScrumIssue>();
                list.add(issue2);
                list.add(issue);
                
                return ServerClientEntities.generateCollectionScrumIssue().setItems(list);
            }
        };
    }
    
    @Override
    public LoadMainTasks loadMainTasks(String projectKey) throws IOException {
        return new LoadMainTasks(projectKey){
            @Override
            public CollectionResponseScrumMainTask execute() throws IOException {
                return ServerClientEntities.generateCollectionScrumMainTask();
            }
        };
    }
    
    @Override
    public LoadPlayers loadPlayers(String projectKey) throws IOException {
        return new LoadPlayers(projectKey){
            @Override
            public CollectionResponseScrumPlayer execute() throws IOException {
                return ServerClientEntities.generateCollectionScrumPlayer();
            }
        };
    }
    
    @Override
    public LoadUnsprintedIssuesForProject loadUnsprintedIssuesForProject(
            String projectKey) throws IOException {
        return new LoadUnsprintedIssuesForProject(projectKey){
            @Override
            public CollectionResponseScrumIssue execute() throws IOException {
                return ServerClientEntities.generateCollectionScrumIssue();
            }
        };
    }
    
    @Override
    public LoadProjects loadProjects(String userKey) throws IOException {
        return new LoadProjects(userKey) {
            @Override
            public CollectionResponseScrumProject execute() throws IOException {
                return ServerClientEntities.generateCollectionScrumProject();
            }  
        };
    }
    
    @Override
    public LoadSprints loadSprints(String projectKey) throws IOException {
        return new LoadSprints(projectKey){
            @Override
            public CollectionResponseScrumSprint execute() throws IOException {
                return ServerClientEntities.generateCollectionScrumSprint();
            }
        };
    }
    
    @Override
    public LoginUser loginUser(String eMail) throws IOException {
        return new LoginUser(eMail){
            @Override
            public ScrumUser execute() throws IOException {
                return ServerClientEntities.generateBasicScrumUser();
            }
        };
    }
    
    @Override
    public RemoveScrumIssue removeScrumIssue(String issueKey)
            throws IOException {
        return new RemoveScrumIssue(issueKey){
            @Override
            public Void execute() throws IOException {
                return null;
            }
        };
    }
    
    @Override
    public RemoveScrumIssueFromSprint removeScrumIssueFromSprint(String issueKey)
            throws IOException {
        return new RemoveScrumIssueFromSprint(issueKey){
            @Override
            public Void execute() throws IOException {
                return null;
            }
        };
    }
    
    @Override
    public RemoveScrumMainTask removeScrumMainTask(String mainTaskKey)
            throws IOException {
        return new RemoveScrumMainTask(mainTaskKey){
            @Override
            public Void execute() throws IOException {
                return null;
            }
        };
    }

    @Override
    public RemoveScrumPlayer removeScrumPlayer(String playerKey)
            throws IOException {
        return new RemoveScrumPlayer(playerKey){
            @Override
            public Void execute() throws IOException {
                return null;
            }
        };
    }

    @Override
    public RemoveScrumProject removeScrumProject(String projectKey)
            throws IOException {
        return new RemoveScrumProject(projectKey) {
            @Override
            public Void execute() throws IOException {
                return null;
            }
        };
    }
    
    @Override
    public RemoveScrumSprint removeScrumSprint(String sprintKey)
            throws IOException {
        return new RemoveScrumSprint(sprintKey){
            @Override
            public Void execute() throws IOException {
                return null;
            }
        };
    }

    @Override
    public RemoveScrumUser removeScrumUser(String userKey) throws IOException {
        return new RemoveScrumUser(userKey){
            @Override
            public Void execute() throws IOException {
                return null;
            }
        };
    }

    @Override
    public UpdateScrumIssue updateScrumIssue(ScrumIssue content)
            throws IOException {
        return new UpdateScrumIssue(content){
            @Override
            public Void execute() throws IOException {
                return null;
            }
        };
    }
    @Override
    public UpdateScrumMainTask updateScrumMainTask(ScrumMainTask content)
            throws IOException {
        return new UpdateScrumMainTask(content) {
            @Override
            public Void execute() throws IOException {
                return null;
            }
        };
    }

    @Override
    public UpdateScrumPlayer updateScrumPlayer(ScrumPlayer content)
            throws IOException {
        return new UpdateScrumPlayer(content){
            @Override
            public Void execute() throws IOException {
                return null;
            }
        };
    }

    @Override
    public UpdateScrumProject updateScrumProject(ScrumProject content)
            throws IOException {
        return new UpdateScrumProject(content){
            @Override
            public Void execute() throws IOException {
                return null;
            }
        };
    }
    
    @Override
    public UpdateScrumSprint updateScrumSprint(ScrumSprint content)
            throws IOException {
        return new UpdateScrumSprint(content) {
            @Override
            public Void execute() throws IOException {
                return null;
            }
        };
    }
    
    @Override
    public UpdateScrumUser updateScrumUser(ScrumUser content)
            throws IOException {
        return new UpdateScrumUser(content){
            @Override
            public Void execute() throws IOException {
                return null;
            }
        };
    }
}
