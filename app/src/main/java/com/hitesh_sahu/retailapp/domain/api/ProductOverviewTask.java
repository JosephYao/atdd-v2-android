package com.hitesh_sahu.retailapp.domain.api;

import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.hitesh_sahu.retailapp.domain.mock.FakeWebServer;
import com.hitesh_sahu.retailapp.model.CenterRepository;
import com.hitesh_sahu.retailapp.util.AppConstants;
import com.hitesh_sahu.retailapp.view.adapter.ProductsInCategoryPagerAdapter;
import com.hitesh_sahu.retailapp.view.fragment.ProductListFragment;

import java.util.Set;

public class ProductOverviewTask extends AsyncTask<String, Void, Void> {

    private final ViewPager viewPager;
    private final TabLayout tabLayout;
    private final FragmentActivity fragmentActivity;

    public ProductOverviewTask(ViewPager viewPager, TabLayout tabLayout, FragmentActivity fragmentActivity) {
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;
        this.fragmentActivity = fragmentActivity;
    }

    @Override
    protected Void doInBackground(String... strings) {

        // Simulate Web service calls
        FakeWebServer.getFakeWebServer().getAllProducts(
                AppConstants.CURRENT_CATEGORY);

        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);

        ProductsInCategoryPagerAdapter adapter = new ProductsInCategoryPagerAdapter(
                fragmentActivity.getSupportFragmentManager());
        Set<String> keys = CenterRepository.getCenterRepository().getMapOfProductsInCategory()
                .keySet();

        for (String string : keys) {
            adapter.addFrag(new ProductListFragment(string), string);
        }

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
