package com.example.accident.model.request;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RequestBase{

	@SerializedName("data")
	private List<RequestItem> data;

	@SerializedName("success")
	private boolean success;

	public List<RequestItem> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}
}