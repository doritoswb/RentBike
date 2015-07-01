package application.brent.com.rentbike;

import android.app.Application;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.location.LocationClient;

/**
 * Created by bwu on 2015/4/12.
 */
public class RentBikeApplication extends Application {

    private static final String TAG = "RentBikeApplication";

    public LocationClient locationClient;

    public final void onCreate() {
        super.onCreate();

        init();

        Log.i(TAG, "application start()");
    }

    private void init(){
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(getApplicationContext());
        locationClient = new LocationClient(this.getApplicationContext());

        MyConnectivityManager.getInstance().init(this);
    }

    public final void onTerminate() {
        super.onTerminate();
        Log.i(TAG, "application terminate()");
    }
}
