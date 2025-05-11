package com.college.yi.bookmanager.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.college.yi.bookmanager.entity.UserEntity;

@Mapper
public interface UserRepository {
	  UserEntity findByUsername(@Param("username") String username);

	void save(UserEntity user);
}
