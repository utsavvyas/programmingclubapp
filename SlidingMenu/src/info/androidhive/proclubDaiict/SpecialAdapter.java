package info.androidhive.proclubDaiict;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parth panchal on 08-04-2015.
 */
public class SpecialAdapter<Type> extends ArrayAdapter<Type> {


    ArrayList<Integer> arr;
    public SpecialAdapter(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
        arr=new ArrayList<Integer>();

    }

    public void getData(ArrayList<Integer> arr){
        this.arr=arr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView view= (TextView)super.getView(position, convertView, parent);
        if(arr.get(position)==0){

            view.setBackgroundColor(Color.TRANSPARENT);
        }
        else{
            view.setTypeface(null, Typeface.BOLD);
            view.setBackgroundColor(Color.rgb(208,208,208));
        }
        return view;
    }
}
