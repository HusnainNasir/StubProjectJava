package com.example.mainthings.network;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.mainthings.MyApplication;
import com.example.mainthings.utils.Constants;
import com.example.mainthings.utils.PreferenceHelper;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {


    private static  RetrofitHelper instance;
    private final ApiService service;

//    private  int attempt=0;

    private RetrofitHelper() {

        Retrofit retrofit = getRetrofit();
        service = retrofit.create(ApiService.class);
    }

    public static RetrofitHelper getInstance(Application application) {
        if (instance==null) {
            instance = new RetrofitHelper();
        }
        return instance;
    }

    public ApiService getApiInterface() {
        return service;
    }

    public static class AuthorizationInterceptor implements Interceptor {
        Response response;
        @NotNull
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            PreferenceHelper preferenceUtil = PreferenceHelper.preferenceInstance(MyApplication.getContext());

            if(request.headers().get("Authorization")==null || Objects.requireNonNull(request.headers().get("Authorization")).isEmpty()){
                String token = getAuthorizationHeader(MyApplication.getContext());
                if (token != null){
                    Headers headers = request.headers().newBuilder().add("Authorization","Bearer "+ token).build();
                    request = request.newBuilder().headers(headers).build();
                }
            }

            response = chain.proceed(request);

//            if(attempt<=2)
//                if (response.code()== 401  && preferenceUtil.getFOR_REFRESH_USER_NAME() != null) {
//                    response.close();
//                    attempt++;
//                    Log.d("Username: ",preferenceUtil.getFOR_REFRESH_USER_NAME());
//                    Log.d("Password: ",preferenceUtil.getFOR_REFRESH_PASSWORD());
//                    ApiServiceInterface tokenApi = getRetrofit().create(ApiServiceInterface.class);
//                    retrofit2.Response<LoginData> loginResponse = tokenApi.login(preferenceUtil.getFOR_REFRESH_USER_NAME() , preferenceUtil.getFOR_REFRESH_PASSWORD() , "password").execute();
//                    if (loginResponse.isSuccessful()) {
//                        LoginData loginResponseObject = loginResponse.body();
//                        try {
//                            request = request.newBuilder()
//                                    .header("Authorization", "Bearer " + loginResponseObject.getAccessToken()).build();
//                            response = chain.proceed(request);
//                            preferenceUtil.setAuthToken(loginResponseObject.getTokenType() + " " + loginResponseObject.getAccessToken());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        attempt=3; // so don't try for next attempt, already handled
//                    } else {
//                        attempt = 3;
//                        BaseResponse responseBody = new Gson().fromJson(loginResponse.errorBody().string(),BaseResponse.class);
//                        new Handler(Looper.getMainLooper()).post(()->{
//                            Toast.makeText(context, responseBody.getMessage(), Toast.LENGTH_SHORT).show();
//                        });
//                        // goto Login as we cannot refresh token
//
//                    }
//
//                }
//
            return response;
        }
//
    }


    public Retrofit getRetrofit(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new AuthorizationInterceptor());
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClientBuilder.build())
                .build();
    }

    public static String getAuthorizationHeader(Context context) {
        return PreferenceHelper.preferenceInstance(context).getAuthToken();
    }


}
