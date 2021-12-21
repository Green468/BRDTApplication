//package com.android.mlccrackmonitoring.connect;
//
//import android.app.DownloadManager;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.CountDownTimer;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;
//import android.text.TextUtils;
//
//
//import com.android.mlccrackmonitoring.MainActivity;
//import com.android.mlccrackmonitoring.MainApplication;
//import com.android.mlccrackmonitoring.util.Debug;
//import com.android.mlccrackmonitoring.util.Define;
//import com.android.mlccrackmonitoring.util.JsonMessageManager;
//import com.android.mlccrackmonitoring.util.Utils;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.net.Socket;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//
///**
// * Created by sev_user on 11/21/2016.
// */
//
//public class ControlClient {
//    public final static int PORT = 9999;
//    public static String IPADDR = null;
//
//    private Socket socket;
//    private BufferedInputStream bis;
//    private BufferedOutputStream bos;
//    private static ControlClient mInstance = null;
//    private static Handler mHandler = null;
//    private ConnectClientThread connectThread = null;
//    private DataClientThread dataThread = null;
//    private boolean stopFlag;
//    private boolean isThreadDataRunning = false;
//
//    private int mFileId = -1;
//
//    private Context mContext;
//    private boolean isReceiveConnectedMessage = false;
//    private CountDownTimer checkConnectTimer = null;
//    private boolean isTryToReconnect;
//
//    private MainApplication mApp;
//    private static final  int MAX_TRY_RECONNECT = 3;
//    private int mCountReconnect;
//
//
//    public ControlClient(Context context){
//        Debug.log("ControlClient()");
//        mHandler = new ControlClienHandler();
//        mContext = context;
//        mApp = MainApplication.getInstance();
//    }
//
//    public static String getIPADDR() {
//        return IPADDR;
//    }
//
//    public void setIPADDR(String iPADDR) {
//        IPADDR = iPADDR;
//    }
//
//    public static synchronized ControlClient getInstance(Context context) {
//        synchronized (ControlClient.class) {
//            if(mInstance == null) {
//                mInstance = new ControlClient(context);
//            }
//        }
//        return mInstance;
//    }
//
//    public synchronized void start() {
//        Debug.log("start()");
//        if(connectThread == null && !isThreadDataRunning){
//            Debug.log("connectThread == null new");
//            isReceiveConnectedMessage = false;
//            connectThread = new ConnectClientThread();
//            connectThread.setName("CONNECT_CLIENT_THREAD");
//            connectThread.start();
//        }
//    }
//
//    private class ControlClienHandler extends Handler {
//
//        @Override
//        public void handleMessage(Message msg) {
//            Debug.log("handleMessage: "+msg.what);
//
//            switch (msg.what){
//                case Define.ERROR:
//                    checkFinish();
//                    setStopControlThreads();
//                    closeSocket();
//
//                    isTryToReconnect = true;
//                    start();
//                    if (mCountReconnect == 0) {
//                        mApp.sendMsgToActivity(msg);
//                    }
//                    mCountReconnect ++;
//                    Debug.logD("reconnect time : "+mCountReconnect);
//                    break;
//
//                case Define.CLOSE:
//                    mCountReconnect = 0;
//                    setStopControlThreads();
//                    closeSocket();
//                    mApp.sendMsgToActivity(msg);
//                    break;
//
//                case Define.CONNECT:
//                    mCountReconnect = 0;
//                    mApp.sendMsgToActivity(msg);
//                    handleConnectMessage(msg.obj);
//                    break;
//
//                case Define.OTHER:
//                    mApp.sendMsgToActivity(msg);
//                    break;
//
//                default:
//                    Debug.log("Unknown msg");
//                    break;
//            }
//
//        }
//    }
//
//    private void handleConnectMessage(Object obj){
//        JSONObject receiveJson = (JSONObject) obj;
//
//        if (receiveJson == null) {
//            Debug.log( "receiveJson is null");
//            return;
//        }
//        else {
//            try {
//                boolean connected = receiveJson.getBoolean(Define.JSON_CONNECTED);
//                if (!connected){
//                    if (mHandler != null) {
//                        mHandler.sendMessage(mHandler.obtainMessage(Define.CLOSE, null));
//                    }
//                }
//                isReceiveConnectedMessage = true;
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    // Thread for socket connect
//    public class ConnectClientThread extends Thread {
//
//        public ConnectClientThread() {
//        }
//
//        public void run(){
//            Debug.log("ConnectClientThread run()");
//            try {
//                Debug.log("run() IPADDR : "+IPADDR);
//                //sock = new Socket(IPADDR, PORT);
//                socket = new Socket();
//                socket.connect(new InetSocketAddress(IPADDR, PORT));
//
//                stopFlag = true;
//
//                bis = new BufferedInputStream(socket.getInputStream());
//                bos = new BufferedOutputStream(socket.getOutputStream());
//
//                Debug.log("Socket connected");
//                onSocketConnected();
//
//                if(dataThread == null){
//                    Debug.log("dataThread == null new");
//                    dataThread = new DataClientThread();
//                    dataThread.setName("DATA_CLIENT_THREAD");
//                    dataThread.start();
//                }
//
//            } catch (IOException e) {
//                Debug.logE("IOException : " + e.getMessage());
//                e.printStackTrace();
//                String errStr = "";
//                if(e.getMessage().contains("ETIMEDOUT"))
//                    errStr = "ETIMEDOUT";
//                else if(e.getMessage().contains("ECONNREFUSED"))
//                    errStr = "ECONNREFUSED";
//                if (mHandler != null) {
//
//                    if (mCountReconnect < MAX_TRY_RECONNECT) {
//                        mHandler.sendMessage(mHandler.obtainMessage(Define.ERROR, errStr));
//                    } else {
//                        mHandler.sendMessage(mHandler.obtainMessage(Define.CLOSE, errStr));
//                    }
//
//                }
//            } catch (Exception e) {
//                Debug.logE("Exception : " + e.getMessage());
//                e.printStackTrace();
//            } finally {
//                Debug.log("ConnectClientThread finally");
//                checkFinish();
//            }
//        }
//    }
//
//    // Thread for receive message
//    public class DataClientThread extends Thread {
//
//        public DataClientThread() {
//
//        }
//
//        public void run(){
//            Debug.log("DataClientThread run()");
//            isThreadDataRunning = true;
//            try {
//                byte[] tempBuf;
//                while(!Thread.currentThread().isInterrupted()) {
//                    if(bis != null) {
//                        int jsonLength = 0;
//                        String fullStr = "";
//                        tempBuf = new byte[1024];
//
//                        jsonLength = bis.read(tempBuf, 0, tempBuf.length);
//                        // read message length
//                        if ( jsonLength > 0) {
//                            byte[] jsonBytes = getbytes(tempBuf, 0, jsonLength);
//                            fullStr = new String(jsonBytes, 0, jsonLength, "UTF-8");
//                            Debug.logD("jsonLength : " + jsonLength);
//                        } else {
//                            Debug.logE("when peear socket closed return -1 !!!");
//                            String errStr = "BUF_READ_ERROR";
//                            if (mHandler != null) {
//                                mHandler.sendMessage(mHandler.obtainMessage(Define.ERROR, errStr));
//                            }
//                            Thread.currentThread().interrupt();
//                        }
//
//                        if(fullStr != null) {
//                            Debug.log( "jsonLength : " + jsonLength +" fullStr : " + fullStr);
//                        }
//
//                        if(fullStr != null && !fullStr.isEmpty()) {
//                            JSONObject jsonObj = new JSONObject(fullStr);
//                            Message msg = new Message();
//                            String type = "";
//
//                            try {
//                                type = jsonObj.getString(Define.JSON_TYPE);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                type = "JSONtypeMismatch";
//                            }
//
//                            if (type.equalsIgnoreCase(Define.JSON_CONNECT)){
//                                if (mHandler != null){
//                                    if (isTryToReconnect) {
//                                        msg.what = Define.RE_CONNECT;
//                                        isTryToReconnect = false;
//                                    } else {
//                                        msg.what = Define.CONNECT;
//                                    }
//
//                                    msg.obj = jsonObj;
//                                    mHandler.sendMessage(msg);
//                                }
//                            } else {
//                                if (mHandler != null){
//                                    msg.what = Define.OTHER;
//                                    msg.obj = jsonObj;
//                                    mHandler.sendMessage(msg);
//                                }
//                            }
//
//                        }
//                    }
//                }
//            } catch (IOException e) {
//                Debug.logE("DataClientThread IOException : " + e.getMessage());
//                e.printStackTrace();
//                String errStr = "";
//                if(e.getMessage().contains("ETIMEDOUT"))
//                    errStr = "ETIMEDOUT";
//                else if(e.getMessage().contains("ECONNRESET"))
//                    errStr = "ECONNRESET";
//
//			/*} catch (JSONException e) {
//				Debug.logE("DataServerThread JSONException : " + e.getMessage());
//				e.printStackTrace();*/
//            } catch (Exception e) {
//                Debug.logE("DataClientThread Exception : " + e.getMessage());
//                e.printStackTrace();
//            } finally {
//                Debug.log("DataClientThread finally");
//
//                if (mCountReconnect < MAX_TRY_RECONNECT) {
//                    mHandler.sendMessage(mHandler.obtainMessage(Define.ERROR, null));
//                } else {
//                    mHandler.sendMessage(mHandler.obtainMessage(Define.CLOSE, null));
//                }
////                closeSocket();
//            }
//        }
//
//    }
//
//    private void onSocketConnected(){
//        sendMessage(JsonMessageManager.createRequestMessage(mContext).toString());
//        startConnectingTimer();
//    }
//
//    // to send message
//    public void sendMessage(String message){
//        if(bos != null){
//            message+="\r\n";
//            Debug.log("send message : "+message);
//            byte[] results = message.toString()
//                    .getBytes(Charset.forName("UTF-8"));
//            try {
//                Debug.log("write to output stream");
//                bos.write(results);
//                bos.flush();
//            } catch (Exception e) {
//                Debug.logE("sendMessage IOException : " + e.getMessage());
//                e.printStackTrace();
//                String errStr = "";
//                if(e.getMessage().contains("EPIPE"))
//                    errStr = "EPIPE";
//                if (mHandler != null) {
//                    mHandler.sendMessage(mHandler.obtainMessage(Define.ERROR, errStr));
//                }
////                closeSocket();
//            }
//        }
//        else{
//            Debug.log( "sendMessage bos == null");
//        }
//    }
//
//    public static final byte[] getbytes( byte src[] , int offset , int length )
//    {
//        byte dest[] = new byte[length];
//        System.arraycopy( src , offset , dest , 0 , length );
//        return dest;
//    }
//
//    public void setStopControlThreads() {
//        Debug.log("setStopControlThreads()");
//        if(!stopFlag){
//            Debug.log("stopFlag false");
//
//        }
//        if(socket != null){
//            try {
//                socket.close();
//                socket = null;
//                Debug.log("sock = null");
//            } catch (IOException e) {
//                Debug.logE("setStopControlThreads sock.close() : " + e.getMessage());
//                e.printStackTrace();
//            }
//        }
//        if(dataThread != null){
//            dataThread.interrupt();
//            Debug.log("dataThread.interrupt()");
//        }
//    }
//
//    public void checkFinish() {
//        Debug.log("checkFinish()");
//        Thread t = new Thread(new Runnable() {
//            public void run() {
//                Debug.log("checkFinish run()");
//                if(connectThread != null){
//                    try {
//                        connectThread.join();
//                    } catch (InterruptedException e) {
//                        Debug.logE("checkFinish InterruptedException : " + e.getMessage());
//                        e.printStackTrace();
//                    }
//                    Debug.log("checkFinish connectThread = null");
//                    connectThread = null;
//                }
//            }
//        });
//        t.start();
//    }
//
//
//    public synchronized void closeSocket() {
//        Debug.log("closeSocket()");
////        if (mHandler != null) {
////            mHandler.sendMessage(mHandler.obtainMessage(Define.CLOSE, null));
////        }
//        stopFlag = false;
//        try{
//            if(bis != null){
//                bis.close();
//                bis = null;
//            }
//            if(bos != null){
//                bos.close();
//                bos = null;
//            }
//            if(socket != null){
//                socket.close();
//                socket = null;
//            }
//            Thread th = new Thread(new Runnable() {
//                public void run() {
//                    if(dataThread != null){
//                        if(isThreadDataRunning){ //to avoid entered here two times before thread is finished
//                            isThreadDataRunning = false;
//                            Debug.log("dataThread != null");
//                            //dataThread.interrupt();
//                            try {
//                                dataThread.join();
//                            } catch (InterruptedException e) {
//                                Debug.logE("setStopControlThreads InterruptedException : " + e.getMessage());
//                                e.printStackTrace();
//                            }
//                            Debug.log("dataThread = null");
//                            dataThread = null;
//                        }
//                    }
//                }
//            });
//            th.start();
//            //connectThread = null;
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public boolean isConnected(){
//        boolean isConnected = false;
//        if(socket != null){
//            isConnected = socket.isConnected();
//        }
//        return isConnected;
//    }
//
//    public void disconnect(){
//        if (mHandler != null) {
//            mHandler.sendMessage(mHandler.obtainMessage(Define.CLOSE, null));
//        }
//    }
//
//    private void startConnectingTimer() {
//        if (checkConnectTimer == null) {
//            Debug.log("Timer -- Start");
//            Looper.prepare();
//            checkConnectTimer = new CountDownTimer(3 * 3000, 3000) {
//                public void onTick(long millisUntilFinished) {
//                    if (isReceiveConnectedMessage){
//                        cancelConnectingTimer();
//                    } else {
//                        // send message connect to server
//                        sendMessage(JsonMessageManager.createRequestMessage(mContext).toString());
//                    }
//                }
//
//                public void onFinish() {
//                    Debug.log("Timer -- Finish");
//                    // cancel connecting timer
//                    cancelConnectingTimer();
//                    // reconnect to web socket
//                    if (!isReceiveConnectedMessage){
//                        isTryToReconnect = false;
//                        if (mHandler != null) {
//                            mHandler.sendMessage(mHandler.obtainMessage(Define.CLOSE, null));
//                        }
////                        closeSocket();
//                    }
//                }
//            }.start();
//        }
//    }
//
//    private void cancelConnectingTimer() {
//        if (checkConnectTimer != null) {
//            Debug.log("Timer -- Cancel");
//            checkConnectTimer.cancel();
//            checkConnectTimer = null;
//        }
//    }
//
//
//}