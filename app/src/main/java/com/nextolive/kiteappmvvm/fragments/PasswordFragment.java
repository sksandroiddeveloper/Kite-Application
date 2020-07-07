package com.nextolive.kiteappmvvm.fragments;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Slide;
import android.view.Gravity;
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
import com.nextolive.kiteappmvvm.viewmodels.PasswordViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PasswordFragment extends Fragment {

    @BindView(R.id.tv_validation_error)
    TextView tv_validation_error;
    @BindView(R.id.tv_onboarding_tap)
    TextView tv_onboarding_tap;
    @BindView(R.id.et_login_password)
    EditText et_login_password;
    @BindView(R.id.btn_login_password)
    Button btn_login_password;
    @BindView(R.id.iv_back)
    ImageView iv_back;

    private PasswordViewModel mViewModel;

    public static PasswordFragment newInstance() {
        return new PasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.password_fragment, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        ButterKnife.bind(this, v);

        UTILITY.buttonEffect(btn_login_password,"#2196F3");//this method will provide click effect on btn

        et_login_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tv_onboarding_tap.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 6) {
                    btn_login_password.setEnabled(true);
                    tv_validation_error.setVisibility(View.GONE);
                } else {
                    btn_login_password.setEnabled(false);
                    tv_validation_error.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_login_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_login_password != null && et_login_password.getText().toString().length() >= 6) {
                    SharedData_Utils.personId(getContext(), "1");
                    SharedData_Utils.password(getContext(), et_login_password.getText().toString());
                    Intent login = new Intent(getContext(), MainActivity.class);
                    login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(login);
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.Enteryouremail), Toast.LENGTH_SHORT).show();
                }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity().onBackPressed();
                EmailFragment fragment = new EmailFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
               // transaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_in_from_right);
                transaction.replace(R.id.framelayoutForFragments, fragment);
                //transaction.addToBackStack("EmailFragment");
                transaction.commit();


            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PasswordViewModel.class);
        // TODO: Use the ViewModel
    }

}
