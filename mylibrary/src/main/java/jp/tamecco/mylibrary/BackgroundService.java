package jp.tamecco.mylibrary;

/**
 * //TODO: Javadoc on jp.tamecco.mylibrary.BackgroundService
 *
 * @author Damien
 * @version //TODO version
 * @since 2016-07-12
 */
public class BackgroundService {

    private static SuperSdkPrime mSdkPrime;

    private BackgroundService(SdkPrime sdkPrime) {
        mSdkPrime = (SuperSdkPrime) sdkPrime;
    }

    public static BackgroundService with(SdkPrime sdkPrime) {
        return new BackgroundService(sdkPrime);
    }

    public void doSomething() {
        if (mSdkPrime != null && mSdkPrime.isConnected()) {
            mSdkPrime.doSomething();
        }
    }

    public void returnNumber() {
        if (mSdkPrime != null && mSdkPrime.isConnected()) {

        }
    }

}
