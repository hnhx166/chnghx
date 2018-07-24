package com.chnghx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chnghx.dao.mysql.entity.SsoDomain;
import com.chnghx.dao.mysql.mapper.SsoDomainMapper;
import com.chnghx.service.SSODomainService;


@Service
public class SSODomainServiceImpl implements SSODomainService {
	
	@Autowired
	SsoDomainMapper  ssoDomainMapper;
	
	public SsoDomain selectByPrimaryKey(Long id) {
		return ssoDomainMapper.selectByPrimaryKey(id);
	}

	
}
