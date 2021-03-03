package com.example.mainthings.model.network_model;


import com.example.mainthings.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;


public class BaseResponse extends BaseObservable {

    private String message;
    private Boolean success ;

    public BaseResponse(String message, Boolean success) {
        setMessage(message);
        setSuccess(success);
    }

    @Bindable
    public String getMessage() { return message; }

    public void copyValues(BaseResponse baseResponse){
        setMessage(baseResponse.getMessage());
        setSuccess(baseResponse.getSuccess());
    }

    public void setMessage(String message) {
        this.message = message;
        notifyPropertyChanged(BR.message);
    }

    public Boolean getSuccess() { return success; }

    public void setSuccess(Boolean success) { this.success = success; }
}
