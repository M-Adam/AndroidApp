package com.example.adamm.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Menu appMenu = null;
    TextView tv = null;
    MyDatabase mydb = new MyDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showMessageButton();
        setupAddingStudent();

        tv = this.findViewById(R.id.textViewId);
        tv.setText("Menu kontekstowe");
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        registerForContextMenu(tv);
    }

    public void setupAddingStudent(){
        Button btn2 = findViewById(R.id.button);
        btn2.setOnClickListener(addStudentListener());
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

    public void showMessageButton(){
        Button btn1 = findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        MainActivity.this,
                        "Nacisnąłeś przycisk",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu); //ładowanie menu z pliku xml
        appMenu = menu;
        AddRegularMenuItem(appMenu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_start) {
            Intent intent = new Intent(this, ContactActivity.class);
            startActivity(intent);
        }else if (id == R.id.menu_message) {
            tv.setText("Wiadomość z aplikacji");
            tv.setTextColor(Color.RED);
            tv.setTextSize(50);
        }else if (id == R.id.menu_exit) {
            finish();
            System.exit(0);
        } else if (id == 100){
            Toast.makeText(
                    MainActivity.this,
                    "Nacisnąłeś przycisk dodany programowo",
                    Toast.LENGTH_LONG
            ).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void AddRegularMenuItem(Menu menu) {
        int base=Menu.FIRST;
        menu.add(base,100,100,"Pozycja dodana programowo");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo
            menuInfo) {
        menu.setHeaderTitle("Menu kontekstowe");
        menu.add(200, 200, 200, "Fragmenty - galeria");
        menu.add(300, 300, 300, "Element 2");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == 200) {
            Intent intent = new Intent(this, GalleryActivity.class);
            intent.putExtra("data", "Dodatkowe dane");
            startActivity(intent);
        }else if (id == 300) {
            tv.append("\n Kontekstowe 2");
        }
        return super.onContextItemSelected(item);
    }
}
