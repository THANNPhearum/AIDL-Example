package com.gg.aidlserver;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gg.aidlserver.service.MessageService;
import com.gg.server.IMessageService;

public class MainActivity extends AppCompatActivity {
    private IMessageService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent serviceIntent = new Intent(this, MessageService.class);
        startService(serviceIntent);
        bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);
        final EditText message = (EditText) findViewById(R.id.message);
        Button send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newMessage = message.getText().toString();
                if (!newMessage.isEmpty()) {
                    try {
                        mService.setMessage(newMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = IMessageService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            mService = null;
        }
    };
}
