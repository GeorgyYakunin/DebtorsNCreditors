package com.debitscredits.debitmaneger;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.debitscredits.debitmaneger.Adapter.ReportAdapter;
import com.debitscredits.debitmaneger.Model.RepoetPlanet;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {
    View view;
    private List<RepoetPlanet> mReportPlanetlist;
    private ReportAdapter reportAdapter;
    ImageView imageView;
    DatabaseHelpher helpher;
    ListView listView;
    String nameg, passname;
    Cursor namelist;
    int amountGive,amountTake,totalbal;
    TextView textViewtotpersonal;
    double total,commontot;


    public PersonalFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_personal, container, false);
        helpher = new DatabaseHelpher(getContext());
        listView = (ListView) view.findViewById(R.id.lvreport);
        textViewtotpersonal = (TextView) view.findViewById(R.id.tvtotpersonal);
        imageView = (ImageView) view.findViewById(R.id.imgpersonalhome);

        filldata();

        mReportPlanetlist = helpher.getcombList();
        reportAdapter = new ReportAdapter(getActivity(), mReportPlanetlist);
        listView.setAdapter(reportAdapter);

        getTotal();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityUserDashboard.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ActivityReportDetail.class);
                passname = ((TextView)view.findViewById(R.id.tvreportname)).getText().toString();
                intent.putExtra("a1",passname);
                startActivity(intent);
            }
        });
        return view;
    }

    public void filldata() {
        namelist = helpher.filllist();

        helpher.DeleteData();

        if (namelist.moveToFirst()) {

            do {
                nameg = namelist.getString(namelist.getColumnIndex("Name"));
                amountGive = helpher.UsersumDataGive(nameg,"GIVEN");
                amountTake = helpher.UsersumDataTake(nameg,"TAKEN");
                totalbal = amountTake - amountGive;

                helpher.UserReportData(nameg,amountGive,amountTake,totalbal);


            }
            while (namelist.moveToNext());

        }

    }

    public void getTotal()
    {
        total = 0.0;
        for(int i=0; i<mReportPlanetlist.size(); i++){
            commontot = Double.parseDouble(mReportPlanetlist.get(i).getGiveAmount()) - Double.parseDouble(mReportPlanetlist.get(i).getTakeAmount());
            total = total + commontot;
            textViewtotpersonal.setText(Integer.toString((int) total) );

        }

    }

}
