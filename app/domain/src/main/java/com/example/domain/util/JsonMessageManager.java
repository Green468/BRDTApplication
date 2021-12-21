package com.example.domain.util;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sev_user on 10/13/2017.
 */

public class JsonMessageManager {

    public static JSONObject createRequestMessage(Context context){
        JSONObject json = new JSONObject();
        try {
            json.put(Define.JSON_TYPE, Define.JSON_CONNECT);
            json.put(Define.JSON_NAME, Utils.getDeviceName(context));
            json.put(Define.JSON_TOKEN, Utils.getToken(context));
            json.put(Define.JSON_SERIAL_NUMBER, Utils.getSerialNumber());
            json.put(Define.JSON_DEVICE_TYPE, "android");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;

    }

    public static JSONObject createSendRollMessage(Context context,String typeSendRoll, String rollValue, String comment){
        JSONObject json = new JSONObject();
        try {
            json.put(Define.JSON_TYPE, typeSendRoll);
            json.put(Define.JSON_TOKEN, Utils.getToken(context));
            json.put(Define.JSON_ROLL, rollValue);
            json.put(Define.JSON_COMMENT, comment);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static JSONObject createConfirmBinMessage(Context context, String typeConfirmBin, String bin){
        JSONObject json = new JSONObject();
        try {
            json.put(Define.JSON_TYPE, typeConfirmBin);
            json.put(Define.JSON_TOKEN, Utils.getToken(context));
            json.put(Define.JSON_BIN, bin);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;

    }

    public static JSONObject createPiTestMessage(Context context, String state, String lamp_type, String light_type, String rack){
        JSONObject json = new JSONObject();
        try {
            json.put(Define.JSON_TYPE, Define.JSON_TYPE_PI_TEST);
            json.put(Define.JSON_TOKEN, Utils.getToken(context));
            json.put(Define.JSON_PI_TEST_STATE, state);
            json.put(Define.JSON_PI_TEST_LAMP_TYPE, lamp_type);
            json.put(Define.JSON_PI_TEST_LIGHT_TYPE, light_type);
            json.put(Define.JSON_PI_TEST_RACK, rack);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;

    }

    public static JSONObject testBinConrfirmMessage(){
        JSONObject json = new JSONObject();
        try {
            json.put(Define.JSON_TYPE, Define.JSON_RETURN_CONFIRM_BIN);
            json.put(Define.JSON_BIN, "1A2-05-3A2");
            json.put(Define.JSON_CONFIRM_VALUE, true);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
