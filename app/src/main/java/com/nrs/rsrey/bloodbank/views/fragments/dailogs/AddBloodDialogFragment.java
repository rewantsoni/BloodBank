package com.nrs.rsrey.bloodbank.views.fragments.dailogs;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.nrs.rsrey.bloodbank.R;
import com.nrs.rsrey.bloodbank.data.BloodGroupEntity;
import com.nrs.rsrey.bloodbank.viewmodel.BloodViewModel;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_blood, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mBloodViewModel = ViewModelProviders.of(this).get(BloodViewModel.class);
        initialize();
        listeners();
        return view;
    }

    private void initialize() {
        mBloodGroup.setAdapter(new ArrayAdapter<>(getActivity()
                , android.R.layout.simple_spinner_dropdown_item, getActivity().getResources().getStringArray(R.array.bloodGroups)));
    }

    private void listeners() {
        mAdd.setOnClickListener(v -> {
            if (verify()) {
                BloodGroupEntity bloodGroupEntity = new BloodGroupEntity();
                bloodGroupEntity.setName(mName.getText().toString());
                bloodGroupEntity.setContactNo(mPhoneNo.getText().toString());
                bloodGroupEntity.setHospitalName(mHospitalName.getText().toString());
                bloodGroupEntity.setBloodGroup((String) mBloodGroup.getSelectedItem());
                bloodGroupEntity.setApproved(0);
                mBloodViewModel.insertBlood(bloodGroupEntity);
                dismiss();
            }
        });
    }

    private boolean verify() {
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
    }
}
