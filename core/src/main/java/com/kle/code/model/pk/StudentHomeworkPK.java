package com.kle.code.model.pk;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class StudentHomeworkPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private int sid;

    private int hid;

}
