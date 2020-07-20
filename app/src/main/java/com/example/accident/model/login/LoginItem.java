package com.example.accident.model.login;

import com.google.gson.annotations.SerializedName;

public class LoginItem {

	@SerializedName("password")
	private String password;

	@SerializedName("address")
	private String address;

	@SerializedName("device_id")
	private String deviceId;

	@SerializedName("phone")
	private String phone;

	@SerializedName("vehicle_no")
	private String vehicleNo;

	@SerializedName("emergency_no")
	private String emergencyNo;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("username")
	private String username;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setDeviceId(String deviceId){
		this.deviceId = deviceId;
	}

	public String getDeviceId(){
		return deviceId;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setVehicleNo(String vehicleNo){
		this.vehicleNo = vehicleNo;
	}

	public String getVehicleNo(){
		return vehicleNo;
	}

	public void setEmergencyNo(String emergencyNo){
		this.emergencyNo = emergencyNo;
	}

	public String getEmergencyNo(){
		return emergencyNo;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"password = '" + password + '\'' + 
			",address = '" + address + '\'' + 
			",device_id = '" + deviceId + '\'' + 
			",phone = '" + phone + '\'' + 
			",vehicle_no = '" + vehicleNo + '\'' + 
			",emergency_no = '" + emergencyNo + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}