package com.nextolive.kiteappmvvm.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.nextolive.kiteappmvvm.R;
import com.nextolive.kiteappmvvm.exceptions.ExceptionLog;

public class AppProgress_Utils {
    private static Dialog dialog=null;
    Activity activity;


    public static ProgressDialog showProgress(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        try {

            progressDialog.setCancelable(false);
            // progressDialog.setTitle();
            progressDialog.setMessage(context.getResources().getString(R.string.pleasewait));
            // progressDialog.create();
            progressDialog.show();

        }catch (Exception ex)
        {
            ExceptionLog.catchException(ex);
        }
        return  progressDialog;
    }


    public static void hideProgress(ProgressDialog progressDialog) {
        try {

            progressDialog.dismiss();


        }catch (Exception ex)
        {
            ExceptionLog.catchException(ex);
        }
    }


}
