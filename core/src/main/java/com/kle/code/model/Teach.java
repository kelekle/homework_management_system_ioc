package com.kle.code.model;

import com.kle.code.model.pk.TeachPK;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Component
@Scope("prototype")
@Data
@Entity
@Table(name = "teach")
public class Teach {

    @EmbeddedId
    private TeachPK teachPK;

    private Date createTime;

}
