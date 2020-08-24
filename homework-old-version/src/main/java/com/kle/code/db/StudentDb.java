package com.kle.code.db;

import com.kle.code.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentDb {

    public Student studentLogin(String sid, String password) {
        String sqlString = "SELECT * FROM student WHERE sid=" + sid;
        Connection connection = DbUtil.getInstance().getConnection();
        ResultSet resultSet = null;
        Statement statement = null;
        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()){
                if(resultSet.getString("password").equals(password)){
                    Student student = new Student();
                    student.setSid(Integer.parseInt(sid));
                    student.setName(resultSet.getString("name"));
                    student.setPassword(password);
                    student.setCreateTime(resultSet.getTimestamp("create_time"));
                    student.setUpdateTime(resultSet.getTimestamp("update_time"));
                    return student;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DbUtil.getInstance().close(connection, statement, resultSet);
        }
        return null;
    }

    public Boolean addStudent(String tid, String name, String password, Date createTime, Date updateTime) {
        Connection connection = DbUtil.getInstance().getConnection();
        Statement statement = null;
        String addSqlString = "INSERT INTO student (`name`, `password`, `create_time`, `update_time`) " +
                "VALUES ('" + name + "', '" + password + "', '" + new Timestamp(createTime.getTime()) +
                "', '" + new Timestamp(updateTime.getTime()) + "')";
        ResultSet resultSet = null;
        try{
            statement = connection.createStatement();
            statement.executeUpdate(addSqlString, Statement.RETURN_GENERATED_KEYS);
            resultSet = statement.getGeneratedKeys();
            int sid = -1;
            while (resultSet.next()){
                sid = resultSet.getInt(1);
            }
            if(sid > 0){
                String teachSqlString = "INSERT INTO teach (`sid`, `tid`, `create_time`) " +
                        "VALUES ('" + sid + "', '" + tid + "', '" + new Timestamp(updateTime.getTime()) + "');";
                return statement.executeUpdate(teachSqlString) > 0;
            }else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DbUtil.getInstance().close(connection, statement, resultSet);
        }
        return false;
    }

    public Boolean deleteStudent(String sid) {
        Connection connection = DbUtil.getInstance().getConnection();
        Statement statement = null;
        String sqlString = "DELETE FROM student WHERE sid=" + sid;
        try{
            statement = connection.createStatement();
            return statement.executeUpdate(sqlString) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DbUtil.getInstance().close(connection, statement);
        }
        return false;
    }

    public Student selectStudentById(String sid) {
        Connection connection = DbUtil.getInstance().getConnection();
        ResultSet resultSet = null;
        Statement statement = null;
        String sqlString = "SELECT * FROM student WHERE sid=" + sid;
        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlString);
            Student student = new Student();
            while (resultSet.next()){
                student.setSid(Integer.parseInt(sid));
                student.setName(resultSet.getString("name"));
                student.setPassword(resultSet.getString("password"));
                student.setCreateTime(resultSet.getTimestamp("create_time"));
                student.setUpdateTime(resultSet.getTimestamp("update_time"));
            }
            return student;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DbUtil.getInstance().close(connection, statement, resultSet);
        }
        return null;
    }

    public List<Student> getStudentOfTeacher(String tid) {
        String sqlString = "SELECT * FROM student, teach WHERE student.sid=teach.sid AND teach.tid=" + tid;
        List<Student> list = new ArrayList<>();
        Connection connection = DbUtil.getInstance().getConnection();
        ResultSet resultSet = null;
        Statement statement = null;
        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()){
                Student sh = new Student();
                sh.setSid(resultSet.getInt("sid"));
                sh.setName(resultSet.getString("name"));
                sh.setPassword(resultSet.getString("password"));
                sh.setCreateTime(resultSet.getTimestamp("create_time"));
                sh.setUpdateTime(resultSet.getTimestamp("update_time"));
                list.add(sh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DbUtil.getInstance().close(connection, statement, resultSet);
        }
        return list;
    }

    public boolean updateStudent(String sid, String name, String password, Date date) {
        Connection connection = DbUtil.getInstance().getConnection();
        Statement statement = null;
        String sqlString = "UPDATE student SET name='"+ name + "', password='" + password + "', update_time='" +
                new Timestamp(date.getTime()) + "' " + "WHERE sid=" + sid;
        try{
            statement = connection.createStatement();
            return statement.executeUpdate(sqlString) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DbUtil.getInstance().close(connection, statement);
        }
        return false;
    }

}
