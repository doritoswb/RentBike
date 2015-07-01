package application.brent.com.rentbike;

/**
 * Created by bwu on 2015/4/18.
 */

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.util.ArrayList;
import java.util.List;

public class MyConnectivityManager extends BroadcastReceiver {
    private static MyConnectivityManager instance = new MyConnectivityManager();

    public static MyConnectivityManager getInstance() {
        return instance;
    }

    private Application application;
    private List<MyConnectivityListener> listeners = new ArrayList<MyConnectivityListener>();
    private boolean isStarted;

    public void init(Application application) {
        this.application = application;
    }

    public synchronized void start() {
        if (this.isStarted)
            return;

        this.isStarted = true;

        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        this.application.registerReceiver(this, filter);
    }

    public synchronized void stop() {
        if (!this.isStarted)
            return;

        this.application.unregisterReceiver(this);
        this.isStarted = false;
    }

    public synchronized void addListener(MyConnectivityListener listener) {
        if (this.listeners.contains(listener))
            return;

        this.listeners.add(listener);
    }

    public synchronized void removeListener(MyConnectivityListener listener) {
        this.listeners.remove(listener);
    }

    public boolean isNetworkAvailable() {
        TelephonyManager telephonyManager = (TelephonyManager) this.application.getSystemService(Context.TELEPHONY_SERVICE);
        ConnectivityManager connectivityManager = (ConnectivityManager) this.application.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo == null) {
            return telephonyManager.getDataState() != TelephonyManager.DATA_DISCONNECTED;

        } else {
            return activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected() ||
                    (activeNetworkInfo.isAvailable() || activeNetworkInfo.isConnected()) &&
                            isDataNetworkType(activeNetworkInfo.getType()) &&
                            telephonyManager.getDataState() != TelephonyManager.DATA_DISCONNECTED;
        }
    }

    public boolean isWifiAvailable() {
        if (!isNetworkAvailable())
            return false;

        WifiManager wifiManager = (WifiManager) this.application.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();

            return wifiInfo != null && SupplicantState.COMPLETED == wifiInfo.getSupplicantState();
        }

        return false;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (!ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
            return;
        }

        synchronized (this) {
            for (MyConnectivityListener listener : this.listeners) {
                listener.updateConnectivityStatus(this.isNetworkAvailable(), this.isWifiAvailable());
            }
        }
    }

    private boolean isDataNetworkType(int type) {
        return type == ConnectivityManager.TYPE_MOBILE || type == ConnectivityManager.TYPE_WIFI || type == ConnectivityManager.TYPE_WIMAX;
    }
}

