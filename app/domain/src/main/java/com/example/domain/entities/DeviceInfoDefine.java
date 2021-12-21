package com.example.domain.entities;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import com.example.domain.util.Debug;
import com.example.domain.util.Utils;


public class DeviceInfoDefine {

    private final static String PROP_KIES_SOM_MODE         = "dev.kies.sommode";
    private final static String PROP_PRODUCT_MODEL         = "ro.product.model";
    private final static String PROP_BUILD_PDA_VERSION     = "ro.build.PDA";
    private final static String PROP_BUILD_PHONE_VERSION   = "ril.sw_ver";
    private final static String PROP_BUILD_CSC_VERSION     = "ril.official_cscver";

    private final static String PROP_RIL_PRODUCT_CODE      = "ril.product_code";
    private final static String PROP_CSC_SALES_CODE        = "ro.csc.sales_code";
    private final static String PROP_RIL_SALES_CODE        = "ril.sales_code";

    private final static String PROP_SERAIL_NUMBER         = "ril.serialnumber";
    private final static String PROP_SERAIL_NUMBER1        = "ro.serialno";

    private final static String SETTING_SYSTEM_DEVICE_NAME = "device_name";

    private final static String PRODUCTCODE_RSP            = "XXXXXXXXXXXXXX";
    private final static String PRODUCTCODE_CTC_RSP        = "XXXXXXXXXXXCTC";
    private final static String PRODUCTCODE_DCM_RSP        = "XXXXXXXXXXXDCM";
    private final static String PRODUCTCODE_KOR_RSP        = "XXX";


    public static String getModelName()
    {
        String modelName = "";
        modelName = Utils.getSystemProperty(PROP_PRODUCT_MODEL);
        if (TextUtils.isEmpty(modelName))
            modelName = "";

        if (modelName.contains("SAMSUNG-"))
            modelName = modelName.replace("SAMSUNG-", "");

        return modelName;
    }

    public static String getUserFriendlyDisplayName(Context context)
    {
        String userFriendlyDisplayName = "";
        userFriendlyDisplayName = Settings.System.getString(context.getContentResolver(), SETTING_SYSTEM_DEVICE_NAME);
        if (TextUtils.isEmpty(userFriendlyDisplayName)){
            //Debug.log("userFriendlyDisplayName isEmpty");
            userFriendlyDisplayName = Settings.Global.getString(context.getContentResolver(), SETTING_SYSTEM_DEVICE_NAME);
            if(userFriendlyDisplayName == null) {
                Debug.log("userFriendlyDisplayName isEmpty again!!");
                userFriendlyDisplayName = "";
            }
        }
        Debug.log("userFriendlyDisplayName " + userFriendlyDisplayName);
        return userFriendlyDisplayName;
    }

    public static String getSerialNumber()
    {
        String serialNumber = "";
        serialNumber = Utils.getSystemProperty(PROP_SERAIL_NUMBER);
        if (TextUtils.isEmpty(serialNumber) || serialNumber.equals("00000000000"))
            serialNumber = Utils.getSystemProperty(PROP_SERAIL_NUMBER1);
        return serialNumber;
    }

}
