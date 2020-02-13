package com.debitscredits.debitmaneger;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityUserDashboard extends AppCompatActivity {

    Button btnreport,btngive,btntake,btnremainder;
    TextView txtmoney,textViewtotgive,textViewtottake;
    DatabaseHelpher helpher;
    int totaltake,totalgive,totaldata;
    ImageView imageViewbackup,imageViewrefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user_dashboard);
        helpher = new DatabaseHelpher(this);

        txtmoney = (TextView)findViewById(R.id.txtmoney);
        textViewtotgive = (TextView)findViewById(R.id.tvtotalgive);
        textViewtottake = (TextView)findViewById(R.id.tvtotaltake);
        btnreport=(Button)findViewById(R.id.btnre);
        btngive=(Button)findViewById(R.id.btngive);
        btntake=(Button)findViewById(R.id.btntake);
        btnremainder=(Button)findViewById(R.id.btnremainder);
        imageViewbackup = (ImageView)findViewById(R.id.imgbackup);
        imageViewrefresh = (ImageView)findViewById(R.id.imgrefresh);

        imageViewbackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityUserDashboard.this, ActivityBackup.class);
                startActivity(intent);
            }
        });

        imageViewrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getsumdata();
                getsumdatagive();
                getsumdatatotal();
            }
        });


        btngive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent give=new Intent(ActivityUserDashboard.this, ActivityGiveMoney.class);
                startActivity(give);

            }
        });

        btntake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent take=new Intent(ActivityUserDashboard.this, ActivityTakeMoney.class);
                startActivity(take);

            }
        });

        btnremainder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent take=new Intent(ActivityUserDashboard.this, ActivityFormForRemaind.class);
                startActivity(take);

            }
        });


        btnreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent report =new Intent(ActivityUserDashboard.this, ActivityReport.class);
                report.putExtra("Position", 2);
                startActivity(report);

            }
        });

        getsumdata();
        getsumdatagive();
        getsumdatatotal();

    }

    public void getsumdata()
    {
        totalgive = helpher.sumData("GIVEN");
        textViewtotgive.setText(String.valueOf(totalgive));
    }

    public void getsumdatagive()
    {
        totaltake = helpher.sumDatagive("TAKEN");
        textViewtottake.setText(String.valueOf(totaltake));
    }

    public void getsumdatatotal()
    {
        totaldata = totalgive - totaltake;
        txtmoney.setText(String.valueOf(totaldata));
    }


}
