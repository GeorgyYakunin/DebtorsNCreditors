package com.debitscredits.debitmaneger;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.debitscredits.debitmaneger.Adapter.AdapterTake;
import com.debitscredits.debitmaneger.Model.Planettake;

import java.util.List;


public class TakeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    View view;
    ListView list;
    EditText edit;
    ImageView imageView;
    double total;
    TextView textViewtottake;
    private List<Planettake> Planetlist;
    private AdapterTake adapterTake;
    DatabaseHelpher helpher;
    SwipeRefreshLayout refreshLayout;

    public TakeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_take, container, false);
        helpher = new DatabaseHelpher(getContext());

        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipefragmenttake);
        refreshLayout.setOnRefreshListener(this);
        list = (ListView) view.findViewById(R.id.listtake);
        textViewtottake = (TextView) view.findViewById(R.id.tvtottake);
        imageView = (ImageView) view.findViewById(R.id.imgtakenrhome);


        Planetlist = helpher.getListtake("TAKEN");
        adapterTake = new AdapterTake(getActivity(),Planetlist);
        list.setAdapter(adapterTake);

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
                Planettake planet1 = adapterTake.getItem(position);
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
        for(int i=0; i<Planetlist.size(); i++){
            total = total +Double.parseDouble(Planetlist.get(i).getAmount1());
            textViewtottake.setText(Integer.toString((int) total) );

        }

    }


    @Override
    public void onRefresh() {
        Planetlist = helpher.getListtake("TAKEN");
        adapterTake = new AdapterTake(getActivity(),Planetlist);
        list.setAdapter(adapterTake);
        refreshLayout.setRefreshing(false);
    }
}
