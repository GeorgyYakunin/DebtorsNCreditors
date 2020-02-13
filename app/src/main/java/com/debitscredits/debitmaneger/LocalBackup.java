/*
 *   Copyright 2016 Marco Gomiero
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.debitscredits.debitmaneger;

import android.os.Environment;
import androidx.appcompat.app.AlertDialog;
import android.text.InputType;
import android.text.format.DateFormat;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;


import java.io.File;
import java.util.Date;

public class LocalBackup {

    private ActivityBackup activity;


    public LocalBackup(ActivityBackup activity) {
        this.activity = activity;
    }

    public void performBackup(final DatabaseHelpher db, final String outFileName) {

        Permissions.verifyStoragePermissions(activity);

        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + activity.getResources().getString(R.string.app_name));


        boolean success = true;
        if (!folder.exists())
            success = folder.mkdirs();
        if (success) {

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Backup Name");
            final EditText input = new EditText(activity);
            Date d1 = new Date();
            CharSequence g1 = DateFormat.format("dd-MM-yyyy",d1.getTime());
            String currentdate = g1.toString();
            input.setInputType(InputType.TYPE_CLASS_DATETIME);
            builder.setView(input);
           input.setText(currentdate);
            builder.setPositiveButton("Save", (dialog, which) -> {
                String m_Text = input.getText().toString();
                String out = outFileName + m_Text;
                db.backup(out);
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        } else
            Toast.makeText(activity, "Unable to create directory. Retry", Toast.LENGTH_SHORT).show();
    }

    public void performRestore(final DatabaseHelpher db) {

        Permissions.verifyStoragePermissions(activity);

        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + activity.getResources().getString(R.string.app_name));
        if (folder.exists()) {

            final File[] files = folder.listFiles();
            try {
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(activity, android.R.layout.select_dialog_item);
                for (File file : files)
                    arrayAdapter.add(file.getName());
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(activity);
                builderSingle.setTitle("Restore:");
                builderSingle.setNegativeButton(
                        "cancel",
                        (dialog, which) -> dialog.dismiss());
                builderSingle.setAdapter(
                        arrayAdapter,
                        (dialog, which) -> {
                            try {
                                db.importDB(files[which].getPath());
                            } catch (Exception e) {
                                Toast.makeText(activity, "Unable to restore. Retry", Toast.LENGTH_SHORT).show();
                            }
                        });
                builderSingle.show();
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }




        } else
            Toast.makeText(activity, "Backup folder not present.\nDo a backup before a restore!", Toast.LENGTH_SHORT).show();
    }

}
