package com.kle.code.db;

import com.kle.code.model.Teacher;
import com.kle.code.util.SpringContextUtil;

import java.sql.*;
import java.util.Date;

public class TeacherDb {

    private DatabasePool databasePool;

    public TeacherDb(){
        databasePool = (DatabasePool) SpringContextUtil.getApplicationContext().getBean("databasePool");
    }

    public Teacher teacherLogin(String tid, String password) {
        String sqlString = "SELECT * FROM teacher WHERE tid=" + tid;
        ResultSet resultSet = null;
        Statement statement = null;
        try(Connection connection = databasePool.getHikariDataSource().getConnection()){
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()){
                if(resultSet.getString("password").equals(password)){
                    Teacher teacher = (Teacher) SpringContextUtil.getApplicationContext().getBean("teacher");
                    teacher.setTid(Integer.parseInt(tid));
                    teacher.setName(resultSet.getString("name"));
                    teacher.setPassword(password);
                    teacher.setCreateTime(resultSet.getTimestamp("create_time"));
                    teacher.setUpdateTime(resultSet.getTimestamp("update_time"));
                    return teacher;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Teacher selectTeacherById(String tid) {
        ResultSet resultSet = null;
        Statement statement = null;
        String sqlString = "SELECT * FROM teacher WHERE tid=" + tid;
        try(Connection connection = databasePool.getHikariDataSource().getConnection()){
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
        try(Connection connection = databasePool.getHikariDataSource().getConnection()){
            statement = connection.createStatement();
            String insertSqlString = "";
            insertSqlString = "INSERT INTO teacher (name , password, create_time, update_time ) VALUES ('" +
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
