package com.example.tanveer.Doc_advise;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanveer.HttpRequester.GetRespond;
import com.example.tanveer.HttpRequester.Request;
import com.example.tanveer.authorization.GoogleApiTokenGenerator;
import com.example.tanveer.sheba2.R;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.wallet.firstparty.GetBuyFlowInitializationTokenRequest;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DocAdviseMain extends Activity implements GetRespond{

    private final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR=1001;
    private final String scope="oauth2:https://www.googleapis.com/auth/plus.login";
    private String accessToken;
    private ImageView propic;
    private TextView profileName;
    private Dialog postDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_advise_main);
        //ListView view = (ListView) findViewById(R.id.images);
        //ListAdapter adapter =new ListAdapter(this,R.layout.single_row_item_doc);
        //view.setAdapter(adapter);
        new GoogleApiTokenGenerator(this,getIntent().getExtras().getString("email"),scope).execute();
        propic = (ImageView) findViewById(R.id.pro_pic);
        profileName = (TextView) findViewById(R.id.pro_name);
    }


    public void handleException(final Exception e) {
        // Because this call comes from the AsyncTask, we must ensure that the following
        // code instead executes on the UI thread.
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (e instanceof GooglePlayServicesAvailabilityException) {
                    // The Google Play services APK is old, disabled, or not present.
                    // Show a dialog created by Google Play services that allows
                    // the user to update the APK
                    int statusCode = ((GooglePlayServicesAvailabilityException)e)
                            .getConnectionStatusCode();
                    Dialog dialog = GooglePlayServicesUtil.getErrorDialog(statusCode,
                            DocAdviseMain.this,
                            REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
                    dialog.show();
                } else if (e instanceof UserRecoverableAuthException) {
                    // Unable to authenticate, such as when the user has not yet granted
                    // the app access to the account, but the user can fix this.
                    // Forward the user to an activity in Google Play services.
                    Intent intent = ((UserRecoverableAuthException)e).getIntent();
                    startActivityForResult(intent,
                            REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            if(requestCode==REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR)
            {
               // new GoogleApiTokenGenerator().execute();
                new GoogleApiTokenGenerator(DocAdviseMain.this,getIntent().getExtras().getString("email"),scope).execute();
            }
        }
    }
    public void outhTokenReciever(String token)
    {
        accessToken =token;
        Log.i("tanvy",token);
        new Request(this,"https://www.googleapis.com/plus/v1/people/me?access_token="+token,"G").execute();
    }

    @Override
    public void getResponse(final String response) {
            if(response.equals("Data posted"))
            {
                Toast.makeText(DocAdviseMain.this,"sdd",Toast.LENGTH_SHORT);
               postDialog.dismiss();
            }

            Runnable r =new Runnable() {
                @Override
                public void run() {
                    JSONObject object = null;
                    try {
                        object = new JSONObject(response);
                        JSONObject profilepicture = object.getJSONObject("image");
                        String imageUri=profilepicture.getString("url");
                        Log.i("tanvy", "" + imageUri + " " + object.getString("displayName"));
                        Picasso.with(DocAdviseMain.this).load(imageUri).into(propic);
                        profileName.setText(object.getString("displayName"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };
        runOnUiThread(r);

    }

    @Override
    public Activity getContext() {
        return this;
    }

    public void onPostClick(View v)
    {
        final CharSequence[] items={"BookkhoBadhi","General","Nuirology","BookkhoBadhi","General","Nuirology","BookkhoBadhi","General","Nuirology","BookkhoBadhi","General","Nuirology","BookkhoBadhi","General","Nuirology"};
        final String[] departmentName = new String[1];
        final SharedPreferences prefs = getSharedPreferences("com.example.tanveer", Context.MODE_PRIVATE);
        final String[] postString = new String[1];

        postDialog = new Dialog(DocAdviseMain.this);
        postDialog.setContentView(R.layout.doc_advise_post);
        postDialog.setTitle(R.string.post_korun);

        Button post = (Button) postDialog.findViewById(R.id.post_button);
        Button cancel = (Button) postDialog.findViewById(R.id.cancel_button);
        Button chooser = (Button) postDialog.findViewById(R.id.department_chooser);
        final TextView departmentText = (TextView) postDialog.findViewById(R.id.department);
        final EditText msgText = (EditText) postDialog.findViewById(R.id.message);


        chooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder[] departmentList = {new AlertDialog.Builder(DocAdviseMain.this)};
                departmentList[0].setTitle("select korun").setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!items[which].equals(null)){
                            departmentName[0] = items[which].toString();
                            departmentText.setText(departmentName[0]);
                        }
                    }
                }).show();
            }
        });

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd/MM/yyyy HH:mm:ss");
        final String time = sdf.format(date);


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!msgText.getText().equals(null) && !departmentName[0].equals(null))
                    postString[0] = "posted_by="+ String.valueOf(accessToken) +"&message=" + msgText.getText() + "&department=" + departmentName[0] + "&time=" + time;
                if(!postString[0].equals(null)){
                    Request request = new Request(DocAdviseMain.this, "http://tutorials.byethost32.com/sheba/post_problem.php?"+ postString[0], "GET");
                    Toast.makeText(DocAdviseMain.this, postString[0], Toast.LENGTH_LONG).show();
                }
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDialog.cancel();
            }
        });

        postDialog.show();
    }



}
