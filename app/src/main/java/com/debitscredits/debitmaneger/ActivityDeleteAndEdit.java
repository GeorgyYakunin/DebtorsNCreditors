package com.debitscredits.debitmaneger;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.debitscredits.debitmaneger.Model.Planet;

public class ActivityDeleteAndEdit extends AppCompatActivity {

    EditText editTextname,editTextamount,editTextdescription;
    TextView textViewdt,textViewduedt,textViewid,textViewtype;
    ImageView imageView;
    private Toolbar toolbar;
    DatabaseHelpher databaseHelpher;
    Button buttonedit,buttondelete;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_edit_or_delete);

        databaseHelpher = new DatabaseHelpher(this);
        toolbar = (Toolbar)findViewById(R.id.givelisttoolbar);
        setSupportActionBar(toolbar);
        editTextname = (EditText)findViewById(R.id.edteditname);
        editTextamount = (EditText)findViewById(R.id.edteditamount);
        editTextdescription = (EditText)findViewById(R.id.edteditdes);
        textViewdt = (TextView)findViewById(R.id.tveditcdate);
        textViewduedt = (TextView)findViewById(R.id.tveditduedate);
        textViewid = (TextView)findViewById(R.id.tveditduedate);
        textViewtype = (TextView)findViewById(R.id.tvedittype);
        buttonedit = (Button) findViewById(R.id.btnedit);
        buttondelete = (Button)findViewById(R.id.btndelete);

        imageView = (ImageView)findViewById(R.id.imgedithome);

        Display();

        buttonedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = databaseHelpher.UpdateGiveTakeData(String.valueOf(id),editTextname.getText().toString(),editTextamount.getText().toString(),editTextdescription.getText().toString(),textViewdt.getText().toString(),textViewduedt.getText().toString(),textViewtype.getText().toString());
                if (isUpdated == true)
                {
                    Toast.makeText(ActivityDeleteAndEdit.this,"Edit Successfully",Toast.LENGTH_LONG).show();

                    editTextname.setText("");
                    editTextamount.setText("");
                    editTextdescription.setText("");
                    textViewdt.setText("");
                    textViewduedt.setText("");
                    textViewtype.setText("");

                    Intent intent = new Intent(ActivityDeleteAndEdit.this, ActivityUserDashboard.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(ActivityDeleteAndEdit.this,"Edit Failed",Toast.LENGTH_LONG).show();
                }
            }
        });

        buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer isDeleted = databaseHelpher.DeleteData(String.valueOf(id));
                if (isDeleted == 0)
                {
                    Toast.makeText(ActivityDeleteAndEdit.this,"Delete Successfully",Toast.LENGTH_LONG).show();
                    editTextname.setText("");
                    editTextamount.setText("");
                    editTextdescription.setText("");
                    textViewdt.setText("");
                    textViewduedt.setText("");
                    textViewtype.setText("");

                    Intent intent = new Intent(ActivityDeleteAndEdit.this, ActivityUserDashboard.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(ActivityDeleteAndEdit.this,"Delete Failed",Toast.LENGTH_LONG).show();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDeleteAndEdit.this, ActivityUserDashboard.class);
                startActivity(intent);
            }
        });

    }

    public void Display()
    {
        Intent inn= getIntent();
        Bundle bg1 = inn.getExtras();
        if(bg1!=null) {
            id = (long) bg1.get("a1");

            Planet product = databaseHelpher.getProductById((int) id);
            textViewid.setText(String.valueOf(product.getId()));
            editTextname.setText(product.getName());
            editTextamount.setText(product.getAmount());
            editTextdescription.setText(product.getDescription());
            textViewdt.setText(product.getGDate());
            textViewduedt.setText(product.getDueDate());
            textViewtype.setText(product.getGiveType());

        }

    }

}
