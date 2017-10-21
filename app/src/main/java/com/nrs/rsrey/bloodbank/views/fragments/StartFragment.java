package com.nrs.rsrey.bloodbank.views.fragments;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nrs.rsrey.bloodbank.R;
import com.nrs.rsrey.bloodbank.views.AdminActivity;
import com.nrs.rsrey.bloodbank.views.fragments.dailogs.AddBloodDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StartFragment extends Fragment {

    private static final String TAG = StartFragment.class.getSimpleName();
    @BindView(R.id.startAdminLogin)
    Button mLogin;
    @BindView(R.id.startDonateBlood)
    Button mDonate;
    private Unbinder mUnbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_start, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        listeners();
        return v;
    }

    private void listeners() {
        mLogin.setOnClickListener(v -> {
            AlertDialog.Builder loginDialog = new AlertDialog.Builder(getActivity());
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_admin_login, null, false);
            EditText password = view.findViewById(R.id.adminLoginPassword);
            loginDialog.setView(view)
                    .setNegativeButton(getActivity().getResources().getString(R.string.cancel), (dialog, which) -> {})
                    .setPositiveButton(getActivity().getResources().getString(R.string.login), (dialog, which) -> {
                        if(verify(password)) {
                            if (Integer.parseInt(password.getText().toString()) == Integer.parseInt(getActivity().getResources().getString(R.string.passwordKey))) {
                                getActivity().startActivity(new Intent(getActivity(), AdminActivity.class));
                            } else {
                                password.setError(getActivity().getResources().getString(R.string.errorIncorrectPassword));
                            }
                        }
                    });
            loginDialog.create().show();
        });

        mDonate.setOnClickListener(v -> {
            new AddBloodDialogFragment().show(getFragmentManager(), "addBlood");
        });
    }

    private boolean verify(TextView view){
        return !(view == null || view.getText().toString().isEmpty());
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