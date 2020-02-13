package com.debitscredits.debitmaneger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import androidx.annotation.RequiresApi;
import android.widget.Toast;

import com.debitscredits.debitmaneger.Model.Planet;
import com.debitscredits.debitmaneger.Model.Planettake;
import com.debitscredits.debitmaneger.Model.ReminderPlanet;
import com.debitscredits.debitmaneger.Model.RepoetPlanet;
import com.debitscredits.debitmaneger.Model.ReportDetailsPlanet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelpher extends SQLiteOpenHelper {

public static final String DATABSE_NAME= "DebitManeger";
    public static final String DBLOCATIONBACKUP = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static final String TABLE_GIVETAKE="GIVETAKE";
    public static final String COL0_GIVETAKE="Id";
    public static final String COL1_GIVETAKE="Name";
    public static final String COL2_GIVETAKE="Amount";
    public static final String COL3_GIVETAKE="Description";
    public static final String COL4_GIVETAKE="CDate";
    public static final String COL5_GIVETAKE="DueDate";
    public static final String COL6_GIVETAKE="Type";


    public static final String TABLE_UserReport="USERREPORT";
    public static final String COL0_UserReport="User_Id";
    public static final String COL1_UserReport="User_Name";
    public static final String COL2_UserReport="User_GiveAmount";
    public static final String COL3_UserReport="User_TakeAmount";
    public static final String COL4_UserReport="User_Balance";


    public static final String TABLE_User_Master="UserMaster";
    public static final String COL0_Add_User_Name="User_Master_Id";
    public static final String COL1_Add_User_Name="User_Master_Name";

    public static final String TABLE_Remainder="Remaindert";
    public static final String COL0_Remainder="RId";
    public static final String COL1_Remainder="RName";
    public static final String COL2_Remainder="RAmount";
    public static final String COL3_Remainder="RDescription";
    public static final String COL4_Remainder="RDate";
    public static final String COL5_Remainder="RDueDate";
    public static final String COL6_Remainder="RType";
    public static final String COL7_Remainder="RStatus";

    private Context mContext;

    public DatabaseHelpher(Context context) {
        super(context, DATABSE_NAME, null, 1);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " +TABLE_GIVETAKE+ "(Id Integer  primary key autoincrement, Name Text ,Amount int, Description Text,CDate Text,DueDate Text,Type Text)");
        db.execSQL("create table " +TABLE_UserReport+ "(User_Id Integer primary key autoincrement, User_Name Text, User_GiveAmount Text, User_TakeAmount Text,User_Balance Text)");
        db.execSQL("create table " +TABLE_User_Master+ "(User_Master_Id Integer primary key autoincrement, User_Master_Name Text)");
        db.execSQL("create table " +TABLE_Remainder+ "(RId Integer primary key autoincrement, RName Text, RAmount Text, RDescription Text, RDate Text, RDueDate Text, RType Text, RStatus Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE " +TABLE_GIVETAKE);
        db.execSQL(" DROP TABLE " +TABLE_UserReport);
        db.execSQL(" DROP TABLE " +TABLE_User_Master);
        db.execSQL(" DROP TABLE " +TABLE_Remainder);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }

public Boolean GiveTakeData(String name,int amount,String des,String cdate,String duedate,String type)
{
    SQLiteDatabase db= getWritableDatabase();
    ContentValues contentValues=new ContentValues();

    contentValues.put(COL1_GIVETAKE,name);
    contentValues.put(COL2_GIVETAKE,amount);
    contentValues.put(COL3_GIVETAKE,des);
    contentValues.put(COL4_GIVETAKE,cdate);
    contentValues.put(COL5_GIVETAKE,duedate);
    contentValues.put(COL6_GIVETAKE,type);

    long result = db.insert(TABLE_GIVETAKE, null, contentValues);

    if (result == -1) {
        return false;
    } else {
        return true;
    }
}

    public Boolean AddRemainderData(String name,String amount, String des, String cdate, String duedate, String type)
    {
        SQLiteDatabase db= getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(COL1_Remainder,name);
        contentValues.put(COL2_Remainder,amount);
        contentValues.put(COL3_Remainder,des);
        contentValues.put(COL4_Remainder,cdate);
        contentValues.put(COL5_Remainder,duedate);
        contentValues.put(COL6_Remainder,type);

        long result = db.insert(TABLE_Remainder, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean AddUserName(String name)
    {
        SQLiteDatabase db= getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(COL1_Add_User_Name,name);

        long result = db.insert(TABLE_User_Master, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor fillname(String name)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from UserMaster where User_Master_Name = ?",new String[]{name});
        return cursor;

    }

    public List<String> getAllLabels(){
        List<String> labels = new ArrayList<String>();

        String selectQuery = "SELECT  * FROM " +TABLE_User_Master;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return labels;
    }

    public Integer DeleteData(String id)
    {
        SQLiteDatabase db= getWritableDatabase();
        db.delete(TABLE_GIVETAKE,"Id = ?",new String[]{id});
        return 0;
    }


    public Boolean UpdateGiveTakeData(String id, String name, String amount, String des, String cdate, String duedate, String type)
    {
        SQLiteDatabase db= getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL0_GIVETAKE,id);
        contentValues.put(COL1_GIVETAKE,name);
        contentValues.put(COL2_GIVETAKE,amount);
        contentValues.put(COL3_GIVETAKE,des);
        contentValues.put(COL4_GIVETAKE,cdate);
        contentValues.put(COL5_GIVETAKE,duedate);
        contentValues.put(COL6_GIVETAKE,type);

        db.update(TABLE_GIVETAKE, contentValues, "Id = ?", new String[]{id});
       return true;
    }

    public Planet getProductById(int id) {
         SQLiteDatabase db = this.getWritableDatabase();
        Planet planet = null;
        Cursor cursor = db.rawQuery("select Id,Name,Amount,Description,CDate,DueDate,Type from GIVETAKE Where Id = ?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        planet = new Planet(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
        cursor.close();
        return planet;
    }


    public Boolean UserReportData(String name,int giveamount, int takeamt, int balance)
    {
        SQLiteDatabase db= getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(COL1_UserReport,name);
        contentValues.put(COL2_UserReport,giveamount);
        contentValues.put(COL3_UserReport,takeamt);
        contentValues.put(COL4_UserReport,balance);


        long result = db.insert(TABLE_UserReport, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public List<Planet> getListgive(String type) {
        SQLiteDatabase db = getWritableDatabase();
        Planet planet = null;
        List<Planet>  mPlanetlis = new ArrayList<>();
        Cursor cursor = db.rawQuery("select Id,Name,Amount,Description,CDate,DueDate,Type from GIVETAKE Where Type = ?", new String[]{type});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            planet = new Planet(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
            mPlanetlis.add(planet);
            cursor.moveToNext();
        }

        cursor.close();
        return  mPlanetlis;

    }

    public List<Planettake> getListtake(String type) {
        SQLiteDatabase db = getWritableDatabase();
        Planettake planet = null;
        List<Planettake>  mPlanetlis = new ArrayList<>();
        Cursor cursor = db.rawQuery("select Id,Name,Amount,Description,CDate,DueDate,Type from GIVETAKE Where Type = ?", new String[]{type});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            planet = new Planettake(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
            mPlanetlis.add(planet);
            cursor.moveToNext();
        }

        cursor.close();
        return  mPlanetlis;

    }

    public List<ReminderPlanet> getListReminder(String type) {
        SQLiteDatabase db = getWritableDatabase();
        ReminderPlanet planet = null;
        List<ReminderPlanet>  mPlanetlis = new ArrayList<>();
        Cursor cursor = db.rawQuery("select RId,RName,RAmount,RDate from Remaindert Where RType = ?", new String[]{type});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            planet = new ReminderPlanet(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            mPlanetlis.add(planet);
            cursor.moveToNext();
        }

        cursor.close();
        return  mPlanetlis;

    }


    public List<RepoetPlanet> getcombList() {
        SQLiteDatabase db = getWritableDatabase();
        RepoetPlanet planet = null;
        List<RepoetPlanet>  mPlanetlis = new ArrayList<>();

        Cursor cursor = db.rawQuery("select User_Name,User_GiveAmount,User_TakeAmount,User_Balance from " + TABLE_UserReport, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            planet = new RepoetPlanet(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3));
            mPlanetlis.add(planet);
            cursor.moveToNext();
        }

        cursor.close();
        return  mPlanetlis;

    }

    public List<ReportDetailsPlanet> getReportDetailList(String name) {
        SQLiteDatabase db = getWritableDatabase();
        ReportDetailsPlanet planet = null;
        List<ReportDetailsPlanet>  mPlanetlis = new ArrayList<>();

        Cursor cursor = db.rawQuery("select Type,CDate,DueDate,Amount from GIVETAKE Where Name = ? order by Id asc", new String[]{name});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            planet = new ReportDetailsPlanet(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3));
            mPlanetlis.add(planet);
            cursor.moveToNext();
        }

        cursor.close();
        return  mPlanetlis;

    }

     int amount;
    public int sumData(String typegive)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select sum(Amount) from GIVETAKE Where Type = ?", new String[]{typegive});
        if(c.moveToFirst())
            amount = c.getInt(0);
        else
            amount = -1;
        c.close();
        return amount;
    }

    int amount1;
    public int sumDatagive(String typetake)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select sum(Amount) from GIVETAKE Where Type = ?", new String[]{typetake});
        if(c.moveToFirst())
            amount1 = c.getInt(0);
        else
            amount1 = -1;
        c.close();
        return amount1;
    }

    int takenamt;
    public int UsersumDataTake(String takename,String type)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select sum(Amount) from GIVETAKE Where Name = ? And Type = ?", new String[]{takename,type});
        if(c.moveToFirst())
            takenamt = c.getInt(0);
        else
            takenamt = -1;
        c.close();
        return takenamt;
    }

    int givenamt;
    public int UsersumDataGive(String givename,String type)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select sum(Amount) from GIVETAKE Where Name = ? And Type = ?", new String[]{givename,type});
        if(c.moveToFirst())
            givenamt = c.getInt(0);
        else
            givenamt = -1;
        c.close();
        return givenamt;
    }

    public Cursor filllist()
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select distinct Name from " + TABLE_GIVETAKE, null);
        return cursor;
    }

    public void DeleteData()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_UserReport, null,null);
    }

    public void backup(String outFileName) {

        final String inFileName = mContext.getDatabasePath(DATABSE_NAME).toString();

        try {

            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);

            OutputStream output = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            output.flush();
            output.close();
            fis.close();

            Toast.makeText(mContext, "Backup Completed", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(mContext, "Unable to backup database. Retry", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void importDB(String inFileName) {

        final String outFileName = mContext.getDatabasePath(DATABSE_NAME).toString();

        try {

            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);

            OutputStream output = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            output.flush();
            output.close();
            fis.close();

            Toast.makeText(mContext, "Import Completed", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(mContext, "Unable to import database. Retry", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


}
