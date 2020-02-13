package com.debitscredits.debitmaneger;

import android.content.Intent;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.debitscredits.debitmaneger.Adapter.ViewPagerAdapter;

public class ActivityReport extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_report);

        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        viewPager = (ViewPager)findViewById(R.id.viewpager);

        display();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new GivenFragment(),"Given");
        adapter.AddFragment(new TakeFragment(),"Taken");
        adapter.AddFragment(new PersonalFragment(),"People");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void display() {

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null)
        {
            position = (Integer)bundle.get("Position");
        }

    }
}
