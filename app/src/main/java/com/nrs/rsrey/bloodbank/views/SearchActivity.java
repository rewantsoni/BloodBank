package com.nrs.rsrey.bloodbank.views;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.nrs.rsrey.bloodbank.R;
import com.nrs.rsrey.bloodbank.data.BloodGroupEntity;
import com.nrs.rsrey.bloodbank.viewmodel.BloodViewModel;
import com.nrs.rsrey.bloodbank.views.adapters.BloodListAdapter;
import com.nrs.rsrey.bloodbank.views.listeners.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements ItemClickListener{

    private static final String TAG = SearchActivity.class.getSimpleName();
    @BindView(R.id.searchToolbar)Toolbar mSearchToolbar;
    @BindView(R.id.searchField)EditText mSearchField;
    @BindView(R.id.searchList)RecyclerView mSearchList;
    private List<BloodGroupEntity> mSearchListEntity;
    private BloodListAdapter mBloodListAdapter;
    private BloodViewModel mBloodViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        mBloodViewModel = ViewModelProviders.of(this).get(BloodViewModel.class);
        initialize();
        listeners();
    }

    private void initialize() {
        setSupportActionBar(mSearchToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mSearchListEntity = new ArrayList<>();

        mSearchList.setItemAnimator(new DefaultItemAnimator());
        mSearchList.setLayoutManager(new LinearLayoutManager(this));
        mSearchList.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        mSearchList.setHasFixedSize(true);

        mBloodListAdapter = new BloodListAdapter(this,mSearchListEntity,this);

        mSearchList.setAdapter(mBloodListAdapter);
    }

    private void listeners(){
        mSearchField.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId== EditorInfo.IME_ACTION_SEARCH){
                performSearch(v.getText().toString());
            }
            return false;
        });
        mSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                performSearch(s.toString());
            }
        });
    }

    private void performSearch(String query){
        mSearchListEntity =  mBloodViewModel.searchBloodByGroup(query + '%').getValue();;
        mBloodListAdapter.swapItem(mSearchListEntity);
        mBloodListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(int position) {

    }
}
