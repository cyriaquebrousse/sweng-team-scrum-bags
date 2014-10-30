/**
 * 
 */
package ch.epfl.scrumtool.network;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import ch.epfl.scrumtool.entity.DSUserHandler;
import ch.epfl.scrumtool.entity.DatabaseCallback;
import ch.epfl.scrumtool.entity.User;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

/**
 * @author aschneuw
 * 
 */
public class Session{
    public static final String CLIENT_ID = "756445222019-sefvcpr9aos0dg4r0unt44unvaqlg1nq.apps.googleusercontent.com";
    public static final int REQUEST_ACCOUNT_PICKER = 2;
    private String name;

    private GoogleAccountCredential googleCredential = null;
    private User scrumUser = null;

    public void authenticate(Activity loginContext) {
        googleCredential = GoogleAccountCredential.usingAudience(loginContext,
                "server:client_id:" + CLIENT_ID);
        Intent googleAccountPicker = googleCredential.newChooseAccountIntent();
        loginContext.startActivityForResult(
                googleCredential.newChooseAccountIntent(),
                REQUEST_ACCOUNT_PICKER);
        googleCredential.setSelectedAccountName((String) googleAccountPicker
                .getExtras().get(AccountManager.KEY_ACCOUNT_NAME));
       
       
       User.Builder uB = new User.Builder();
       uB.setName("Arno");
       uB.setEmail("arno.schneuwly@epfl.ch");
       
       DSUserHandler uH = new DSUserHandler();
       uH.insert(uB.build());
       
       uH.load(uB.getEmail(), new DatabaseCallback<User>() {
		
		@Override
		public void interactionDone(User object) {
			name = object.getEmail();
			Log.d("GET", name);
			
		}
	});
//
//        User user = uB.build();
        //DatabaseHandler<User> handler = new GoogleUserHandler();
//        handler.insert(user);

         //User arno = handler.get("arno.schneuwly@epfl.ch");
        
        
        

//        int n = Role.PRODUCT_OWNER.ordinal();
//        AuthenticateTask newTask = new AuthenticateTask();
//        newTask.execute();
    }

    public GoogleAccountCredential getCurrenUser() {
        return googleCredential;
    }

    public User getCurrentUser() {
        return scrumUser;
    }

//    class AuthenticateTask extends AsyncTask<Void, Void, Employee> {
//    	Employee emp = null;
//        /*
//         * (non-Javadoc)
//         * 
//         * @see android.os.AsyncTask#doInBackground(Params[])
//         */
//        @Override
//        protected Employee doInBackground(Void... params) {
//            Scrumtool.Builder builder = new Scrumtool.Builder(
//                    AndroidHttp.newCompatibleTransport(), new GsonFactory(),
//                    null);
//            builder.setApplicationName("ScrumToolApp");
//            builder.setRootUrl("http://10.0.0.10:8888/_ah/api/");
//            Scrumtool service = builder.build();
//
////            ScrumUser test = new ScrumUser();
////            test.setEmail("test@gmail.com");
////            test.setName("Tester");
//            
//            Employee emp = new Employee();
//            emp.setName("Arno");
//            ContactInfo cf = new ContactInfo();
//            cf.setStreetAddress("Zenhusernstrasse");
//            ContactInfo cf2 = new ContactInfo();
//            cf2.setStreetAddress("Engelberg");
//            List<ContactInfo> l = new ArrayList<ContactInfo>();
//            l.add(cf);
//            l.add(cf2);
//            emp.setContactInfo(l);
//            
//            try {
////            	service.insertEmployee(emp).execute();
//                emp = service.getEmployee(5207287069147136L).execute();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } finally {
//                Log.d("GET", emp.getName());
//               
//                
//            }
//            return emp;
//        }
//        
//        @Override
//        protected void onPostExecute(Employee arg) {
////            Log.d("DB_TEST", emp.getName());
////            Log.d("DB_TEST", emp.getContactInfo().getStreetAddress());
//        }
//    }

}
