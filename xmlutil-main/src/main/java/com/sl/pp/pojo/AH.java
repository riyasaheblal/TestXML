package com.sl.pp.pojo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class AH {
	
	@JacksonXmlProperty(localName = "AHDetail1")
    private String ahDetail1;

    @JacksonXmlProperty(localName = "AHDetail2")
    private String ahDetail2;

    @JacksonXmlProperty(localName = "AHDetail3")
    private String ahDetail3;

    @JacksonXmlProperty(localName = "AHDetail4")
    private String ahDetail4;

    @JacksonXmlProperty(localName = "AHTYPE")
    private String ahType;

    @JacksonXmlProperty(localName = "AddressLine1")
    private String addressLine1;

    @JacksonXmlProperty(localName = "AddressLine2")
    private String addressLine2;

    @JacksonXmlProperty(localName = "DOB")
    private String dob;

    @JacksonXmlProperty(localName = "District")
    private String district;

    @JacksonXmlProperty(localName = "DistrictLGDCode")
    private String districtLGDCode;

    @JacksonXmlProperty(localName = "Gender")
    private String gender;

    @JacksonXmlProperty(localName = "Mobile")
    private String mobile;

    @JacksonXmlProperty(localName = "Name")
    private String name;

    @JacksonXmlProperty(localName = "Nationality")
    private String nationality;

    @JacksonXmlProperty(localName = "PAN")
    private String pan;

    @JacksonXmlProperty(localName = "PinCode")
    private String pinCode;

    @JacksonXmlProperty(localName = "State")
    private String state;

    @JacksonXmlProperty(localName = "StateLGDCode")
    private String stateLGDCode;

    @JacksonXmlProperty(localName = "TAN")
    private String tan;

    @JacksonXmlProperty(localName = "emailID")
    private String emailID;

	public String getAhDetail1() {
		return ahDetail1;
	}

	public void setAhDetail1(String ahDetail1) {
		this.ahDetail1 = ahDetail1;
	}

	public String getAhDetail2() {
		return ahDetail2;
	}

	public void setAhDetail2(String ahDetail2) {
		this.ahDetail2 = ahDetail2;
	}

	public String getAhDetail3() {
		return ahDetail3;
	}

	public void setAhDetail3(String ahDetail3) {
		this.ahDetail3 = ahDetail3;
	}

	public String getAhDetail4() {
		return ahDetail4;
	}

	public void setAhDetail4(String ahDetail4) {
		this.ahDetail4 = ahDetail4;
	}

	public String getAhType() {
		return ahType;
	}

	public void setAhType(String ahType) {
		this.ahType = ahType;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDistrictLGDCode() {
		return districtLGDCode;
	}

	public void setDistrictLGDCode(String districtLGDCode) {
		this.districtLGDCode = districtLGDCode;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateLGDCode() {
		return stateLGDCode;
	}

	public void setStateLGDCode(String stateLGDCode) {
		this.stateLGDCode = stateLGDCode;
	}

	public String getTan() {
		return tan;
	}

	public void setTan(String tan) {
		this.tan = tan;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	@Override
	public String toString() {
		return "AH [ahDetail1=" + ahDetail1 + ", ahDetail2=" + ahDetail2 + ", ahDetail3=" + ahDetail3 + ", ahDetail4="
				+ ahDetail4 + ", ahType=" + ahType + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2
				+ ", dob=" + dob + ", district=" + district + ", districtLGDCode=" + districtLGDCode + ", gender="
				+ gender + ", mobile=" + mobile + ", name=" + name + ", nationality=" + nationality + ", pan=" + pan
				+ ", pinCode=" + pinCode + ", state=" + state + ", stateLGDCode=" + stateLGDCode + ", tan=" + tan
				+ ", emailID=" + emailID + "]";
	}
    
    
    

}
