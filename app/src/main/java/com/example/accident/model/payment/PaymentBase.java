package com.example.accident.model.payment;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PaymentBase{

	@SerializedName("data")
	private List<PaymentItem> data;

	@SerializedName("success")
	private boolean success;

	public List<PaymentItem> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}
}