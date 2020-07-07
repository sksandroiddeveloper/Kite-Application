package com.nextolive.kiteappmvvm.fragments;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.pm.ActivityInfo;
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
import com.nextolive.kiteappmvvm.utils.UTILITY;
import com.nextolive.kiteappmvvm.viewmodels.SignUpFullnameViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpFullnameFragment extends Fragment {

    private SignUpFullnameViewModel mViewModel;

    @BindView(R.id.tv_onboarding_tap)
    TextView tv_onboarding_tap;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.et_fullName)
    EditText et_fullName;
    @BindView(R.id.btn_signup_fullName)
    Button btn_signup_fullName;
    @BindView(R.id.tv_skip)
    TextView tv_skip;

    public static SignUpFullnameFragment newInstance() {
        return new SignUpFullnameFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sign_up_fullname_fragment, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        ButterKnife.bind(this, v);

        UTILITY.buttonEffect(btn_signup_fullName, "#2196F3");//this method will provide click effect on btn

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpPassFragment fragment = new SignUpPassFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                transaction.replace(R.id.framelayoutForFragments, fragment);
                transaction.addToBackStack("signUpPassword");
                transaction.commit();
            }
        });

        et_fullName.addTextChangedListener(new TextWatcher() {
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

        btn_signup_fullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_fullName != null && et_fullName.getText().toString().length() > 0) {

                    SignUpPassFragment fragment = new SignUpPassFragment();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                    transaction.replace(R.id.framelayoutForFragments, fragment);
                    transaction.addToBackStack("signUpPassword");
                    transaction.commit();

                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.Enter_your_full_name), Toast.LENGTH_SHORT).show();
                }
            }
        });


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SignUpFullnameViewModel.class);
        // TODO: Use the ViewModel
    }

}
