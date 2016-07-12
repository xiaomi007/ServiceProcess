package jp.xiaomi.serviceprocess;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * //TODO: Javadoc on jp.xiaomi.serviceprocess.MyService
 *
 * @author Damien
 * @version //TODO version
 * @since 2016-03-31
 */
public class MyService extends Service {
    private static final String TAG = MyService.class.getSimpleName();

    private final IBinder mBinder = new LocalBinder();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mBinder;
    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
    }

    public class LocalBinder extends Binder {
        MyService getService() {
            Log.d(TAG, "getService");
            return MyService.this;
        }
    }

    public int getNumber() {
        Log.d(TAG, "doSomething:" + Thread.currentThread().getName());
        return 123;
    }


}
