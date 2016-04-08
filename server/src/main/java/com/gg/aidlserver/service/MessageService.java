package com.gg.aidlserver.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.gg.server.IMessageService;
import com.gg.server.MainObject;

/**
 * Created by phearum on 4/5/2016.
 */
public class MessageService extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IMessageService.Stub mBinder = new IMessageService.Stub() {
        private String mMessage = "Hello Guys!";
        private MainObject mMainObject;

        @Override
        public String getMessage() throws RemoteException {
            return this.mMessage;
        }

        @Override
        public void setMessage(String message) throws RemoteException {
            this.mMessage = message;
        }

        @Override
        public MainObject getObject() throws RemoteException {
            return mMainObject;
        }

        @Override
        public void setObject(MainObject obj) throws RemoteException {
            mMainObject = obj;
        }
    };
}
