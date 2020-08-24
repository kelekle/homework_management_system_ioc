package com.kle.code.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
public interface StudentHomeworkMapper {

    int insert(@Param("sid") Integer sid, @Param("hid") Integer hid);

    int deleteByPrimaryKey(@Param("sid") Integer sid, @Param("hid") Integer hid);

    int deleteByHid(@Param("hid") Integer hid);

    int deleteBySid(@Param("sid") Integer sid);

    int submitHomework(@Param("sid") Integer sid, @Param("hid") Integer hid,
                       @Param("submitContent") String submitContent, @Param("submitTime") Timestamp submitTime);

    List<Map<String, Object>> selectStudentHomeworkByHid(Integer hid);

    List<Map<String, Object>> selectStudentHomeworkOfStudent(Integer sid);

    Map<String, Object> selectStudentHomeworkByPrimaryKey( @Param("sid") Integer sid, @Param("hid") Integer hid);
}