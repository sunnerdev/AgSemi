package com.semicolstudio.android.agsemi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class DepActivity extends AppCompatActivity {

    public Integer aUserID;
    public String aUser;
    public String aPass;

    public Spinner vListIDgame;
    public ArrayList<String> sListIDgame = new ArrayList<String>();

    public Spinner vListBank;
    public ArrayList<String> sListBank = new ArrayList<String>();

    public Spinner vListBankAcc;
    public ArrayList<String> sListBankAcc = new ArrayList<String>();

    public Spinner vListPromotion;
    public ArrayList<String> sListPromotion = new ArrayList<String>();

    public TextView date;
    public TextView time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dep);

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
        date = findViewById(R.id.dep_date);
        time = findViewById(R.id.dep_time);

        //Set GameIdList
        vListIDgame = findViewById(R.id.dep_idgame);
        vListBank = findViewById(R.id.dep_bank);
        vListBankAcc = findViewById(R.id.dep_bankacc);
        vListPromotion = findViewById(R.id.dep_promotion);
        createListData();

        ArrayAdapter<String> GameIdList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, sListIDgame);
        vListIDgame.setAdapter(GameIdList);

        ArrayAdapter<String> BankList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, sListBank);
        vListBank.setAdapter(BankList);

        ArrayAdapter<String> BankAccList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, sListBankAcc);
        vListBankAcc.setAdapter(BankAccList);

        ArrayAdapter<String> PromotionList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, sListPromotion);
        vListPromotion.setAdapter(PromotionList);

    }



    public void createListData() {
        sListIDgame.add("เลือก");
        sListIDgame.add("ID1");
        sListIDgame.add("ID2");

        sListBank.add("เลือก");
        sListBank.add("กสิกรไทย");
        sListBank.add("ไทยพาณิชย์");
        sListBank.add("กรุงไทย");
        sListBank.add("กรุงเทพ");
        sListBank.add("ทหารไทย");

        sListBankAcc.add("นิคม - xxx-x-xx672-6");

        sListPromotion.add("เลือก");
        sListPromotion.add("ไม่รับโปรโมชั่น");
        sListPromotion.add("โปร 50% ครั้งแรก");
    }

}
