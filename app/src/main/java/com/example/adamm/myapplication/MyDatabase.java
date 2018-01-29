package com.example.adamm.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by adamm on 29.01.2018.
 */

public class MyDatabase extends SQLiteOpenHelper {
    public MyDatabase(Context context){
        super(context, "students.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table students(StudentId integer primary key autoincrement, Name text, Surname text, IndexNumber text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addStudent(String name, String surname, String indexNumber){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues student = new ContentValues();
        student.put("Name", name);
        student.put("Surname", surname);
        student.put("IndexNumber", indexNumber);
        db.insert("students", null, student);

    }

    public Cursor getStudents(){
        String[] columns = {"StudentId", "Name", "Surname", "IndexNumber"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor records = db.query("students", columns, null, null, null, null, null);
        return records;
    }
}
