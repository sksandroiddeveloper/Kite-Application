package com.nextolive.kiteappmvvm;

import android.content.Context;
import android.util.Log;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.nextolive.kiteappmvvm.utils.UTILITY;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ConvDpToPx {

    @Test
    public void convDpToPx_Passing_normal_value() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        int result = UTILITY.convDpToPx(appContext, 50);
        Log.i("convDpToPx", String.valueOf(result));

    }

    @Test
    public void convDpToPx_passing_zero() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        int result = UTILITY.convDpToPx(appContext, 0);
        Log.i("convDpToPx", String.valueOf(result));

    }

    @Test
    public void convDpToPx_passing_nagative_value() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        int result = UTILITY.convDpToPx(appContext, -1);
        Log.i("convDpToPx", String.valueOf(result));

    }

}
