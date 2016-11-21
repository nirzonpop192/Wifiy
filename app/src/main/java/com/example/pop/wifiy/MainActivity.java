package com.example.pop.wifiy;
/**
 * wifi: list
 * target: office attendance
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    /**
     * basic views
     */
    private Button enableButton, disableButton, searchButton;
    private TextView tv_display;

    private WifiScanner mReceiver;
    private WifiManager mWifiManager;
    private StringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * basic UI
         */
        setContentView(R.layout.activity_main);

        inti();


        displayInText();




        if (mReceiver == null) {
            mReceiver = new WifiScanner(MainActivity.this);
            registerReceiver(mReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        }

        /**
         * listener
         */
        setListener();

    }

    private void displayInText() {

        List<ScanResult> wiFiList = mWifiManager.getScanResults();

        /**
         * if any wifi can ba found
         */
        if (wiFiList.size() > 0) {
            WifiInfo info = mWifiManager.getConnectionInfo();

            tv_display.append("\n\n Wifi Status:" + info.toString());


            List<WifiConfiguration> configurationList = mWifiManager.getConfiguredNetworks();
            for (WifiConfiguration config : configurationList) {
                tv_display.append("\n\n" + config.toString());
            }

        }
    }

    private void inti() {
        /**
         * refer to views in   R.layout.activity_main container basic ui
         */
        viewReferenceBasic();
        requestWifiForWifiService();

    }

    /**
     * request the system to get wifi service to manipulate
     */
    private void requestWifiForWifiService() {
        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
    }


    @Override
    protected void onStop() {
        unregisterReceiver(mReceiver);
        super.onStop();


    }

    /**
     * Unregister the previous registered broadcast receiver
     * Al filters that have been register for the broadcast
     * receiver  will be removed in  on Pause state .
     * Other wise it will affect to rest of apps
     */

    @Override
    protected void onPause() {
        unregisterReceiver(mReceiver);
        super.onPause();
    }

    /**
     * This method is a set of Listener
     */
    private void setListener() {

        enableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableWifi();


            }
        });

        disableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableWifi();


            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enableWifi();

            }
        });
    }

    /**
     * This method disable wifi
     */
    private void disableWifi() {
        // IF WIFI IS Enable
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
        }
    }

    /**
     * This method enable wifi
     */
    private void enableWifi() {
        // IF WIFI IS DISABLE
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
            Toast.makeText(MainActivity.this, "Wifi is Enabling ", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * refer xml object to java
     * basic view reference
     */
    private void viewReferenceBasic() {
        searchButton = (Button) findViewById(R.id.btn_search);
        enableButton = (Button) findViewById(R.id.btn_enableWifi);
        disableButton = (Button) findViewById(R.id.btn_disableWifi);
        tv_display = (TextView) findViewById(R.id.tv_result);
    }

    /**
     * WifiScanner is used to detect available new wifi device
     */
    private class WifiScanner extends BroadcastReceiver {
        private MainActivity mainActivity;

        public WifiScanner() {
        }

        public WifiScanner(MainActivity mMainActivity) {
            this.mainActivity = mMainActivity;
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            List<ScanResult> wiFiList = mainActivity.mWifiManager.getScanResults();
            // an Intent broadcast.

            /**
             * to find out the strongest wifi signal
             */
            // findStrongestWifiSignal(scanResultList);

            /**
             * to find out the all wifi signal
             */
            //  findAllWifiSignal(scanResultList);

            /**
             * to find out the all wifi signal show in Text View
             */
            //  findAllWifiSignal_1(wiFiList);

        }

        /**
         * This method find out all available wifi signal
         * but not accessing the text view . use too much variable
         *
         * @param scanResultList scan wifi Result List
         */

        private void findAllWifiSignal_1(List<ScanResult> scanResultList) {
            sb = new StringBuilder();
            sb.append("\n Number of the wifi Connection :" + scanResultList.size() + " \n\n");
            for (int index = 0; index < scanResultList.size(); index++) {
                sb.append(String.valueOf(index + 1) + ".>");
                sb.append(scanResultList.get(index).toString() + "\n\n");
            }

            tv_display.setText(sb);
        }

        /**
         * This method find out all available wifi signal
         * but not accessing the text view . use too much variable
         *
         * @param wifiResultList scan wifi Result List
         * @deprecated
         */
        @Deprecated
        private void findAllWifiSignal(List<ScanResult> wifiResultList) {
            String message = String.format("%s network found .\n", wifiResultList.size());
            int index = 0;
            for (ScanResult result : wifiResultList) {
                if (wifiResultList.size() > 0) {
                    message += String.format("\n %s is the strongest  ", wifiResultList.get(index).SSID);

                    ++index;
                }

            }
            /**
             * if toast massage is needed
             */
            String toastMessage = String.format("%s network found .", wifiResultList.size());
            Toast.makeText(mainActivity, message, Toast.LENGTH_SHORT).show();
        }

        /**
         * This method  find out the strongest wifi signal
         */
        private void findStrongestWifiSignal(List<ScanResult> wifiResultList) {
            ScanResult bestSignal = null;
            for (ScanResult result : wifiResultList) {
                if (bestSignal == null || WifiManager.compareSignalLevel(bestSignal.level, result.level) < 0) {
                    bestSignal = result;
                }
            }
            if (bestSignal != null) {
                String message = String.format("%s network found .%s is the strongest  ", wifiResultList.size(), bestSignal.SSID);
                Toast.makeText(mainActivity, message, Toast.LENGTH_SHORT).show();
            }

        }
    }// end of WifiScanner


}

