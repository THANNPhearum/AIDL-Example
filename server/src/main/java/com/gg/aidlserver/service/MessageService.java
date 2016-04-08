package com.gg.aidlserver.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.gg.server.IMessageService;

/**
 * Created by phearum on 4/5/2016.
 */
public class MessageService extends Service {
    private String mMessage = "Hello Guys!";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IMessageService.Stub() {
            @Override
            public String getMessage() throws RemoteException {
                return mMessage;
            }

            @Override
            public void setMessage(String message) throws RemoteException {
                mMessage = message;
            }
        };
    }
}
