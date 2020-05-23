package com.kle.code.mapper_jpa;

import com.kle.code.model.Teach;
import com.kle.code.model.pk.TeachPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeachMapper extends JpaRepository<Teach, TeachPK> {

}
