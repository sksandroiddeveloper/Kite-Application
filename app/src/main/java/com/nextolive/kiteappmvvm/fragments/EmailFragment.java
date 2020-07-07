package com.nextolive.kiteappmvvm.fragments;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nextolive.kiteappmvvm.R;
import com.nextolive.kiteappmvvm.utils.UTILITY;
import com.nextolive.kiteappmvvm.viewmodels.EmailViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EmailFragment extends Fragment {

    private EmailViewModel mViewModel;

    @BindView(R.id.et_login_email)
    EditText et_login_email;
    @BindView(R.id.btn_login_email)
    Button btn_login_email;
    @BindView(R.id.tv_validation_error)
    TextView tv_validation_error;
    @BindView(R.id.tv_onboarding_tap)
    TextView tv_onboarding_tap;
    @BindView(R.id.pb_onboarding_progressbar)
    ProgressBar pb_onboarding_progressbar;
    @BindView(R.id.btn_SignUp)
    Button btn_SignUp;

    public static EmailFragment newInstance() {
        return new EmailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.email_fragment, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        ButterKnife.bind(this, v);

        //to unhide the status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow();
            w.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        UTILITY.buttonEffect(btn_login_email, "#2196F3");//this method will provide click effect on btn

        et_login_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tv_onboarding_tap.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (et_login_email != null && !et_login_email.getText().toString().equalsIgnoreCase("")) {
                    if (UTILITY.isEmailValid(et_login_email.getText().toString().trim())) {
                        tv_validation_error.setVisibility(View.GONE);
                        pb_onboarding_progressbar.setVisibility(View.VISIBLE);
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    sleep(3000);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            btn_login_email.setEnabled(true);
                                            pb_onboarding_progressbar.setVisibility(View.GONE);
                                            btn_login_email.setVisibility(View.VISIBLE);
                                        }
                                    });

                                } catch (Exception e) {
                                    Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        };
                        thread.start();

                    } else {
                        btn_login_email.setEnabled(false);
                        tv_validation_error.setVisibility(View.VISIBLE);
                    }
                } else {
                    btn_login_email.setEnabled(false);
                    tv_validation_error.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_login_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_login_email != null && !et_login_email.getText().toString().equalsIgnoreCase("")) {
                    PasswordFragment fragment = new PasswordFragment();
                    fragment.setExitTransition(new Slide(Gravity.LEFT));
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left,R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                    transaction.replace(R.id.framelayoutForFragments, fragment);
                    transaction.addToBackStack("PasswordFragment");
                    transaction.commit();

                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.Enteryouremail), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SignUpUsernameFragment fragment = new SignUpUsernameFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left,
                        R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                transaction.replace(R.id.framelayoutForFragments, fragment);
                transaction.addToBackStack("signUpUsername");
                transaction.commit();

            }
        });
        UTILITY.hideKeyboardFrom(getActivity(), v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(EmailViewModel.class);
        // TODO: Use the ViewModel
    }


}
