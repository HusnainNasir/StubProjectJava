package com.example.mainthings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mainthings.utils.PreferenceHelper;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    public BaseActivity mBaseActivity;
    public View view;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            mBaseActivity = (BaseActivity) context;
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getID(), container, false);
        ButterKnife.bind(this,view);
        this.view = view;
        initUI(view);
        return view;
    }


    public abstract int getID();

    public abstract void initUI(View view);

    public View getView(){
        return this.view;
    }

    public Gson getGSON() {
        return mBaseActivity.getGSON();
    }

    public PreferenceHelper getPreferenceHelper() {
        return mBaseActivity.getPreferenceHelper();
    }

}
