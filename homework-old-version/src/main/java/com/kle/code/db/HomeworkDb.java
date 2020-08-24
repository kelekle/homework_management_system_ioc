package com.kle.code.db;

import com.kle.code.model.Homework;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeworkDb {

    public Homework selectHomeworkById(String hid) {
        Connection connection = DbUtil.getInstance().getConnection();
        ResultSet resultSet = null;
        Statement statement = null;
        String sqlString = "SELECT * FROM homework WHERE tid=" + hid;
        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlString);
            Homework student = new Homework();
            while (resultSet.next()) {
                student.setHid(Integer.parseInt(hid));
                student.setTitle(resultSet.getString("title"));
                student.setContent(resultSet.getString("content"));
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

    public Homework addHomework(String tid, String title, String content, Date createTime, Date updateTime) {
        String sqlString = "INSERT INTO homework (`tid`, `title`, `content`, `create_time`, `update_time`) " +
                "VALUES ('" + tid + "', '" + title + "', '" + content + "', '" + new Timestamp(createTime.getTime()) +
                "', '" + new Timestamp(updateTime.getTime()) + "');";
        Connection connection = DbUtil.getInstance().getConnection();
        ResultSet resultSet = null;
        Statement statement = null;
        try{
            statement = connection.createStatement();
            statement.executeUpdate(sqlString, Statement.RETURN_GENERATED_KEYS);
            resultSet = statement.getGeneratedKeys();
            int hid = -1;
            while (resultSet.next()){
                hid = resultSet.getInt(1);
            }
            Homework homework = new Homework();
            homework.setHid(hid);
            homework.setTitle(title);
            homework.setContent(content);
            homework.setCreateTime(createTime);
            homework.setUpdateTime(updateTime);
            return homework;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DbUtil.getInstance().close(connection, statement, resultSet);
        }
        return null;
    }

    public boolean updateHomework(String hid, String title, String content, Date date) {
        Connection connection = DbUtil.getInstance().getConnection();
        Statement statement = null;
        String sqlString = "UPDATE homework SET title='"+ title + "', content='" + content + "', update_time='" +
                new Timestamp(date.getTime()) + "' " + "WHERE hid=" + hid;
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

    public Boolean deleteHomework(String hid) {
        String sqlString = "DELETE FROM homework WHERE hid=" + hid;
        Connection connection = DbUtil.getInstance().getConnection();
        Statement statement = null;
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

    public List<Homework> getHomeworkOfTeacher(String tid) {
        String sqlString = "SELECT * FROM homework WHERE tid=" + tid;
        List<Homework> list = new ArrayList<>();
        Connection connection = DbUtil.getInstance().getConnection();
        ResultSet resultSet = null;
        Statement statement = null;
        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()){
                Homework sh = new Homework();
                sh.setHid(resultSet.getInt("hid"));
                sh.setTitle(resultSet.getString("title"));
                sh.setContent(resultSet.getString("content"));
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

}
