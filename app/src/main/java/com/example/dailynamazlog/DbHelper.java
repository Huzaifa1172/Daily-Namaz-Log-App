package com.example.dailynamazlog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    public static final String dbName="Students.db";
    public static final String studentTableName="Students";
    public static final String reportTableName="Report";

    public static final String columnID="id";
    public static final String columnName="name";
    public static final String columnFatherName="fathername";
    public static final String columnRollNumber="RollNumber";

    public static final String ReportColumnID="id";
    public static final String ReportColumnStudentID="StudentID";
    public static final String ReportColumnDate="Date";
    public static final String ReportColumnFajar="Fajar";
    public static final String ReportColumnZuhr="Zuhr";
    public static final String ReportColumnAsar="Asar";
    public static final String ReportColumnMaghrib="Maghrib";
    public static final String ReportColumnEsha="Esha";


    public DbHelper(@Nullable Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String studentTable="CREATE TABLE IF NOT EXISTS "+ studentTableName + " ("
                + columnID + " integer primary key autoincrement, "
                + columnName + " varchar(50), "
                + columnFatherName + " varchar(50), "
                + columnRollNumber + " varchar(6)"
                + ")";

        String reportTable = "CREATE TABLE IF NOT EXISTS " + reportTableName + "("
                + ReportColumnID + " integer primary key autoincrement, "
                + ReportColumnStudentID + " integer not null, "//Foreign key refrences " + studentTableName + "("+ columnID +"),"
                + ReportColumnDate + " date not null, "
                + ReportColumnFajar + " varchar(20), "
                + ReportColumnZuhr + " varchar(20), "
                + ReportColumnAsar + " varchar(20), "
                + ReportColumnMaghrib + " varchar(20), "
                + ReportColumnEsha + " varchar(20), "
                + "CONSTRAINT FK_StudentID FOREIGN KEY ("+ReportColumnStudentID+")" + " REFERENCES "+ studentTableName +"(" + columnID +"))";

        String setIndex = "Create Index idx_stdID"
                + " ON " + reportTableName + " (" + ReportColumnStudentID + ") ";

        sqLiteDatabase.execSQL(studentTable);
        sqLiteDatabase.execSQL(reportTable);
        sqLiteDatabase.execSQL(setIndex);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String delStudent="drop table if exists "+ studentTableName;
        String delReport="drop table if exists "+ reportTableName;
        sqLiteDatabase.execSQL(delStudent);
        sqLiteDatabase.execSQL(delReport);
        onCreate(sqLiteDatabase);
    }

    public boolean addStudent(Student s){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(columnName,s.name);
        values.put(columnFatherName,s.father_name);
        values.put(columnRollNumber,s.rollnum);

        if(db.insert(studentTableName,null,values)!=-1){
            db.close();
            return true;
        }db.close();
        return false;

    }

    public List<String> getStudents(){
        String query="select * from "+studentTableName + ";";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        //List<Student> list=null;
        List<String> list=new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                //list.add(new Student(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
                list.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;

    }


    public boolean insertNamaz(String[] str,long id){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(ReportColumnStudentID,id);
        values.put(ReportColumnFajar,str[0]);
        values.put(ReportColumnZuhr,str[1]);
        values.put(ReportColumnAsar,str[2]);
        values.put(ReportColumnMaghrib,str[3]);
        values.put(ReportColumnEsha,str[4]);
        if(db.insert(reportTableName,null,values)!=-1){
            db.close();
            return true;
        }
        db.close();
        return false;
    }

    public List<String> getReport(long id){
        String sql="select * from "+reportTableName + "where "+ReportColumnStudentID + "="+id;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
        //List<Student> list=null;
        List<String> list=new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                //list.add(new Student(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
                list.add(cursor.getString(0)+","+cursor.getString(2)+","+cursor.getString(3)+","+cursor.getString(4)+","+cursor.getString(5)+","+cursor.getString(6));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;


    }

}
