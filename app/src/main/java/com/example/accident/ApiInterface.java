package com.example.accident;

import com.example.accident.model.accident.AccidentBase;
import com.example.accident.model.login.LoginBase;
import com.example.accident.model.payment.PaymentBase;
import com.example.accident.model.payment.PaymentItem;
import com.example.accident.model.request.RequestBase;


import retrofit2.Call;

import retrofit2.http.GET;

import retrofit2.http.Query;

public interface ApiInterface {

    @GET("login.php")
    Call<LoginBase> userLogin(@Query("username") String username, @Query("password") String password);

    @GET("insert_accident.php")
    Call<AccidentBase> insertAccident(@Query("imei") String imei,
                                      @Query("user_id") String userId,
                                      @Query("latitude") String latitude,
                                      @Query("longitude") String longitude,
                                      @Query("vehicle_no") String vehicle_no);


    @GET("cancelRequest.php")
    Call<AccidentBase> cancelRequest(@Query("request_id") String userId);

    @GET("getCancelRequest.php")
    Call<RequestBase> getRequest(@Query("user_id") String userId);





    @GET("getPayment.php")
    Call<PaymentBase> getPayment(@Query("user_id") String userId);

}


