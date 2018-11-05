package com.semicolstudio.android.agsemi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.*;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;



import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_openlivechat:
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(getString(R.string.url_livechat)));
                    startActivity(i);
                    return true;
            }
            return false;
        }
    };

    private String UrlAPI;
    private Integer aUserID;
    private String aUser;
    private String aPass;
    private JSONObject Userdata;
    private JSONObject jObject;
    public Context mContext;
    private String wUserID;
    private TextView mTextMessage;
    private Boolean statusInternet=false;
    private TextView txtRes;
    private int TBMainCC;
    private TableLayout TBMain;
    private UserManager usrHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        txtRes = findViewById(R.id.textResMT);
        TBMain = findViewById(R.id.tbMain);
        final String UrlNews = getString(R.string.url_livechat);

        final WebView browser = (WebView) findViewById(R.id.webview_news);
        browser.getSettings().setLoadsImagesAutomatically(true);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        browser.setWebViewClient(new WebViewClient());
        browser.loadUrl(UrlNews);




        final int ClrRed = getResources().getColor(R.color.BtnColorRed);
        final int ClrGreen = getResources().getColor(R.color.btncolorgreen);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Include
        //final CheckInternetConn CheckInternetConnect = new CheckInternetConn(this);
        final PostString ApipostStr = new PostString();
        usrHelper = new UserManager(this);

        //Check Internet Connect
        CheckInternetConn CheckInternetConnect = new CheckInternetConn(this);
        boolean statusInternet = CheckInternetConnect.CheckNetworkStatus();
        //Check Internet Return false > No Connect
        if(!statusInternet) {
            Intent newlgActivity = new Intent(this, LoginActivity.class);
            startActivity(newlgActivity);
            txtRes.setText("No Internet Connect");
            txtRes.setBackgroundColor(ClrRed);
            txtRes.setHeight(60);
            return;
        }



            //finish();

          /*  for (int i = 0; i < TBMainCC; i++) {
                View child = TBMain.getChildAt(i);
                //your processing....
                child.setEnabled(false);
                int chibg = getResources().getColor(R.color.txtColorWhiteTr);
                child.setBackgroundColor(chibg);

            }
*/
        /* Declare the timer */
       /* Timer myTimer = new Timer();
        TBMain = findViewById(R.id.tbMain);
        TBMainCC = TBMain.getChildCount();

        //Set the schedule function and rate
        myTimer.scheduleAtFixedRate(new TimerTask() {

                                        @Override
                                        public void run() {
                                            //final CheckInternetConn CheckInternetConnect = new CheckInternetConn(mContext);


                                            //Called at every 1000 milliseconds (1 second)

                                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                                statusInternet1 = CheckInternetConnect.hasInternetConnectionM();
                                                //Log.i("hasInternetConnectionM", statusInternet.toString());
                                            } else {
                                                statusInternet2 = CheckInternetConnect.connectionAvailable();
                                                //Log.i("connectionAvailable", statusInternet.toString());
                                            }

                                            Log.i("hasInternet:", statusInternet1.toString()+", connectionA:"+statusInternet2.toString());

                                            if ((statusInternet1 || statusInternet2)) {
                                                statusInternet = true;
                                                //finish();
                                                //startActivity(getIntent());
                                                for (int i = 0; i < TBMainCC; i++) {
                                                    View child = TBMain.getChildAt(i);
                                                    //your processing....
                                                    child.setEnabled(true);


                                                }
                                            }

                                            if(!statusInternet1 && !statusInternet2) {
                                                statusInternet = false;
                                                for (int i = 0; i < TBMainCC; i++) {
                                                    View child = TBMain.getChildAt(i);
                                                    //your processing....
                                                    child.setEnabled(false);
                                                    int chibg = getResources().getColor(R.color.txtColorWhiteTr);
                                                    child.setBackgroundColor(chibg);

                                                }
                                            }
                                        }
                                    },
                //set the amount of time in milliseconds before first execution
                0,
                //Set the amount of time between each execution (in milliseconds)
                4000);*/




           // myTimer.cancel();
            // Permission StrictMode
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            //*** Get Session Login


            //*** Get Login Status
            if (!usrHelper.getLoginStatus()) {
                final boolean chkl = usrHelper.getLoginStatus();
                final String CKlll = String.valueOf(chkl);
                Toast.makeText(this, "ยังไม่ได้เข้าสู่ระบบ : " + CKlll, Toast.LENGTH_LONG).show();

                Intent newlgActivity = new Intent(this, LoginActivity.class);
                startActivity(newlgActivity);
                return;
            }

            UrlAPI = getString(R.string.url_api);
            aUserID = usrHelper.getUserID();
            aUser = usrHelper.getUserName();
            aPass = usrHelper.getUserPass();
            Toast.makeText(this, "UserInfo : " + aUserID + "," + aUser, Toast.LENGTH_LONG).show();


/*
            RequestBody formBody = new FormEncodingBuilder()
                    .add("GFun", "checklogin")
                    .add("Usr", "" + aUser)
                    .add("Pwd", "" + aPass)
                    .build();


            String response = null;
            JSONObject jObject = null;
            JSONObject Userdata = null;


                Request request = new Request.Builder()
                        .url(UrlAPI)
                        .post(formBody)
                        .build();
            Response responsess = null;
            try {
                responsess = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                response = responsess.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                jObject = new JSONObject(response);
                String webStatus = jObject.getString("status");
                String webMsg = jObject.getString("msg");
                String webRow = jObject.getString("row");
                if (!webRow.isEmpty()) {

                    Userdata = new JSONObject(webRow);
                    String dbUserID = Userdata.getString("id");
                    String dbUser = Userdata.getString("username");
                    String dbPass = Userdata.getString("password");


                    usrHelper.createSession(dbUserID, dbUser, dbPass);



                    Toast.makeText(mContext, " กำลังเข้านำ "+aUser+" เข้าสู่ระบบ...", Toast.LENGTH_SHORT).show();

                } else {
                    // txtAlert.setText(webMsg);
                    // txtAlert.setCursorVisible(true);
                    Toast.makeText(mContext, webMsg, Toast.LENGTH_LONG).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.appbarsettings, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.bar_logout:
                //*** Get Session Login


                Intent ilogout = new Intent(mContext,LoginActivity.class);
                startActivity(ilogout);

                Toast.makeText(this, "Logout on bar!", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void dep(View view) {
        Intent intent = new Intent(mContext, DepActivity.class);
        startActivity(intent);

        Toast.makeText(this, "dep Clicked!", Toast.LENGTH_LONG).show();

    }

    public void trans(View view) {
        Intent intent = new Intent(mContext, TransActivity.class);
        startActivity(intent);

        Toast.makeText(this, "trans Clicked!", Toast.LENGTH_LONG).show();

    }

    public void wi(View view) {
        Intent intent = new Intent(mContext, WithActivity.class);
        startActivity(intent);

        Toast.makeText(this, "wi Clicked!", Toast.LENGTH_LONG).show();

    }

    public void newuser(View view) {
        Intent intent = new Intent(mContext, NewuserActivity.class);
        startActivity(intent);

        Toast.makeText(this, "newuser Clicked!", Toast.LENGTH_LONG).show();

    }

    public void logout(View view) {
        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivity(intent);

        Toast.makeText(this, "logout", Toast.LENGTH_LONG).show();

    }
}
