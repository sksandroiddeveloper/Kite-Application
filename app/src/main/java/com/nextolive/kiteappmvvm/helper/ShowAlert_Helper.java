package com.nextolive.kiteappmvvm.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.nextolive.kiteappmvvm.exceptions.ExceptionLog;


public class ShowAlert_Helper {

    public ShowAlert_Helper(final Activity activity, String message) {
        try {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
            alertDialog.setMessage(message);
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   // activity.finish();
                }
            });
            alertDialog.setCancelable(false);
            alertDialog.show();
        } catch (Exception ex) {
            ExceptionLog.catchException(ex);
        }
    }
}
