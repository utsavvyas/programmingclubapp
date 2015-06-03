package info.androidhive.proclubDaiict;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import info.androidhive.slidingmenu.R;
import info.androidhive.proclubDaiict.service.Blog;

public class BlogListView extends Fragment {
	
	SQLiteDatabase myDB= null;
	JSONArray jArray = null;
	String TableName = "BLOG2";
	int u=0;
	//inner class for retrive data from wordpress rest api
	public class asyncex extends AsyncTask<String, String, String> {


		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub.
			String response = "";
			String url = arg0[0];

			try {
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
					response += inputLine;
				}
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return response;
		}
	}









	//TextView tv;
	ListView listView;
	ArrayList<String> values;
	
	
	public BlogListView(){}
	ArrayList<Integer> arr;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

		final MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(this.getActivity());
		final asyncex as = new asyncex();
		JSONArray jArray = null;
		String param[] = new String[3];
		try {
			String result = as.execute("https://public-api.wordpress.com/rest/v1.1/sites/proclubdaiict.wordpress.com/posts?number=100").get();
			JSONObject jobj = new JSONObject(result);
			jArray = jobj.getJSONArray("posts");
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			Toast.makeText(getActivity().getApplicationContext(),"No Intenet Connection\n Please connect to Internet\n",Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        arr=new ArrayList<Integer>();
        values=new ArrayList<String>();
		listView = (ListView) rootView.findViewById(R.id.list);

		//taking all blogs from sqlite database
		List<Blog> list = mySQLiteHelper.getAllBlog();

		String UserName="";
		String Title = "";
		String Tag = "";
		String Content = "";
		String Comment = "";
		int id=0;

		for(int i=0;i<list.size();i++)
		{
			UserName = list.get(i).getUsrername();
			Title = list.get(i).getTitle();
			id = list.get(i).getId();
			Content = list.get(i).getContent();
			Comment = list.get(i).getComment();
			Tag = list.get(i).getTag();
			//System.out.println(Title);
			values.add(0,Title);
			arr.add(0,0);
		}

		//convert to string format from json array
		   if(jArray!=null)
		   {
			   for(int i=0;i<jArray.length();i++)
			   {
			   JSONObject author;
			   try {
			    	author = (JSONObject)((JSONObject)jArray.get(i)).get("author");
				    String aut = author.getString("name");
				    String content = (Html.fromHtml(((JSONObject)jArray.get(i)).getString("content")).toString());
				    String title = ((JSONObject)jArray.get(i)).getString("title");
				    int blogid =  Integer.parseInt(((JSONObject) jArray.get(i)).getString("ID"));
				    boolean eq=false;
				    for(int i1=0;i1<values.size();i1++){
					    if(values.get(i1).equals(title)){
						    eq=true;
						    break;
					    }
				    }
				    if(!eq){

					    values.add(0, title);
                        arr.add(0, 1);
						Blog blog = new Blog();
						blog.setUsrername(aut);
						blog.setContent(content);
						blog.setTitle(title);
						blog.setId(blogid);
						mySQLiteHelper.createBlog(blog);
				    }
				}
				catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		SpecialAdapter<String> adapter = new SpecialAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1, values);
        adapter.getData(arr);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
			 
            
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                
                  // ListView Clicked item index
                  int itemPosition     = position;
				List<Blog> list = mySQLiteHelper.getAllBlog();
                  String  itemValue    = (String) listView.getItemAtPosition(position);
				   	String UserName="";
					String Title = "";
					String Tag = null;
					String Content = null;
					String Comment = null;
					int bid = 0;

					int i=0;
				   while(!Title.equals(itemValue))
				   {

					   if(itemValue.equals(list.get(i).getTitle())) {
						   UserName = list.get(i).getUsrername();
						   Title = list.get(i).getTitle();
						   bid = list.get(i).getId();
						   Content = list.get(i).getContent();
						   Comment = list.get(i).getComment();
						   Tag = list.get(i).getTag();
						   break;
					   }
						i++;
				   }
				   Bundle bundle = new Bundle();
				   bundle.putString("Author", UserName);
				   bundle.putString("Title", Title);
				   bundle.putString("Content", Content);


                try {
                    asyncex as1=new asyncex();
                    String s = as1.execute("https://public-api.wordpress.com/rest/v1.1/sites/proclubdaiict.wordpress.com/posts/" + bid + "/replies/").get();
                    bundle.putString("comment",s);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Intent BlogView=new Intent("info.androidhive.slidingmenu.BLOGVIEW");
                BlogView.putExtras(bundle);
				   startActivity(BlogView);
                   // Show Alert 
                   Toast.makeText(getActivity().getApplicationContext(),"Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG).show();
                
                 }

       }); 

        return rootView;
    }
}
