package com.nrs.rsrey.bloodbank.views.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nrs.rsrey.bloodbank.R;
import com.nrs.rsrey.bloodbank.data.BloodGroupEntity;
import com.nrs.rsrey.bloodbank.viewmodel.BloodViewModel;
import com.nrs.rsrey.bloodbank.views.adapters.BloodListAdapter;
import com.nrs.rsrey.bloodbank.views.fragments.dailogs.AddBloodDialogFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BloodListFragment extends Fragment {

    @BindView(R.id.bloodList)RecyclerView mBloodListView;
    @BindView(R.id.bloodListAdd)FloatingActionButton mAddBlood;
    private Unbinder mUnbinder;
    private List<BloodGroupEntity> mBloodList;
    private BloodViewModel mBloodViewModel;
    private BloodListAdapter mBloodListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_blood_list, container, false);
        mUnbinder  = ButterKnife.bind(this,view);
        mBloodViewModel = ViewModelProviders.of(this).get(BloodViewModel.class);
        initialize();
        listeners();
        return view;
    }

    private void initialize(){
        mBloodList = new ArrayList<>();

        mBloodListView.setItemAnimator(new DefaultItemAnimator());
        mBloodListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBloodListView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        mBloodListView.setHasFixedSize(true);

        mBloodListAdapter = new BloodListAdapter(getActivity(),mBloodList);

        mBloodListView.setAdapter(mBloodListAdapter);

        mBloodViewModel.getBloodList().observe(this, bloodGroupEntities -> {
            mBloodList = bloodGroupEntities;
            mBloodListAdapter.swapItem(mBloodList);
            mBloodListAdapter.notifyDataSetChanged();
        });

    }

    private void listeners(){
        mAddBlood.setOnClickListener(v->{
            new AddBloodDialogFragment().show(getFragmentManager(),"addBlood");
        });
    }

    private void cleanUp(){
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cleanUp();
    }
}
