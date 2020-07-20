package com.example.accident;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accident.model.payment.PaymentItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentListAdapter extends RecyclerView.Adapter<PaymentListAdapter.PaymentViewHolder> {

    private Context context;
    private ArrayList<PaymentItem> paymentItemArrayList;

    public PaymentListAdapter(Context context, ArrayList<PaymentItem> paymentItemArrayList) {
        this.context = context;
        this.paymentItemArrayList = paymentItemArrayList;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_input_payment,parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
      PaymentItem paymentItem=paymentItemArrayList.get(position);
      holder.mAmountPaymentTextView.setText(paymentItem.getAmount());
      holder.mVehiclePaymentTextView.setText(paymentItem.getAmbulanceNo());
      if(paymentItem.getStatus().equals("1")){
          holder.mStatusPaymentTextView.setText("Paid");
          holder.mStatusPaymentTextView.setTextColor(context.getResources().getColor(R.color.colorLightgreen));
      }else{
          holder.mStatusPaymentTextView.setText("Not Paid");
          holder.mStatusPaymentTextView.setTextColor(context.getResources().getColor(R.color.colorLightgreen));
      }


    }

    @Override
    public int getItemCount() {
        return paymentItemArrayList.size();
    }

    public class PaymentViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.amount_payment_text)
        TextView mAmountPaymentTextView;

        @BindView(R.id.vehicleno_payment_text)
        TextView mVehiclePaymentTextView;

        @BindView(R.id.status_payment_text)
        TextView mStatusPaymentTextView;



        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
