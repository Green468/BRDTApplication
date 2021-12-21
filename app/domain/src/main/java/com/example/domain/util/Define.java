package com.example.domain.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by Qnv96 on 17-Nov-16.
 */

public class Define {
    //Save file on: /storage/extSdCard/TvContentSync

    // FOLDER APPLICATION
    public static final String APP_PATH = Environment.getExternalStorageDirectory() + File.separator + "MLCCRackMonitoring/";

    // Json message

    //------ connect -------------
    public static final String JSON_TYPE = "type";
    public static final String JSON_CONNECT = "connect";
    public static final String JSON_NAME = "name";
    public static final String JSON_TOKEN = "token";
    public static final String JSON_COMMENT = "comment";
    public static final String JSON_SERIAL_NUMBER = "serial_number";
    public static final String JSON_DEVICE_TYPE= "device_type";
    public static final String JSON_CONNECTED = "connected";

    //---- in stock --------------
    public static final String JSON_INSTOCK_SEND_ROLL = "in_stock_send_roll";
    public static final String JSON_INSTOCK_SUGGEST_BIN = "in_stock_bin_suggest";
    public static final String JSON_INSTOCK_CONFIRM_BIN = "in_stock_confirm_bin";

    //---- out stock --------------
    public static final String JSON_OUTSTOCK_SEND_ROLL = "out_stock_send_roll";
    public static final String JSON_OUTSTOCK_SUGGEST_BIN = "out_stock_bin_suggest";
    public static final String JSON_OUTSTOCK_CONFIRM_BIN = "out_stock_confirm_bin";

    //---- return --------------
    public static final String JSON_RETURN_SEND_ROLL = "return_send_roll";
    public static final String JSON_RETURN_SUGGEST_BIN = "return_bin_suggest";
    public static final String JSON_RETURN_CONFIRM_BIN = "return_confirm_bin";

    //------- pi test -----------
    public static final String JSON_TYPE_PI_TEST = "pi_test";
    public static final String JSON_PI_TEST_STATE = "state";
    public static final String JSON_PI_TEST_LAMP_TYPE = "lamp_type";
    public static final String JSON_PI_TEST_LIGHT_TYPE = "light_type";
    public static final String JSON_PI_TEST_RACK = "rack";

    public static final String JSON_ROLL = "roll";
    public static final String JSON_BIN = "bin";
    public static final String JSON_CONFIRM_VALUE = "confirm";
    public static final String JSON_ERROR = "error";

    //-----       MSG.WHAT   --------------------
    public static final int CONNECT          = 1;
    public static final int OTHER            = 2;
    public static final int ERROR            = 5;
    public static final int CLOSE            = 6;
    public static final int RE_CONNECT       = 7;

    // share preference
    public static final String TOKEN = "token";
    public static final String IP_ADDRESS = "ip_address";

}
