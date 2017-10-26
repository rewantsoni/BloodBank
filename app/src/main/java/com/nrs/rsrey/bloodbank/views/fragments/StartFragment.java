package com.nrs.rsrey.bloodbank.views.fragments;


import android.app.AlertDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.nrs.rsrey.bloodbank.BuildConfig;
import com.nrs.rsrey.bloodbank.MyApplication;
import com.nrs.rsrey.bloodbank.R;
import com.nrs.rsrey.bloodbank.views.MainActivity;
import com.nrs.rsrey.bloodbank.views.MainActivity.ViewModelType;
import com.nrs.rsrey.bloodbank.views.fragments.dailogs.AddBloodDialogFragment;
import com.squareup.leakcanary.RefWatcher;

import org.jetbrains.annotations.Contract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

public class StartFragment extends Fragment {

    @BindView(R.id.startAdminLogin)
    Button mLogin;
    @BindView(R.id.startDonateBlood)
    Button mDonate;
    @BindView(R.id.startGetBlood)
    Button mGetBlood;
    private Unbinder mUnbinder;
    private Resources mResources;
    private CompositeDisposable mCompositeDisposable;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_start, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        initialize();
        listeners();
        return v;
    }

    private void initialize() {
        if (getActivity() != null) {
            mResources = getActivity().getResources();
        }
        mCompositeDisposable = new CompositeDisposable();
    }

    private void listeners() {
        mCompositeDisposable.add(RxView.clicks(mLogin).subscribe(v -> {
            AlertDialog.Builder loginDialog = new AlertDialog.Builder(getActivity());
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_admin_login, null, false);
            EditText password = view.findViewById(R.id.adminLoginPassword);
            loginDialog.setView(view)
                    .setNegativeButton(mResources.getString(R.string.cancel), (dialog, which) -> {
                    })
                    .setPositiveButton(mResources.getString(R.string.login), (dialog, which) -> {
                        if (verify(password)) {
                            if (password.getText().toString().equalsIgnoreCase(BuildConfig.ADMIN_PASSWORD)) {
                                if (getActivity() != null) {
                                    ((MainActivity) getActivity()).replaceFragment(new BloodListFragment(), ViewModelType.ADMIN);
                                }
                            } else {
                                password.setError(mResources.getString(R.string.errorIncorrectPassword));
                            }
                        }
                    });
            loginDialog.create().show();
        }));
        mCompositeDisposable.add(RxView.clicks(mDonate).subscribe(v -> {
            if (getFragmentManager() != null) {
                new AddBloodDialogFragment().show(getFragmentManager(), "addBlood");
            }
        }));
        mCompositeDisposable.add(RxView.clicks(mGetBlood).subscribe(v -> {
            if (getActivity() != null) {
                ((MainActivity) getActivity()).replaceFragment(new BloodListFragment(), ViewModelType.USER);
            }
        }));
    }

    @Contract("null -> false")
    private boolean verify(TextView view) {
        return !(view == null || view.getText().toString().isEmpty());
    }

    private void cleanUp() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable.dispose();
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