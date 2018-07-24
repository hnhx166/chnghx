package com.chnghx.web.login.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chnghx.dao.mysql.entity.User;
import com.chnghx.dao.mysql.mapper.UserMapper;
import com.chnghx.web.login.service.RegService;

@Service
public class RegServiceImpl implements RegService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(User record) {
		return userMapper.insertSelective(record);
	}

	@Override
	public User selectByPrimaryKey(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(User record) {
		return userMapper.updateByPrimaryKey(record);
	}

	@Override
	public User selectByLoginName(String loginName) {
		return userMapper.selectByLoginName(loginName);
	}

	@Override
	public List<User> selectByLoginNameAndPassword(String loginName, String password) {
		return userMapper.selectByLoginNameAndPassword(loginName, password);
	}

	
}
