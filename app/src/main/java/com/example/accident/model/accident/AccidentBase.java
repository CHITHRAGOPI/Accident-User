package com.example.accident.model.accident;

import com.google.gson.annotations.SerializedName;

public class AccidentBase {

    @SerializedName("success")
    private boolean success;

    public void setSuccess(boolean success){
        this.success = success;
    }

    public boolean isSuccess(){
        return success;
    }


}


