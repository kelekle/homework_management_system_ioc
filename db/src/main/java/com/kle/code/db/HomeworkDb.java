package com.kle.code.db;

import com.kle.code.model.Homework;
import com.kle.code.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 作业相关数据库操作类
 * @author ypb
 */
@Component
public class HomeworkDb {

//    private final DatabasePool databasePool;

    private final ConnectionUtils connectionUtils;

    @Autowired
    public HomeworkDb(ConnectionUtils connectionUtils){
//        databasePool = (DatabasePool) SpringContextUtil.getApplicationContext().getBean("databasePool");
//        this.databasePool = databasePool;
        this.connectionUtils = connectionUtils;
    }

    public Homework selectHomeworkById(String hid) {
        String sqlString = "SELECT * FROM homework WHERE hid=" + hid;
        ResultSet resultSet = null;
        Statement statement = null;
        try{
            Connection connection = connectionUtils.getThreadConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlString);
            Homework homework = (Homework) SpringContextUtil.getApplicationContext().getBean("homework");
            while (resultSet.next()) {
                homework.setHid(Integer.parseInt(hid));
                homework.setTitle(resultSet.getString("title"));
                homework.setContent(resultSet.getString("content"));
                homework.setCreateTime(resultSet.getTimestamp("create_time"));
                homework.setUpdateTime(resultSet.getTimestamp("update_time"));
            }
            return homework;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Homework addHomework(String tid, String title, String content, Date createTime, Date updateTime) throws SQLException{
        String sqlString = "INSERT INTO homework (`tid`, `title`, `content`, `create_time`, `update_time`) " +
                "VALUES ('" + tid + "', '" + title + "', '" + content + "', '" + new Timestamp(createTime.getTime()) +
                "', '" + new Timestamp(updateTime.getTime()) + "');";
        ResultSet resultSet = null;
        Statement statement = null;
        Connection connection = connectionUtils.getThreadConnection();
        statement = connection.createStatement();
        statement.executeUpdate(sqlString, Statement.RETURN_GENERATED_KEYS);
        resultSet = statement.getGeneratedKeys();
        int hid = -1;
        while (resultSet.next()){
            hid = resultSet.getInt(1);
        }
        Homework homework = (Homework) SpringContextUtil.getApplicationContext().getBean("homework");
        homework.setHid(hid);
        homework.setTitle(title);
        homework.setContent(content);
        homework.setCreateTime(createTime);
        homework.setUpdateTime(updateTime);
        return homework;
    }

    public boolean updateHomework(String hid, String title, String content, Date date) {
        String sqlString = "UPDATE homework SET title='"+ title + "', content='" + content + "', update_time='" +
                new Timestamp(date.getTime()) + "' " + "WHERE hid=" + hid;
        return executeSqlString(sqlString, connectionUtils);
    }

    public boolean deleteHomework(String hid) throws SQLException {
        String sqlString = "DELETE FROM homework WHERE hid=" + hid;
        Statement statement = null;
        Connection connection = connectionUtils.getThreadConnection();
        statement = connection.createStatement();
        return statement.executeUpdate(sqlString) > 0;
    }

    public List<Homework> getHomeworkOfTeacher(String tid) {
        String sqlString = "SELECT * FROM homework WHERE tid=" + tid;
        List<Homework> list = new ArrayList<>();
        ResultSet resultSet = null;
        Statement statement = null;
        try{
            Connection connection = connectionUtils.getThreadConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()){
                Homework sh = (Homework) SpringContextUtil.getApplicationContext().getBean("homework");
                sh.setHid(resultSet.getInt("hid"));
                sh.setTitle(resultSet.getString("title"));
                sh.setContent(resultSet.getString("content"));
                sh.setCreateTime(resultSet.getTimestamp("create_time"));
                sh.setUpdateTime(resultSet.getTimestamp("update_time"));
                list.add(sh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean executeSqlString(String sqlString, ConnectionUtils connectionUtils) {
        try {
            Statement statement = null;
            Connection connection = connectionUtils.getThreadConnection();
            statement = connection.createStatement();
            return statement.executeUpdate(sqlString) > 0;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
