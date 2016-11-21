package com.example.pop.wifiy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class MainActivityList extends BaseActivity {
    ListView mListView;
    private WifiManager mWifiManager;
    private WifiReceiver mReceiverWifi;
    private WifiListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        inti();

        mReceiverWifi = new WifiReceiver();
        registerReceiver(mReceiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        scanWifiList();


    }


    private void scanWifiList() {
        mWifiManager.startScan();
        List<ScanResult>  sWifiList = mWifiManager.getScanResults();

        setAdapter(sWifiList);

    }

    private void setAdapter(List<ScanResult> wifiList) {
        adapter = new WifiListAdapter(getApplicationContext(), wifiList);
        mListView.setAdapter(adapter);
    }

    private void inti() {

        viewReference();

        requestWifiForWifiService();
    }

    /**
     * request the system to get wifi service to manipulate
     */
    private void requestWifiForWifiService() {
        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
    }

    private class WifiReceiver extends BroadcastReceiver {
        private Context mContext;

        public WifiReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    @Override
    void viewReference() {
        mListView = (ListView) findViewById(R.id.lvWifi);
    }
}
