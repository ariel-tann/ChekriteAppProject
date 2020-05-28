/*
 * Date: 2020.5.5
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Tests;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chekrite_group44.Asset_Properties.Inspection_checklist;
import com.chekrite_group44.Asset_Properties.Inspection_checklist_item;
import com.chekrite_group44.Asset_Properties.Inspection_checklist_items;
import com.chekrite_group44.Asset_Properties.Inspection_test;
import com.chekrite_group44.Chekrite;
import com.chekrite_group44.DashBoard.Dashboard;
import com.chekrite_group44.MetaData.MetaData_Asset;
import com.chekrite_group44.R;
import com.chekrite_group44.Http_Request.APIs;
import com.chekrite_group44.Http_Request.APIsListener;
import com.chekrite_group44.Http_Request.APIsTask;
import com.chekrite_group44.Permission.Permission;
import com.chekrite_group44.Asset_Properties.Control_Type;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class Inspection_main extends AppCompatActivity
        implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks, NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "Inspection";
    private Permission mPermission;
    TextView txt_inspection;
    private Inspection_PagerAdapter mPagerAdapter;
    private InspectionViewPager mViewPager;
    Inspection_checklist_items mItems;
    Inspection_test mTest;
    long start_inspection;
    ProgressDialog dialog;
    DrawerLayout drawer;
    Button nav_discard_btn;
    Button close_discard_dialog_btn;
    Dialog discard_dialog;
    TickView tick;
    LinearLayout l_layout;
    private APIsListener ResponseAPI = new APIsListener() {
        @Override
        public void API_Completed(JSONObject jsonObject) {
            String status = null;
            try {
                status = (String) jsonObject.get("status");
                String message = (String) jsonObject.get("message");
                if (status.equals("success")) {
                    Log.d(TAG, "success: " + message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private APIsListener DiscardAPI = new APIsListener() {
        @Override
        public void API_Completed(JSONObject jsonObject) {
            String status = null;
            try {
                status = (String) jsonObject.get("status");
                String message = (String) jsonObject.get("message");
                if (status.equals("success")) {
                    Log.d(TAG, "success: " + message);
                    Toast toast = Toast.makeText(getApplicationContext(),
                            message, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private APIsListener SubmitAPI = new APIsListener() {
        @Override
        public void API_Completed(JSONObject jsonObject) {
            String status = null;
            try {
                status = (String) jsonObject.get("status");
                String message = (String) jsonObject.get("message");
                if (status.equals("success")) {
                    dialog.dismiss();
                    Log.d(TAG, "success: " + message);
                    // go back to DashBoard
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Submit Successfully", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    openDashBoard();
                }
            } catch (JSONException e) {
                dialog.dismiss();
                Toast toast = Toast.makeText(getApplicationContext(),
                        jsonObject.toString(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                e.printStackTrace();
            }
        }
    };
    private InspectionListener submitListener = new InspectionListener() {

        @Override
        public void Completed(String type, int button_order, double button_value, String txt_value,
                              long start_timestamp, long response_timestamp) {
            //TODO submit
            long end = System.currentTimeMillis();
            try {
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setTitle("Submit Results");
                dialog.setMessage("Please wait...");
                dialog.setIndeterminate(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                Playload_Submit payload = new Playload_Submit(mTest, start_inspection, end,
                        new MetaData_Asset(getApplicationContext()));
                new APIsTask(SubmitAPI).execute("POST", APIs.SUBMIT, "", payload.getPayload().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    private InspectionListener inspectionListener = new InspectionListener() {

        @Override
        public void Completed(String type, int button_order, double value, String txt_value,
                              long start_timestamp, long response_timestamp) {
            if (mViewPager.getCurrentItem()<mItems.getChecklists().size()-1) {
                // get which item is completed
                Inspection_checklist_item item = mItems.getChecklists().get(mViewPager.getCurrentItem());
                // get response payload
                try {
                    Payload_Response payload = new Payload_Response(item, mTest, type, button_order, txt_value, value,
                            start_timestamp,response_timestamp, new MetaData_Asset(getApplicationContext()));
                    new APIsTask(ResponseAPI).execute("POST", APIs.RESPONSES, "", payload.getPayload().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // switch to next page
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }else{
                // The last item
                Inspection_checklist_item item = mItems.getChecklists().get(mViewPager.getCurrentItem());
                // get response payload
                try {
                    Payload_Response payload = new Payload_Response(item, mTest, type, button_order, txt_value, value,
                            start_timestamp,response_timestamp, new MetaData_Asset(getApplicationContext()));
                    new APIsTask(ResponseAPI).execute("POST", APIs.RESPONSES, "", payload.getPayload().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // inflate submit dialog

                dialog_submit submit = new dialog_submit(submitListener);
                submit.show(getSupportFragmentManager(),"submit");
            }
        }
    };
    APIsListener StartListener = new APIsListener() {
        @Override
        public void API_Completed(JSONObject jsonObject) {
            // GET Response from DB
            try {
                String status = (String) jsonObject.get("status");
                if(status.equals("success")){
                    // start inspection
                    start_inspection = System.currentTimeMillis();
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONObject jchecklist = data.getJSONObject("checklist");
                    Inspection_checklist checklist = new Inspection_checklist(jchecklist);
                    txt_inspection.setText(checklist.getName());
                    JSONArray jchecklist_items = data.getJSONArray("checklist_items");
                    mItems = new Inspection_checklist_items(jchecklist_items);
                    mTest = new Inspection_test(data.getJSONObject("test"));
                    // Create Recycle View
                    mPagerAdapter = new Inspection_PagerAdapter(getSupportFragmentManager(),
                            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT );

                    // setup the pager
                    setupViewPager(mViewPager, mItems);

                }else{

                    String message = jsonObject.getString("message");
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Error: "+message, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    public void openDashBoard() {

        Intent dashboardIntent = new Intent(this, Dashboard.class);
        startActivity(dashboardIntent);
        finish();
    }
    private void setupViewPager(ViewPager viewPager, Inspection_checklist_items items){
        // setup list of Fragments
        Inspection_PagerAdapter adapter = new Inspection_PagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT );
        for (int i = 0; i< items.getChecklists().size(); i++){
            // get single item
            Inspection_checklist_item item = items.getChecklists().get(i);
            // get control type
            switch (item.getControl().getType()){
                case Control_Type.BUTTONS:
                    adapter.addFragment(new fragment_button(item,items.getChecklists().size(), i, inspectionListener),
                            items.getChecklists().get(i).getId());
                    break;
                case Control_Type.GAUGE:
                    // only works for two side gauge
                    adapter.addFragment(new fragment_gauge(item,items.getChecklists().size(), i, inspectionListener),
                            items.getChecklists().get(i).getId());
                    break;
            }
        }

        viewPager.setAdapter(adapter);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);
        // permission checking
        mPermission = new Permission(this, this);
        mPermission.RequestPermissions();
        SharedPreferences pref = getSharedPreferences(Chekrite.SHARED_PREFS, Context.MODE_PRIVATE);
        txt_inspection = findViewById(R.id.inspection_name);
        // Display company name received from API
        String profile_link = pref.getString("profile_photo", "");

        // set profile photo
        ImageView profile = findViewById(R.id.inspection_profile);
        if(!profile_link.equals("null"))
            Glide.with(getApplicationContext()).load(profile_link).apply(RequestOptions.circleCropTransform()).into(profile);

        // get color and set to btn background
        Toolbar toolbar = findViewById(R.id.inspection_toolbar);
        toolbar.setBackgroundColor(Chekrite.getParseColor());
        setSupportActionBar(toolbar);

        //Set up side navigation bar
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        changeMenuTextColor(menu, toggle);
        setNavBarProfile(navigationView, pref, profile_link);
        toggle.syncState();

        //Discard dialog
        discard_dialog = new Dialog(this);

        // get info from previous class
        String checklist_id =getIntent().getStringExtra("checklist_id");
        int asset_id = getIntent().getIntExtra("asset_id", 0);
        String asset_selection = getIntent().getStringExtra("asset_selection");
        // disable fragment swipe left
        mViewPager = findViewById(R.id.inspection_container);
        mViewPager.setAllowedSwipeDirection(SwipeDirection.left);
        // get payload
        Playload_Start api_payload = new Playload_Start();
        String payload = api_payload.StartAPI_payload(getApplicationContext(), checklist_id,asset_id, asset_selection);
        // send payload to DB
        new APIsTask(StartListener).execute("POST", APIs.START,"",payload);
        //
        dialog = new ProgressDialog(this); // Login log
    }

    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_review:
                Toast.makeText(this, "Review", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_suspend:
                Toast.makeText(this, "Suspend", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_discard:
//                Toast.makeText(this, "Discard", Toast.LENGTH_SHORT).show();
                ShowDiscardDialog();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setNavBarProfile (NavigationView navigationView, SharedPreferences pref, String profile_link) {
        String name = pref.getString("first_name", "") + " " + pref.getString("last_name", "");
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.nav_profile_name);
        ImageView nav_profile_image = (ImageView)hView.findViewById(R.id.nav_profile_image);
        if(!profile_link.equals("null")) {
            Glide.with(getApplicationContext()).load(profile_link).centerCrop().apply(RequestOptions.circleCropTransform()).into(nav_profile_image);
        }
        nav_user.setText(name);

    }

    public void changeMenuTextColor(Menu menu, ActionBarDrawerToggle toggle) {
        MenuItem tools= menu.findItem(R.id.nav_suspend);
        SpannableString s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.Suspend), 0, s.length(), 0);
        tools.setTitle(s);

        tools= menu.findItem(R.id.nav_discard);
        s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.Discard), 0, s.length(), 0);
        tools.setTitle(s);

        tools= menu.findItem(R.id.nav_review);
        s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.Review), 0, s.length(), 0);
        tools.setTitle(s);

//        tools= menu.findItem(R.id.checklist_title);
//        s = new SpannableString(tools.getTitle());
//        s.setSpan(new TextAppearanceSpan(this, R.style.Checklist), 0, s.length(), 0);
//        tools.setTitle(s);

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
    }

    public void ShowDiscardDialog() {
        discard_dialog.setContentView(R.layout.discard_dialog);
        Window window = discard_dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        close_discard_dialog_btn = discard_dialog.findViewById(R.id.return_checklist_btn);
        close_discard_dialog_btn.setAllCaps(false);
        close_discard_dialog_btn.setText(R.string.rtn_checklist);

        l_layout = discard_dialog.findViewById(R.id.l_layout);
        tick = new TickView(this, new TickListener() {
            @Override
            public void Completed() {
                new APIsTask(DiscardAPI).execute("DELETE", APIs.DISCARD, Integer.toString(mTest.getId()),"");
                openDashBoard();
            }
        });
        // set a Listener to monitor when layout finished creating
        ViewTreeObserver vto = l_layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                l_layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int l_width  = l_layout.getMeasuredWidth();
                int l_height = l_layout.getMeasuredHeight();
                tick.setLayoutParams(new LinearLayout.LayoutParams(l_width,l_height));
                l_layout.addView(tick);
            }
        });
        // dismiss dialog
        close_discard_dialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discard_dialog.dismiss();
            }
        });
        discard_dialog.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d("KAI", this.toString()+"Permission Deny: "+perms.toString());

    }

    @Override
    public void onRationaleAccepted(int requestCode) {

    }

    @Override
    public void onRationaleDenied(int requestCode) {

    }
}
