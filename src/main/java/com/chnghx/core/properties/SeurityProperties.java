package com.chnghx.core.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SeurityProperties {

	@Value("${com.chnghx.security}")
	private String security;
	
	@Value("${com.chnghx.random.v1}")
	private String v1;
	
	@Value("${com.chnghx.random.v2}")
	private String v2;
	
	@Value("${com.chnghx.random.v3}")
	private String v3;
	
	
	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}
	
	public String getV1() {
		return v1;
	}

	public void setV1(String v1) {
		this.v1 = v1;
	}

	public String getV2() {
		return v2;
	}

	public void setV2(String v2) {
		this.v2 = v2;
	}

	public String getV3() {
		return v3;
	}

	public void setV3(String v3) {
		this.v3 = v3;
	}
	
}
