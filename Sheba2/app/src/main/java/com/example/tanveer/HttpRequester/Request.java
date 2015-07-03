package com.example.tanveer.HttpRequester;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanveer on 7/2/2015.
 */
public class Request extends AsyncTask{

    private GetRespond activity;
    private String uri,method;
    private HttpClient client;
    private HttpGet request;
    private HttpPost postRequest;
    private List<NameValuePair> postParams;
    public Request(GetRespond context,String uri,String method)
    {
        activity=context;
        this.uri=uri;
        this.method=method;
        client = new DefaultHttpClient();
        if(method.equals("G"))
        request = new HttpGet(uri);
        else {
            postRequest = new HttpPost(uri);
            postParams = new ArrayList<NameValuePair>();
        }
    }
    public void setKeyVal(String key,String val)
    {
        if(method.equals("G"))
           return;
        postParams.add(new BasicNameValuePair(key,val));
    }
    @Override
    protected Object doInBackground(Object[] params) {
        if(method.equals("G")) {

            try {
                String str = client.execute(request, new BasicResponseHandler());
                Log.i("tanvy", str);
                activity.getResponse(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                postRequest.setEntity(new UrlEncodedFormEntity(postParams));
                String str = client.execute(postRequest, new BasicResponseHandler());
                Log.i("tanvy", str);
                activity.getResponse(str);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
