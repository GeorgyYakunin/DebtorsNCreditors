package com.debitscredits.debitmaneger;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.debitscredits.debitmaneger.Adapter.ReportDetailsAdapter;
import com.debitscredits.debitmaneger.Model.ReportDetailsPlanet;

import java.util.List;

public class ActivityReportDetail extends AppCompatActivity {

    private List<ReportDetailsPlanet> mReportPlanetDetaillist;
    private ReportDetailsAdapter reportAdapter;
    TextView textViewname;
    DatabaseHelpher helpher;
    ListView listView;
    String contactname;
    TextView textViewdetailtotamt;
    double takentotal,giventotal,commontot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_report_detail);

        helpher = new DatabaseHelpher(this);
        listView = (ListView) findViewById(R.id.lvreportdetails);
        textViewdetailtotamt = (TextView) findViewById(R.id.tvdetailtotamt);
        textViewname = (TextView) findViewById(R.id.tvcustname);

       Display();

        mReportPlanetDetaillist = helpher.getReportDetailList(contactname);
        reportAdapter = new ReportDetailsAdapter(this, mReportPlanetDetaillist);
        listView.setAdapter(reportAdapter);

        getTotal();

    }



    public void Display()
    {
        Intent intent = getIntent();
        Bundle bg1 = intent.getExtras();
        if(bg1!=null) {
            contactname = (String) bg1.get("a1");
        }
        textViewname.setText(contactname);
    }

    public void getTotal()
    {
        commontot = 0.0;
        takentotal = 0;
        giventotal = 0;
        for(int i=0; i<mReportPlanetDetaillist.size(); i++){


            String tp = mReportPlanetDetaillist.get(i).getTypeDetail();
            if(tp.equals("TAKEN"))
            {
                takentotal = takentotal +Double.parseDouble(mReportPlanetDetaillist.get(i).getAmount());
            }
            else
            {
                giventotal = giventotal +Double.parseDouble(mReportPlanetDetaillist.get(i).getAmount());
            }


        }
        commontot = (giventotal - takentotal);
        textViewdetailtotamt.setText(Integer.toString((int) commontot) );
    }
}
