package com.kle.code.model;

import com.kle.code.model.pk.StudentHomeworkPK;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Component(value = "student_homework")
@Scope("prototype")
@Data
@Entity
@Table(name = "student_homework")
public class StudentHomework {

    @EmbeddedId
    private StudentHomeworkPK studentHomeworkPK;

    private String submitContent;

    private Date submitTime;

}
