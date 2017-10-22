package com.nrs.rsrey.bloodbank.views.fragments;


import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nrs.rsrey.bloodbank.R;
import com.nrs.rsrey.bloodbank.data.BloodGroupEntity;
import com.nrs.rsrey.bloodbank.viewmodel.BloodViewModel;
import com.nrs.rsrey.bloodbank.views.adapters.BloodListAdapter;
import com.nrs.rsrey.bloodbank.views.listeners.ItemClickListener;
import com.nrs.rsrey.bloodbank.views.listeners.PopUpMenuClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BloodListFragment extends Fragment implements ItemClickListener, PopUpMenuClickListener {

    private static final String TAG = BloodListFragment.class.getSimpleName();
    @BindView(R.id.bloodList)RecyclerView mBloodListView;
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
        return view;
    }

    private void initialize(){
        mBloodList = new ArrayList<>();

        mBloodListView.setItemAnimator(new DefaultItemAnimator());
        mBloodListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBloodListView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        mBloodListView.setHasFixedSize(true);

        mBloodListAdapter = new BloodListAdapter(getActivity(), mBloodList, this, this);

        mBloodListView.setAdapter(mBloodListAdapter);

        mBloodViewModel.getBloodList().observe(this, bloodGroupEntities -> {
            mBloodList = bloodGroupEntities;
            mBloodListAdapter.swapItem(mBloodList);
            mBloodListAdapter.notifyDataSetChanged();
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

    private void editEntry(BloodGroupEntity bloodGroupEntity, String message, int approved) {
        bloodGroupEntity.setApproved(1);
        AlertDialog.Builder editDialog = new AlertDialog.Builder(getActivity());
        editDialog.setTitle(getActivity().getResources().getString(R.string.adminDialogTitle))
                .setMessage(message)
                .setNegativeButton(getActivity().getResources().getString(R.string.cancel), (dialog, which) -> {

                })
                .setPositiveButton(getActivity().getResources().getString(R.string.yes), (dialog, which) -> {
                    bloodGroupEntity.setApproved(approved);
                    mBloodViewModel.updateBlood(bloodGroupEntity);
                });
        editDialog.create().show();
    }

    private void deleteEntry(BloodGroupEntity bloodGroupEntity) {
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getActivity());
        deleteDialog.setTitle(getActivity().getResources().getString(R.string.dialogDeleteTitle))
                .setMessage(getActivity().getResources().getString(R.string.dialogDeleteMessage))
                .setNegativeButton(getActivity().getResources().getString(R.string.cancel), (dialog, which) -> {
                })
                .setPositiveButton(getActivity().getResources().getString(R.string.yes), (dialog, which) -> mBloodViewModel.deleteBlood(bloodGroupEntity));
        deleteDialog.create().show();
    }

    private void updateEntry(BloodGroupEntity bloodGroupEntity) {
        Log.d(TAG, "TODO");
        //new AddBloodDialogFragment().show(getFragmentManager(), "addBlood");
    }

    @Override
    public void onClick(int position) {
        BloodGroupEntity bloodGroupEntity = mBloodList.get(position);
        if (bloodGroupEntity.getApproved() == 0) {
            editEntry(bloodGroupEntity, getActivity().getResources().getString(R.string.adminDialogMessage), 1);
        } else {
            editEntry(bloodGroupEntity, getActivity().getResources().getString(R.string.adminDialogMessageUndo), 0);
        }
    }

    @Override
    public void edit(int position) {
        updateEntry(mBloodList.get(position));
    }

    @Override
    public void delete(int position) {
        deleteEntry(mBloodList.get(position));
    }

}
