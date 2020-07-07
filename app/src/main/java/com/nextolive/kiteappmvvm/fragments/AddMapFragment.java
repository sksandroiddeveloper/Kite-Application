package com.nextolive.kiteappmvvm.fragments;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.nextolive.kiteappmvvm.R;
import com.nextolive.kiteappmvvm.utils.UTILITY;
import com.nextolive.kiteappmvvm.viewmodels.AddMapViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMapFragment extends Fragment {

    private AddMapViewModel mViewModel;
    AlertDialog dialog_privacy;

    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.et_map_name)
    EditText et_map_name;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_searchInMap)
    TextView tv_searchInMap;
    @BindView(R.id.tv_onboarding_tap)
    TextView tv_onboarding_tap;
    @BindView(R.id.tv_onboarding_tap_MapPurpose)
    TextView tv_onboarding_tap_MapPurpose;
    @BindView(R.id.cl_addMap)
    ConstraintLayout cl_addMap;
    @BindView(R.id.cl_addMapPurpose)
    ConstraintLayout cl_addMapPurpose;
    @BindView(R.id.btn_nextMapPurpose)
    Button btn_nextMapPurpose;
    @BindView(R.id.et_map_purpose)
    EditText et_map_purpose;
    @BindView(R.id.tv_headerLabel)
    TextView tv_headerLabel;
    int isAddMapClick = 0;

    public static AddMapFragment newInstance() {
        return new AddMapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_map_fragment, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        ButterKnife.bind(this, v);
        //to unhide the status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow();
            w.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        UTILITY.buttonEffect(btn_next,"#2196F3");//this method will provide click effect on btn
        UTILITY.buttonEffect(btn_nextMapPurpose,"#2196F3");//this method will provide click effect on btn

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAddMapClick == 0) {
                    getActivity().onBackPressed();
                } else {
                    isAddMapClick = 0;
                    cl_addMap.setVisibility(View.VISIBLE);
                    cl_addMapPurpose.setVisibility(View.GONE);
                    tv_headerLabel.setText(getResources().getString(R.string.Addmap));
                    tv_searchInMap.setVisibility(View.VISIBLE);
                }

            }
        });

        //addMap
        et_map_name.addTextChangedListener(new TextWatcher() {
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
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_map_name != null && et_map_name.getText().toString().length() >= 0) {

                    isAddMapClick = 1;
                    cl_addMap.setVisibility(View.GONE);
                    cl_addMapPurpose.setVisibility(View.VISIBLE);
                    tv_headerLabel.setText(getResources().getString(R.string.Mappurpose));
                    tv_searchInMap.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.EnterMapName), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //addMapPurpose
        et_map_purpose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    tv_onboarding_tap_MapPurpose.setVisibility(View.GONE);
                } else {
                    tv_onboarding_tap_MapPurpose.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btn_nextMapPurpose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_map_purpose != null && et_map_purpose.getText().toString().length() >= 0) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    // Get the layout inflater
                    LayoutInflater inflater = getLayoutInflater();
                    builder.setTitle("");
                    View view1 = inflater.inflate(R.layout.privacy_add_map_dialog, null);
                    builder.setView(view1);
                    LinearLayout ll_private = view1.findViewById(R.id.ll_private);

                    ll_private.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog_privacy.dismiss();
                            MapFragment fragment = new MapFragment();
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction transaction = fm.beginTransaction();
                            transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                            transaction.replace(R.id.framelayoutForFragments, fragment);
                            //transaction.addToBackStack(null);
                            transaction.commit();

                            Toast.makeText(getActivity(), "Map Added Successfully!", Toast.LENGTH_SHORT).show();

                        }
                    });

                    LinearLayout ll_public = view1.findViewById(R.id.ll_public);

                    ll_public.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog_privacy.dismiss();
                            MapFragment fragment = new MapFragment();
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction transaction = fm.beginTransaction();
                            transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                            transaction.replace(R.id.framelayoutForFragments, fragment);
                            //transaction.addToBackStack(null);
                            transaction.commit();
                            Toast.makeText(getActivity(), "Map Added Successfully!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.create();
                    dialog_privacy = builder.show();


                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.EnterMapName), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddMapViewModel.class);
        // TODO: Use the ViewModel
    }

}
