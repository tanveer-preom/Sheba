package com.example.tanveer.sheba2;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.tanveer.Doc_advise.DocAdviseMain;
import com.example.tanveer.MenuActivities.AccountSelector;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{
    private ListView navigationList;
    public static int pickAccount=1000;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationList = (ListView) findViewById(R.id.frontlist);
        navigationList.setOnItemClickListener(this);
        navigationList.setAdapter(new ListAdapterMain(this, R.layout.single_row_main_list));
        prefs = getSharedPreferences("com.example.tanveer", Context.MODE_PRIVATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this
        // adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {

        }
        if(id==R.id.select_acc)
        {


            String accName =prefs.getString("gmail_acc","");
            if(accName.equals(""))
            {

                Log.i("tanvy","accpick");
                pickUserAccount();


            }
            else
            {
                Intent i =new Intent(MainActivity.this, AccountSelector.class);
                i.putExtra("account",accName);
                startActivity(i);
            }

        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==pickAccount)
        {
            if(resultCode==RESULT_OK)
            {
                prefs.edit().putString("gmail_acc",data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)).apply();
            }

        }
        Log.i("tanvy",""+resultCode+" "+RESULT_OK);
    }

    private void pickUserAccount() {
        String[] accountTypes = new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE};
        Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                accountTypes, true, null, null, null, null);
        startActivityForResult(intent, pickAccount);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position==0)
        {
            

        }
        else if(position==1)
        {

        }
        else
        {
            Intent i =new Intent(MainActivity.this, DocAdviseMain.class);
            i.putExtra("email",prefs.getString("gmail_acc","error"));
            startActivity(i);
        }
    }
}
