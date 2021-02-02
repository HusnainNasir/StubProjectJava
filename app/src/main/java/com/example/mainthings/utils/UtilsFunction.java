package com.example.mainthings.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.util.Base64;

import com.example.mainthings.callbacks.NetworkCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilsFunction {

    // ENCODE BASE 64 STRING
    public static String compressBitmap(Bitmap bitmap){
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bOut);
        return Base64.encodeToString(bOut.toByteArray(), Base64.NO_WRAP);
    }

    // DECODE BASE 64 STRING to FILE
    public static String compressFile(File file){
        File imgFile = new File(file.getPath());
        Bitmap bm = BitmapFactory.decodeFile(file.getPath());
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bOut);

        return Base64.encodeToString(bOut.toByteArray(), Base64.NO_WRAP);
    }

    // DECODE BASE 64 STRING to BITMAP
    public static Bitmap base64ToBitmapDecode(String base64) {
        Bitmap bitmap = null;
        byte[] imageAsBytes = Base64.decode(base64.getBytes(), Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        return bitmap;
    }

    // DATE TO STRING
    public String convertDateToString(Date date , String dateFormatString){
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat(dateFormatString); // "yyyy-MM-dd hh:mm:ss"
        return dateFormat.format(date);
    }

    // STRING TO DATE
    public Date convertStringToDate(String stringDate , String dateFormatString){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter =new SimpleDateFormat(dateFormatString); // "dd-MMM-yyyy HH:mm:ss"
        try {
            return formatter.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    // DATE FORMAT CHANGE
    public Date changeDateFormatFromAnother(String date , String outputFormatString){

//        @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat(inputFormatString); // "yyyy-MM-dd'T'HH:mm:ss.SSS"
        @SuppressLint("SimpleDateFormat") DateFormat outputFormat = new SimpleDateFormat(outputFormatString); // "yyyy-MM-dd"
        Date resultDate = null;
        try {
            resultDate = outputFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultDate;
    }

    // DAYS AGO FUNCTION
    private String getDaysAgo(Date date){
        long days = (new Date().getTime() - date.getTime()) / 86400000;

        if(days == 0) return "Today";
        else if(days == 1) return "Yesterday";
        else return days + " days ago";
    }

    // CHECK INTERNET CONNECTIVITY
    public static void isNetworkConnected(NetworkCallback networkCallback) {

        Thread thread = new Thread(){
            public void run(){
                Looper.prepare();//Call looper.prepare()
                try {
                    // Connect to Google DNS to check for connection
                    int timeoutMs = 1500;
                    Socket socket = new Socket();
                    InetSocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53);
                    socket.connect(socketAddress, timeoutMs);
                    socket.close();
                    networkCallback.networkCallback(true);
                } catch (IOException e) {
                    networkCallback.networkCallback(false);
                }
                Looper.loop();
            }
        };
        thread.start();


    }

}
