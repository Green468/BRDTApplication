package com.example.domain.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.widget.Toast;

import com.example.domain.entities.DeviceInfoDefine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by sev_user on 10/13/2017.
 */

public class Utils {

    public static String getToken(Context c) {
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        return mSharedPreferences.getString(Define.TOKEN, "null");
    }

    public static void saveToken(Context c, String token) {
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        mSharedPreferences.edit().putString(Define.TOKEN, token).commit();
    }

    public static String getIpAddress(Context c) {
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        return mSharedPreferences.getString(Define.IP_ADDRESS, "");
    }

    public static void saveIpAddress(Context c, String ipAddress) {
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        mSharedPreferences.edit().putString(Define.IP_ADDRESS, ipAddress).commit();
    }

    public static String getDeviceName(Context context) {
        String deviceName = Settings.System.getString(context.getContentResolver(), "device_name");
        if (deviceName == null) {
            if (Build.MODEL != null) {
                deviceName = Build.MODEL;
            } else {
                deviceName = "Mobile";
            }
        }
        return deviceName;
    }

    public static String getSerialNumber() {
        String serialNumber = "";
        serialNumber = DeviceInfoDefine.getSerialNumber();
        return serialNumber;
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    public static String getSystemProperty(String strParam) {
        Class<?> Klass = null;
        String strRet = "";
        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                Klass = Class.forName("android.os.SemSystemProperties");
            } else {
                Klass = Class.forName("android.os.SystemProperties");
            }
            Method method = Klass.getMethod("get", String.class);
            strRet = (String) method.invoke(Klass, strParam);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            Debug.logW(e);
        }
        return strRet;
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

}
