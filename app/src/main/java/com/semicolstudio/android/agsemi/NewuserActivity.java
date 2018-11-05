package com.semicolstudio.android.agsemi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class NewuserActivity extends AppCompatActivity {

    public Integer aUserID;
    public String aUser;
    public String aPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);

        //*** Get Session Login
        final UserManager usrHelper = new UserManager(this);

        //*** Get Login Status
        if(!usrHelper.getLoginStatus())
        {
            final boolean chkl = usrHelper.getLoginStatus();
            final String CKlll = String.valueOf(chkl);
            Toast.makeText(this, "ยังไม่ได้เข้าสู่ระบบ : "+ CKlll, Toast.LENGTH_LONG).show();

            Intent newlgActivity = new Intent(this, LoginActivity.class);
            startActivity(newlgActivity);
            finish();
            return;
        }

        aUserID = usrHelper.getUserID();
        aUser = usrHelper.getUserName();
        aPass = usrHelper.getUserPass();
        Toast.makeText(this, "UserInfo : "+aUserID+","+aUser+","+aPass, Toast.LENGTH_LONG).show();
    }
}
