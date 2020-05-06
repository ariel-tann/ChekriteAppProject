/*
 * Date: 2020.5.5
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Tests;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chekrite_group44.Asset_Properties.Inspection_checklist;
import com.chekrite_group44.Asset_Properties.Inspection_checklist_item;
import com.chekrite_group44.Asset_Properties.Inspection_checklist_items;
import com.chekrite_group44.Chekrite;
import com.chekrite_group44.PinView.Chekrite_PinView;
import com.chekrite_group44.R;
import com.chekrite_group44.Http_Request.APIs;
import com.chekrite_group44.Http_Request.APIsListener;
import com.chekrite_group44.Http_Request.APIsTask;
import com.chekrite_group44.Permission.Permission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class Inspection extends AppCompatActivity
        implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks{

    private Permission mPermission;
    TextView txt_inspection;
    private Inspection_PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    Inspection_checklist_items mItems;
    private InspectionListener inspectionListener = new InspectionListener() {
        @Override
        public void Completed() {
            if (mViewPager.getCurrentItem()<mItems.getChecklists().size()-1) {
                // TODO call API response
                // switch to next page
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }else{
                // TODO setup submit
                Inspection_submit submit = new Inspection_submit();
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
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONObject jchecklist = data.getJSONObject("checklist");
                    Inspection_checklist checklist = new Inspection_checklist(jchecklist);
                    txt_inspection.setText(checklist.getName());
                    JSONArray jchecklist_items = data.getJSONArray("checklist_items");
                    mItems = new Inspection_checklist_items(jchecklist_items);
                    // Create Recycle View
                    mPagerAdapter = new Inspection_PagerAdapter(getSupportFragmentManager(),
                            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT );
                    mViewPager = findViewById(R.id.inspection_container);
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

    private void setupViewPager(ViewPager viewPager, Inspection_checklist_items items){
        // setup list of Fragments
        Inspection_PagerAdapter adapter = new Inspection_PagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT );
        for (int i = 0; i< items.getChecklists().size(); i++){
            // get single item
            Inspection_checklist_item item = items.getChecklists().get(i);
            // get control type
            switch (item.getName()){
                case "Step":
                    adapter.addFragment(new Inspection_step(item,items.getChecklists().size(),i, inspectionListener) ,
                            items.getChecklists().get(i).getId());
                    break;
                case "Pass/Fail":
                    adapter.addFragment(new Inspection_button(item,items.getChecklists().size(), i, inspectionListener),
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

        txt_inspection = findViewById(R.id.inspection_name);
        // Display company name received from API
        SharedPreferences pref = getSharedPreferences(Chekrite.SHARED_PREFS, Context.MODE_PRIVATE);
        String profile_link = pref.getString("profile_photo", "");

        // set profile photo
        ImageView profile = findViewById(R.id.inspection_profile);
        if(profile_link.length() > 0)
            Glide.with(getApplicationContext()).load(profile_link).apply(RequestOptions.circleCropTransform()).into(profile);

        // get color and set to btn background
        String highlight_colour = pref.getString("highlight_colour", "#65cb81");
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.inspection_toolbar);
        toolbar.setBackgroundColor(Color.parseColor(highlight_colour));
        // get info from previous class
        String checklist_id=getIntent().getStringExtra("checklist_id");
        int asset_id = getIntent().getIntExtra("asset_id", 0);
        String asset_selection = getIntent().getStringExtra("asset_selection");

        // get payload
        StartAPI_payload api_payload = new StartAPI_payload();
        String payload = api_payload.StartAPI_payload(getApplicationContext(), checklist_id,asset_id, asset_selection);
        // send payload to DB
        new APIsTask(StartListener, getApplicationContext()).execute("POST", APIs.START,"",payload);

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
