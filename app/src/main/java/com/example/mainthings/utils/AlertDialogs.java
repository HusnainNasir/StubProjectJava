package com.example.mainthings.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainthings.R;
import com.example.mainthings.callbacks.OnClickCallback;

import java.util.Objects;

public class AlertDialogs {

    // CUSTOM PROGRESS DIALOG
    public static AlertDialog getProgressDialog(Context context , Boolean cancelable) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context );

        LayoutInflater inflater =  (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View dialogView= inflater.inflate(R.layout.custom_progress_dialog_layout, null);
        dialogBuilder.setView(dialogView);


        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(cancelable);


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = (int)(context.getResources().getDisplayMetrics().widthPixels * 0.17f);

        Objects.requireNonNull(dialog.getWindow()).setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);

        return dialog;
//        ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.show();
//
//        if (progressDialog.getWindow() != null ) {
//            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        }
//        progressDialog.setContentView(R.layout.custom_progress_dialog_layout);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setCancelable(cancelable);
//        progressDialog.setCanceledOnTouchOutside(cancelable);
//        return progressDialog;
    }

    // CUSTOM ALERT DIALOG
    public static void showAlertDialog(Context context , String headingText , String subHeadingText , String positiveText , String negativeText , boolean hideButton  , final OnClickCallback onClick){

        if(context == null)
            return;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context );

        LayoutInflater inflater =  (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View dialogView= inflater.inflate(R.layout.custom_alert_dialog_layout, null);
        dialogBuilder.setView(dialogView);


        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

        TextView heading = dialogView.findViewById(R.id.heading);
        TextView subheading = dialogView.findViewById(R.id.subHeading);
        Button clickPositive = dialogView.findViewById(R.id.yesButton);
        Button clickNegative = dialogView.findViewById(R.id.noButton);

        heading.setText(headingText);
        subheading.setText(subHeadingText);
        clickPositive.setText(positiveText);
        clickNegative.setText(negativeText);

        clickPositive.setOnClickListener(view -> {
            dialog.dismiss();
            onClick.onclick(Constants.CLICK_POSITIVE);
        });

        clickNegative.setOnClickListener(view -> {
            dialog.dismiss();
            onClick.onclick(Constants.CLICK_NEGATIVE);
        });

        if (hideButton)
            clickPositive.setVisibility(View.GONE);

        int width = (int)(context.getResources().getDisplayMetrics().widthPixels * 0.80f);

        Objects.requireNonNull(dialog.getWindow()).setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }
}
