package jp.xiaomi.serviceprocess;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import jp.tamecco.mylibrary.ISdkInterface;
import jp.tamecco.mylibrary.MySdkService;
import jp.tamecco.mylibrary.MySdkService2;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

//    MyService myService;
//    MyService.LocalBinder binder;

    ISdkInterface iSdkInterface;
    MySdkService mySdkService;
    MySdkService.SdkBinder sdkBinder;

    HandlerThread handlerThread;
    Handler handler;
    Looper looper;

    boolean mBound = false;

    TextView mTextView;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                final String action = intent.getAction();
                if (action != null) {
                    Log.d(TAG, "onReceive: " + Thread.currentThread().getName());
                    Log.d(TAG, "onReceive: " + action);
                    if (context != null) {
                        Toast.makeText(context, action, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textView);

        Log.d(TAG, "onCreate: " + Process.myPid());
        Log.d(TAG, "onCreate: " + Thread.currentThread().getName());
        handlerThread = new HandlerThread("master", Process.THREAD_PRIORITY_DEFAULT);
        handlerThread.start();
        looper = handlerThread.getLooper();
        handler = new Handler(looper);

        IntentFilter intentFilter = new IntentFilter(MySdkService.ACTION_AFTER_DO_SOMETHING);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);

        Intent intent = new Intent(this, MySdkService2.class);
        intent.setAction(MySdkService2.class.getName());
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
//        IntentFilter intentFilter = new IntentFilter(MySdkService.ACTION_AFTER_DO_SOMETHING);
//        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);
//
//        Intent intent = new Intent(this, MySdkService2.class);
//        intent.setAction(MySdkService2.class.getName());
//        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
//        if (mBound) {
//            unbindService(mConnection);
//            mBound = false;
//        }
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);

    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
//            sdkBinder = (MySdkService.SdkBinder) service;
//            mySdkService = sdkBinder.getService();
            iSdkInterface = ISdkInterface.Stub.asInterface(service);
            mBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
            iSdkInterface = null;
            mBound = false;

        }
    };

    public void doSomething(View view) {
        Log.d(TAG, "doSomething: ");
        try {
            iSdkInterface.doSomething();
        } catch (RemoteException e) {
            Log.d(TAG, "doSomething: " + e);
        }
//        mySdkService.doSomething();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                if (mBound) {
//                    Log.d(TAG, "run: do something");
//                    mySdkService.doSomething();
//                }
//            }
//        });

    }

    public void getNumber(View view) {
    }
}
