package com.example.accident.model.request;

import com.google.gson.annotations.SerializedName;

public class RequestItem {

	@SerializedName("device_id")
	private String deviceId;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("vehicle_no")
	private String vehicleNo;

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("ambulance_id")
	private String ambulanceId;

	@SerializedName("remark")
	private String remark;

	@SerializedName("id")
	private String id;

	@SerializedName("hospital_id")
	private String hospitalId;

	@SerializedName("longitude")
	private String longitude;

	@SerializedName("timestamp")
	private String timestamp;

	@SerializedName("status")
	private String status;

	public String getDeviceId(){
		return deviceId;
	}

	public String getUserId(){
		return userId;
	}

	public String getVehicleNo(){
		return vehicleNo;
	}

	public String getLatitude(){
		return latitude;
	}

	public String getAmbulanceId(){
		return ambulanceId;
	}

	public String getRemark(){
		return remark;
	}

	public String getId(){
		return id;
	}

	public String getHospitalId(){
		return hospitalId;
	}

	public String getLongitude(){
		return longitude;
	}

	public String getTimestamp(){
		return timestamp;
	}

	public String getStatus(){
		return status;
	}
}