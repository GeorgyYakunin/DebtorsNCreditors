package com.debitscredits.debitmaneger;

import android.content.Intent;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.debitscredits.debitmaneger.Adapter.ListAdapter;
import com.debitscredits.debitmaneger.Model.Planet;

import java.util.List;

public class ActivityGivelist extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ListView list;
    TextView textViewtotamt;
    private List<Planet> mPlanetlist;
    private ListAdapter listAdapter;
    DatabaseHelpher helpher;
    private Toolbar toolbar;
    String id;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_give_list);

        helpher = new DatabaseHelpher(this);

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipegivelist);
        refreshLayout.setOnRefreshListener(this);

        list = (ListView) findViewById(R.id.list);
        toolbar = (Toolbar)findViewById(R.id.givelisttoolbar);
        textViewtotamt = (TextView)findViewById(R.id.tvtotamt);

        mPlanetlist = helpher.getListgive("GIVEN");
        listAdapter = new ListAdapter(this, mPlanetlist);
        list.setAdapter(listAdapter);

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
                Intent intent = new Intent(ActivityGivelist.this, ActivityDeleteAndEdit.class);
                intent.putExtra("a1",id);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRefresh() {
        mPlanetlist = helpher.getListgive("GIVEN");
        listAdapter = new ListAdapter(this, mPlanetlist);
        list.setAdapter(listAdapter);
        refreshLayout.setRefreshing(false);
    }
}
