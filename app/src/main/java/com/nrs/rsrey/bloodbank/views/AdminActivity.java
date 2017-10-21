package com.nrs.rsrey.bloodbank.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nrs.rsrey.bloodbank.R;
import com.nrs.rsrey.bloodbank.views.fragments.BloodListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminActivity extends AppCompatActivity {

    @BindView(R.id.mainToolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAdminSearch:
                startActivity(new Intent(AdminActivity.this,SearchActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initialize() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportFragmentManager().beginTransaction().add(R.id.mainContainer, new BloodListFragment()).commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
