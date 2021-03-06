package com.example.adamm.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements SensorEventListener {

    private SmsListener SMSReceiver;
    private static final int PERMISSION_REQUEST_CODE_READ = 1;
    private static final int PERMISSION_REQUEST_CODE_RECEIVE = 1;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    Menu appMenu = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showMessageButton();



        //registerForContextMenu(tv);
        requireSmsReadingPermission();
        SMSReceiver = new SmsListener();
        registerReceiver(SMSReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }



    private void requireSmsReadingPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_SMS)
                    == PackageManager.PERMISSION_DENIED) {
                Log.d("permission", "permission denied to READ_SMS - requesting it");
                String[] permissions = {Manifest.permission.READ_SMS};
                requestPermissions(permissions, PERMISSION_REQUEST_CODE_READ);
            }
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.RECEIVE_SMS)
                    == PackageManager.PERMISSION_DENIED) {
                Log.d("permission", "permission denied to RECEIVE_SMS - requesting it");
                String[] permissions = {Manifest.permission.RECEIVE_SMS};
                requestPermissions(permissions, PERMISSION_REQUEST_CODE_RECEIVE);
            }
        }
    }



    public void showMessageButton(){
        Button btn1 = findViewById(R.id.button1);
        final MainActivity a = this;
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(a, SmsActivity.class);
                startActivity(i);
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

        }else if (id == R.id.menu_exit) {
            finish();
            System.exit(0);
        } else if (id == 100){
            Toast.makeText(
                    MainActivity.this,
                    "Nacisnąłeś przycisk dodany programowo",
                    Toast.LENGTH_LONG
            ).show();
        } else if (id == 200){
            Intent intent = new Intent(this, StudentActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void AddRegularMenuItem(Menu menu) {
        int base=Menu.FIRST;
        menu.add(base,100,100,"Pozycja dodana programowo");
        menu.add(base, 200, 200, "Studenci");
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

        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        TextView tvX = (TextView)this.findViewById(R.id.tvaX);
        TextView tvY = (TextView)this.findViewById(R.id.tvaY);
        TextView tvZ = (TextView)this.findViewById(R.id.tvaZ);
        tvX.setText("X axis" + "\t\t" + x);
        tvY.setText("Y axis" + "\t\t" + y);
        tvZ.setText("Z axis" + "\t\t" + z);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

}
