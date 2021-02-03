package com.example.mainthings.ui.main_activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mainthings.BaseActivity;
import com.example.mainthings.R;

public class MainActivity extends BaseActivity {


    @Override
    protected void created(Bundle savedInstance) {
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
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