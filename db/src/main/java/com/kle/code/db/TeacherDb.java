package com.kle.code.db;

import com.kle.code.model.Teacher;
import com.kle.code.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Date;

/**
 * 老师相关数据库操作类
 * @author ypb
 */
@Component
public class TeacherDb {

    private final ConnectionUtils connectionUtils;

//    private final DatabasePool databasePool;

    @Autowired
    public TeacherDb(ConnectionUtils connectionUtils){
//        databasePool = (DatabasePool) SpringContextUtil.getApplicationContext().getBean("databasePool");
//        this.databasePool = databasePool;
        this.connectionUtils = connectionUtils;
    }

    public Teacher selectTeacherById(String tid) {
        ResultSet resultSet = null;
        Statement statement = null;
        String sqlString = "SELECT * FROM teacher WHERE tid=" + tid;
        try{
            Connection connection = connectionUtils.getThreadConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlString);
            Teacher student = (Teacher) SpringContextUtil.getApplicationContext().getBean("teacher");
            while (resultSet.next()) {
                student.setTid(Integer.parseInt(tid));
                student.setName(resultSet.getString("name"));
                student.setPassword(resultSet.getString("password"));
                student.setCreateTime(resultSet.getTimestamp("create_time"));
                student.setUpdateTime(resultSet.getTimestamp("update_time"));
            }
            return student;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int addTeacher(String name, String password, Date date) {
        Statement statement = null;
        ResultSet resultSet = null;
        int tid = -1;
        try{
            Connection connection = connectionUtils.getThreadConnection();
            statement = connection.createStatement();
            String insertSqlString = "INSERT INTO teacher (name , password, create_time, update_time ) VALUES ('" +
                    name + "', '" + password + "', '" + new Timestamp(date.getTime()) + "', '" +
                    new Timestamp(date.getTime()) + "')";
            int i = statement.executeUpdate(insertSqlString, Statement.RETURN_GENERATED_KEYS);
            resultSet = statement.getGeneratedKeys();
            while (resultSet.next()){
                tid = resultSet.getInt(1);
            }
            return tid;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tid;
    }

}
