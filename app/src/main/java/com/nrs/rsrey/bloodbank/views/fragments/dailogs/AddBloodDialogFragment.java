package com.nrs.rsrey.bloodbank.views.fragments.dailogs;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nrs.rsrey.bloodbank.BuildConfig;
import com.nrs.rsrey.bloodbank.MyApplication;
import com.nrs.rsrey.bloodbank.R;
import com.nrs.rsrey.bloodbank.data.BloodGroupEntity;
import com.nrs.rsrey.bloodbank.viewmodel.BloodViewModel;
import com.squareup.leakcanary.RefWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AddBloodDialogFragment extends DialogFragment {


    @BindView(R.id.bloodUserName)
    EditText mName;
    @BindView(R.id.bloodUserContact)
    EditText mPhoneNo;
    @BindView(R.id.bloodUserHospitalName)
    EditText mHospitalName;
    @BindView(R.id.bloodUserGroup)
    Spinner mBloodGroup;
    @BindView(R.id.bloodUserAdd)
    Button mAdd;
    private Unbinder mUnbinder;
    private BloodViewModel mBloodViewModel;
    private BloodGroupEntity mBloodGroupEntity;
    private int mApproved;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_blood, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mBloodViewModel = ViewModelProviders.of(this).get(BloodViewModel.class);
        initialize();
        listeners();
        setValues();
        return view;
    }

    private void setValues() {
        if (getActivity() != null && getArguments() != null) {
            Bundle bundle = getArguments();
            mBloodGroupEntity = bundle.getParcelable(getActivity().getResources().getString(R.string.bundleBloodGroupParcel));
            setFields(mBloodGroupEntity);
        }
    }

    private void initialize() {
        if (getActivity() != null) {
            mBloodGroup.setAdapter(new ArrayAdapter<>(getActivity()
                    , android.R.layout.simple_spinner_dropdown_item, getActivity().getResources().getStringArray(R.array.bloodGroups)));
        }
    }

    private void listeners() {
        mAdd.setOnClickListener(v -> {
            if (verify()) {
                if (mBloodGroupEntity == null) {
                    addNew();
                } else {
                    modify();
                }
            }
        });
    }

    private void addNew() {
        BloodGroupEntity bloodGroupEntity = new BloodGroupEntity();
        bloodGroupEntity.setName(mName.getText().toString());
        bloodGroupEntity.setContactNo(mPhoneNo.getText().toString());
        bloodGroupEntity.setHospitalName(mHospitalName.getText().toString());
        bloodGroupEntity.setBloodGroup((String) mBloodGroup.getSelectedItem());
        bloodGroupEntity.setApproved(mApproved);
        mBloodViewModel.insertBlood(bloodGroupEntity);
        if (getActivity() != null) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.thankYou), Toast.LENGTH_SHORT).show();
        }
        dismiss();
    }

    private void modify() {
        mBloodGroupEntity.setName(mName.getText().toString());
        mBloodGroupEntity.setContactNo(mPhoneNo.getText().toString());
        mBloodGroupEntity.setHospitalName(mHospitalName.getText().toString());
        mBloodGroupEntity.setBloodGroup((String) mBloodGroup.getSelectedItem());
        mBloodGroupEntity.setApproved(mApproved);
        mBloodViewModel.updateBlood(mBloodGroupEntity);
        if (getActivity() != null) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.updated), Toast.LENGTH_SHORT).show();
        }
        dismiss();
    }

    private void setFields(BloodGroupEntity bloodGroupEntity) {
        if (getActivity() != null && bloodGroupEntity != null) {
            mName.setText(bloodGroupEntity.getName());
            mPhoneNo.setText(bloodGroupEntity.getContactNo());
            mHospitalName.setText(bloodGroupEntity.getHospitalName());
            mApproved = bloodGroupEntity.getApproved();
            mBloodGroup.setSelection(getSpinner(bloodGroupEntity.getBloodGroup()));
            mAdd.setText(getActivity().getResources().getString(R.string.updateBlood));
        }
    }

    private int getSpinner(String group) {
        if (getActivity() != null) {
            String[] groups = getActivity().getResources().getStringArray(R.array.bloodGroups);
            for (int i = 0, size = groups.length; i < size; i++) {
                if (groups[i].equalsIgnoreCase(group)) {
                    return i;
                }
            }
        }
        return 0;
    }

    private boolean verify() {
        if (getActivity() != null) {
            if (mName.getText().toString().isEmpty()) {
                mName.setError(getActivity().getResources().getString(R.string.errorNoUserName));
                return false;
            }
            if (mPhoneNo.getText().toString().isEmpty()) {
                mPhoneNo.setError(getActivity().getResources().getString(R.string.errorNoContactNo));
                return false;
            }
            if (mHospitalName.getText().toString().isEmpty()) {
                mHospitalName.setError(getActivity().getResources().getString(R.string.errorNoHospitalName));
                return false;
            }
        }
        return true;
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
}
