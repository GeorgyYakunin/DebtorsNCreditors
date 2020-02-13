package com.debitscredits.debitmaneger;

import android.content.Intent;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.debitscredits.debitmaneger.Adapter.AdapterTake;
import com.debitscredits.debitmaneger.Model.Planettake;

import java.util.List;

public class ActivityTakelist extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    ListView list;

    private List<Planettake> Planetlist;
    private AdapterTake adapterTake;
    DatabaseHelpher helpher;
    SwipeRefreshLayout refreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_take_list);
        helpher = new DatabaseHelpher(this);

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipetakelist);
        refreshLayout.setOnRefreshListener(this);

        list = (ListView) findViewById(R.id.listtake);

        Planetlist = helpher.getListtake("TAKEN");
        adapterTake = new AdapterTake(this,Planetlist);
        list.setAdapter(adapterTake);

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
                Intent intent = new Intent(ActivityTakelist.this, ActivityDeleteAndEdit.class);
                intent.putExtra("a1",id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRefresh() {
        Planetlist = helpher.getListtake("TAKEN");
        adapterTake = new AdapterTake(this,Planetlist);
        list.setAdapter(adapterTake);
        refreshLayout.setRefreshing(false);
    }
}
