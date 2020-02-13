package com.debitscredits.debitmaneger;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.debitscredits.debitmaneger.Adapter.TakenRemindAdapter;
import com.debitscredits.debitmaneger.Model.ReminderPlanet;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TakenReminderFragment extends Fragment {

    ListView listView;
    TakenRemindAdapter adapter;
    List<ReminderPlanet> mPlanetlist = new ArrayList<ReminderPlanet>();
    DatabaseHelpher helpher;
    ImageView imageView;
    View view;

    public TakenReminderFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_taken_reminder, container, false);

        helpher = new DatabaseHelpher(getActivity());

        listView = (ListView) view.findViewById(R.id.listremindtaken);
        imageView = (ImageView) view.findViewById(R.id.imggivenrhome);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityUserDashboard.class);
                startActivity(intent);
            }
        });

        mPlanetlist = helpher.getListReminder("Taken");
        adapter = new TakenRemindAdapter(getActivity(), mPlanetlist);
        listView.setAdapter(adapter);

        return view;

    }

}
