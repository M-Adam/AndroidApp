package com.example.adamm.myapplication;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StudentActivity extends AppCompatActivity {

    TextView tv;
    MyDatabase mydb = new MyDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        setupAddingStudent();

        tv = this.findViewById(R.id.textViewId);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    public View.OnClickListener addStudentListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, surname, index;
                name = ((EditText) findViewById(R.id.editTextName)).getText().toString();
                surname = ((EditText) findViewById(R.id.editTextSurname)).getText().toString();
                index = ((EditText) findViewById(R.id.editTextIndex)).getText().toString();
                mydb.addStudent(name, surname, index);

                tv.setText("Lista studentów: ");
                Cursor c = mydb.getStudents();
                while(c.moveToNext()){
                    tv.append("\n " + c.getString(0) + ". " + c.getString(1) + " " + c.getString(2) + " (" + c.getString(3) + "); ");
                }
            }
        };
    }

    public void setupAddingStudent(){
        Button btn2 = findViewById(R.id.button);
        btn2.setOnClickListener(addStudentListener());
    }
}
