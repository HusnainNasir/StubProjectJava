package com.example.mainthings.ui.main_activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mainthings.BaseActivity;
import com.example.mainthings.R;
import com.example.mainthings.utils.NetworkManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void created(Bundle savedInstance) {
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();

        NetworkManager networkManager = NetworkManager.getNetworkManager();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {

                case R.id.first :

                    if (networkManager.isInternetWorking()){
                        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                case R.id.second :
                    if (networkManager.isWorking()){
                        Toast.makeText(this, "Working", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "No Working", Toast.LENGTH_SHORT).show();
                    }
                    return true;
            }
            return false;
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected String getTag() {
        return MainActivity.class.getSimpleName();
    }
}