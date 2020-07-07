package com.nextolive.kiteappmvvm.fragments;

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
import android.text.TextWatcher;
import android.util.Log;
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

import com.nextolive.kiteappmvvm.R;
import com.nextolive.kiteappmvvm.utils.UTILITY;
import com.nextolive.kiteappmvvm.viewmodels.SignUpUsernameViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpUsernameFragment extends Fragment {

    @BindView(R.id.tv_onboarding_tap)
    TextView tv_onboarding_tap;
    @BindView(R.id.pb_onboarding_progressbar)
    ProgressBar pb_onboarding_progressbar;
    @BindView(R.id.btn_signup_username)
    Button btn_signup_username;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.iv_back)
    ImageView iv_back;

    private SignUpUsernameViewModel mViewModel;

    public static SignUpUsernameFragment newInstance() {
        return new SignUpUsernameFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sign_up_username_fragment, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        ButterKnife.bind(this, v);
        //to unhide the status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow();
            w.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        UTILITY.buttonEffect(btn_signup_username,"#2196F3");//this method will provide click effect on btn

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        et_username.addTextChangedListener(new TextWatcher() {
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

                if (et_username != null && !et_username.getText().toString().equalsIgnoreCase("")) {
                    //here we need to check Is userName Exist or not
                    pb_onboarding_progressbar.setVisibility(View.VISIBLE);
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                sleep(3000);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        btn_signup_username.setEnabled(true);
                                        pb_onboarding_progressbar.setVisibility(View.GONE);
                                        //btn_signup_Username.setVisibility(View.VISIBLE);
                                    }
                                });

                            } catch (Exception e) {
                                //Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.i("error",e.getMessage());
                            }
                        }
                    };
                    thread.start();

                } else {
                    btn_signup_username.setEnabled(false);
                }

            }
        });

        btn_signup_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_username != null && et_username.getText().toString().length() > 0) {

                    SignUpFullnameFragment fragment = new SignUpFullnameFragment();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                    transaction.replace(R.id.framelayoutForFragments, fragment);
                    transaction.addToBackStack("signUpFullname");
                    transaction.commit();

                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.Enter_your_Username), Toast.LENGTH_SHORT).show();
                }
            }
        });



        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SignUpUsernameViewModel.class);
        // TODO: Use the ViewModel
    }

}
