package com.nextolive.kiteappmvvm.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.nextolive.kiteappmvvm.R;
import com.nextolive.kiteappmvvm.viewmodels.SendDirectMessageViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SendDirectMessageFragment extends Fragment {

    private SendDirectMessageViewModel mViewModel;

    @BindView(R.id.iv_close_button)
    ImageView iv_close_button;

    public static SendDirectMessageFragment newInstance() {
        return new SendDirectMessageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.send_direct_message_fragment, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        ButterKnife.bind(this, v);
        //to unhide the status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow();
            w.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        iv_close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SendDirectMessageViewModel.class);
        // TODO: Use the ViewModel
    }

}
