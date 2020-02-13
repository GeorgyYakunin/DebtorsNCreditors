package com.debitscredits.debitmaneger;

import android.content.Intent;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityOptions;

import java.io.File;

public class ActivityBackup extends AppCompatActivity {

    private static final String TAG = "Google Drive Activity";
    public static final int REQUEST_CODE_SIGN_IN = 0;
    public static final int REQUEST_CODE_OPENING = 1;
    public static final int REQUEST_CODE_CREATION = 2;
    public static final int REQUEST_CODE_PERMISSIONS = 2;
    Button buttonbackup,buttonrestore, buttongooglebck, buttongooglerst;
    ImageView imageViewhome;
    DatabaseHelpher helpher;
    private ActivityBackup activity;
    private LocalBackup localBackup;
    private RemoteBackup remoteBackup;
    private boolean isBackup = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_backup);

        helpher = new DatabaseHelpher(this);
        buttonbackup = (Button)findViewById(R.id.btnbackup);
        buttonrestore = (Button)findViewById(R.id.btnrestore);
        buttongooglebck = (Button)findViewById(R.id.btnonlinebackup);
        buttongooglerst = (Button)findViewById(R.id.btnonlineimport);

        imageViewhome = (ImageView)findViewById(R.id.imgbackuphome);

        localBackup = new LocalBackup(this);
        remoteBackup = new RemoteBackup(this);



        buttonbackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String outFileName = Environment.getExternalStorageDirectory() + File.separator + getResources().getString(R.string.app_name) + File.separator;
                localBackup.performBackup(helpher, outFileName);
            }
        });

        buttongooglebck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isBackup = true;
                remoteBackup.connectToDrive(isBackup);
            }
        });

        buttongooglerst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBackup = false;
                remoteBackup.connectToDrive(isBackup);
            }
        });

        imageViewhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityBackup.this, ActivityUserDashboard.class);
                startActivity(intent);
            }
        });

        buttonrestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localBackup.performRestore(helpher);
            }
        });

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {

            case REQUEST_CODE_SIGN_IN:
                Log.i(TAG, "Sign in request code");
                if (resultCode == RESULT_OK) {
                    remoteBackup.connectToDrive(isBackup);
                }
                break;

            case REQUEST_CODE_CREATION:
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "Backup successfully saved.");
                    Toast.makeText(this, "Backup successufly loaded!", Toast.LENGTH_SHORT).show();
                }
                break;

            case REQUEST_CODE_OPENING:
                if (resultCode == RESULT_OK) {
                    DriveId driveId = data.getParcelableExtra(
                            OpenFileActivityOptions.EXTRA_RESPONSE_DRIVE_ID);
                    remoteBackup.mOpenItemTaskSource.setResult(driveId);
                } else {
                    remoteBackup.mOpenItemTaskSource.setException(new RuntimeException("Unable to open file"));
                }

        }
    }


}
