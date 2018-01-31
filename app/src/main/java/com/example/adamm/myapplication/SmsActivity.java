package com.example.adamm.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SmsActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        setSmsSending();
        requireSmsSendingPermission();
    }

    private void requireSmsSendingPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_DENIED) {
                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.SEND_SMS};
                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            }
        }
    }

    private void setSmsSending() {
        ((Button)findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                String telephone = ((EditText)findViewById(R.id.editText2phone)).getText().toString();
                String message = ((EditText)findViewById(R.id.editText3sms)).getText().toString();
                smsManager.sendTextMessage(telephone, null, message, null, null);
                Toast.makeText(
                        SmsActivity.this,
                        "Wiadomość została wysłana",
                        Toast.LENGTH_LONG
                ).show();
            }
            catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                    "Nie udało się wysłać SMSa",
                    Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            }
        });
    }
}
