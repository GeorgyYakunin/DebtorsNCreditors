package com.debitscredits.debitmaneger;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.debitscredits.debitmaneger.Adapter.ListAdapter;
import com.debitscredits.debitmaneger.Model.Planet;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GivenFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    View view;
    ListView list;
    double total;
    TextView textViewtotamt;
    ImageView imageView;
    private List<Planet> mPlanetlist;
    private ListAdapter listAdapter;
    DatabaseHelpher helpher;
    SwipeRefreshLayout refreshLayout;


    public GivenFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_given, container, false);
        helpher = new DatabaseHelpher(getContext());

        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipefragmentgive);
        refreshLayout.setOnRefreshListener(this);

        list = (ListView)view.findViewById(R.id.list);

        textViewtotamt = (TextView)view.findViewById(R.id.tvtotamt);
        imageView = (ImageView)view.findViewById(R.id.imggivenrhome);

        mPlanetlist = helpher.getListgive("GIVEN");
        listAdapter = new ListAdapter(getActivity(), mPlanetlist);
        list.setAdapter(listAdapter);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityUserDashboard.class);
                startActivity(intent);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Planet planet1 = listAdapter.getItem(position);
                int ti = 0;

                ti = list.getAdapter().getCount();

                for (int i1 = 0; i1 < ti; i1++)
                {
                    id = (int) planet1.getId();
                }
                Intent intent = new Intent(getActivity(), ActivityDeleteAndEdit.class);
                intent.putExtra("a1",id);
                startActivity(intent);
            }
        });

        getTotal();


        return view;
    }

    public void getTotal()
    {
        total = 0.0;
        for(int i=0; i<mPlanetlist.size(); i++){
            total = total +Double.parseDouble(mPlanetlist.get(i).getAmount());
            textViewtotamt.setText(Integer.toString((int) total) );

        }

    }

    @Override
    public void onRefresh() {
        mPlanetlist = helpher.getListgive("GIVEN");
        listAdapter = new ListAdapter(getActivity(), mPlanetlist);
        list.setAdapter(listAdapter);
        refreshLayout.setRefreshing(false);
    }
}
