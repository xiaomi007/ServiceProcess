package jp.tamecco.mylibrary;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * //TODO: Javadoc on jp.tamecco.mylibrary.MySdkService
 *
 * @author Damien
 * @version //TODO version
 * @since 2016-07-12
 */
public class MySdkService extends Service {
    private static final String TAG = MySdkService.class.getSimpleName();
    public static final String ACTION_AFTER_DO_SOMETHING = "lolilol";

    private BackgroundInterface backgroundInterface;
    private SdkBinder iBinder = new SdkBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
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

    public class SdkBinder extends Binder {
        public MySdkService getService() {
            return MySdkService.this;
        }
    }

    public void doSomething() {
        backgroundInterface.doSomething();
    }


}
