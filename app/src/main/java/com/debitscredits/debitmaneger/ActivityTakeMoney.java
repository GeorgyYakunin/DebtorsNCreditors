package com.debitscredits.debitmaneger;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class ActivityTakeMoney extends AppCompatActivity {

    private static final int RQS_PICK_CONTACT = 1;
    private static final int PERMISSION_REQUEST_CONTACT = 1;
    DatabaseHelpher helpher;
    EditText editTextamount, editTextdescription;
    AutoCompleteTextView editTextname;
    TextView textViewcdate,textViewduedate;
    Button buttoncdate,buttonduedate, btnsubmit1;
    private int year, month, day,yeardue,monthdue,daydue;
    ImageView imageViewlist,imageViewtakecont, imageViewhome;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private DatePickerDialog.OnDateSetListener dateSetListenerdue;
    boolean contactbool = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_take_money);
        helpher = new DatabaseHelpher(this);

        buttoncdate = (Button) findViewById(R.id.btnsetdatetake);
        buttonduedate = (Button) findViewById(R.id.btnsetduedatetake);
        btnsubmit1 = (Button) findViewById(R.id.btnsub1);
        editTextamount = (EditText) findViewById(R.id.ettakeamount);
        editTextdescription = (EditText) findViewById(R.id.ettakedes);
        textViewcdate = (TextView) findViewById(R.id.tvcdatetake);
        textViewduedate = (TextView) findViewById(R.id.tvduedatetake);
        imageViewtakecont = (ImageView)findViewById(R.id.imgtakecontact);
        imageViewlist = (ImageView)findViewById(R.id.imgtakelist);
        imageViewhome = (ImageView) findViewById(R.id.imgtakenhome);

        contactbool = false;
        List<String> lables = helpher.getAllLabels();
        ArrayAdapter<String> My_arr_adapter= new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,lables);
        editTextname = (AutoCompleteTextView) findViewById(R.id.ettakename);
        editTextname.setThreshold(2);
        editTextname.setAdapter(My_arr_adapter);


        imageViewtakecont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                askForContactPermission();
            }
        });

        imageViewlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityTakeMoney.this, ActivityTakelist.class);
                startActivity(intent);
            }
        });


        buttoncdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ActivityTakeMoney.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                textViewcdate.setText(date);
            }
        };

        imageViewhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityTakeMoney.this, ActivityUserDashboard.class);
                startActivity(intent);
            }
        });

        buttonduedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                yeardue = calendar.get(Calendar.YEAR);
                monthdue = calendar.get(Calendar.MONTH);
                daydue = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ActivityTakeMoney.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListenerdue,yeardue,monthdue,daydue);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListenerdue = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                monthdue = monthdue + 1;
                String datedue = daydue + "/" + monthdue + "/" + yeardue;
                textViewduedate.setText(datedue);
            }
        };

        btnsubmit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = editTextname.getText().toString();
                String Amount = editTextamount.getText().toString();
                String Description = editTextdescription.getText().toString();
                String Cdate = textViewcdate.getText().toString();
                String DueDate = textViewduedate.getText().toString();
                String Type = "TAKEN";

                if (Name.length() !=0 && Amount.length() != 0) {

                    if (Cdate.length() != 0)
                    {
                        InsertData(Name,Integer.parseInt(Amount), Description,Cdate,DueDate,Type);

                        editTextname.setText("");
                        editTextamount.setText("");
                        editTextdescription.setText("");
                        textViewcdate.setText("");
                        textViewduedate.setText("");
                        Intent intent1 = new Intent(ActivityTakeMoney.this, ActivityUserDashboard.class);
                        startActivity(intent1);
                        finish();
                    }else {
                        Toast.makeText(ActivityTakeMoney.this, "Select Your Date", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(ActivityTakeMoney.this, "Enter Blank Feild", Toast.LENGTH_LONG).show();
                }


            }

        });

    }

    private void getContact(){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, RQS_PICK_CONTACT);
    }

    public void askForContactPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(ActivityTakeMoney.this,Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(ActivityTakeMoney.this,
                        Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityTakeMoney.this);
                    builder.setTitle("Contacts access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("please confirm Contacts access");
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {Manifest.permission.READ_CONTACTS}
                                    , PERMISSION_REQUEST_CONTACT);
                        }
                    });
                    builder.show();

                } else {



                    ActivityCompat.requestPermissions(ActivityTakeMoney.this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            PERMISSION_REQUEST_CONTACT);

                }
            }else{
                getContact();
            }
        }
        else{
            getContact();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CONTACT: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContact();


                } else {

                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RQS_PICK_CONTACT){
            if(resultCode == RESULT_OK){
                Uri contactData = data.getData();
                Cursor cursor =  managedQuery(contactData, null, null, null, null);
                cursor.moveToFirst();

                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                editTextname.setText(name);
            }
        }
    }

    private void InsertData (String name, int amount, String description,String cdate,String duedate,String type)
    {
        boolean isInserted = helpher.GiveTakeData(name,amount,description, cdate, duedate,type);

        Cursor cursor = helpher.fillname(name);
        if (cursor.getCount() > 0)
        {

        }
        else
        {
            if(contactbool == true)
            {
            }
            else {
                helpher.AddUserName(name);
            }

            contactbool = false;
        }
        if (isInserted == true) {

            Toast.makeText(ActivityTakeMoney.this, "Submit Successfully", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(ActivityTakeMoney.this, "Submit Failed", Toast.LENGTH_LONG).show();
        }


    }


}
