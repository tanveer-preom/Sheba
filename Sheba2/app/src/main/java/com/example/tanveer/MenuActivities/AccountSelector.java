package com.example.tanveer.MenuActivities;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.tanveer.sheba2.MainActivity;
import com.example.tanveer.sheba2.R;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;

public class AccountSelector extends ActionBarActivity {
    private TextView currentAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_selector);
        currentAcc = (TextView) findViewById(R.id.account);
        currentAcc.setText(getIntent().getExtras().getString("account"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    public void onChange(View v)
    {
        pickUserAccount();
    }
    public void onDone(View v)
    {
        finish();
    }
    private void pickUserAccount() {
        String[] accountTypes = new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE};
        Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                accountTypes, true, null, null, null, null);
        startActivityForResult(intent, MainActivity.pickAccount);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            SharedPreferences prefs = getSharedPreferences("com.example.tanveer", Context.MODE_PRIVATE);
            prefs.edit().putString("gmail_acc",data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)).apply();
            currentAcc.setText(data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME));
        }
    }
}
