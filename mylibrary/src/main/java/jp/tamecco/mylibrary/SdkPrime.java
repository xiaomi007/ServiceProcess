package jp.tamecco.mylibrary;

import android.content.Context;

/**
 * //TODO: Javadoc on jp.tamecco.mylibrary.SdkPrime
 *
 * @author Damien
 * @version //TODO version
 * @since 2016-07-12
 */
public abstract class SdkPrime {

    public abstract void connect();

    public abstract void disconnect();

    public abstract boolean isConnected();

    public SdkPrime() {
    }

    public static class Builder {

        private Context pContext;
        private OnConnectionCallback onConnectionCallback;
        private OnConnectionFailedCallback pOnConnectionFailedCallback;

        public Builder(Context pContext) {
            this.pContext = pContext;
        }

        public Builder(Context context, OnConnectionCallback onConnectionCallback, OnConnectionFailedCallback onConnectionFailedCallback) {

            pContext = context;
            this.onConnectionCallback = onConnectionCallback;
            pOnConnectionFailedCallback = onConnectionFailedCallback;
        }

        public SdkPrime.Builder addOnConnectionCallback(OnConnectionCallback connectionCallback) {
            onConnectionCallback = connectionCallback;
            return this;
        }

        public SdkPrime.Builder addOnConnectionFailedCallback(OnConnectionFailedCallback connectionFailedCallback){
            pOnConnectionFailedCallback = connectionFailedCallback;
            return this;
        }


        public SdkPrime build() {
            return new SuperSdkPrime(pContext, onConnectionCallback, pOnConnectionFailedCallback);
        }
    }

    public interface OnConnectionCallback {
        void connected();
    }

    public interface OnConnectionFailedCallback {
        void onConnectionFailed();
    }

}
