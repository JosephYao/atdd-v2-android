package com.hitesh_sahu.retailapp.domain.api;

import android.os.AsyncTask;

import com.hitesh_sahu.retailapp.domain.mock.FakeWebServer;
import com.hitesh_sahu.retailapp.util.AppConstants;

public class ProductOverviewTask extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... strings) {

        // Simulate Web service calls
        FakeWebServer.getFakeWebServer().getAllProducts(
                AppConstants.CURRENT_CATEGORY);

        return null;
    }
}
