package com.nextolive.kiteappmvvm.fragments;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.nextolive.kiteappmvvm.R;
import com.nextolive.kiteappmvvm.utils.UTILITY;
import com.nextolive.kiteappmvvm.viewmodels.ChangeUserDetailsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangeUserDetailsFragment extends Fragment {

    private ChangeUserDetailsViewModel mViewModel;

    @BindView(R.id.cl_confirmPass)
    ConstraintLayout cl_confirmPass;
    @BindView(R.id.et_login_password)
    EditText et_login_password;
    @BindView(R.id.tv_onboarding_tapPass)
    TextView tv_onboarding_tapPass;
    @BindView(R.id.btn_forget_pass)
    Button btn_forget_pass;
    @BindView(R.id.btn_login_password)
    Button btn_login_password;
    @BindView(R.id.tv_validation_errorpass)
    TextView tv_validation_errorpass;
    @BindView(R.id.cl_userDetails)
    ConstraintLayout cl_userDetails;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.tv_onboarding_tap)
    TextView tv_onboarding_tap;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.iv_userdetails)
    ImageView iv_userdetails;
    @BindView(R.id.tv_validation_error)
    TextView tv_validation_error;
    @BindView(R.id.pb_onboarding_progressbar)
    ProgressBar pb_onboarding_progressbar;
    @BindView(R.id.tv_headerLabel)
    TextView tv_headerLabel;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.textInputLayout)
    TextInputLayout textInputLayout;

    String fragmentName = "";

    public static ChangeUserDetailsFragment newInstance() {
        return new ChangeUserDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.change_user_details_fragment, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        ButterKnife.bind(this, v);
        //to unhide the status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow();
            w.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        UTILITY.buttonEffect(btn_forget_pass, "#2196F3");//this method will provide click effect on btn
        UTILITY.buttonEffect(btn_login_password,"#2196F3");//this method will provide click effect on btn
        UTILITY.buttonEffect(btn_submit,"#2196F3");//this method will provide click effect on btn

        fragmentName = getArguments().getString("fragment");
        tv_validation_error.setVisibility(View.GONE);

        if (fragmentName != null && fragmentName.equalsIgnoreCase("ChangeNameFragment")) {
            cl_confirmPass.setVisibility(View.GONE);
            cl_userDetails.setVisibility(View.VISIBLE);
            tv_headerLabel.setText(getResources().getString(R.string.changeYourName));
            tv_validation_error.setText(getResources().getString(R.string.invalid_password));
            textInputLayout.setHint(getResources().getString(R.string.enteryourfullname));
            iv_userdetails.setImageResource(R.drawable.ic_email);
            iv_userdetails.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorBlack), android.graphics.PorterDuff.Mode.MULTIPLY);

        }else{
            tv_headerLabel.setText(getResources().getString(R.string.Confirm_your_password));
        }

        et_login_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tv_onboarding_tapPass.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 6) {
                    btn_login_password.setEnabled(true);
                    tv_validation_errorpass.setVisibility(View.GONE);
                } else {
                    btn_login_password.setEnabled(false);
                    tv_validation_errorpass.setVisibility(View.VISIBLE);
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
                    cl_confirmPass.setVisibility(View.GONE);

                    if (fragmentName != null && fragmentName.equalsIgnoreCase("ChangeEmailFragment")) {
                        cl_userDetails.setVisibility(View.VISIBLE);
                        tv_headerLabel.setText(getResources().getString(R.string.ChangeyourEmail));
                        tv_validation_error.setText(getResources().getString(R.string.invalid_email));
                        textInputLayout.setHint(getResources().getString(R.string.enter_email));
                        iv_userdetails.setImageResource(R.drawable.ic_email);

                    } else if (fragmentName != null && fragmentName.equalsIgnoreCase("ChangePasswordFragment")) {
                        cl_userDetails.setVisibility(View.VISIBLE);
                        tv_headerLabel.setText(getResources().getString(R.string.changeYourPassword));
                        editText.setInputType(InputType.TYPE_CLASS_TEXT |
                                InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        tv_validation_error.setText(getResources().getString(R.string.invalid_password));
                        textInputLayout.setHint(getResources().getString(R.string.enteryoutnewpassword));
                        iv_userdetails.setImageResource(R.drawable.ic_security_white_24dp);
                        iv_userdetails.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorBlack), android.graphics.PorterDuff.Mode.MULTIPLY);

                    } else if (fragmentName != null && fragmentName.equalsIgnoreCase("ChangeUsernameFragment")) {
                        cl_userDetails.setVisibility(View.VISIBLE);
                        tv_headerLabel.setText(getResources().getString(R.string.changeyourusername));
                        tv_validation_error.setText(getResources().getString(R.string.invalid_password));
                        textInputLayout.setHint(getResources().getString(R.string.Enter_your_Username));
                        iv_userdetails.setImageResource(R.drawable.ic_map_black_24dp);
                        iv_userdetails.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorBlack), android.graphics.PorterDuff.Mode.MULTIPLY);
                    }

                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.Enteryourpassword), Toast.LENGTH_SHORT).show();
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tv_onboarding_tap.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (fragmentName != null && !fragmentName.equalsIgnoreCase("ChangeEmailFragment")) {
                    if (charSequence.length() >= 6) {
                        btn_submit.setEnabled(true);
                        tv_validation_error.setVisibility(View.GONE);
                    } else {
                        btn_submit.setEnabled(false);
                        tv_validation_error.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editText != null && !editText.getText().toString().equalsIgnoreCase("")) {

                    if (fragmentName != null && fragmentName.equalsIgnoreCase("ChangeEmailFragment")) {

                        if (UTILITY.isEmailValid(editText.getText().toString().trim())) {
                            btn_submit.setEnabled(true);
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
                                                pb_onboarding_progressbar.setVisibility(View.GONE);
                                                btn_submit.setVisibility(View.VISIBLE);
                                            }
                                        });

                                    } catch (Exception e) {
                                        Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            };
                            thread.start();


                        } else {
                            btn_submit.setEnabled(false);
                            tv_validation_error.setVisibility(View.VISIBLE);
                        }
                    } else {

                    }

                } else {
                    btn_submit.setEnabled(false);
                    tv_validation_error.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText != null && !editText.getText().toString().equalsIgnoreCase("")) {
                    MapFragment fragment = new MapFragment();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                    transaction.replace(R.id.framelayoutForFragments, fragment);
                    //transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.fillthedetails), Toast.LENGTH_SHORT).show();
                }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
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
        mViewModel = ViewModelProviders.of(this).get(ChangeUserDetailsViewModel.class);
        // TODO: Use the ViewModel
    }

}
