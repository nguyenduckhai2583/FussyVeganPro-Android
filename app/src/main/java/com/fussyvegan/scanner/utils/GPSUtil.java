package com.fussyvegan.scanner.utils;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.fussyvegan.scanner.R;

public class GPSUtil extends Service implements LocationListener {
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5;
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000;
    private static final String TAG = GPSUtil.class.getSimpleName();
    private Context mContext = null;
    private boolean mCanGetLocation = false;
    private Location mLocation = null;
    private double mLatitude;
    private double mLongitude;
    private boolean mIsGPSEnabled;
    private boolean mIsNetworkEnabled;
    private LocationManager mLocationManager;
    private boolean mIsShow;
    private TurnOnGPS mTurnOnGps;

    public GPSUtil() {
    }

    public GPSUtil(Context context, boolean isShowPopUp) {
        mContext = context;
    }

    public Location getCurrentLocation(boolean isShowPopup) {
        if (!canGetLocation() && isShowPopup) {
            showSettingsAlert();
        } else {
            this.mCanGetLocation = true;
            try {
                mLocationManager = (LocationManager) mContext
                        .getSystemService(LOCATION_SERVICE);
                // getting GPS status
                mIsGPSEnabled = mLocationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);
                // getting network status
                mIsNetworkEnabled = mLocationManager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                if (mIsNetworkEnabled) {
                    mLocationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES,this);
                    mLocation = mLocationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (mLocationManager != null) {
                        mLocation = mLocationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                    if (mLocation != null) {
                        mLatitude = mLocation.getLatitude();
                        mLongitude = mLocation.getLongitude();
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (mIsGPSEnabled && mLocation == null) {
                    mLocationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    mLocation = mLocationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (mLocation != null) {
                        mLatitude = mLocation.getLatitude();
                        mLongitude = mLocation.getLongitude();
                    }
                }
            } catch (SecurityException e) {
                Log.e(TAG, String.valueOf(e));
            }
        }
        return mLocation;
    }

    /**
     * Function to get latitude
     */
    public double getLatitude() {
        if (mLocation != null) {
            mLatitude = mLocation.getLatitude();
        }
        return mLatitude;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude() {
        if (mLocation != null) {
            mLongitude = mLocation.getLongitude();
        }
        return mLongitude;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */
    public boolean canGetLocation() {
        mLocationManager = (LocationManager) mContext
                .getSystemService(LOCATION_SERVICE);
        mIsGPSEnabled = mLocationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        mIsNetworkEnabled = mLocationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        this.mCanGetLocation = !(!mIsGPSEnabled && !mIsNetworkEnabled);
        return this.mCanGetLocation;
    }

    /**
     * Function to show settings alert dialog On pressing Settings button will
     * launch Settings Options
     */
    public void showSettingsAlert() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        alertDialog
                .setMessage(mContext.getString(R.string.textView_message_gps_settings));

        alertDialog.setPositiveButton(mContext.getString(R.string.tv_text_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (!mIsShow) {
                            Intent intent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            mContext.startActivity(intent);
                            mIsShow = true;
                        }


                    }
                });

        alertDialog.setNegativeButton(mContext.getString(R.string.textView_cancel_gps_settings),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (mTurnOnGps != null) {
            mTurnOnGps.onChangeLocation(location);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void setOnListener(TurnOnGPS onListener) {
        mTurnOnGps = onListener;
    }

    /**
     * Interface for handler click don't open gps
     */
    public interface TurnOnGPS {

        void onChangeLocation(Location location);

    }

}

