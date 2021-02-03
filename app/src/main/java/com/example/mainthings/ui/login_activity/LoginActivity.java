package com.example.mainthings.ui.login_activity;


import android.os.Bundle;

import com.example.mainthings.BaseActivity;
import com.example.mainthings.R;
import com.example.mainthings.utils.AlertDialogs;


public class LoginActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected String getTag() {
        return LoginActivity.class.getSimpleName();
    }

    @Override
    protected void created(Bundle savedInstance) {

        init();
    }

    private void init() {
        setToolbar(getString(R.string.app_name));
        getProgressDialog().show();
    }


}