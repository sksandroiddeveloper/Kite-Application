package com.nextolive.kiteappmvvm.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nextolive.kiteappmvvm.R;
import com.nextolive.kiteappmvvm.activities_views.MainActivity;
import com.nextolive.kiteappmvvm.utils.SharedData_Utils;
import com.nextolive.kiteappmvvm.utils.UTILITY;
import com.nextolive.kiteappmvvm.viewmodels.IntroViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroFragment extends Fragment {

    private IntroViewModel mViewModel;

    @BindView(R.id.btn_intro_button)
    Button btn_intro_button;

    public static IntroFragment newInstance() {
        return new IntroFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.intro_fragment, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        ButterKnife.bind(this, v);

        UTILITY.buttonEffect(btn_intro_button, "#C5C5C5");

        btn_intro_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedData_Utils.setFlagHasSeenIntro(getActivity(),true);
                Intent login = new Intent(getActivity(), MainActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(login);
               /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                    getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                }*/
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
        mViewModel = ViewModelProviders.of(this).get(IntroViewModel.class);
    }

}
