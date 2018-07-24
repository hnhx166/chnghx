package com.chnghx.service;

import com.chnghx.dao.mysql.entity.SsoDomain;

public interface SSODomainService {

    SsoDomain selectByPrimaryKey(Long id);
}
