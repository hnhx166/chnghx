package com.chnghx.dao.mysql.mapper;

import java.util.List;

import com.chnghx.dao.mysql.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User selectByLoginName(String loginName);
    
    List<User> selectByLoginNameAndPassword(String loginName, String password);
}