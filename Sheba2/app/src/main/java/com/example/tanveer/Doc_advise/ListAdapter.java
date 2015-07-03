package com.example.tanveer.Doc_advise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.tanveer.sheba2.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Tanveer on 7/2/2015.
 */
public class ListAdapter extends ArrayAdapter{
    Context context;
    public ListAdapter(Context context, int resource) {


        super(context, resource);
        this.context =context;
    }

    @Override
    public int getCount() {
        return 17;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.single_row_item_doc,parent,false);
        ImageView view = (ImageView) convertView.findViewById(R.id.img);
        Picasso.with(context).load("https://lh5.googleusercontent.com/-XnZDEoiF09Y/AAAAAAAAAAI/AAAAAAAAYCI/7fow4a2UTMU/photo.jpg").into(view);
        return convertView;
    }
}
