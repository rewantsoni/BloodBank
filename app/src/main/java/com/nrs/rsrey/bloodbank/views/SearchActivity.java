package com.nrs.rsrey.bloodbank.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.nrs.rsrey.bloodbank.BuildConfig;
import com.nrs.rsrey.bloodbank.MyApplication;
import com.nrs.rsrey.bloodbank.R;
import com.nrs.rsrey.bloodbank.views.MainActivity.ViewModelType;
import com.nrs.rsrey.bloodbank.views.fragments.BloodListFragment;
import com.nrs.rsrey.bloodbank.views.listeners.SearchListener;
import com.squareup.leakcanary.RefWatcher;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.searchToolbar)
    Toolbar mSearchToolbar;
    @BindView(R.id.searchField)
    EditText mSearchField;
    private CompositeDisposable mCompositeDisposable;
    private SearchListener mSearchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initialize();
        listeners();
    }

    private void initialize() {
        setSupportActionBar(mSearchToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mCompositeDisposable = new CompositeDisposable();

        BloodListFragment bloodListFragment = new BloodListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(getResources().getString(R.string.bundleViewModelType), ViewModelType.SEARCH.ordinal());
        bloodListFragment.setArguments(bundle);
        mSearchListener = bloodListFragment;

        getSupportFragmentManager().beginTransaction().add(R.id.searchListContainer, bloodListFragment).commit();
    }

    private void listeners() {
        mCompositeDisposable.add(RxTextView.textChanges(mSearchField).debounce(200, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(charSequence -> {
            if (!charSequence.toString().isEmpty() && charSequence.toString().length() > 0) {
                performSearch(charSequence.toString());
            }
        }));
    }

    private void performSearch(String query) {
        mSearchListener.search(query);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void cleanUp() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable.dispose();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanUp();
        if (BuildConfig.DEBUG) {
            RefWatcher refWatcher = MyApplication.getRefWatcher(this);
            refWatcher.watch(this);
        }
    }
}
