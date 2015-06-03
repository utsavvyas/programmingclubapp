package info.androidhive.proclubDaiict;

/**
 * Created by parth panchal on 08-04-2015.
 */

        import android.app.Activity;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.text.Html;
        import android.text.method.LinkMovementMethod;
        import android.text.method.ScrollingMovementMethod;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.TextView;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;

        import info.androidhive.slidingmenu.R;

public class BlogView extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState1) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState1);
        setContentView(R.layout.blog_viewer);
        //ListView listView = (ListView)findViewById(R.id.list_comment);
        if(getIntent().getExtras()!=null)
            System.out.println("hi");
        Bundle savedInstanceState = getIntent().getExtras();
        String title = (String) savedInstanceState.get("Title");
        String author = (String) savedInstanceState.get("Author");
        String Content = (String) savedInstanceState.get("Content");
        JSONObject json;
        ArrayList<String> array  = new ArrayList<String>();
        try {
             json = new JSONObject((String)savedInstanceState.get("comment"));
            JSONArray arr = json.getJSONArray("comments");
            for(int i=0;i<arr.length();i++)
            {
                array.add(Html.fromHtml(arr.getJSONObject(i).getJSONObject("author").getString("nice_name") + ":\n" + arr.getJSONObject(i).getString("content")).toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.comment, android.R.id.text1, array);
        //listView.setAdapter(adapter);


        TextView t1,t2,t3;
        t1 = (TextView)(findViewById(R.id.textView1));
        t2 = (TextView)(findViewById(R.id.textView2));
        t3 = (TextView)(findViewById(R.id.textView3));
        t3.setMovementMethod(new ScrollingMovementMethod());
        t3.setMovementMethod(LinkMovementMethod.getInstance());
        Button comm=(Button)(findViewById(R.id.list_comment));
        comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent("info.androidhive.proclubDaiict.COMMENTVIEW");
                Bundle b=new Bundle();
                b.putStringArrayList("comments",array);
                in.putExtras(b);
                startActivity(in);
            }
        });
        if(t1==null)
            System.out.println("1");
        if(t2==null)
            System.out.println("2");
        if(t3==null)
            System.out.println("3");




        t1.setTextColor(Color.BLACK);
        t2.setTextColor(Color.BLACK);
        t3.setTextColor(Color.BLACK);

        t1.setText(title);
        t2.setText(author);
        t3.setText(Content);


    }


}
