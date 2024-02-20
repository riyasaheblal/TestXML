package com.sl.pp.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
	@JsonProperty("ReqMsgId")
    private String reqMsgId;

    @JsonProperty("AccountNumber")
    private String accountNumber;
    
    @JsonProperty("AccountValidity")
    private String accountValidity;
    
    @JsonProperty("AccountStatus")
    private String accountStatus;
    
    @JsonProperty("AccountType")
    private String accountType;
    
    @JsonProperty("BSRCode")
    private String bsrCode;
    
    @JsonProperty("IFSCCode")
    private String ifscCode;
    
    @JsonProperty("AccountOpenDate")
    private String accountOpenDate;
    
    @JsonProperty("AccountCloseDate")
    private String accountCloseDate;

    @JsonProperty("EntityCode")
    private String entityCode;
    
    @JsonProperty("AHDetails")
    private AHDetails ahDetails;
    
    @JsonProperty("AHOTHDTL1")
    private String ahOthDtl1;
    
    @JsonProperty("AHOTHDTL2")
    private String ahOthDtl2;
    
    @JsonProperty("AHOTHDTL3")
    private String ahOthDtl3;
    
    @JsonProperty("AHOTHDTL4")
    private String ahOthDtl4;

    @JsonProperty("DataRequired")
    private String dataRequired;

	public String getReqMsgId() {
		return reqMsgId;
	}

	public void setReqMsgId(String reqMsgId) {
		this.reqMsgId = reqMsgId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountValidity() {
		return accountValidity;
	}

	public void setAccountValidity(String accountValidity) {
		this.accountValidity = accountValidity;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getBsrCode() {
		return bsrCode;
	}

	public void setBsrCode(String bsrCode) {
		this.bsrCode = bsrCode;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getAccountOpenDate() {
		return accountOpenDate;
	}

	public void setAccountOpenDate(String accountOpenDate) {
		this.accountOpenDate = accountOpenDate;
	}

	public String getAccountCloseDate() {
		return accountCloseDate;
	}

	public void setAccountCloseDate(String accountCloseDate) {
		this.accountCloseDate = accountCloseDate;
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public AHDetails getAhDetails() {
		return ahDetails;
	}

	public void setAhDetails(AHDetails ahDetails) {
		this.ahDetails = ahDetails;
	}

	public String getAhOthDtl1() {
		return ahOthDtl1;
	}

	public void setAhOthDtl1(String ahOthDtl1) {
		this.ahOthDtl1 = ahOthDtl1;
	}

	public String getAhOthDtl2() {
		return ahOthDtl2;
	}

	public void setAhOthDtl2(String ahOthDtl2) {
		this.ahOthDtl2 = ahOthDtl2;
	}

	public String getAhOthDtl3() {
		return ahOthDtl3;
	}

	public void setAhOthDtl3(String ahOthDtl3) {
		this.ahOthDtl3 = ahOthDtl3;
	}

	public String getAhOthDtl4() {
		return ahOthDtl4;
	}

	public void setAhOthDtl4(String ahOthDtl4) {
		this.ahOthDtl4 = ahOthDtl4;
	}

	public String getDataRequired() {
		return dataRequired;
	}

	public void setDataRequired(String dataRequired) {
		this.dataRequired = dataRequired;
	}

	@Override
	public String toString() {
		return "Account [reqMsgId=" + reqMsgId + ", accountNumber=" + accountNumber + ", accountValidity="
				+ accountValidity + ", accountStatus=" + accountStatus + ", accountType=" + accountType + ", bsrCode="
				+ bsrCode + ", ifscCode=" + ifscCode + ", accountOpenDate=" + accountOpenDate + ", accountCloseDate="
				+ accountCloseDate + ", entityCode=" + entityCode + ", ahDetails=" + ahDetails + ", ahOthDtl1="
				+ ahOthDtl1 + ", ahOthDtl2=" + ahOthDtl2 + ", ahOthDtl3=" + ahOthDtl3 + ", ahOthDtl4=" + ahOthDtl4
				+ ", dataRequired=" + dataRequired + "]";
	}

    // Getters and setters

    
    
    
}

