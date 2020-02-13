package com.debitscredits.debitmaneger;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.debitscredits.debitmaneger.Adapter.ViewPagerAdapter;

public class ActivityTakenRemainder extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_taken_remainder);

        tabLayout = (TabLayout)findViewById(R.id.tablayoutremind);
        viewPager = (ViewPager)findViewById(R.id.viewpagerrmind);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new GivenReminderFragment(),"Given");
        adapter.AddFragment(new TakenReminderFragment(),"Taken");
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

    }
}
