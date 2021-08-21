/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.hitesh_sahu.retailapp.domain.api;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitesh_sahu.retailapp.R;
import com.hitesh_sahu.retailapp.model.CenterRepository;
import com.hitesh_sahu.retailapp.model.entities.ProductCategoryModel;
import com.hitesh_sahu.retailapp.util.AppConstants;
import com.hitesh_sahu.retailapp.util.Utils;
import com.hitesh_sahu.retailapp.util.Utils.AnimationType;
import com.hitesh_sahu.retailapp.view.activities.ECartHomeActivity;
import com.hitesh_sahu.retailapp.view.adapter.CategoryListAdapter;
import com.hitesh_sahu.retailapp.view.adapter.CategoryListAdapter.OnItemClickListener;
import com.hitesh_sahu.retailapp.view.fragment.ProductOverviewFragment;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * The Class ImageLoaderTask.
 */
public class ProductCategoryLoaderTask extends AsyncTask<String, Void, Void> {

    private static final int NUMBER_OF_COLUMNS = 2;
    private Context context;
    private RecyclerView recyclerView;

    public ProductCategoryLoaderTask(RecyclerView listView, Context context) {

        this.recyclerView = listView;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        if (null != ((ECartHomeActivity) context).getProgressBar())
            ((ECartHomeActivity) context).getProgressBar().setVisibility(
                    View.VISIBLE);

    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        if (null != ((ECartHomeActivity) context).getProgressBar())
            ((ECartHomeActivity) context).getProgressBar().setVisibility(
                    View.GONE);

        if (recyclerView != null) {
            CategoryListAdapter simpleRecyclerAdapter = new CategoryListAdapter(
                    context);

            recyclerView.setAdapter(simpleRecyclerAdapter);

            simpleRecyclerAdapter
                    .SetOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(View view, int position) {

                            AppConstants.CURRENT_CATEGORY = position;

                            Utils.switchFragmentWithAnimation(
                                    R.id.frag_container,
                                    new ProductOverviewFragment(),
                                    ((ECartHomeActivity) context), null,
                                    AnimationType.SLIDE_LEFT);

                        }
                    });
        }

    }

    @Override
    protected Void doInBackground(String... params) {
//        FakeWebServer.getFakeWebServer().addCategory();

        try {
            URL url = new URL("http://192.168.0.105:9081/product-categories");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            InputStream in = new BufferedInputStream(conn.getInputStream());
            String response = IOUtils.toString(in, "UTF-8");
            System.out.println(response);

            ObjectMapper objectMapper = new ObjectMapper();
            List<ProductCategoryModel> productCategories = objectMapper.readValue(response, new TypeReference<List<ProductCategoryModel>>() {
            });

            CenterRepository.getCenterRepository().setListOfCategory(productCategories);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}