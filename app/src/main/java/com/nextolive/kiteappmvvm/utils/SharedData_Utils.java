package com.nextolive.kiteappmvvm.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedData_Utils {

    public static String defvalue = "";

    public static SharedPreferences getSP(Context context) {
        return context.getSharedPreferences(ConstantVariable_Utils.SHARED_DATABASE_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor putSP(Context context) {
        return context.getSharedPreferences(ConstantVariable_Utils.SHARED_DATABASE_NAME, Context.MODE_PRIVATE).edit();
    }

    public static void Server_URL(Context context, String value) {
        putSP(context).putString(ConstantVariable_Utils.Server_URL, value).commit();
    }

    public static String Server_URL(Context context) {
        return getSP(context).getString(ConstantVariable_Utils.Server_URL, defvalue);
    }

    public static void personId(Context context, String value) {
        putSP(context).putString(ConstantVariable_Utils.personId, value).commit();
    }
    public static String personId(Context context) {
        return getSP(context).getString(ConstantVariable_Utils.personId, defvalue);
    }


    public static void email(Context context, String value) {
        putSP(context).putString(ConstantVariable_Utils.email, value).commit();
    }
    public static String email(Context context) {
        return getSP(context).getString(ConstantVariable_Utils.email, defvalue);
    }

    public static void userName(Context context, String value) {
        putSP(context).putString(ConstantVariable_Utils.userName, value).commit();
    }
    public static String userName(Context context) {
        return getSP(context).getString(ConstantVariable_Utils.userName, defvalue);
    }

    public static void password(Context context, String value) {
        putSP(context).putString(ConstantVariable_Utils.password, value).commit();
    }
    public static String password(Context context) {
        return getSP(context).getString(ConstantVariable_Utils.password, defvalue);
    }

    public static void setFlagShowAddLicense(Context context,boolean showAddLicense) {
        putSP(context).putBoolean(ConstantVariable_Utils.FLAG_SHOW_ADD_LICENSE, showAddLicense).commit();
    }
    public static void setFlagHasSeenIntro(Context context,boolean hasSeenIntro) {
        putSP(context).putBoolean(ConstantVariable_Utils.FLAG_HAS_SEEN_INTRO, hasSeenIntro).commit();
    }
    public static boolean getFlagHasSeenIntro(Context context) {
        return getSP(context).getBoolean(ConstantVariable_Utils.FLAG_HAS_SEEN_INTRO, false);
    }

}
