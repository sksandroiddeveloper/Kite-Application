package com.nextolive.kiteappmvvm.fragments;

import androidx.annotation.RequiresApi;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.hardsoftstudio.widget.AnchorSheetBehavior;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.nextolive.kiteappmvvm.R;
import com.nextolive.kiteappmvvm.adapters.MapRecyclerView;
import com.nextolive.kiteappmvvm.adapters.Message_Read_unReadRecyclerView;
import com.nextolive.kiteappmvvm.helper.GPSTracker_Helper;
import com.nextolive.kiteappmvvm.utils.SharedData_Utils;
import com.nextolive.kiteappmvvm.utils.UTILITY;
import com.nextolive.kiteappmvvm.viewmodels.MapViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.nextolive.kiteappmvvm.activities_views.SplashAcitivity.MY_PERMISSIONS_REQUEST_LOCATION;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapViewModel mViewModel;

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    private String geojsonSourceLayerId = "geojsonSourceLayerId";
    private String symbolIconId = "symbolIconId";
    private AnchorSheetBehavior<View> anchorBehaviorMsgLoc, anchorBehaviorProfile;
   // AnchorSheetBehavior<View> anchor;//used for click on anchorBehaviorMsgLoc
    GPSTracker_Helper gpsTracker = null;
    Activity context;
    boolean checkforFloatingMenu = false;

    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.tv_channel_header_title)
    TextView tv_channel_header_title;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.cl_navi_full_Activity)
    ConstraintLayout cl_navi_full_Activity;
    @BindView(R.id.cl_half_Navilayout)
    ConstraintLayout cl_half_Navilayout;
    @BindView(R.id.rv_listOfMap)
    RecyclerView rv_listOfMap;
    @BindView(R.id.floating_action_menu)
    FloatingActionMenu floating_action_menu;
    @BindView(R.id.fab_center_map_holder)
    FloatingActionButton fab_center_map_holder;
    @BindView(R.id.fab_message_button)
    com.github.clans.fab.FloatingActionButton fab_message_button;
    @BindView(R.id.rl_openDrawer)
    RelativeLayout rl_openDrawer;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.iv_dialog_searchbar)
    ImageView iv_dialog_searchbar;
    @BindView(R.id.fl_msg_loc_anchor)
    FrameLayout fl_msg_loc_anchor;
    @BindView(R.id.fl_profileanchor_panel)
    FrameLayout fl_profileanchor_panel;
    @BindView(R.id.iv_channel_image)
    ImageView iv_channel_image;
    @BindView(R.id.ll_locationMain)
    LinearLayout ll_locationMain;
    @BindView(R.id.ll_messagesMain)
    LinearLayout ll_messagesMain;
    @BindView(R.id.ll_navi_changename)
    LinearLayout ll_navi_changename;
    @BindView(R.id.ll_navi_changeusername)
    LinearLayout ll_navi_changeusername;
    @BindView(R.id.ll_navi_changeEmail)
    LinearLayout ll_navi_changeEmail;
    @BindView(R.id.ll_navi_changePass)
    LinearLayout ll_navi_changePass;
    @BindView(R.id.ll_navi_signOut)
    LinearLayout ll_navi_signOut;
    @BindView(R.id.view_locationUnderLine)
    View view_locationUnderLine;
    @BindView(R.id.view_messageunderLine)
    View view_messageunderLine;
    @BindView(R.id.rl_messagesMain)
    RelativeLayout rl_messagesMain;
    @BindView(R.id.rl_locationMain)
    RelativeLayout rl_locationMain;
    @BindView(R.id.cv_unread)
    CardView cv_unread;
    @BindView(R.id.cv_read)
    CardView cv_read;
    @BindView(R.id.rv_unreadchat)
    RecyclerView rv_unreadchat;
    @BindView(R.id.rv_readchat)
    RecyclerView rv_readchat;
    TextView tv_add_map;
    @BindView(R.id.tv_back_to_map_location)
    TextView tv_back_to_map_location;


    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mapbox.getInstance(getActivity(), getString(R.string.access_token));//mapbox API key
        View v = inflater.inflate(R.layout.map_fragment, container, false);
        context = getActivity();

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);//orientation

        ButterKnife.bind(this, v);
        UTILITY.buttonEffect(iv_channel_image, "#C5C5C5");

        // To hide the status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        UTILITY.checkLocationPermission(context);

        gpsTracker = new GPSTracker_Helper(context);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);//mapView call back method
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments
                        enableLocationComponent(style);
                        // addUserLocations();
                        // Add the symbol layer icon to map for future use
                        style.addImage(symbolIconId, getResources().getDrawable(R.drawable.ic_location_blue));
                        // Create an empty GeoJSON source using the empty feature collection
                        setUpSource(style);
                        // Set up a new symbol layer for displaying the searched ic_location's feature coordinates
                        setupLayer(style);
                    }
                });
            }
        });

        for (int i = 0; i < navigationView.getChildCount(); i++) {
            navigationView.getChildAt(i).setOverScrollMode(View.OVER_SCROLL_NEVER);
        }

        MapRecyclerView adapter = new MapRecyclerView(context);
        rv_listOfMap.setAdapter(adapter);
        rv_listOfMap.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        ConstraintLayout headerLayout = (ConstraintLayout) navigationView.getHeaderView(0);
        final ImageView iv_down_arrow_nav = (ImageView) headerLayout.findViewById(R.id.iv_down_arrow_nav);
        final ImageView iv_profle_pic = (ImageView) headerLayout.findViewById(R.id.iv_profle_pic);
        final ImageView iv_arrow_drop_up = (ImageView) headerLayout.findViewById(R.id.iv_arrow_drop_up);
        tv_add_map = (TextView) v.findViewById(R.id.tv_add_map);

        tv_add_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddMapFragment fragment = new AddMapFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left,R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                transaction.replace(R.id.framelayoutForFragments, fragment);
                transaction.addToBackStack("AddMapFragment");
                transaction.commit();
            }
        });
        ll_navi_changename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("fragment", "ChangeNameFragment");

                ChangeUserDetailsFragment fragment = new ChangeUserDetailsFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                fragment.setArguments(bundle);//data passing
                transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left,
                        R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                transaction.replace(R.id.framelayoutForFragments, fragment);
                transaction.addToBackStack("ChangeUserDetailsFragment");
                transaction.commit();
            }
        });

        ll_navi_changeusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("fragment", "ChangeUsernameFragment");

                ChangeUserDetailsFragment fragment = new ChangeUserDetailsFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                fragment.setArguments(bundle);//data passing
                transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left,
                        R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                transaction.replace(R.id.framelayoutForFragments, fragment);
                transaction.addToBackStack("ChangeUserDetailsFragment");
                transaction.commit();
            }
        });

        ll_navi_changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("fragment", "ChangeEmailFragment");

                ChangeUserDetailsFragment fragment = new ChangeUserDetailsFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                fragment.setArguments(bundle);//data passing
                transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left,
                        R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                transaction.replace(R.id.framelayoutForFragments, fragment);
                transaction.addToBackStack("ChangeUserDetailsFragment");
                transaction.commit();
            }
        });

        ll_navi_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("fragment", "ChangePasswordFragment");

                ChangeUserDetailsFragment fragment = new ChangeUserDetailsFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                fragment.setArguments(bundle);//data passing
                transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left,
                        R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                transaction.replace(R.id.framelayoutForFragments, fragment);
                transaction.addToBackStack("ChangeUserDetailsFragment");
                transaction.commit();

            }
        });

        ll_navi_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedData_Utils.personId(context, "");
                //Toast.makeText(MainActivity.this, "Sign Out Successfully!!", Toast.LENGTH_SHORT).show();
                EmailFragment fragment = new EmailFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                transaction.replace(R.id.framelayoutForFragments, fragment);
                //transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        iv_down_arrow_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DisplayMetrics metrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) navigationView.getLayoutParams();
                params.width = metrics.widthPixels;
                navigationView.setLayoutParams(params);

                cl_half_Navilayout.setVisibility(View.GONE);
                cl_navi_full_Activity.setVisibility(View.VISIBLE);
                iv_arrow_drop_up.setVisibility(View.VISIBLE);
                iv_down_arrow_nav.setVisibility(View.GONE);
                iv_profle_pic.setVisibility(View.GONE);

                ((ViewGroup) navigationView).getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

            }
        });

        iv_arrow_drop_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DisplayMetrics metrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) navigationView.getLayoutParams();
                params.width = (int) ((metrics.widthPixels) * (.8));
                navigationView.setLayoutParams(params);

                iv_arrow_drop_up.setVisibility(View.GONE);
                iv_down_arrow_nav.setVisibility(View.VISIBLE);
                cl_half_Navilayout.setVisibility(View.VISIBLE);
                cl_navi_full_Activity.setVisibility(View.GONE);
                iv_profle_pic.setVisibility(View.GONE);

                ((ViewGroup) navigationView).getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
            }
        });


        setupFloatingActionMenu();

        anchorBehaviorMsgLoc = AnchorSheetBehavior.from(fl_msg_loc_anchor);
        anchorBehaviorMsgLoc.setHideable(false);
        anchorBehaviorMsgLoc.setAnchorSheetCallback(new AnchorSheetBehavior.AnchorSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, @AnchorSheetBehavior.State int newState) {
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        anchorBehaviorProfile = AnchorSheetBehavior.from(fl_profileanchor_panel);
        anchorBehaviorProfile.setHideable(true);
        anchorBehaviorProfile.setState(AnchorSheetBehavior.STATE_HIDDEN);
        anchorBehaviorProfile.setAnchorSheetCallback(new AnchorSheetBehavior.AnchorSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, @AnchorSheetBehavior.State int newState) {
                if (newState == AnchorSheetBehavior.STATE_HIDDEN) {
                    anchorBehaviorMsgLoc.setState(AnchorSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        rl_openDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        fab_center_map_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gpsTracker.getIsGPSTrackingEnabled()) {
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target(new LatLng(gpsTracker.latitude, gpsTracker.longitude))
                                    .zoom(15)
                                    .build()), 4000);
                } else {
                    gpsTracker.showSettingsAlert();
                }
            }
        });

        iv_dialog_searchbar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                // More Button Click Open Popup Menu
                Context wrapper = new ContextThemeWrapper(context, context.getTheme());
                @SuppressLint("RestrictedApi") final MenuBuilder menuBuilder = new MenuBuilder(wrapper);
                MenuInflater inflater = new MenuInflater(wrapper);
                inflater.inflate(R.menu.popup_menu, menuBuilder);
                @SuppressLint("RestrictedApi") MenuPopupHelper optionsMenu = new MenuPopupHelper(wrapper, menuBuilder, view);
                optionsMenu.setForceShowIcon(true);
                //optionsMenu.setGravity(Gravity.END);
                // set icons for Default map or satellite
                menuBuilder.findItem(R.id.use_satellite).setIcon(R.drawable.ic_use_satellite);
                // Display the menu
                optionsMenu.show();
            }
        });

        iv_channel_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnchorSheetBehavior<View> anchor;
                anchor = anchorBehaviorProfile;
                anchorBehaviorProfile.setState(AnchorSheetBehavior.STATE_ANCHOR);
                anchorState(anchor);
                anchorBehaviorMsgLoc.setState(AnchorSheetBehavior.STATE_FORCE_HIDDEN);


            }
        });

        ll_locationMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnchorSheetBehavior<View> anchor;
                ll_locationMain.setAlpha(1.0f);
                ll_messagesMain.setAlpha(0.5f);
                view_locationUnderLine.setVisibility(View.VISIBLE);
                view_messageunderLine.setVisibility(View.GONE);
                anchor = anchorBehaviorMsgLoc;
                rl_messagesMain.setVisibility(View.GONE);
                rl_locationMain.setVisibility(View.VISIBLE);
                anchorState(anchor);
            }
        });

        ll_messagesMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnchorSheetBehavior<View> anchor;
                ll_locationMain.setAlpha(0.5f);
                ll_messagesMain.setAlpha(1.0f);
                view_locationUnderLine.setVisibility(View.GONE);
                view_messageunderLine.setVisibility(View.VISIBLE);
                anchor = anchorBehaviorMsgLoc;
                rl_messagesMain.setVisibility(View.VISIBLE);
                rl_locationMain.setVisibility(View.GONE);
                anchorState(anchor);

            }
        });
        //userd for nested scrolling in recyclerView
        UTILITY.helperForRecyclerViewInScrolling(rv_unreadchat);

        UTILITY.helperForRecyclerViewInScrolling(rv_readchat);

        //these recyclerView of bottomsheet layout
        cv_unread.setVisibility(View.VISIBLE);
        //temporary logic later on we will implement dynamic check
        Message_Read_unReadRecyclerView adaptermsg = new Message_Read_unReadRecyclerView(context, "NO");
        rv_unreadchat.setAdapter(adaptermsg);
        rv_unreadchat.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rv_unreadchat.setNestedScrollingEnabled(false);

        cv_read.setVisibility(View.VISIBLE);
        Message_Read_unReadRecyclerView adapter1 = new Message_Read_unReadRecyclerView(context, "YES");
        rv_readchat.setAdapter(adapter1);
        rv_readchat.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rv_readchat.setNestedScrollingEnabled(false);


        tv_back_to_map_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //here we need to hide map
                anchorBehaviorMsgLoc.setState(AnchorSheetBehavior.STATE_COLLAPSED);

            }
        });

        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                anchorBehaviorMsgLoc.setState(AnchorSheetBehavior.STATE_COLLAPSED);
                anchorBehaviorProfile.setState(AnchorSheetBehavior.STATE_FORCE_HIDDEN);
                return false;
            }
        });
        return v;
    }

    private void anchorState(AnchorSheetBehavior<View> anchor) {

        switch (anchor.getState()) {
            case AnchorSheetBehavior.STATE_ANCHOR:
                anchor.setState(AnchorSheetBehavior.STATE_EXPANDED);
                break;
            case AnchorSheetBehavior.STATE_COLLAPSED:
                anchor.setState(AnchorSheetBehavior.STATE_ANCHOR);
                break;
            case AnchorSheetBehavior.STATE_HIDDEN:
                anchor.setState(AnchorSheetBehavior.STATE_ANCHOR);

                break;
            case AnchorSheetBehavior.STATE_EXPANDED:
                anchor.setState(AnchorSheetBehavior.STATE_ANCHOR);
                break;
            default:
                break;
        }
    }

    private void setupFloatingActionMenu() {

        floating_action_menu.setIconAnimated(false);
        floating_action_menu.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floating_action_menu.toggle(true);
                if (floating_action_menu.isOpened()) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fab_center_map_holder.show();
                        }
                    });

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(UTILITY.convDpToPx(context, 50), RelativeLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                            floating_action_menu.setLayoutParams(layoutParams);
                        }
                    }, 300);

                } else {
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    floating_action_menu.setLayoutParams(layoutParams);
                    fab_center_map_holder.hide();

                }


            }
        });

        floating_action_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (floating_action_menu.isOpened()) {
                    floating_action_menu.toggle(true);
                    fab_center_map_holder.bringToFront();
                    checkforFloatingMenu = false;
                    floating_action_menu.setIconAnimated(false);
                    floating_action_menu.getMenuIconView().setImageResource(R.drawable.ic_add);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(UTILITY.convDpToPx(context, 50), RelativeLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                            floating_action_menu.setLayoutParams(layoutParams);
//                            mFloatingActionMenu.getMenuIconView().setImageResource(R.drawable.ic_add);
                        }
                    }, 300);
                }
            }
        });

        fab_message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floating_action_menu.performClick();

                SendDirectMessageFragment fragment = new SendDirectMessageFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                transaction.replace(R.id.framelayoutForFragments, fragment);
                transaction.addToBackStack("SendDirectMessageFragment");
                transaction.commit();

            }
        });
        fab_message_button.setColorFilter(ContextCompat.getColor(context, R.color.waver_text),
                PorterDuff.Mode.MULTIPLY);


        floating_action_menu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened) {
                    fab_center_map_holder.hide();
                    floating_action_menu.setIconAnimated(false);
                    floating_action_menu.getMenuIconView().setImageResource(R.drawable.ic_location);
                    checkforFloatingMenu = true;
                    floating_action_menu.setBackgroundColor(getResources().getColor(R.color.colorWhite));

                } else {
                    fab_center_map_holder.show();
                    if (checkforFloatingMenu) {
                        //here we need to change floatingMenu icon
                        floating_action_menu.setIconAnimated(false);
                        floating_action_menu.getMenuIconView().setImageResource(R.drawable.ic_add);

                        PostLocationFragment fragment = new PostLocationFragment();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction transaction = fm.beginTransaction();
                        transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                        transaction.replace(R.id.framelayoutForFragments, fragment);
                        transaction.addToBackStack("PostLocationFragment");
                        transaction.commit();
                    }


                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MapViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        initSearchFab();
    }

    private void initSearchFab() {
        tv_channel_header_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new PlaceAutocomplete.IntentBuilder()
                        .accessToken(Mapbox.getAccessToken())
                        .placeOptions(PlaceOptions.builder()
                                .backgroundColor(Color.parseColor("#EEEEEE"))
                                .limit(10)
                                .build(PlaceOptions.MODE_CARDS))
                        .build(getActivity());
                startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);

            }
        });
    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(getActivity())) {
            // Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            // Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(getActivity(), loadedMapStyle).build());
            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);
            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);

            //mapboxMap.getUiSettings().setCompassMargins(0, 80, 0, 0);
            mapboxMap.getUiSettings().setCompassEnabled(false);
            mapboxMap.getUiSettings().setZoomGesturesEnabled(true);
            mapboxMap.getUiSettings().setScrollGesturesEnabled(true);
            mapboxMap.getUiSettings().setAllGesturesEnabled(true);
        } else {
            permissionsManager = new PermissionsManager((PermissionsListener) getActivity());
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    //used for placelocater
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            // Retrieve selected ic_location's CarmenFeature
            CarmenFeature selectedCarmenFeature = PlaceAutocomplete.getPlace(data);
            // Create a new FeatureCollection and add a new Feature to it using selectedCarmenFeature above.
            // Then retrieve and update the source designated for showing a selected ic_location's symbol layer icon

            if (mapboxMap != null) {
                Style style = mapboxMap.getStyle();
                if (style != null) {
                    GeoJsonSource source = style.getSourceAs(geojsonSourceLayerId);
                    if (source != null) {
                        source.setGeoJson(FeatureCollection.fromFeatures(
                                new Feature[]{Feature.fromJson(selectedCarmenFeature.toJson())}));
                    }
                    // Move map camera to the selected ic_location
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target(new LatLng(((Point) selectedCarmenFeature.geometry()).latitude(),
                                            ((Point) selectedCarmenFeature.geometry()).longitude()))
                                    .zoom(14)
                                    .build()), 4000);
                }
            }
        }
    }

    private void setUpSource(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(geojsonSourceLayerId));
    }

    private void setupLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addLayer(new SymbolLayer("SYMBOL_LAYER_ID", geojsonSourceLayerId).withProperties(
                iconImage(symbolIconId),
                iconOffset(new Float[]{0f, -8f})
        ));
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


}
