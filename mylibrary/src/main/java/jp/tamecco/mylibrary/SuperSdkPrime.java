package jp.tamecco.mylibrary;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

/**
 * //TODO: Javadoc on jp.tamecco.mylibrary.SuperSdkPrime
 *
 * @author Damien
 * @version //TODO version
 * @since 2016-07-12
 */
final class SuperSdkPrime extends SdkPrime {

    private Context mContext;
    private volatile boolean mIsConnected;
    private ISdkInterface sdkInterface;
    private final OnConnectionCallback mOnConnectionCallback;
    private final OnConnectionFailedCallback mOnConnectionFailedCallback;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    public SuperSdkPrime(Context context, OnConnectionCallback onConnectionCallback, OnConnectionFailedCallback onConnectionFailedCallback) {
        mContext = context;
        mOnConnectionCallback = onConnectionCallback;
        mOnConnectionFailedCallback = onConnectionFailedCallback;
    }

    @Override
    public void connect() {
        if (mContext != null) {
            Intent intent = new Intent(mContext, MySdkService2.class);
            mContext.startService(intent);
            boolean result = mContext.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
            if (!result) {
                mOnConnectionFailedCallback.onConnectionFailed();
            }
        }
    }

    @Override
    public void disconnect() {
        if (mContext != null) {
            mContext.unbindService(mServiceConnection);
        }
    }

    @Override
    public boolean isConnected() {
        return mIsConnected;
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            sdkInterface = ISdkInterface.Stub.asInterface(service);
            mIsConnected = true;
            mOnConnectionCallback.connected();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            sdkInterface = null;
            mIsConnected = false;
        }
    };

    void doSomething() {
        if (mIsConnected) {
            try {
                sdkInterface.doSomething();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    void returnNumber() {
        if (mIsConnected) {

        }
    }

}
