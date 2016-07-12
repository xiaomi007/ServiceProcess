package jp.tamecco.mylibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * //TODO: Javadoc on jp.tamecco.mylibrary.BackgroundLogic
 *
 * @author Damien
 * @version //TODO version
 * @since 2016-07-12
 */
class BackgroundLogic implements BackgroundInterface {
    private static final String TAG = BackgroundLogic.class.getSimpleName();
    private Handler mHandler;
    private final WeakReference<Context> mContextWeakReference;

    BackgroundLogic(Context context) {
        mContextWeakReference = new WeakReference<>(context);
        final HandlerThread mHandlerThread = new HandlerThread("background");
        mHandlerThread.start();
        final Looper mLooper = mHandlerThread.getLooper();
        mHandler = new Handler(mLooper);
    }

    @Override
    public void doSomething() {
        Log.d(TAG, "doSomething: " + Thread.currentThread().getName());

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1; i++) {
                    Log.d(TAG, "run:" + i +" sleep " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "run: ", e);
                    }
                    if (mContextWeakReference.get() != null) {
                        Log.d(TAG, "run: send LBM");
                        LocalBroadcastManager.getInstance(mContextWeakReference.get())
                                .sendBroadcast(new Intent(MySdkService.ACTION_AFTER_DO_SOMETHING));
                    } else {
                        Log.d(TAG, "run: context null");
                    }
                    Log.d(TAG, "run: wake");
                }
            }
        });
    }

    @Override
    public void returnNumber() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
