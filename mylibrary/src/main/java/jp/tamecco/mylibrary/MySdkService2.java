package jp.tamecco.mylibrary;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * //TODO: Javadoc on jp.tamecco.mylibrary.MySdkService2
 *
 * @author Damien
 * @version //TODO version
 * @since 2016-07-12
 */
public class MySdkService2 extends Service {

    private static final String TAG = MySdkService.class.getSimpleName();
    public static final String ACTION_AFTER_DO_SOMETHING = "lolilol";

    private BackgroundInterface backgroundInterface;
    private ISdkInterface.Stub sdkInterface = new ISdkInterface.Stub() {
        @Override
        public void doSomething() throws RemoteException {
            backgroundInterface.doSomething();
        }

    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return sdkInterface.asBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        backgroundInterface = new BackgroundLogic(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind: ");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d(TAG, "onTaskRemoved");
    }
}
