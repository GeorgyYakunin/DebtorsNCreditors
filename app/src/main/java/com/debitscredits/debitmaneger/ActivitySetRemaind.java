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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class ActivitySetRemaind extends AppCompatActivity {

    private static final int RQS_PICK_CONTACT = 1;
    private static final int PERMISSION_REQUEST_CONTACT = 1;
    EditText editTextamount,editTextdescription;
    AutoCompleteTextView editTextname;
    RadioButton radioButtongiven, radioButtontaken;
    ImageView imageViewlist,img,imageViewcontact, imageView;
    Button buttoncdate,buttonduedate,btnsubmit;

    TextView textViewcdate,textViewduedate;
    DatabaseHelpher helpher;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private DatePickerDialog.OnDateSetListener dateSetListenerdue;
    int year,month,day,yeardue,monthdue,daydue;
    String datedue, Username, Amount, Description, Duedate, Cdate, Type;
    boolean contactbool = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_set_remainder);

        helpher=new DatabaseHelpher(this);
        buttoncdate = (Button) findViewById(R.id.btncdate);
        buttonduedate = (Button) findViewById(R.id.btnduedate);
        btnsubmit = (Button) findViewById(R.id.btnsub);
        textViewduedate = (TextView) findViewById(R.id.tvduedate);
        radioButtongiven = (RadioButton) findViewById(R.id.radiogiven);
        radioButtontaken = (RadioButton) findViewById(R.id.radiotaken);
        editTextamount = (EditText) findViewById(R.id.edtamount);
        editTextdescription = (EditText) findViewById(R.id.edtdes);
        imageViewcontact = (ImageView)findViewById(R.id.imgcontact);
        imageView = (ImageView)findViewById(R.id.imgrhome);
        textViewcdate = (TextView)findViewById(R.id.tvcdate);

        contactbool = false;

        List<String> lables = helpher.getAllLabels();
        ArrayAdapter<String> My_arr_adapter= new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,lables);
        editTextname = (AutoCompleteTextView) findViewById(R.id.edtname);
        editTextname.setThreshold(2);
        editTextname.setAdapter(My_arr_adapter);

        imageViewcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                askForContactPermission();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitySetRemaind.this, ActivityUserDashboard.class);
                startActivity(intent);
                finish();
            }
        });

        buttoncdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ActivitySetRemaind.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener,year,month,day);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
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

        buttonduedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                yeardue = calendar.get(Calendar.YEAR);
                monthdue = calendar.get(Calendar.MONTH);
                daydue = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ActivitySetRemaind.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListenerdue,yeardue,monthdue,daydue);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListenerdue = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                datedue = day + "/" + month + "/" + year;
                textViewduedate.setText(datedue);
            }
        };

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });


    }

    private void insertData() {

        Username=editTextname.getText().toString();
        Amount = editTextamount.getText().toString();
        Description=editTextdescription.getText().toString();
        Cdate=textViewcdate.getText().toString();
        Duedate=textViewduedate.getText().toString();

        if (radioButtongiven.isChecked())
        {
            Type = "Given";
        }
        else
        {
            Type = "Taken";
        }

        if(Username.length() != 0 && Amount.length() !=0 )
        {
            if (Cdate.length() != 0)
            {
                boolean isInserted = helpher.AddRemainderData(Username, Amount, Description,Cdate,Duedate,Type);

                if (isInserted == true) {

                    editTextname.setText("");
                    editTextamount.setText("");
                    editTextdescription.setText("");
                    textViewcdate.setText("");
                    textViewduedate.setText("");
                    Intent intent1 = new Intent(ActivitySetRemaind.this, ActivityFormForRemaind.class);
                    startActivity(intent1);
                    finish();

                }
                else {
                    Toast.makeText(ActivitySetRemaind.this, "Something went wrong please try again", Toast.LENGTH_LONG).show();
                }


            }else {
                Toast.makeText(ActivitySetRemaind.this, "Select your Date", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(ActivitySetRemaind.this, "Enter Blank Feild", Toast.LENGTH_LONG).show();
        }


    }


    private void getContact(){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, RQS_PICK_CONTACT);
    }

    public void askForContactPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(ActivitySetRemaind.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(ActivitySetRemaind.this,
                        Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySetRemaind.this);
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

                    ActivityCompat.requestPermissions(ActivitySetRemaind.this,
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

}
