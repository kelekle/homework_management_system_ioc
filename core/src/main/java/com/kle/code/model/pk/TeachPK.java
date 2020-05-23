package com.kle.code.model.pk;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class TeachPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer sid;

    private Integer tid;

}
