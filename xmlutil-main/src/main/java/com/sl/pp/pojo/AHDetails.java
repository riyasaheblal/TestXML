package com.sl.pp.pojo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class AHDetails {

	@JacksonXmlProperty(localName = "AH")
	private AH ah;

	public AH getAh() {
		return ah;
	}

	public void setAh(AH ah) {
		this.ah = ah;
	}

	@Override
	public String toString() {
		return "AHDetails [ah=" + ah + "]";
	}
	
	
}
