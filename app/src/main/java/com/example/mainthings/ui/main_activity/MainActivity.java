package com.example.mainthings.ui.main_activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.mainthings.BaseActivity;
import com.example.mainthings.R;
import com.example.mainthings.ui.login_activity.LoginActivity;
import com.example.mainthings.utils.AlertDialogs;
import com.example.mainthings.utils.UtilsFunction;
import com.google.android.material.navigation.NavigationView;
import java.util.Collections;
import java.util.Objects;

@SuppressLint("NonConstantResourceId")
public class MainActivity extends BaseActivity {


    @BindView(R.id.nav)
    NavigationView nav;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected String getTag() {
        return MainActivity.class.getSimpleName();
    }

    @Override
    protected void created(Bundle savedInstance) {

        init();

    }

    private void init() {

        // Drawer Toggle
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Nav Item Selected Listener

        nav.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            switch (id) {
                case R.id.account:

                    activityNavigation(this , LoginActivity.class , false , Collections.emptyList(), Collections.emptyList());
                    break;
                case R.id.update:

                    getProgressDialog().dismiss();
                    Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.logout:
                    Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                    break;

            }

            return false;
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}