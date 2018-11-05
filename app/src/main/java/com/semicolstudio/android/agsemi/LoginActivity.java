package com.semicolstudio.android.agsemi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.invoke.ConstantCallSite;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class LoginActivity extends AppCompatActivity {
    private Context mContext;
    private TextView txtRes;
    private TextView txtAlert;
    private EditText iiUser;
    private EditText iiPass;
    public  String UrlAPI;
    private PostString httpApis;
    private UserManager UsrManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        mContext = this;
        UrlAPI = getString(R.string.url_api);
        txtRes = findViewById(R.id.textRes);
        txtAlert = findViewById(R.id.textAlert);
        iiUser = findViewById(R.id.etAppUser);
        iiPass = findViewById(R.id.etAppPass);

        final Button button = findViewById(R.id.BtnSubmit);
        final int ClrRed = getResources().getColor(R.color.BtnColorRed);
        final int ClrGreen = getResources().getColor(R.color.btncolorgreen);

        //User On Local
        UsrManager = new UserManager(this);
        //User On DB
        httpApis = new PostString();

        //Check Internet Connect
        CheckInternetConn CheckInternetConnect = new CheckInternetConn(this);
        boolean statusInternet = CheckInternetConnect.CheckNetworkStatus();
        
        //Check Internet Return false > No Connect
        if(!statusInternet) {
           txtRes.setText("No Internet Connect");
           txtRes.setBackgroundColor(ClrRed);
           txtRes.setHeight(60);
           return;
        }

        txtRes.setText("Internet Connecting");
        txtRes.setTextColor(ClrGreen);
        txtRes.setHeight(0);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
             StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
             StrictMode.setThreadPolicy(policy);
        }



        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                String iUser = iiUser.getText().toString();
                String iPass = iiPass.getText().toString();
                //Toast.makeText(mContext, " AA "+iUser+" BB..."+iPass+","+UrlAPI, Toast.LENGTH_SHORT).show();


                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("Controller","ChkSess")
                        .addFormDataPart("Usr", ""+iUser)
                        .addFormDataPart("Pwd", ""+iPass)
                        .build();
                String respond = null;
                try {
                     respond = httpApis.post(UrlAPI,requestBody);
                    //Toast.makeText(mContext, "Success : httpApis.run -> "+respond, Toast.LENGTH_SHORT).show();
                    txtRes.setText(UrlAPI+"Respon : "+respond);
                } catch (IOException e) {
                    e.printStackTrace();
                    //Toast.makeText(mContext, "Error : httpApis.run -> "+e, Toast.LENGTH_SHORT).show();
                    txtRes.setText(UrlAPI+"ResponErr : "+e.getMessage());
                }
                Log.i("Respons", respond);
                JSONObject jObject=null;
                String webStatus="Fail";
                String webMsg="";
                try {
                    jObject = new JSONObject(respond);
                    webStatus = jObject.getString("status");
                    webMsg = jObject.getString("msg");

                switch (webStatus){
                    case "Fail":

                        Toast.makeText(mContext, "แจ้งเตื่อน :"+ webMsg, Toast.LENGTH_LONG).show();
                        txtRes.setText(webMsg);
                        txtRes.setBackgroundColor(ClrRed);
                        txtRes.setHeight(60);
                        return;

                    case "Success":
                        Toast.makeText(mContext, " กำลังเข้านำ "+iUser+" เข้าสู่ระบบ...", Toast.LENGTH_SHORT).show();
                        txtRes.setText("apiStatus"+webStatus);
                        txtRes.setText(webMsg);
                        txtRes.setTextColor(ClrGreen);
                        String webRow = jObject.getString("row");
                        JSONObject Userdata = new JSONObject(webRow);
                        int dbUserID = Userdata.getInt("id");
                        String dbUser = Userdata.getString("username");
                        String dbPass = Userdata.getString("password");

                        UsrManager.createSession(dbUserID, ""+dbUser, ""+dbPass);
                        //txtAlert.setCursorVisible(false);
                        Intent intent2;
                        intent2 = new Intent(mContext,MainActivity.class);
                        startActivity(intent2);
                        break;


                        default:
                            break;
                }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.appbarlogin, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.bar_refresh:
                //*** Get Session Login
                finish();
                startActivity(getIntent());
                Toast.makeText(this, "Logout on bar!", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
