package com.debitscredits.debitmaneger;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ActivityFormForRemaind extends AppCompatActivity {

    Button buttonadd, buttonview;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_form_for_remainder);

        buttonadd = (Button) findViewById(R.id.btnrmdgiven);
        buttonview = (Button) findViewById(R.id.btnrmdtaken);

        imageView = (ImageView) findViewById(R.id.imgremainderhome);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityFormForRemaind.this, ActivitySetRemaind.class);
                startActivity(intent);
                finish();
            }
        });

        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityFormForRemaind.this, ActivitySetRemaind.class);
                startActivity(intent);
            }
        });

        buttonview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityFormForRemaind.this, ActivityTakenRemainder.class);
                startActivity(intent);
            }
        });

    }
}
