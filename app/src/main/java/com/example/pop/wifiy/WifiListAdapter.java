package com.example.pop.wifiy;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pop on 11/21/2016.
 */

public class WifiListAdapter extends BaseAdapter {

    private List<ScanResult> mWiFiList;
    private Context mContext;
//    private LayoutInflater mLayoutInflater;

    /**
     * Constructor
     *
     * @param mContext
     * @param mWiFiList
     */
    public WifiListAdapter(Context mContext, List<ScanResult> mWiFiList) {
        this.mContext = mContext;
        this.mWiFiList = mWiFiList;
    }

    @Override
    public int getCount() {
        return mWiFiList.size();
    }

    @Override
    public Object getItem(int position) {
        return mWiFiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            row = inflater.inflate(R.layout.row_wifi_list_adpter, null);
            holder = new ViewHolder();
            holder.tvSSID = (TextView) row.findViewById(R.id.tvSSD);


            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.tvSSID.setText("SSSID::" + mWiFiList.get(position).SSID);
        return row;
    }

    private class ViewHolder {
        private TextView tvSSID;
    }
}
