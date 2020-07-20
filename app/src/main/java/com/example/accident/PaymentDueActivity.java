package com.example.accident;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.accident.model.payment.PaymentBase;
import com.example.accident.model.payment.PaymentItem;
import com.example.accident.utils.GlobalPreference;
import com.example.accident.utils.GpsTrackers;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentDueActivity extends AppCompatActivity {

    @BindView(R.id.payment_recylerview)
    RecyclerView mPaymentRecyclerView;


    private GlobalPreference mGlobalPreference;
    private ApiInterface mApiInterface;
    public static final String TAG = HomeActivity.class.getSimpleName();

    private ArrayList<PaymentItem> mPaymentItem=new ArrayList<>();

    private PaymentListAdapter paymentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_due);
        ButterKnife.bind(this);
        init();
        getData();
    }


    private void init(){
        mGlobalPreference=new GlobalPreference(this);
        mPaymentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        paymentListAdapter=new PaymentListAdapter(this,mPaymentItem);
        mPaymentRecyclerView.setAdapter(paymentListAdapter);
    }
    public void getData(){
        mApiInterface=ApiClient.getRetrofit(mGlobalPreference.RetrieveIp()).create(ApiInterface.class);
        Call<PaymentBase> mPaymentBaseCall=mApiInterface.getPayment(mGlobalPreference.getID());
        mPaymentBaseCall.enqueue(new Callback<PaymentBase>() {
            @Override
            public void onResponse(Call<PaymentBase> call, Response<PaymentBase> response) {
                mPaymentItem.clear();
                mPaymentItem.addAll(response.body().getData());
                paymentListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PaymentBase> call, Throwable t) {

            }
        });
    }


}
