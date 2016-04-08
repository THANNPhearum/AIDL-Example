package com.phearum.getresult;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gg.server.IMessageService;

public class MainActivity extends AppCompatActivity {
    private final static String PACKAGE_NAME_SERVICE = "com.gg.aidlserver";
    private final static String CLASS_NAME_SERVICE = "com.gg.aidlserver.service.MessageService";
    private IMessageService mService;
    private TextView mLog;
    private boolean mIsBind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mLog = (TextView) findViewById(R.id.result);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsBind) {
                    try {
                        mLog.setText(String.format("Message: %s\nMainObject: %s",
                            mService.getMessage(),
                            mService.getObject().getMessage()
                        ));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        showError();
                    }
                } else {
                    showError();
                }
            }
        });
    }

    private void showError() {
        Toast.makeText(this, getString(R.string.service_not_bind), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindRemoteService();
    }

    private void bindRemoteService() {
        Intent serviceIntent = new Intent()
            .setComponent(new ComponentName(
                PACKAGE_NAME_SERVICE,
                CLASS_NAME_SERVICE));
        startService(serviceIntent);
        mIsBind = bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);
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
