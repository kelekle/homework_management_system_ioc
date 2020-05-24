package com.kle.code.model;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 作业实体类
 * @author ypb
 */
@Component
@Scope("prototype")
@Data
public class Homework {

    private int hid;

    private int tid;

    private String title;

    private String content;

    private Date createTime;

    private Date updateTime;

}
