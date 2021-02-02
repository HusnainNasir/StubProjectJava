package com.example.mainthings.network;

import android.app.Application;

import com.example.mainthings.db.ApplicationDatabase;

import java.util.concurrent.Executor;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.disposables.CompositeDisposable;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class Repository {

    // MEMBERS
    private final ApiService apiServiceInterface;
    private final Executor executor;

    // COMPOSITE DISPOSABLE
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    // MUTABLE LIVE DATA

    // Error Live Data
    private final MutableLiveData<Throwable> onThrowableLiveData = new MutableLiveData<>();


    public Repository(Application application) {

        apiServiceInterface = RetrofitHelper.getInstance(application).getApiInterface();
        executor = newSingleThreadExecutor();
    }


    //GETTERS

    public MutableLiveData<Throwable> getOnThrowableLiveData() { return onThrowableLiveData; }
}
