package info.androidhive.proclubDaiict;

import info.androidhive.proclubDaiict.service.ScheduleClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import info.androidhive.slidingmenu.R;

import java.util.Calendar;

import static android.widget.Toast.*;

public class Events extends Fragment {
	
	SQLiteDatabase myDB= null;
	JSONArray jArray = null;
	String TableName = "EVENTS1";
	int u=0;
	ListView listView;
	ArrayList<String> values;
	private PendingIntent pendingIntent;

	public class asyncex extends AsyncTask<String, String, String> {

		
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub.
			String response = "";
			String url = "https://public-api.wordpress.com/rest/v1.1/sites/proclubevents.wordpress.com/posts?number=100";
			try{
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
			// optional default is GET
			con.setRequestMethod("GET");
	 
			//add request header
			con.setRequestProperty("Accept", "text/json");
	                
			int responseCode = con.getResponseCode();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			
			while ((inputLine = in.readLine()) != null) {
				response+=inputLine;
			}
			in.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			//print result

			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			System.out.println("post");
			
			
			super.onPostExecute(result);
			try {
				//System.out.println(result);
				//tv.setText(result);
				System.out.println("try");
				u=30;
			}
			 catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
		/*		*/
				   
			}

		}

	
	public Events(){}

    ScheduleClient scheduleClient;
	@Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_find_people, container, false);
        values=new ArrayList<String>();
		listView = (ListView) rootView.findViewById(R.id.list);
        scheduleClient = new ScheduleClient(getActivity());
        scheduleClient.doBindService();
		String tableName = "EVENTS1";
		asyncex as = new asyncex();
		String param[] = new String[3];
		 try {
			String result = as.execute().get();
			JSONObject jobj = new JSONObject(result);
			jArray = jobj.getJSONArray("posts");
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("jkl");
		String UserName;
		String Title = null;
		String Tag = null;
		String Content = null;
		String Category = null;
		String id=null;
			try {
				Thread.sleep(5000);
				System.out.println("hiiii");
				 myDB = getActivity().openOrCreateDatabase("PROCLUBDAIICT1", android.content.Context.MODE_PRIVATE, null);

				   
				   // Create a Table in the Database	
				myDB.execSQL("CREATE TABLE IF NOT EXISTS "+ "EVENTS1" + " (USERNAME VARCHAR, TITLE VARCHAR , ID VARCHAR , CONTENT VARCHAR,CATEGORY VARCHAR,TAG VARCHAR);");


				Cursor c = myDB.rawQuery("SELECT * FROM " + TableName , null);

				   int Column1 = c.getColumnIndex("USERNAME");
				   int Column2 = c.getColumnIndex("TITLE");
				   int Column3 = c.getColumnIndex("ID");
				   int Column4 = c.getColumnIndex("CONTENT");
				   int Column5 = c.getColumnIndex("CATEGORY");
				   int Column6 = c.getColumnIndex("TAG");
				   	
					c.moveToFirst();
				   while(c.moveToNext())
				   {
					   UserName = c.getString(Column1);
					   Title = c.getString(Column2);
					   id = c.getString(Column3);
					   Content = c.getString(Column4);
					   Category = c.getString(Column5);
					   Tag = c.getString(Column6);
					   System.out.println(Title);
					   values.add(Title);
					   System.out.println(Title);
				   }

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(u++);
		
				
		   if(jArray==null)
			   {
			   System.out.println("nulllll");
			   }
		   else
		   {
		   for(int i=0;i<jArray.length();i++)
			   {
			   JSONObject author;
			   try {
				author = (JSONObject)((JSONObject)jArray.get(i)).get("author");
			
				String aut = author.getString("name");
				String content = (Html.fromHtml(((JSONObject)jArray.get(i)).getString("content")).toString());
				String title = ((JSONObject)jArray.get(i)).getString("title");
				String blogid =  ((JSONObject)jArray.get(i)).getString("ID");
				boolean eq=false;
				for(int i1=0;i1<values.size();i1++){
					if(values.get(i1).equals(title)){
						eq=true;
						break;
					}
				}
				if(!eq){
					values.add(title);


                    myDB.execSQL("INSERT INTO " + TableName + " (USERNAME,TITLE,ID,CONTENT,CATEGORY,TAG)" + " VALUES ('"+aut+"', '" + title+"', '" +blogid+"', '" +content+"', '" +Category+"', '" +Tag+"');");
				}
			   
			//System.out.println(values.get(values.size()-1));
			//tv.setText(title + " " + Html.fromHtml(((JSONObject)jArray.get(i)).getString("content")));
			
			//tv.setMovementMethod(LinkMovementMethod.getInstance());
			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			}
		}
		System.out.println(values.size()+"values");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1, values);
		listView.setAdapter(adapter); 
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

                int day = 0;
                int month = 0;
                int year = 0;
                String details=(String)listView.getItemAtPosition(arg2);
				String temp[] = details.split(",");
				String a[] = temp[0].split(" ");
                // Create a new calendar set to the date chosen
                // we set the time to midnight (i.e. the first minute of that day)
                Calendar c = Calendar.getInstance();
                c.set(Integer.parseInt(a[5]), Integer.parseInt(a[4])-1, Integer.parseInt(a[3]));
                c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(a[0]));
                c.set(Calendar.MINUTE, Integer.parseInt(a[1]));
                c.set(Calendar.SECOND, Integer.parseInt(a[2]));

				Intent myIntent = new Intent(getActivity(), MyReceiver.class);
				pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, myIntent, 0);

				AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(getActivity().ALARM_SERVICE);
				alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
                // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
                // if(scheduleClient!=null)
               // scheduleClient.setAlarmForNotification(c,temp[1]);
                // Notify the user what they just did
                //Toast.makeText(getActivity(), "Notification set for: " + Integer.parseInt(a[3]) + "/" + Integer.parseInt(a[4]) + "/" + Integer.parseInt(a[5]), LENGTH_SHORT).show();
				
			}
		}); 
        return rootView;
    }
}
