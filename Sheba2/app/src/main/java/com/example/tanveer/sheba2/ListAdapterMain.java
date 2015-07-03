package com.example.tanveer.sheba2;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Tanveer on 6/26/2015.
 */
public class ListAdapterMain extends ArrayAdapter{

    private Context context;
    public ListAdapterMain(Context context, int resource) {

        super(context, resource);
        this.context=context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.single_row_main_list,parent,false);
        TextView text = (TextView) convertView.findViewById(R.id.text);
        //Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/sra.ttf");
        ImageView image = (ImageView) convertView.findViewById(R.id.icon);
        if(position==0)
        {
            image.setImageResource(R.drawable.bluecircle);

            text.setText(R.string.ajker_alochona);
            //text.setTypeface(tf);


        }
        if(position==1)
        {
            image.setImageResource(R.drawable.yellocircle);
            text.setText(R.string.rog_nirnoy);

        }
        if(position==2)
        {
            image.setImageResource(R.drawable.purple);
            text.setText(R.string.daktarer_poramorsho_nin);
        }
        return convertView;
    }
}
