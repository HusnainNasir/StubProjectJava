package com.example.mainthings.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilsFunction {

    public static String compressBitmap(Bitmap bitmap){
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bOut);
        return Base64.encodeToString(bOut.toByteArray(), Base64.NO_WRAP);
    }

    public static String compressFile(File file){
        File imgFile = new File(file.getPath());
        Bitmap bm = BitmapFactory.decodeFile(file.getPath());
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bOut);

        return Base64.encodeToString(bOut.toByteArray(), Base64.NO_WRAP);
    }

    public static Bitmap base64ToBitmapDecode(String base64) {
        Bitmap bitmap = null;
        byte[] imageAsBytes = Base64.decode(base64.getBytes(), Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        return bitmap;
    }


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

    private String getDaysAgo(Date date){
        long days = (new Date().getTime() - date.getTime()) / 86400000;

        if(days == 0) return "Today";
        else if(days == 1) return "Yesterday";
        else return days + " days ago";
    }


}
