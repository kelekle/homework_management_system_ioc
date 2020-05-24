package com.kle.code.dao;

import com.kle.code.model.Teacher;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeacherMapper {

//    int insert(@Param("name") String name, @Param("password") String password,
//               @Param("createTime") Timestamp createTime);
    int insert(Teacher teacher);

    Teacher selectByPrimaryKey(int tid);

}