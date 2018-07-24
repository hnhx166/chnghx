package com.chnghx.common.fail;

import org.springframework.stereotype.Component;

@Component
public class FallBackServiceImpl implements FallBackService{

	@Override
	public String sayErr(String name) {
		return "sorry "+name;
	}

}
