package com.kle.code.aop;

import com.alibaba.fastjson.JSONObject;
import com.kle.code.db.ConnectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author ypb
 * AOP 实现的事务管理类
 */
//@Aspect
//@Component("MyTransactionManager")
public class TransactionManager {

    private final ConnectionUtils connectionUtils;

    public TransactionManager(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    //3 个事务切点

    @Pointcut("execution(* com.kle.code.service.impl.TeacherServiceImpl.addHomework(..))")
    private void addHomework(){}

    @Pointcut("execution(* com.kle.code.service.impl.TeacherServiceImpl.deleteHomework(..))")
    private void deleteHomework(){}

    @Pointcut("execution(* com.kle.code.service.impl.TeacherServiceImpl.deleteStudent(..))")
    private void deleteStudent(){}

    //pointcut combine

    @Pointcut("addHomework() || deleteHomework() || deleteStudent()")
    private void service(){}

    /**
     * 事物开始，关闭自动提交
     */
    @Before("service()")
    public void beginTransaction(){
        try {
            Connection connection = connectionUtils.getThreadConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    @Around("service()")
//    public JSONObject handleException(ProceedingJoinPoint pjp){
//        JSONObject jsonObject = new JSONObject();
//        try {
//            pjp.proceed();
//            jsonObject.put("status", "success");
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//            System.out.println("throwing...");
//            jsonObject.put("status", "fail");
//            Connection connection = connectionUtils.getThreadConnection();
//            try {
//                connection.rollback();
//                connection.close();//将连接放回连接池
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            connectionUtils.removeConnection();//解绑
//        }
//        return jsonObject;
//    }

    /**
     * 提交事务，关闭数据库连接，解绑当前线程与数据库的连接
     */
    @AfterReturning("service()")
    public void commit(){
        try {
            System.out.println("committing...");
            Connection connection = connectionUtils.getThreadConnection();
            connection.commit();
            connection.close();//将连接放回连接池
            connectionUtils.removeConnection();//解绑
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 回滚事务，关闭数据库连接，解绑当前线程与数据库的连接
     */
    @AfterThrowing("service()")
    public void rollback(){
        try {
            System.out.println("throwing...");
            Connection connection = connectionUtils.getThreadConnection();
            connection.rollback();
            connection.close();//将连接放回连接池
            connectionUtils.removeConnection();//解绑
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 事务被执行，做个记录
     */
    @After("service()")
    public void adviceMethodInvoked() {
        System.out.println("service method invoked");
    }

}