package jp.xiaomi.serviceprocess;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import jp.tamecco.mylibrary.BackgroundService;
import jp.tamecco.mylibrary.MySdkService2;
import jp.tamecco.mylibrary.SdkPrime;

/**
 * //TODO: Javadoc on jp.xiaomi.serviceprocess.MainActivity2
 *
 * @author Damien
 * @version //TODO version
 * @since 2016-07-12
 */
public class MainActivity2 extends AppCompatActivity {
    private static final String TAG = MainActivity2.class.getSimpleName();
    private SdkPrime mSdkPrime;

    private long time;

    public BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                final String action = intent.getAction();
                if (action != null) {
                    Toast.makeText(context, action, Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSdkPrime = new SdkPrime.Builder(
                this,
                new SdkPrime.OnConnectionCallback() {
                    @Override
                    public void connected() {
                        Log.d(TAG, "connected:" + (SystemClock.elapsedRealtime() - time));
                    }
                },
                new SdkPrime.OnConnectionFailedCallback() {
                    @Override
                    public void onConnectionFailed() {
                        Log.d(TAG, "onConnectionFailed");
                    }
                })
                .build();
    }


    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(MySdkService2.ACTION_AFTER_DO_SOMETHING);
        registerReceiver(br, intentFilter);
        mSdkPrime.connect();
        time = SystemClock.elapsedRealtime();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mSdkPrime.disconnect();
        unregisterReceiver(br);
    }


    public void doSomething(View view) {
        BackgroundService.with(mSdkPrime).doSomething();
    }

    public void getNumber(View view) {


    }
}

