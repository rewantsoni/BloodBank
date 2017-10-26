package com.nrs.rsrey.bloodbank.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nrs.rsrey.bloodbank.BuildConfig;
import com.nrs.rsrey.bloodbank.MyApplication;
import com.nrs.rsrey.bloodbank.R;
import com.nrs.rsrey.bloodbank.views.fragments.StartFragment;
import com.squareup.leakcanary.RefWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.mainToolbar)
    Toolbar mToolbar;
    private MenuItem mSearchMenuItem;
    private ViewModelType mViewModelType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        setSupportActionBar(mToolbar);

        /*Fragment fragment = new StartFragment();
        Bundle args = new Bundle();
        mViewModelType = ViewModelType.ADMIN;
        args.putInt(getResources().getString(R.string.bundleViewModelType),ViewModelType.ADMIN.ordinal());
        fragment.setArguments(args);

        getSupportFragmentManager().beginTransaction().add(R.id.mainContainer, fragment).commit();*/
        mViewModelType = ViewModelType.DEFAULT;
        getSupportFragmentManager().beginTransaction().add(R.id.mainContainer, new StartFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        mSearchMenuItem = menu.findItem(R.id.menuAdminSearch);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mViewModelType == ViewModelType.ADMIN) {
            mSearchMenuItem.setVisible(true);
        } else {
            mSearchMenuItem.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAdminSearch:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        mViewModelType = ViewModelType.DEFAULT;
        invalidateOptionsMenu();
        super.onBackPressed();
    }

    public void replaceFragment(Fragment fragment, ViewModelType viewModelType) {

        Bundle args = new Bundle();
        args.putInt(getResources().getString(R.string.bundleViewModelType), viewModelType.ordinal());
        fragment.setArguments(args);

        mViewModelType = viewModelType;

        if (viewModelType == ViewModelType.ADMIN) {
            invalidateOptionsMenu();
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack("backStack");
        ft.replace(R.id.mainContainer, fragment);
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (BuildConfig.DEBUG) {
            RefWatcher refWatcher = MyApplication.getRefWatcher(this);
            refWatcher.watch(this);
        }
    }

    public enum ViewModelType {
        DEFAULT, SEARCH, ADMIN, USER
    }
}
