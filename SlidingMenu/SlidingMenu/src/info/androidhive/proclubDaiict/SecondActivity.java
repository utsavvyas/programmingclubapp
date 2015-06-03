package info.androidhive.proclubDaiict;

/**
 * Created by parth panchal on 05-04-2015.
 */

        import android.app.Activity;
        import android.os.Bundle;
        import android.widget.ImageView;

        import info.androidhive.slidingmenu.R;


/**
 * This is the activity that is started when the user presses the notification in the status bar
 * @author paul.blundell
 */
public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ImageView imageView = (ImageView)findViewById(R.id.codingImage);
        imageView.setImageResource(R.drawable.event);
    }

}
