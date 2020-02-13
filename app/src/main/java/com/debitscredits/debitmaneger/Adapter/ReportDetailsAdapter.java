package com.debitscredits.debitmaneger.Adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.debitscredits.debitmaneger.R;
import com.debitscredits.debitmaneger.Model.ReportDetailsPlanet;

import java.util.ArrayList;
import java.util.List;

public class ReportDetailsAdapter extends ArrayAdapter<ReportDetailsPlanet> implements Filterable {
    private final List<ReportDetailsPlanet> orgplanetList;
    private Context mContext;
    private List<ReportDetailsPlanet> mPlanetlist;
    private PlanetFilter filter;


    public ReportDetailsAdapter(@NonNull Context mContext, List<ReportDetailsPlanet> mPlanetlist) {
        super(mContext, R.layout.report_item_details);
        this.mContext = mContext;
        this.mPlanetlist = mPlanetlist;
        this.orgplanetList = mPlanetlist;


    }

    @Override
    public int getCount() {
        return mPlanetlist.size();
    }

    @Override
    public ReportDetailsPlanet getItem(int position) {
        return mPlanetlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.report_item_details, null);
        TextView txttype = (TextView) v.findViewById(R.id.tvdetailname);
        TextView txtdt = (TextView) v.findViewById(R.id.tvdt);
        TextView txtduedt = (TextView) v.findViewById(R.id.tvduedt);
        TextView txtamount = (TextView) v.findViewById(R.id.tvdetailamt);

        String amount = mPlanetlist.get(position).getAmount();

        txttype.setText(mPlanetlist.get(position).getTypeDetail());
        txtdt.setText(mPlanetlist.get(position).getDt());
        txtduedt.setText(mPlanetlist.get(position).getDueDt());
        txtamount.setText(amount);

        if (mPlanetlist.get(position).getTypeDetail().toString().equals("GIVEN"))
        {
            txttype.setTextColor(Color.parseColor("#00574B"));
        }
        else
        {
            txttype.setTextColor(Color.RED);
        }

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
                List<ReportDetailsPlanet> nPlanetList = new ArrayList<ReportDetailsPlanet>();
                List<ReportDetailsPlanet> nPlanetListLocal = new ArrayList<ReportDetailsPlanet>();
                nPlanetListLocal.addAll(orgplanetList);
                final int count = nPlanetListLocal.size();
                for (int i = 0; i < count; i++) {
                    final ReportDetailsPlanet item = nPlanetListLocal.get(i);
//                    final String itemName = item.ge().toLowerCase();
//                    if (itemName.contains(perfixString)) {
//                        nPlanetList.add(item);
//                    }

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
                mPlanetlist = (List<ReportDetailsPlanet>) results.values;
                notifyDataSetInvalidated();
            }
        }
    }
}



