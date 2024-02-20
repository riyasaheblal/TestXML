package com.sl.pp.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class Accounts {

    @JsonProperty("MessageId")
    private String messageId;

    @JsonProperty("Source")
    private String source;

    @JsonProperty("Destination")
    private String destination;

    @JsonProperty("BankCode")
    private String bankCode;

    @JsonProperty("BankName")
    private String bankName;

    @JsonProperty("RecordsCount")
    private int recordsCount;

    @JsonProperty("Account")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Account> accounts;

    @JsonProperty("xmlns")
    private String xmlns;

    // Getters and setters

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getRecordsCount() {
        return recordsCount;
    }

    public void setRecordsCount(int recordsCount) {
        this.recordsCount = recordsCount;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

	@Override
	public String toString() {
		return "Accounts [messageId=" + messageId + ", source=" + source + ", destination=" + destination
				+ ", bankCode=" + bankCode + ", bankName=" + bankName + ", recordsCount=" + recordsCount + ", accounts="
				+ accounts + ", xmlns=" + xmlns + "]";
	}
    
}

