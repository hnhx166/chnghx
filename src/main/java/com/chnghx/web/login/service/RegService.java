package com.chnghx.web.login.service;

import java.util.List;

import com.chnghx.dao.mysql.entity.User;

public interface RegService {

	int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(User record);
    
    User selectByLoginName(String loginName);
    
    List<User> selectByLoginNameAndPassword(String loginName, String password);
    
    List<User> getUsers(User user);
}
