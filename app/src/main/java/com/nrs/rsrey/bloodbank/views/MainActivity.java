package com.nrs.rsrey.bloodbank.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nrs.rsrey.bloodbank.R;
import com.nrs.rsrey.bloodbank.views.fragments.StartFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.mainToolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        setSupportActionBar(mToolbar);
        getSupportFragmentManager().beginTransaction().add(R.id.mainContainer, new StartFragment()).commit();
    }
}
