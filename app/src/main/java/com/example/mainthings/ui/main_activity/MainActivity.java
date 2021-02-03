package com.example.mainthings.ui.main_activity;
import android.os.Bundle;
import com.example.mainthings.BaseActivity;
import com.example.mainthings.R;

public class MainActivity extends BaseActivity {


    @Override
    protected void created(Bundle savedInstance) {


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