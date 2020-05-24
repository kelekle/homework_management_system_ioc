package com.kle.code.dao;

import com.kle.code.model.Homework;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface HomeworkMapper {

    int deleteByPrimaryKey(Integer hid);

    int insert(Homework record);

    Homework selectByPrimaryKey(Integer hid);

    int updateByPrimaryKey(@Param("hid") Integer hid, @Param("title") String title,
                           @Param("content") String content, @Param("updateTime") Timestamp updateTime);

    List<Homework> selectHomeworksOfTeacher(Integer tid);

}