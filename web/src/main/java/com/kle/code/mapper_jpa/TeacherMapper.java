package com.kle.code.mapper_jpa;

import com.kle.code.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherMapper extends JpaRepository<Teacher, Integer> {

}
