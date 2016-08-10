package com.example.demo.ui.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;

import com.example.demo.R;
import com.example.demo.common.Constants;
import com.example.demo.common.Utils;
import com.example.demo.data.DatabaseContract;
import com.example.demo.ui.fragment.ModelItemFragment;

import timber.log.Timber;

public class ModelItemActivity extends AppCompatActivity implements
        ModelItemFragment.Contract{

    // impl contract methods
    @Override
    public void saveModelItem(ContentValues values) {
        // DEBUG
        String name = values.getAsString(DatabaseContract.Model.COLUMN_NAME);
        String address = values.getAsString(DatabaseContract.Model.COLUMN_ADDRESS);
        String url = values.getAsString(DatabaseContract.Model.COLUMN_URL);
        String email = values.getAsString(DatabaseContract.Model.COLUMN_EMAIL);
        String phone = values.getAsString(DatabaseContract.Model.COLUMN_PHONE);
        Timber.i("%s: name: %s, address: %s, url: %s, email: %s, phone: %s",
                Constants.LOG_TAG, name, address, url, email, phone);

        // use the activity's content resolver to invoke insert on the content provider
        Uri newRecordUri = getContentResolver().insert(DatabaseContract.Model.CONTENT_URI, values);
        if (newRecordUri != null) {
            Utils.showSnackbar(mLayout, "Record added successfully");
        } else {
            Utils.showSnackbar(mLayout, "Error, recorded not added to database");
        }
        finish();
    }

    @Override
    public void quit() {
        finish();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, ModelItemActivity.class);
        activity.startActivity(intent);
    }

    private CoordinatorLayout mLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_item);
        mLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, ModelItemFragment.newInstance())
                    .commit();
        }
    }
}