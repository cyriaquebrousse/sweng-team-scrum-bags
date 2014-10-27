/**
 * 
 */
package ch.epfl.scrumtool.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import ch.epfl.scrumtool.server.scrumuserendpoint.Scrumuserendpoint;
import ch.epfl.scrumtool.server.scrumuserendpoint.model.ScrumUser;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.Key;

/**
 * @author Arno
 *
 */
public class GoogleUserHandler extends DatabaseHandler<User> {
	private ScrumUser scrumUser = new ScrumUser();
	
	/* (non-Javadoc)
	 * @see ch.epfl.scrumtool.entity.DatabaseHandler#insert(java.lang.Object)
	 */
	@Override
	public void insert(User object) {
		scrumUser = new ScrumUser();
		scrumUser.setEmail(object.getEmail());
		scrumUser.setName(object.getEmail());
		List<Key> test = new ArrayList<Key>(object.getProjectsKeys());
		scrumUser.setProjects(test);
		
	}

	/* (non-Javadoc)
	 * @see ch.epfl.scrumtool.entity.DatabaseHandler#get(com.google.api.client.util.Key)
	 */
	@Override
	public User get(Key key) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.scrumtool.entity.DatabaseHandler#getAll()
	 */
	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ch.epfl.scrumtool.entity.DatabaseHandler#update(java.lang.Object)
	 */
	@Override
	public void update(User modified) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see ch.epfl.scrumtool.entity.DatabaseHandler#remove(java.lang.Object)
	 */
	@Override
	public void remove(User object) {
		// TODO Auto-generated method stub
		
	}

	class InsertUser extends AsyncTask<Void, Void, Void>{

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Void doInBackground(Void... params) {
			Scrumuserendpoint.Builder builder = new Scrumuserendpoint.Builder(AndroidHttp.newCompatibleTransport(),
					new GsonFactory(), null);
			builder.setRootUrl("http://192.168.10.20:8888/_ah/api");
			Scrumuserendpoint service = builder.build();
			service.getScrumUser("trans@joey.com").execute();
			
			//PersistenceManager pm = PMF.get().getPersistenceManager();
			
			ScrumUser test = new ScrumUser();
			test.setEmail("trans@joey.com");
			test.setName("Joey Transa");
			
			
			try {
				//service.makePersistent(teste);
				service.insertScrumUser(test).execute();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				//pm.close();
			}
			
			
			return null;
		}
		
		
	}
	
	
}
