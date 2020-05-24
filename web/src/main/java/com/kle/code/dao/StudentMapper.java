package com.kle.code.dao;

import com.kle.code.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface StudentMapper {

    int deleteByPrimaryKey(Integer sid);

//    int insert(@Param("tid") Integer tid, @Param("name") String name,
//               @Param("password") String password, @Param("createTime") Timestamp createTime,
//               @Param("updateTime") Timestamp updateTime);
    int insert(Student record);

    Student selectByPrimaryKey(Integer sid);

    List<Student> selectStudentOfTeacher(Integer tid);

    int updateByPrimaryKey(@Param("sid") Integer sid, @Param("name") String name,
                           @Param("password") String password, @Param("updateTime") Timestamp updateTime);
}