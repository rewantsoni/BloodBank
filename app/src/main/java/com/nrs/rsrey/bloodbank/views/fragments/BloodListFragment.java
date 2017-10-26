package com.nrs.rsrey.bloodbank.views.fragments;


import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nrs.rsrey.bloodbank.BuildConfig;
import com.nrs.rsrey.bloodbank.MyApplication;
import com.nrs.rsrey.bloodbank.R;
import com.nrs.rsrey.bloodbank.data.BloodGroupEntity;
import com.nrs.rsrey.bloodbank.viewmodel.BloodViewModel;
import com.nrs.rsrey.bloodbank.views.MainActivity.ViewModelType;
import com.nrs.rsrey.bloodbank.views.adapters.BloodListAdapter;
import com.nrs.rsrey.bloodbank.views.fragments.dailogs.AddBloodDialogFragment;
import com.nrs.rsrey.bloodbank.views.listeners.ItemClickListener;
import com.nrs.rsrey.bloodbank.views.listeners.PopUpMenuClickListener;
import com.nrs.rsrey.bloodbank.views.listeners.SearchListener;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BloodListFragment extends Fragment implements ItemClickListener, PopUpMenuClickListener, SearchListener {

    @BindView(R.id.bloodList)
    RecyclerView mBloodListView;
    private Unbinder mUnbinder;
    private List<BloodGroupEntity> mBloodList;
    private BloodViewModel mBloodViewModel;
    private BloodListAdapter mBloodListAdapter;
    private Resources mResources;
    private ViewModelType mViewModelType;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blood_list, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mBloodViewModel = ViewModelProviders.of(this).get(BloodViewModel.class);
        initialize();
        return view;
    }

    private void initialize() {

        if (getActivity() != null) {
            mResources = getActivity().getResources();
        }

        mBloodList = new ArrayList<>();

        mBloodListView.setItemAnimator(new DefaultItemAnimator());
        mBloodListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (getActivity() != null) {
            mBloodListView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        }
        mBloodListView.setHasFixedSize(true);

        if (getArguments() != null) {
            int viewModel = getArguments().getInt(mResources.getString(R.string.bundleViewModelType));
            mViewModelType = ViewModelType.values()[viewModel];
            mBloodListAdapter = new BloodListAdapter(getActivity(), mBloodList, this, this, mViewModelType);
            mBloodListView.setAdapter(mBloodListAdapter);
            setViewModel(mViewModelType);
        }
    }

    private void setViewModel(ViewModelType viewModelType) {
        switch (viewModelType) {
            case ADMIN:
                mBloodViewModel.getBloodList().observe(this, this::swapList);
                break;
            case USER:
                mBloodViewModel.getApprovedBloodList().observe(this, this::swapList);
                break;
        }
    }

    private void swapList(List<BloodGroupEntity> bloodGroupEntities) {
        mBloodList = bloodGroupEntities;
        mBloodListAdapter.swapItem(mBloodList);
        mBloodListAdapter.notifyDataSetChanged();
    }

    private void cleanUp() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cleanUp();
        if (getActivity() != null && BuildConfig.DEBUG) {
            RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
            refWatcher.watch(this);
        }
    }

    private void approveEntry(BloodGroupEntity bloodGroupEntity, String message, int approved) {
        bloodGroupEntity.setApproved(approved);
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

    private void editEntry(BloodGroupEntity bloodGroupEntity) {
        if (getActivity() != null && getFragmentManager() != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(getActivity().getResources().getString(R.string.bundleBloodGroupParcel), bloodGroupEntity);

            AddBloodDialogFragment addBloodDialogFragment = new AddBloodDialogFragment();
            addBloodDialogFragment.setArguments(bundle);

            addBloodDialogFragment.show(getFragmentManager(), "editBlood");
        }
    }

    private void getBlood(BloodGroupEntity bloodGroupEntity) {
        AlertDialog.Builder getBlood = new AlertDialog.Builder(getActivity());
        getBlood.setTitle(mResources.getString(R.string.adminDialogTitle))
                .setMessage(mResources.getString(R.string.userDialogMessage))
                .setNegativeButton(mResources.getString(R.string.cancel), (dialogInterface, i) -> {
                })
                .setPositiveButton(mResources.getString(R.string.yes), (dialogInterface, i) -> mBloodViewModel.deleteBlood(bloodGroupEntity));
        getBlood.create().show();
    }

    @Override
    public void onClick(int position) {
        if (mViewModelType == ViewModelType.USER) {
            getBlood(mBloodList.get(position));
        } else {
            BloodGroupEntity bloodGroupEntity = mBloodList.get(position);
            if (getActivity() != null) {
                if (bloodGroupEntity.getApproved() == 0) {
                    approveEntry(bloodGroupEntity, getActivity().getResources().getString(R.string.adminDialogMessage), 1);
                } else {
                    approveEntry(bloodGroupEntity, getActivity().getResources().getString(R.string.adminDialogMessageUndo), 0);
                }
            }
        }
    }


    @Override
    public void edit(int position) {
        editEntry(mBloodList.get(position));
    }

    @Override
    public void delete(int position) {
        deleteEntry(mBloodList.get(position));
    }

    @Override
    public void search(String query) {
        mBloodViewModel.searchBloodByGroup(query.toUpperCase() + '%').observe(this, this::swapList);
    }
}
