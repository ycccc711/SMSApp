package sg.edu.rp.c346.smsapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText to,context;
    Button send, sendVia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        to = findViewById(R.id.etTo);
        context = findViewById(R.id.etContent);
        send = findViewById(R.id.btnSend);
        sendVia = findViewById(R.id.btnSendVia);

        checkPermission();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pp = to.getText().toString();
                String [] pps = pp.split(",");

                String message = context.getText().toString();

                SmsManager smsManager = SmsManager.getDefault();
                for(int i = 0; i<pps.length;i++){
                    smsManager.sendTextMessage(pps[i],null,message,null,null);
                    Toast.makeText(MainActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
                    clear();
                }

            }
        });
        sendVia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri smsUri = Uri.parse("sms:"+to.getText().toString());
                Intent smsIntent = new Intent(Intent.ACTION_VIEW, smsUri);
                smsIntent.putExtra("address", smsUri);
                smsIntent.putExtra("sms_body",context.getText().toString());
                startActivity(smsIntent);
            }
        });
    }
    private void checkPermission() {
        int permissionSendSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        int permissionRecvSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS);
        if (permissionSendSMS != PackageManager.PERMISSION_GRANTED &&
                permissionRecvSMS != PackageManager.PERMISSION_GRANTED) {
            String[] permissionNeeded = new String[]{Manifest.permission.SEND_SMS,
                    Manifest.permission.RECEIVE_SMS};
            ActivityCompat.requestPermissions(this, permissionNeeded, 1);
        }
    }
    public void clear(){
        context.setText(" ");
    }

}
