package com.debitscredits.debitmaneger.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.debitscredits.debitmaneger.Model.ReminderPlanet;
import com.debitscredits.debitmaneger.R;

import java.util.ArrayList;
import java.util.List;

public class GivenRemindAdapter extends ArrayAdapter<ReminderPlanet> implements Filterable {

    private Context mContext;
    private List<ReminderPlanet> mPlanetlist;
    private List<ReminderPlanet> orgplanetList;
    private PlanetFilter filter;



    public GivenRemindAdapter(@NonNull Context context, List<ReminderPlanet> mPlanetlist) {
        super(context, R.layout.reminder_list_given);

        this.mContext = context;
        this.mPlanetlist = mPlanetlist;
    }

    @Override
    public int getCount() {
        return mPlanetlist.size();
    }

    @Override
    public ReminderPlanet getItem(int position) {
        return mPlanetlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mPlanetlist.get(position).getId();
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.reminder_list_given, null);
        TextView txtname = (TextView) v.findViewById(R.id.tvremindgname);
        TextView txtdate = (TextView) v.findViewById(R.id.tvremindgdate);
        TextView txtamount = (TextView) v.findViewById(R.id.tvremindgamt);

        txtname.setText(mPlanetlist.get(position).getRName());
        txtdate.setText(mPlanetlist.get(position).getRAmount());
        txtamount.setText(mPlanetlist.get(position).getRDate());

        return v;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new PlanetFilter();
        }
        return filter;

    }

    private class PlanetFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = orgplanetList;
                results.values = orgplanetList.size();
            } else {
                String perfixString = constraint.toString().toLowerCase();
                List<ReminderPlanet> nPlanetList = new ArrayList<ReminderPlanet>();
                List<ReminderPlanet> nPlanetListLocal = new ArrayList<ReminderPlanet>();
                nPlanetListLocal.addAll(orgplanetList);
                final int count = nPlanetListLocal.size();
                for (int i = 0; i < count; i++) {
                    final ReminderPlanet item = nPlanetListLocal.get(i);
                    final String itemName = item.getRName().toLowerCase();
                    if (itemName.contains(perfixString)) {
                        nPlanetList.add(item);
                    }

                }
                results.values = nPlanetList;
                results.count = nPlanetList.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraints, FilterResults results) {

            if (results.count == 0)

            {
                notifyDataSetInvalidated();
            } else {
                mPlanetlist = (List<ReminderPlanet>) results.values;
                notifyDataSetInvalidated();
            }
        }
    }

}
