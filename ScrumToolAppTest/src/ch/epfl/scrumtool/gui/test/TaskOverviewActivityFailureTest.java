package ch.epfl.scrumtool.gui.test;

import java.util.List;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.robotium.solo.Solo;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import ch.epfl.scrumtool.database.Callback;
import ch.epfl.scrumtool.entity.Issue;
import ch.epfl.scrumtool.entity.MainTask;
import ch.epfl.scrumtool.entity.Project;
import ch.epfl.scrumtool.gui.TaskOverviewActivity;
import ch.epfl.scrumtool.gui.utils.test.MockData;
import ch.epfl.scrumtool.network.Client;
import ch.epfl.scrumtool.network.DatabaseScrumClient;

/**
 * 
 * @author sylb
 *
 */
public class TaskOverviewActivityFailureTest extends ActivityInstrumentationTestCase2<TaskOverviewActivity> {

    private static final MainTask TASK = MockData.TASK1;
    private static final Project PROJECT = MockData.MURCS;
    
    private Solo solo = null;
    
    
    private DatabaseScrumClient mockClient = Mockito.mock(DatabaseScrumClient.class);
    
    public TaskOverviewActivityFailureTest() {
        super(TaskOverviewActivity.class);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        Client.setScrumClient(mockClient);
        
        solo = new Solo(getInstrumentation());
        
        Intent mockIntent = new Intent();
        mockIntent.putExtra(Project.SERIALIZABLE_NAME, PROJECT);
        mockIntent.putExtra(MainTask.SERIALIZABLE_NAME, TASK);
        setActivityIntent(mockIntent);

        Answer<Void> loadIssuesAnswerFail = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<List<Issue>>) invocation.getArguments()[1]).failure(MockData.ERROR_MESSAGE);
                return null;
            }
        };
        
        Mockito.doAnswer(loadIssuesAnswerFail).when(mockClient).loadIssues(Mockito.any(MainTask.class),
                Mockito.any(Callback.class));
        getActivity();
    }
    
    public void testToastMessage() throws Exception {

           assertTrue(solo.waitForText(MockData.ERROR_MESSAGE));
    }
}