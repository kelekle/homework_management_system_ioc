package com.kle.code.model;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 学生实体类
 * @author ypb
 */
@Component
@Scope("prototype")
@Data
public class Student {

    private int sid;

    private String name;

    private String password;

    private Date createTime;

    private Date updateTime;

}
