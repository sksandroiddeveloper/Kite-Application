package com.nextolive.kiteappmvvm.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nextolive.kiteappmvvm.R;
import com.nextolive.kiteappmvvm.activities_views.MainActivity;
import com.nextolive.kiteappmvvm.utils.SharedData_Utils;
import com.nextolive.kiteappmvvm.utils.UTILITY;
import com.nextolive.kiteappmvvm.viewmodels.SignUpPassViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpPassFragment extends Fragment {

    private SignUpPassViewModel mViewModel;

    @BindView(R.id.tv_onboarding_tap)
    TextView tv_onboarding_tap;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.et_signup_password)
    EditText et_signup_password;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.tv_termsAndCondtions)
    TextView tv_termsAndCondtions;

    public static SignUpPassFragment newInstance() {
        return new SignUpPassFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sign_up_pass_fragment, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        ButterKnife.bind(this, v);

        UTILITY.buttonEffect(btn_submit, "#2196F3");//this method will provide click effect on btn

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        et_signup_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    tv_onboarding_tap.setVisibility(View.GONE);
                } else {
                    tv_onboarding_tap.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_signup_password != null && et_signup_password.getText().toString().length() > 0) {
                    SharedData_Utils.personId(getContext(), "1");
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(getActivity(), "Successfully Registered!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.EnterPassword), Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_termsAndCondtions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.kiteapp.com/privacy")));
            }
        });


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SignUpPassViewModel.class);
        // TODO: Use the ViewModel
    }

}
