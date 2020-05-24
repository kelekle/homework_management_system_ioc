package com.kle.code.model;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 老师实体类
 * @author ypb
 */
@Component
@Scope("prototype")
@Data
public class Teacher {

    private int tid;

    private String name;

    private String password;

    private Date createTime;

    private Date updateTime;

}
